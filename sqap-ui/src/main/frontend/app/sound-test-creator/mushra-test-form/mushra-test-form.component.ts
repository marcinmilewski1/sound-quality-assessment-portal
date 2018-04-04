import {Component, OnInit, Input, OnDestroy, OnChanges, SimpleChanges} from '@angular/core';
import {FormArray, Validators, FormGroup, FormBuilder, FormControl} from "@angular/forms";
import {TestSample} from "../../shared/data/TestSample";
import {ControlUtils} from "../../shared/utils/ControlUtils";
import {CharacterUtils} from "../../shared/utils/CharacterUtils";
import {SimpleEvent} from "../../shared/SimpleEvent";
import {CustomValidators} from "../../shared/validators/CustomValidators";

@Component({
    selector: 'mushra-test-form',
    templateUrl: './mushra-test-form.component.html',
    styleUrls: ['./mushra-test-form.component.scss']
})
export class MushraTestFormComponent implements OnInit, OnDestroy, OnChanges {

    @Input()
    orderNo: number;

    @Input()
    formArray: FormArray;

    form: FormGroup;
    samplesNumberOptions = [4, 5, 6, 7, 8, 9, 10, 11, 12];
    index: number;
    samples: FormArray; // index of form in formArray
    event: any;

    constructor(private fb: FormBuilder) {
    }

    selectSamplesNumber(no: number) {
        ControlUtils.removeAllFromFormArray(this.samples); // angular bug ?
        let startChar = 'A';
        for (let i = 0; i < no; i++) {
            this.samples.setControl(i,TestSample.getFormGroup(startChar));
            startChar = CharacterUtils.nextChar(startChar);
        }
    }

    ngOnInit() {
        this.form = this.fb.group({
            name: ["", Validators.required],
            description: ["", Validators.required],
            orderNumber: [this.orderNo, Validators.required],
            repetitionNumber: [1, CustomValidators.requiredPositiveNumber],
            samples: this.fb.array([TestSample.getFormGroup("A"), TestSample.getFormGroup("B"),
                TestSample.getFormGroup("C")]),
        });
        this.samples = this.form.get('samples') as FormArray;
        this.index = this.orderNo - 1;
        this.formArray.insert(this.index, this.form);
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

    ngOnDestroy(): void {
        ControlUtils.removeFromFormArrayAt(this.formArray, this.index);
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes['orderNo'] != null && this.form != null)  {
            this.form.get('orderNumber').patchValue(changes['orderNo'].currentValue);
            this.index = changes['orderNo'].currentValue - 1; // important for removing

        }
    }
}
