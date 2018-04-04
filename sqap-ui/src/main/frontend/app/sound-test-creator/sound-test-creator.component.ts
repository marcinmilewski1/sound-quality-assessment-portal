import {
    Component, OnInit, AfterViewInit, ViewChildren, QueryList, ContentChildren,
    ViewContainerRef, EventEmitter
} from '@angular/core';
import {
    FormGroup, FormBuilder, Validators,
    FormArray
} from "@angular/forms";
import {ControlUtils} from "../shared/utils/ControlUtils";
import {SingleTestComponent} from "./single-test/single-test.component";
import {SimpleDescriptor} from "../shared/data/SimpleDescriptor";
import {NotificationsService} from "angular2-notifications";
import {TestGroupService} from "../shared/test/group/test-group.service";
import {Router} from "@angular/router";

@Component({
    selector: 'sqap-sound-test-creator',
    templateUrl: './sound-test-creator.component.html',
    styleUrls: ['./sound-test-creator.component.scss'],
    providers: [FormBuilder]
})
export class SoundTestCreatorComponent implements OnInit {
    readonly URL = 'api/groups';

    testGroupForm: FormGroup;
    testMethods = [
        {value: 'ABX', display: 'ABX'},
        {value: 'MUSHRA', display: 'MUSHRA'}
    ];

    private formalitiesSubmitted = false;
    private tests = [new SimpleDescriptor(1)];
    private submitEventEmmiter = new EventEmitter<boolean>();

    @ViewChildren(SingleTestComponent)
    singleTestComponents: QueryList<SingleTestComponent>;

    private response;
    private tabIndex = 0;

    constructor(private fb: FormBuilder, private notificationService: NotificationsService, private testGroupService: TestGroupService, private router: Router) {

    }

    getFormData() {
        return this.testGroupForm.value;
    }

    addAnotherTest(event: Event) {
        this.tests.sort((n1, n2) => n1.id - n2.id);
        let lastId = this.tests.length;
        this.tests.push(new SimpleDescriptor(lastId + 1));
        this.singleTestComponents.filter((singleTestComponent => singleTestComponent.id !== lastId + 1))
            .map((c => {
                c.minimize();
            }));
    }

    onSingleTestDelete(id: number) {
        console.log(id);
        this.tests = this.tests.filter((val) => val.id !== id);
        this.tests.filter((element => element.id > id)).forEach((filtered) => filtered.id = filtered.id - 1);
    }

    ngOnInit(): void {
        this.testGroupForm = this.fb.group({
            name: ["", Validators.required],
            description: [""],
            tests: this.fb.group({
                abxTests: this.fb.array([]),
                mushraTests: this.fb.array([])
            }),
        });

    }

    shouldHaveTest(abxTextLength: number, mushraTestsLength: number) {
        if (abxTextLength > 0 || mushraTestsLength > 0) return null;
        else {
            return {
                haveTests: {
                    valid: false
                }
            }
        }
    }

    getFormArray(testType: string): FormArray {
        if (testType === 'ABX') return this.testGroupForm.get('abxTests') as FormArray;
        else if (testType === 'MUSHRA') return this.testGroupForm.get('mushraTests') as FormArray;
        else throw Error('Incorrect test type');
    }

    onSubmit(creationForm: FormGroup) {
        this.submitEventEmmiter.emit(true);
        if (creationForm.valid) {
            console.log('Test creation form submitted');
            console.log(creationForm.value);

            // const formData: FormData = ControlUtils.getFormData(this.testGroupForm);
            const formData: FormData = this.testGroupForm.value;

            this.testGroupService.create(formData)
                .subscribe(
                    data => {
                        let response = data.json();
                        // this.notificationService.success("Successful", "Test group added");
                        this.router.navigate(['test-creator/successful/', response.id],{skipLocationChange: false});
                    },
                    err => {
                        console.log('Something went wrong')
                    }
                );
        }
        else {
            ControlUtils.markAllDirty(creationForm);
        }
    }

    formalitiesFormSubmitHandler() {
        this.tabIndex = 1;
    }
}

