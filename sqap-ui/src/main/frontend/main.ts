import './polyfills.ts';

import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { enableProdMode } from '@angular/core';
import { environment } from './environments/environment';
import { AppModule } from './app/';
import {hmrBootstrap} from "./hmr";

const bootstrap = () => {
  return platformBrowserDynamic().bootstrapModule(AppModule);
};

if (environment.production) {
  enableProdMode();
  bootstrap();
}
else if (environment.hmr) {
  console.log("HMR enabled");
  if (module['hot']) {
    hmrBootstrap(module, bootstrap);
  } else {
    console.error('HMR is not enabled for webpack-dev-server!');
    console.info('Are you using the --hmr flag for ng serve?');
  }
}
else {
  bootstrap();
}
// else {
//   platformBrowserDynamic().bootstrapModule(AppModule);
// }


