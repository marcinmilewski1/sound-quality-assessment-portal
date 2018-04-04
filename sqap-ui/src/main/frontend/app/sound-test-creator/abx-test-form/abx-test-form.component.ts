import {Component, OnInit, Input, OnDestroy, OnChanges, SimpleChanges} from '@angular/core';
import {FormGroup, FormControl, Validators, FormArray, FormBuilder} from "@angular/forms";
import {ControlUtils} from "../../shared/utils/ControlUtils";
import {SimpleEvent} from "../../shared/SimpleEvent";
import {TestSample} from "../../shared/data/TestSample";
import {CustomValidators} from "../../shared/validators/CustomValidators";

@Component({
    selector: 'abx-test-form',
    templateUrl: './abx-test-form.component.html',
    styleUrls: ['./abx-test-form.component.scss'],
    providers: [FormBuilder]
})
export class AbxTestFormComponent implements OnInit, OnDestroy, OnChanges {
    @Input()
    formArray: FormArray;

    @Input()
    orderNo: number;

    form: FormGroup;
    event: any;
    private index; // index of this form in formArray

    constructor(private fb: FormBuilder) {
    }

    private samples: FormArray;

    ngOnInit() {
        this.form = this.fb.group({
            name: ["", Validators.required],
            description: ["", Validators.required],
            orderNumber: [this.orderNo, Validators.required],
            repetitionNumber: [1, CustomValidators.requiredPositiveNumber],
            samples: this.fb.array([TestSample.getFormGroup("A"), TestSample.getFormGroup("B")], Validators.required)
        });
        this.samples = this.form.get('samples') as FormArray;
        this.index = this.orderNo - 1;
        this.formArray.insert(this.index, this.form);
    }

    ngOnDestroy(): void {
        console.log("destroy: " + this.index);
        ControlUtils.removeFromFormArrayAt(this.formArray, this.index);
    }

    onFileUploadSuccessful(event: SimpleEvent) {
        this.event = event;
        let sampleFormGroups = this.samples.controls as FormGroup[];
        sampleFormGroups.find((e => e.get('key').value === event.id)).get('fileDesc').setValue(event.data);
    }

    onFileUploadError(event: SimpleEvent) {
        this.event = event;
        let sampleFormGroups = this.samples.controls as FormGroup[];
        sampleFormGroups.find((e => e.get('key').value === event.id)).get('fileDesc').setErrors({
            "uploadError": true
        });
        sampleFormGroups.find((e => e.get('key').value === event.id)).get('fileDesc').markAsDirty();
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes['orderNo'] != null && this.form != null)  {
             this.form.get('orderNumber').patchValue(changes['orderNo'].currentValue);
            this.index = changes['orderNo'].currentValue - 1; // important for removing
        }
    }
}
