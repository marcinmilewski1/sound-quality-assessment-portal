import {Directive, forwardRef, Provider} from "@angular/core";
import {NG_VALIDATORS, NG_VALUE_ACCESSOR, Validator, ControlValueAccessor, FormControl} from "@angular/forms";


@Directive({
  selector: 'input[type=file][formControlName],input[type=file][formControl],input[type=file][ngModel]',
  host : {
    '(change)' : 'onChange($event.target.files)',
    '(blur)': 'onTouched()'
  },
  providers: [
    {provide: NG_VALUE_ACCESSOR, useExisting: FileControlValueAccessor, multi: true}
  ]
})

export class FileControlValueAccessor implements ControlValueAccessor {
  onChange = (_: any) => {};
  onTouched = () => {};

  writeValue(value: any): void {
  }

  registerOnChange(fn: () => any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => any): void {
    this.onTouched = fn;
  }
}
