import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';

@Component({
  selector: 'sample-slider',
  templateUrl: './sample-slider.component.html',
  styleUrls: ['./sample-slider.component.scss']
})
export class SampleSliderComponent implements OnInit {

  private _isDisabled = true;

  @Input()
  sampleKey: string;

  @Input()
  buttonDisabled: boolean;

  @Output()
  onChangeEvent = new EventEmitter<ChangeEvent>();

  @Output()
  onButtonClick = new EventEmitter<string>();

  constructor() { }

  ngOnInit() {
  }

  onChange(event: any) {
    this.onChangeEvent.emit({key: this.sampleKey, value: event.value});
  }

  buttonClick() {
    this.onButtonClick.emit(this.sampleKey);
  }


  get isDisabled(): boolean {
    return this._isDisabled;
  }

  setDisabled(val: boolean) {
    this._isDisabled = val;
  }
}

export interface ChangeEvent {
  key: string;
  value: number;
}
