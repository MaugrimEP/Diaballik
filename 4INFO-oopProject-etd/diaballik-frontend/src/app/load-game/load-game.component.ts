import {Component, OnInit} from '@angular/core';
import {IA_level, Player} from '../../model/Player';

@Component({
  selector: 'app-load-game',
  templateUrl: './load-game.component.html',
  styleUrls: ['./load-game.component.css']
})
export class LoadGameComponent implements OnInit {
  joueur1: Player;
  joueur2: Player;
  IA_level: any;

  constructor() {
    this.IA_level = IA_level;
  }

  ngOnInit() {
  }

}
