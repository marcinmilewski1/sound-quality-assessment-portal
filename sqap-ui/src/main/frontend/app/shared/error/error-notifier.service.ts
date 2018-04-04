import {Injectable} from '@angular/core';
import {Observable, Observer} from "rxjs";

@Injectable()
export class ErrorNotifierService {

    private errorObservable: Observable<any>;
    private errorObserver: Observer<any>;

    constructor() {
        this.errorObservable = Observable.create((observer: Observer<any>) => {
            this.errorObserver = observer;
        }).share();
    }

    notifyError(error: any) {
        this.errorObserver.next(error);
    }

    onError(callback: (err: any) => void) {
        this.errorObservable.subscribe(callback);
    }
}
