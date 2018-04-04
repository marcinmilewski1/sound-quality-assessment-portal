import {Component, OnInit} from '@angular/core';
import {Http, Headers} from "@angular/http";
import {AuthService} from "../shared/auth/auth.service";
import {TestGroupGeneral} from "./domain/TestGroupGeneral";
import {TestGroupService} from "../shared/test/group/test-group.service";

@Component({
    selector: 'sqap-sound-test-manager',
    templateUrl: './sound-test-manager.component.html',
    styleUrls: ['./sound-test-manager.component.scss']
})
export class SoundTestManagerComponent implements OnInit {

    testGroups: any[];
    private hasTests: boolean = true;

    constructor(private testGroupService: TestGroupService) {}

    ngOnInit() {
        this.testGroupService.getOwn("general")
            .subscribe(data => {
                    if (data.hasOwnProperty('_embedded')) {
                        this.testGroups = data['_embedded']['testGroups'];
                    } else {
                        this.hasTests = false;
                    }
                },
                err => {
                    console.log("ERR");
                }
            );
    }

}
