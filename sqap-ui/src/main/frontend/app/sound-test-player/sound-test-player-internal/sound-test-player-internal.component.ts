import {Component, OnInit} from '@angular/core';
import {Headers, Http} from "@angular/http";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../shared/auth/auth.service";
import {SoundTest} from "../../sound-test-manager/domain/SoundTest";
import {SoundTestGroup} from "../../sound-test-manager/domain/SoundTestGroup";
import {TestGroupService} from "../../shared/test/group/test-group.service";
import {IteratorImpl} from "../../shared/data/IteratorImpl";
import {FilesDownloadService} from "../../shared/files/files-download.service";
import {AsyncIteratorResult} from "../../shared/data/AsyncIteratorResult";
import {Subject} from "rxjs";
import {TestService} from "../../shared/test/single/test.service";
import {UserService} from "../../shared/user/user-service";


@Component({
    selector: 'sound-test-player-internal',
    templateUrl: './sound-test-player-internal.component.html',
    styleUrls: ['./sound-test-player-internal.component.scss']
})
export class SoundTestPlayerInternalComponent implements OnInit {

    private precedingQuestions;
    private testGroup: any;
    private tests: any[];
    private testsIterator: IteratorImpl;
    private isPlayerActivated = false;

    private currentTest: AsyncIteratorResult<any>;
    private nextCachedTest: AsyncIteratorResult<any>;
    private participantStatementAccepted: boolean = false;

    commandEmmiter: Subject<string> = new Subject();

    constructor(private route: ActivatedRoute, private testGroupService: TestGroupService,
                private fileDownloadService: FilesDownloadService, private router: Router, private testService: TestService, private userService: UserService) {
        this.precedingQuestions = {
            isHearingDefect: null,
            sex: null,
            age: null
        };
    }

    ngOnInit() {
        let id = this.route.snapshot.params['id'];

        if (this.userService.isAuthenticated()) {
            this.userService.getPersonalData().subscribe(data => {
                this.precedingQuestions = data;
                this.precedingQuestions.isHearingDefect = this.precedingQuestions.isHearingDefect.toString();
                console.log(this.precedingQuestions);
            });
        }

        this.testGroupService.getOne(id)
            .subscribe(data => {
                    this.testGroup = data;
                    this.testGroupService.getTests(this.testGroup._links.tests.href).subscribe(data => {
                        this.tests = data;
                        this.testsIterator = new IteratorImpl(this.tests.sort((n1, n2) => n1.orderNumber - n2.orderNumber));
                    });
                },
                err => {
                    console.log("ERR");
                }
            );
    }

    initPlayer() {
        console.log("Init player");
        if (this.testGroup != null && this.tests != null) {
            this.nextTest();
            this.isPlayerActivated = true;
        }
    }

    nextTest() {
        if (this.currentTest != null) {
            if (!this.currentTest.done) {
                this.currentTest = this.nextCachedTest;
                if (this.currentTest.value) {
                    this.currentTest.value.currentIteration = 1;
                }
                if (!this.currentTest.done) {
                    this.nextCachedTest = this.testsIterator.next();
                    this.fetchSamples(this.nextCachedTest);
                }
            }
        }
        else {
                this.currentTest = this.testsIterator.next();
                this.currentTest.value.currentIteration = 1;
                this.fetchSamples(this.currentTest);
                this.nextCachedTest = this.testsIterator.next();
                this.fetchSamples(this.nextCachedTest);
        }
    }

    fetchSamples(test: AsyncIteratorResult<any>) {
        if (!test.done) {
            this.testGroupService.get(test.value._links.samples.href).subscribe(
                data => {
                    test.value.samples = [];
                    let obsArray = this.fileDownloadService.getSampleFiles(data._embedded.testSamples);
                    let mergedStream = obsArray.reduce((merged, current) => merged.merge(current));
                    mergedStream.subscribe(res => {
                            console.log(res);
                            (test.value.samples as any[]).push(res);
                            if (obsArray.length === (test.value.samples as any[]).length) {
                                test.isReady = true;
                            }
                        },
                        err => {
                            console.log(err);
                        }
                    );
                }
            );
        }

    }

    onTestFinish(results: any) {
        if (this.currentTest.value.currentIteration < this.currentTest.value.repetitionsNumber) {
            console.log("Next iteration");
            this.commandEmmiter.next("iteration");
            this.currentTest.value.currentIteration++;
        }
        else {
            console.log("Next test")
            console.log(results);
            this.sendResults(results);
        }
    }

    private sendResults(results) {
        results.precedingQuestions = this.precedingQuestions;
        this.testService.addResult(this.currentTest.value._links.addResult.href, results).subscribe(
            data => {
                console.log(data);
                this.nextTest();
            },
            err => {
                console.log(err);
            }
        );
    }

    participantStatementAcceptationHandler(value: boolean) {
        this.participantStatementAccepted = value;
    }

    exit() {
        this.router.navigate(['']);
    }

    isPrecedingQuestionsFilled(): boolean {
        return this.precedingQuestions.isHearingDefect && this.precedingQuestions.sex && this.precedingQuestions.age;
    }
}
