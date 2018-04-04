import {FormControl, FormGroup, Validators} from "@angular/forms";
export class TestSample {
    static getFormGroup(key:string) :FormGroup {
        return new FormGroup({
            "key": new FormControl(key, Validators.required),
            "fileDesc": new FormControl("", Validators.required)
        });
    }
}