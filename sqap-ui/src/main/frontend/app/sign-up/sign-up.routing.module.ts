import {Routes, RouterModule} from '@angular/router';
import {NgModule} from '@angular/core/src/metadata/ng_module';
import {SignUpComponent} from "./sign-up.component";


export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: SignUpComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class SignUpRoutingModule {
}



