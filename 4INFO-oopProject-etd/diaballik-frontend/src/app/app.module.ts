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
import {LoadGameComponent} from './load-game/load-game.component';

const appRoutes: Routes = [
  {
    path: 'menu',
    component: MenuComponent,
  },
  {
    path: 'board',
    component: BoardComponent,
  }, {
    path: 'load/:gameid',
    component: LoadGameComponent
  },
  {
    path: '',
    redirectTo: '/menu',
    pathMatch: 'full'
  },

];

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    LastGameComponent,
    BoardComponent,
    LoadGameComponent
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
