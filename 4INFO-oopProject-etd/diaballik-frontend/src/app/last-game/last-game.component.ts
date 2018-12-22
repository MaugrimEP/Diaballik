import {Component, OnInit} from '@angular/core';
import {ShortGameInfo} from '../../model/ShortGameInfo';
import {RequesterBackEndService} from '../../service/RequesterBackEnd.service';

@Component({
  selector: 'app-last-game',
  templateUrl: './last-game.component.html',
  styleUrls: ['./last-game.component.css']
})
export class LastGameComponent implements OnInit {

  lastGames: Promise<ShortGameInfo[]>;

  constructor(requesterBackEnd: RequesterBackEndService) {

    this.lastGames = requesterBackEnd.getListShortGame();
  }

  ngOnInit() {
  }

}
