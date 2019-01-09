import {Component, OnDestroy, OnInit} from '@angular/core';
import {ShortGameInfo} from '../../model/ShortGameInfo';
import {RequesterBackEndService} from '../../service/RequesterBackEnd.service';
import {Route, Router} from '@angular/router';
import {Transmetter} from '../../model/Transmetter';
import {Player} from '../../model/Player';

@Component({
  selector: 'app-last-game',
  templateUrl: './last-game.component.html',
  styleUrls: ['./last-game.component.css']
})
export class LastGameComponent implements OnInit {

  lastGames: Promise<ShortGameInfo[]>;

  listGame = [];

  constructor(private requesterBackEnd: RequesterBackEndService, private router: Router) {

    this.lastGames = requesterBackEnd.getListShortGame();
    this.lastGames.then(s=>{
      this.listGame = s;
    });
  }

  ngOnInit() {
  }

  selectOneGame(date: Date, joueur1: Player, joueur2: Player) {
    Transmetter.data = {
      'date': date,
      'j1': joueur1,
      'j2': joueur2
    };
    this.router.navigate(['load']);
  }

  deleteThisId(idGame : number,time: number) {
    this.requesterBackEnd.deleteGameRegistrered(time.toString());

    document.querySelector(`#game${idGame}`).remove();

    this.router.navigate(['menu']);
  }
}
