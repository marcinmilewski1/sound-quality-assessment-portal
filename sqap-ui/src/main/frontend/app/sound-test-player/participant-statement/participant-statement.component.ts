import {Component, OnInit, Output, EventEmitter, Input} from '@angular/core';
import {GlobalRulesService, GlobalRules} from "../../shared/rules/global-rules.service";

@Component({
    selector: 'participant-statement',
    templateUrl: './participant-statement.component.html',
    styleUrls: ['./participant-statement.component.scss']
})
export class ParticipantStatementComponent implements OnInit {

    @Input()
    private toParticipantAccept;

    @Output()
    private acceptedEvent = new EventEmitter<boolean>();

    private globalRules: GlobalRules;

    constructor(private rulesService: GlobalRulesService) {

    }

    ngOnInit() {
        this.rulesService.fetchGlobalRules().subscribe(data => this.globalRules = data);
    }

    checkboxChangeEventHandler(value: boolean) {
        this.acceptedEvent.emit(value);
    }
}
