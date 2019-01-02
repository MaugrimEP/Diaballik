import {Component, OnInit, Injectable} from '@angular/core';
import {Player} from '../../model/Player';
import {Coordonnee} from '../../model/Coordonnee';
import {IA_level} from '../../model/Player';
import {PlateauType} from '../../model/PlateauType';
import {RequesterBackEndService} from '../../service/RequesterBackEnd.service';
import {Router} from '@angular/router';
import {Action} from '../../model/Action';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})


export class MenuComponent implements OnInit {

  IA_level = IA_level;
  scenario: PlateauType;
  joueur1: Player;
  joueur2: Player;
  Scenario: any;

  constructor(private requester: RequesterBackEndService, private router: Router) {
    this.joueur1 = new Player('#ffffff', 'Tsiry', false);
    this.joueur2 = new Player('#000000', 'William', true);
    this.scenario = PlateauType.Standard;
    this.Scenario = PlateauType;
  }

  ngOnInit() {
  }

  testFunction(): void {
    console.log(`
    joueur 1  => ${this.joueur1}
    joueur 2  => ${this.joueur2}
    scenario  => ${this.scenario}
    `);
  }

  startNewGame() {
    this.router.navigate(['board']);
  }

  /*testPlay() {
    let resultOfAPlayPromise = this.requester.play(new Action(new Coordonnee(6, 0), new Coordonnee(5, 0)));
    resultOfAPlayPromise.then(res => console.log(res));
  }*/
}



