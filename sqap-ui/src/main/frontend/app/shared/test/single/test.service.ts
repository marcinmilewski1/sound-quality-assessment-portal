import { Injectable } from '@angular/core';
import {AuthService} from "../../auth/auth.service";
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs";
import {UrlUtils} from "../../utils/UrlUtils";

@Injectable()
export class TestService {

  constructor(private authService: AuthService, private http: Http) { }

  public addResult(href: string, result: any) :Observable<Response>{
    let URL = UrlUtils.removePrefix(href);
    return this.http.post(URL, result, {headers: this.authService.getAuthorizationHeaders()}).map(result => result.json());
  }

  public getResults(href: string) :Observable<any>{
    let URL = UrlUtils.removePrefix(href);
    return this.http.get(URL, {headers: this.authService.getAuthorizationHeaders()}).map(result => result.json());
  }
}
