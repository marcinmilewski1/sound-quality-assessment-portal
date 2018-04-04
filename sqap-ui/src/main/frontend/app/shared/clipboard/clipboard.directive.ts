import {Directive,ElementRef,Input,Output,EventEmitter} from '@angular/core';

declare var Clipboard: any;

@Directive({
  selector: '[clipboard]'
})
export class ClipboardDirective {
  clipboard: any;
  
  @Input('clipboard')
  elt:ElementRef;
  
  @Output()
  clipboardSuccess:EventEmitter<any> = new EventEmitter();
  
  @Output()
  clipboardError:EventEmitter<any> = new EventEmitter();
  
  constructor(private eltRef:ElementRef) {
  }
  
  ngOnInit() {
    this.clipboard = new Clipboard(this.eltRef.nativeElement, {
      target: () => {
        return this.elt;
      }
    });

    this.clipboard.on('success', (e) => {
      this.clipboardSuccess.emit();
    });

    this.clipboard.on('error', (e) => {
      this.clipboardError.emit();
    });
  }
  
  ngOnDestroy() {
    if (this.clipboard) {
      this.clipboard.destroy();
    }
  }
}