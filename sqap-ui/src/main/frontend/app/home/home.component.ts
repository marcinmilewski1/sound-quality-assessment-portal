import {Component, OnInit} from '@angular/core';
import {AuthService} from '../shared/auth/auth.service';
import {TitleService} from './shared/title.service';
import {TranslateService} from "ng2-translate";

@Component({
  selector: 'sqap-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  tittle = "Internetowy portal do oceny jakości dzwięku";
  angularLogo = 'assets/img/angular-logo.png';

  data = {value: ''};

  constructor(public authService: AuthService) {

  }

  ngOnInit() {
    console.log('hello `Home` component');
  }
}

