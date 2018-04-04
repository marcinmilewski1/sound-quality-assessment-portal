import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {FormGroup, FormArray} from "@angular/forms";

@Component({
    selector: 'single-test',
    templateUrl: './single-test.component.html',
    styleUrls: ['./single-test.component.scss']
})
export class SingleTestComponent implements OnInit {

    @Input()
    private testGroup: FormGroup;

    @Input()
    id: number;

    @Input()
    private maximized: boolean;

    @Input() submitEvent: EventEmitter<boolean>;

    private submitted = false;
    @Output()
    onDeleteEvent = new EventEmitter<number>();

    private testMethods = [
        {value: 'ABX', display: 'ABX'},
        {value: 'MUSHRA', display: 'MUSHRA'}
    ];

    public getId() {
        return this.id;
    }

    minimize() {
        this.maximized = false;
    }

    maximize() {
        this.maximized = true;
    }

    isValid() {
        return this.testGroup.valid;
    }

    onDelete() {
        console.log("delete id: " + this.id);
        this.onDeleteEvent.emit(this.id);
    }

    onChange(event: any) {
    }

    constructor() {
    }

    ngOnInit() {
        this.submitEvent.subscribe(data => {
            this.submitted = data;
        })
    }

}
