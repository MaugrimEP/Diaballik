import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';

import {RouterModule, Routes} from '@angular/router';
import {AppComponent} from './app.component';
import {MenuComponent} from './menu/menu.component';
import {LastGameComponent} from './last-game/last-game.component';
import {RequesterBackEndService} from '../service/RequesterBackEnd.service';

const appRoutes: Routes = [
  {
    path: 'menu',
    component: MenuComponent,
  },
  {
    path: '',
    redirectTo: '/config',
    pathMatch: 'full'
  },
];

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    LastGameComponent
  ],
  imports: [
    RouterModule.forRoot(
      appRoutes,
      {enableTracing: false}
    ),
    BrowserModule,
    HttpClientModule
  ],
  providers: [
    RequesterBackEndService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
