import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';

import {RouterModule, Routes} from '@angular/router';
import {AppComponent} from './app.component';
import {MenuComponent} from './menu/menu.component';
import {LastGameComponent} from './last-game/last-game.component';
import {RequesterBackEndService} from '../service/RequesterBackEnd.service';
import {BoardComponent} from './board/board.component';

const appRoutes: Routes = [
  {
    path: 'menu',
    component: MenuComponent,
  },
  {
    path: 'board',
    component: BoardComponent,
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
    LastGameComponent,
    BoardComponent
  ],
  imports: [
    RouterModule.forRoot(
      appRoutes,
      {enableTracing: false}
    ),
    BrowserModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    RequesterBackEndService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
