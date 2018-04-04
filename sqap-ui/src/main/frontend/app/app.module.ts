import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {AccessDeniedComponent} from './access-denied/access-denied.component';
import {AuthService} from './shared/auth/auth.service';
import {AdminGuard} from './shared/guards/admin.guard';
import {AuthenticatedGuard} from './shared/guards/authenticated.guard';
import {UnauthenticatedGuard} from './shared/guards/unauthenticated.guard';
import {TitleService} from './home/shared/title.service';
import {AppRoutingModule} from './app-routing.module';
import {COMMON_ROOT_MODULES} from './shared';
import { SoundTestCreatorComponent } from './sound-test-creator/sound-test-creator.component';
import { SoundTestPlayerComponent } from './sound-test-player/sound-test-player.component';
import { SoundTestManagerComponent } from './sound-test-manager/sound-test-manager.component';
import {FileControlValueAccessor} from "./shared/accessors/file-control-value-accessor.directive";
import {UserGuard} from "./shared/guards/user.guard";
import { FileUploadComponent } from './file-upload/file-upload.component';
import {Ng2UploaderModule} from "ng2-uploader";
import { AbxTestFormComponent } from './sound-test-creator/abx-test-form/abx-test-form.component';
import { SingleTestComponent } from './sound-test-creator/single-test/single-test.component';
import { MushraTestFormComponent } from './sound-test-creator/mushra-test-form/mushra-test-form.component';
import {ErrorNotifierService} from "./shared/error/error-notifier.service";
import {XHRBackend, RequestOptions, Http} from "@angular/http";
import {CustomHttpService} from "./shared/http/custom-http.service";
import {SimpleNotificationsModule} from "angular2-notifications";
import { TestGroupDetailsComponent } from './sound-test-manager/test-group-details/test-group-details.component';
import {ErrorResolverService} from "./shared/error/error-resolver.service";
import { SoundTestPlayerInternalComponent } from './sound-test-player/sound-test-player-internal/sound-test-player-internal.component';
import {FlexLayoutModule} from "@angular/flex-layout";
import {TestGroupService} from "./shared/test/group/test-group.service";
import { AbxTestPlayerComponent } from './sound-test-player/abx-test-player/abx-test-player.component';
import { MushraTestPlayerComponent } from './sound-test-player/mushra-test-player/mushra-test-player.component';
import { AudioPlayerComponent } from './sound-test-player/audio-player/audio-player.component';
import {FilesDownloadService} from "./shared/files/files-download.service";
import {SafeUrlPipe} from "./shared/url/safe-url.pipe";
import {TestService} from "./shared/test/single/test.service";
import { SampleSliderComponent } from './sound-test-player/sample-slider/sample-slider/sample-slider.component';
import { FormalitiesFormComponent } from './sound-test-creator/formalities-form/formalities-form.component';
import {GlobalRulesService} from "./shared/rules/global-rules.service";
import {TranslateModule, TranslateLoader, TranslateStaticLoader} from "ng2-translate";
import {AboutComponent} from "./about/about.component";
import { ParticipantStatementComponent } from './sound-test-player/participant-statement/participant-statement.component';
import { TermsComponent } from './terms/terms.component';
import { SuccessfulComponent } from './sound-test-creator/successful/successful.component';
import {ClipboardDirective} from "./shared/clipboard/clipboard.directive";
import {UserService} from "./shared/user/user-service";
import {DomSanitizer} from "@angular/platform-browser";
import {NoopDomSanitizerService} from "./shared/noop-dom-sanitizer.service";
import { PoolingComponent } from './pooling/pooling.component';
import { GeneralPoolingComponent } from './pooling/general-pooling/general-pooling.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AboutComponent,
    NotFoundComponent,
    AccessDeniedComponent,
    SoundTestCreatorComponent,
    SoundTestPlayerComponent,
    SoundTestManagerComponent,
    FileUploadComponent,
    AbxTestFormComponent,
    SingleTestComponent,
    MushraTestFormComponent,
    TestGroupDetailsComponent,
    SoundTestPlayerInternalComponent,
    AbxTestPlayerComponent,
    MushraTestPlayerComponent,
    AudioPlayerComponent,
      SafeUrlPipe,
      SampleSliderComponent,
      FormalitiesFormComponent,
      ParticipantStatementComponent,
      TermsComponent,
      SuccessfulComponent,
      ClipboardDirective,
      PoolingComponent,
      GeneralPoolingComponent
  ],
  imports: [
    ...COMMON_ROOT_MODULES,
    TranslateModule.forRoot({
      provide: TranslateLoader,
      useFactory: translateModuleFactory,
      deps: [Http]
    }),
    Ng2UploaderModule,
    AppRoutingModule,
    SimpleNotificationsModule,
    FlexLayoutModule.forRoot()
  ],
  providers: [
    AuthService,
    AdminGuard,
    UserGuard,
    AuthenticatedGuard,
    UnauthenticatedGuard,
    TitleService,
    ErrorNotifierService,
      ErrorResolverService,
    { provide:Http,
      useFactory: httpFactory,
      deps: [ XHRBackend, RequestOptions, ErrorNotifierService, ErrorResolverService ]
    },
      TestGroupService,
      FilesDownloadService,
      TestService,
      GlobalRulesService,
      UserService,
    {provide: DomSanitizer, useClass: NoopDomSanitizerService} // bug when assign src to audio programmaticaly
    // {provide: BrowserXhr, useClass: CustomBrowserXhr}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

}

export function httpFactory(backend: XHRBackend, defaultOptions: RequestOptions, errorNotifier: ErrorNotifierService, errorResolver: ErrorResolverService) {
  return new CustomHttpService(backend, defaultOptions, errorNotifier, errorResolver);
}

export function translateModuleFactory(http: Http) {
  return new TranslateStaticLoader(http, '/assets/i18n', '.json');
}
//
// export function httpFactory(backend: XHRBackend, defaultOptions: RequestOptions, errorNotifier: ErrorNotifierService, errorResolver: ErrorResolverService) {
//   return new CustomHttpService(backend, defaultOptions, errorNotifier, errorResolver);
// }
