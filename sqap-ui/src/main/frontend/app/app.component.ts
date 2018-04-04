import {Component, OnInit} from '@angular/core';
import {AuthService} from './shared/auth/auth.service';
import {Router} from '@angular/router';
import {APP_MENU, AppMenuItem} from './app.menu';
import {ErrorNotifierService} from "./shared/error/error-notifier.service";
import {NotificationsService} from "angular2-notifications";
import {Response} from "@angular/http";
import {CustomError} from "./shared/error/CustomError";
import {TranslateService} from "ng2-translate";

@Component({
  selector: 'sqap-app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  // public name: String = 'Sqap';
  public loading: boolean = false;

  views: AppMenuItem[] = APP_MENU;

  error: any;
  validationError: any;


  constructor(public authService: AuthService,
              public router: Router,
              private errorNotifier: ErrorNotifierService,
              private notificationService: NotificationsService,
              private translateService: TranslateService ) {
    translateService.addLangs(['pl']);
    translateService.setDefaultLang('pl');
    translateService.use('pl');
    this.errorNotifier.onError((err : CustomError) => {
      notificationService.error(err.tittle, err.content);
      console.log(err);
    });
  }

  options = {
    timeOut: 5000
  };
  logMeOut(): void {
    this.authService.logout();
    this.router.navigate(['']);
  }

  ngOnInit(): any {
    console.log('app on init');
  }

  changeLang(lang: string) {
    this.translateService.use(lang);
  }

}
