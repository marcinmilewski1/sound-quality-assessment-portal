import {FormGroup, AbstractControl, FormArray, FormControl} from "@angular/forms";
export class ControlUtils {
  static addControls(formGroup: FormGroup, controls) {
    controls.forEach((control) => {
      formGroup.addControl(control.key, control.value);
    });
  }

  static addFormGroupToFormArrayAt(formArray: FormArray, index: number, formGroup: FormGroup) {
    if (formArray.length > index) {
      formArray.insert(index, formGroup);
    }
    else throw new Error("index too big");
  }

  // returns index
  static addFormGroupToFormArray(formArray: FormArray, formGroup: FormGroup) : number{
    formArray.push(formGroup);
    return formArray.length -1;
  }

  static removeFromFormArrayAt(formArray: FormArray, index: number) {
    // if (formArray.length > index) {
      formArray.removeAt(index);
    // }
    // else throw new Error("index too big");
  }

  static removeAllFromFormArray(formArray: FormArray) {
    for (let i = 0; i < formArray.length; i++) {
      formArray.removeAt(i);
    }
  }

  static removeControls(formGroup: FormGroup, controls) {
    controls.forEach((control) => {
      formGroup.removeControl(control.key);
    });
  }

  static markAllDirty(control: AbstractControl) {
      if(control.hasOwnProperty('controls')) {
          control.markAsDirty(true) // mark group
          let ctrl = <any>control;
          for (let inner in ctrl.controls) {
              this.markAllDirty(ctrl.controls[inner] as AbstractControl);
          }
      }
      else {
          (<FormControl>(control)).markAsDirty(true);
      }
  }

  static getFormData(formGroup: FormGroup) :FormData {
    let formData = new FormData();
    for (let controlName in formGroup.controls) {
      let control : AbstractControl = formGroup.controls[controlName];

      if (control.hasOwnProperty("push")) {
        // formArray
      }
      else if (control.hasOwnProperty("controls")) {
        // form
      }
      else {
        if ((<FileList>control.value).item) {
          let fileList: FileList = control.value;
          for (let i = 0; i < fileList.length; i++) {
            let file = fileList.item(i);
            formData.append(controlName, file);
            console.log(`added file, name ${file.name} with control ${controlName}`);
          }
        }
        else {
          formData.append(controlName, control.value);
        }
      }
    }
    return formData;
  }
}
