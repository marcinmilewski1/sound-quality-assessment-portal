import { NgModule } from '@angular/core';
import { SignUpComponent } from './sign-up.component';
import {COMMON_CHILD_MODULES} from "../shared/common/common.modules";
import {SignUpRoutingModule} from "./sign-up.routing.module";

@NgModule({
  imports: [
    COMMON_CHILD_MODULES,
    SignUpRoutingModule,
  ],
  declarations: [SignUpComponent]
})
export class SignUpModule {}
