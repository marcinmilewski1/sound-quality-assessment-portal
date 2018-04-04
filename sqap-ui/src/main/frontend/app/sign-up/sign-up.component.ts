import { Component, OnInit } from '@angular/core';
import {NgForm} from '@angular/forms';
import {Http} from "@angular/http";
import {Router} from "@angular/router";

@Component({
  selector: 'sqap-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent implements OnInit {

  private registerStatement = "Wyrażam zgodę na przetwarzanie i składowanie powyższych danych osobowych w ramach portalu.";

  private readonly URL = '/api/users';
  private message = '';
  constructor(private http : Http, private router: Router) { }

  ngOnInit() {
    console.log("SignUp component")
  }

  onSubmit(f :NgForm) {
    console.log(f);
    this.http.post(this.URL, f.value).subscribe(
        response => {
          if(response.status === 201) {
            this.router.navigate(['login']);
          }
        },
        err => {
          this.message = "Something went wrong. Try again";
          f.resetForm();
        }
    );
    console.log("Sign up form subbmitted");
  }
}

interface User {
  username: String;
  password :String;
  passwordConfirmation:String;
  email: String;
  registerStatementConfirmed: boolean;

  // additional data
  sex: String;
  age: number;
  isHearingDefect: boolean;
}