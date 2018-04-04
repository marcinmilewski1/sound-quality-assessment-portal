import { Component, OnInit } from '@angular/core';
import {GlobalRulesService, GlobalRules} from "../shared/rules/global-rules.service";

@Component({
  selector: 'terms',
  templateUrl: './terms.component.html',
  styleUrls: ['./terms.component.scss']
})
export class TermsComponent implements OnInit {
  private globalRules: GlobalRules;

  constructor(private globalRulesService: GlobalRulesService) { }

  ngOnInit() {
    this.globalRulesService.fetchGlobalRules().subscribe(data => this.globalRules = data);
  }

}
