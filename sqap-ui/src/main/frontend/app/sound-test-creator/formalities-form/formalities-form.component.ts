import {Component, OnInit, Input, EventEmitter, Output} from '@angular/core';
import {FormGroup, FormControl, FormBuilder, Validators} from "@angular/forms";
import {GlobalRulesService, GlobalRules} from "../../shared/rules/global-rules.service";
import {CustomValidators} from "../../shared/validators/CustomValidators";

@Component({
  selector: 'formalities-form',
  templateUrl: './formalities-form.component.html',
  styleUrls: ['./formalities-form.component.scss'],
  providers: [FormBuilder]
})
export class FormalitiesFormComponent implements OnInit {

  @Input()
  private rootFormGroup: FormGroup;
  private localFormGroup: FormGroup;
  @Output()
  private submitEvent = new EventEmitter<any>();
  private submitTry = false;
  private globalRules: GlobalRules;

  constructor(private fb: FormBuilder, private globalRulesService: GlobalRulesService) {
    this.localFormGroup = this.fb.group({
      globalRulesForCreatorAccepted: [false, Validators.requiredTrue],
      allowAnonymouse: [false, Validators.required],
      purposeOfResearch: ["", Validators.required],
      durationTime: ["", CustomValidators.requiredPositiveNumber]
    });
  }

  ngOnInit() {
    if (this.rootFormGroup.contains("formalities")) {
      this.localFormGroup = this.rootFormGroup.get("formalities") as FormGroup;
    }
    else {
      this.rootFormGroup.addControl("formalities", this.localFormGroup);
    }
    this.globalRulesService.fetchGlobalRules().subscribe(data => {
      this.globalRules = data;
      console.log(data);
    });
  }

  submit() {
    this.submitTry = true;
    if (this.localFormGroup.valid) {
      this.submitEvent.emit();
    }
  }
}
