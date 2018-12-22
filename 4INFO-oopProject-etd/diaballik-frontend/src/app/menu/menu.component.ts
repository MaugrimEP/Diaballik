import {Component, OnInit, Injectable} from '@angular/core';
import {Player} from '../../model/Player';
import {IA_level} from '../../model/Player';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})


export class MenuComponent implements OnInit {

  IA_level = IA_level;
  Scenario = Scenario;

  scenario: Scenario;
  joueur1: Player;
  joueur2: Player;

  constructor() {
    this.joueur1 = new Player('#FFF', 'Tsiry', false);
    this.joueur2 = new Player('#000', 'William', true);
    this.scenario = Scenario.Standard;
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
}

export enum Scenario {
  Standard = 'standard',
  Random = 'random',
  Enemy = 'enemy',
}

