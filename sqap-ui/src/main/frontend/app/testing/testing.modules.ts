import {FormsModule, FormBuilder, ReactiveFormsModule} from '@angular/forms';
import {BaseRequestOptions, Http} from '@angular/http';
import {
  MdInputModule,
  MdIconModule,
  MdSidenavModule,
  MdCardModule,
  MdButtonModule,
  MdCheckboxModule,
  MdCoreModule,
  MdGridListModule,
  MdListModule,
  MdMenuModule,
  MdProgressBarModule,
  MdProgressCircleModule,
  MdRadioModule,
  MdSlideToggleModule,
  MdSliderModule,
  MdTabsModule,
  MdToolbarModule,
  MdTooltipModule,
  MdButtonToggleModule,
  MdDialogModule,
  MdSnackBarModule
} from '@angular/material';
import {BrowserModule} from '@angular/platform-browser';
import {MockBackend} from '@angular/http/testing';
import {SimpleNotificationsModule} from "angular2-notifications";
import {ErrorNotifierService} from "../shared/error/error-notifier.service";

export const COMMON_TESTING_MODULES = [
  BrowserModule,
  FormsModule,
  ReactiveFormsModule,
  MdButtonModule.forRoot(),
  MdButtonToggleModule.forRoot(),
  MdCardModule.forRoot(),
  MdCheckboxModule.forRoot(),
  MdCoreModule.forRoot(),
  MdDialogModule.forRoot(),
  MdGridListModule.forRoot(),
  MdIconModule.forRoot(),
  MdInputModule.forRoot(),
  MdListModule.forRoot(),
  MdMenuModule.forRoot(),
  MdProgressBarModule.forRoot(),
  MdProgressCircleModule.forRoot(),
  MdRadioModule.forRoot(),
  MdSidenavModule.forRoot(),
  MdSlideToggleModule.forRoot(),
  MdSliderModule.forRoot(),
  MdSnackBarModule.forRoot(),
  MdTabsModule.forRoot(),
  MdToolbarModule.forRoot(),
  MdTooltipModule.forRoot(),
  SimpleNotificationsModule
];

export const COMMON_TESING_PROVIDERS = [
  BaseRequestOptions,
  MockBackend,
  FormBuilder,
  {
    provide: Http,
    useFactory: function (backend, defaultOptions) {
      return new Http(backend, defaultOptions);
    },
    deps: [MockBackend, BaseRequestOptions]
  },
  ErrorNotifierService
];
