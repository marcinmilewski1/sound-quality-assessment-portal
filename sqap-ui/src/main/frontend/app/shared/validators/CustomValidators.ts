import {FormControl, AbstractControl} from '@angular/forms';


function isEmptyInputValue(value: any) {
  return value == null || typeof value === 'string' && value.length === 0;
}

export class CustomValidators {
  static fileInputRequired(c: FormControl) {
    return (isEmptyInputValue(c.value)) ? {
      fileInputRequired: {
        valid: false
      }
    } : null;
  }

  static requiredNumber(control: AbstractControl): {[key: string]: boolean} {
    return (isNaN(control.value) || (control.value == null)) ?
        {'required': true} :
        null;
  }

  static requiredPositiveNumber(control: AbstractControl): {[key: string]: boolean} {
    return (isNaN(control.value) || (control.value == null) || (control.value != null && control.value <= 0)) ?
        {'required': true} :
        null;
  }
}


