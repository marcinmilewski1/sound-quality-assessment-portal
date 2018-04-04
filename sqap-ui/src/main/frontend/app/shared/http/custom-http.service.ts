import {Injectable} from '@angular/core';
import {Http, ConnectionBackend, RequestOptions, RequestOptionsArgs, Request, Response} from "@angular/http";
import {ErrorNotifierService} from "../error/error-notifier.service";
import {Observable} from "rxjs";
import {ErrorResolverService} from "../error/error-resolver.service";

@Injectable()
export class CustomHttpService extends Http {

    constructor(backend: ConnectionBackend,
                defaultOptions: RequestOptions,
                private errorService:ErrorNotifierService,
                private errorResolverService: ErrorResolverService) {
        super(backend, defaultOptions);
    }

    request(url: string | Request, options?: RequestOptionsArgs): Observable<any> {
        return super.request(url, options)
            .catch((err: Response): any => {
                this.errorService.notifyError(this.errorResolverService.resolve(err));
                return Observable.empty();
            })
            // .retryWhen(error => error.delay(500))
            // .timeout(10000, new Error('delay exceeded'))
    }

}
