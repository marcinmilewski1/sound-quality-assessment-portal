import { Injectable } from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {Http} from "@angular/http";
import {Observable} from "rxjs";

@Injectable()
export class UserService {

  constructor(private http: Http, private authService: AuthService) {

  }

  public getPersonalData() : Observable<any>{
    return this.http.get("/api/users/personal", {headers: this.authService.getAuthorizationHeaders()})
        .map(res => res.json());
  }

  isAuthenticated() {
    return this.authService.isAuthenticated();
  }

}
