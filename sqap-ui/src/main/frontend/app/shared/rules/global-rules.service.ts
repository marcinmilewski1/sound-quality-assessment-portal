import {Injectable} from '@angular/core';
import {Http} from "@angular/http";
import {Observable} from "rxjs";

@Injectable()
export class GlobalRulesService {

    readonly URL = "/api/rules";

    constructor(private http: Http) {
    }

    fetchGlobalRules(): Observable<GlobalRules> {
        // return this.http.get(this.URL).map(res => res.json());
        return this.http.get(this.URL).map(res => (res.json() as GlobalRules));
    }

}

export interface GlobalRules {
    creatorStatement: string;
    testerStatement: string;
    registrationStatement: string;
}