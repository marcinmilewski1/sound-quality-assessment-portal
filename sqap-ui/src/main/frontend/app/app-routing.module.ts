import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {AuthenticatedGuard} from './shared/guards/authenticated.guard';
import {AdminGuard} from './shared/guards/admin.guard';
import {UnauthenticatedGuard} from './shared/guards/unauthenticated.guard';
import {AccessDeniedComponent} from './access-denied/access-denied.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {SoundTestCreatorComponent} from "./sound-test-creator/sound-test-creator.component";
import {SoundTestPlayerComponent} from "./sound-test-player/sound-test-player.component";
import {SoundTestManagerComponent} from "./sound-test-manager/sound-test-manager.component";
import {UserGuard} from "./shared/guards/user.guard";
import {TestGroupDetailsComponent} from "./sound-test-manager/test-group-details/test-group-details.component";
import {SoundTestPlayerInternalComponent} from "./sound-test-player/sound-test-player-internal/sound-test-player-internal.component";
import {AboutComponent} from "./about/about.component";
import {TermsComponent} from "./terms/terms.component";
import {SuccessfulComponent} from "./sound-test-creator/successful/successful.component";
import {PoolingComponent} from "./pooling/pooling.component";
import {GeneralPoolingComponent} from "./pooling/general-pooling/general-pooling.component";

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'prefix',
    redirectTo: 'home'
  },
  {
    path: 'home',
    pathMatch: 'prefix',
    component: HomeComponent
  },
  {
    path: 'about',
    pathMatch: 'prefix',
    component: AboutComponent
  },
  {
    path: 'terms',
    pathMatch: 'prefix',
    component: TermsComponent
  },
  {
    path: 'login',
    pathMatch: 'prefix',
    loadChildren: './login/login.module#LoginModule',
    canActivate: [UnauthenticatedGuard]
  },
  {
    path: 'sign-up',
    pathMatch: 'prefix',
    loadChildren: './sign-up/sign-up.module#SignUpModule',
    canActivate: [UnauthenticatedGuard]
  },
  {
    path: 'accessDenied',
    pathMatch: 'prefix',
    component: AccessDeniedComponent
  },
  {
    path: '404',
    pathMatch: 'prefix',
    component: NotFoundComponent
  },
  {
    path: 'test-creator',
    pathMatch: 'prefix',
    component: SoundTestCreatorComponent,
    canActivate: [UserGuard]
  },
  {
    path: 'test-creator/successful/:id',
    pathMatch: 'prefix',
    component: SuccessfulComponent,
  },
  {
    path: 'test-player',
    pathMatch: 'prefix',
    component: SoundTestPlayerComponent
  },
  {
    path: 'test-manager',
    pathMatch: 'prefix',
    component: SoundTestManagerComponent,
    canActivate: [UserGuard]
  },
  {
    path: 'test-manager/group/:id',
    pathMatch: 'prefix',
    component: TestGroupDetailsComponent,
    canActivate: [UserGuard]
  },
  {
    path: 'test-player/player/:id',
    pathMatch: 'prefix',
    component: SoundTestPlayerInternalComponent
  },
  {
    path: 'pooling',
    pathMatch: 'prefix',
    component: PoolingComponent
  },
  {
    path: 'generalPooling',
    pathMatch: 'prefix',
    component: GeneralPoolingComponent
  },
  {
    path: '**',
    pathMatch: 'prefix',
    redirectTo: '404'
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule {
}
