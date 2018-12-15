import { Component, OnInit, Injectable} from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})


export class MenuComponent implements OnInit {

  IA_level = IA_level;
  Scenario = Scenario;

  scenario : Scenario;
  joueur1 : Joueur;
  joueur2 : Joueur;

  constructor() {
    this.joueur1  = new Joueur();
    this.joueur1.ia = true;
    this.joueur2  = new Joueur();
    this.scenario = Scenario.Standard;
  }

  ngOnInit() {
  }

  testFunction() : void {
    console.log(`
    joueur 1  => ${this.joueur1}
    joueur 2  => ${this.joueur2}
    scenario  => ${this.scenario}
    `);
  }
}

@Injectable()
export class Joueur {
  name      : string;
  color     : string;
  ia        : boolean;
  niveau_ia : IA_level;

  constructor() {
    this.name       = "";
    this.color      = "";
    this.ia         = false;
    this.niveau_ia  = IA_level.Noob;
  }

  public toString() : string {
    return `
      {
        name : ${this.name}
        color : ${this.color}
        ia : ${this.ia}
        niveau_ia : ${this.niveau_ia}
      }`;
  }
}

export enum Scenario {
  Standard = 'standard',
  Random   = 'random',
  Enemy    = 'enemy',
}

export enum IA_level {
  Noob         = 'noob',
  Starting     = 'starting',
  Progressive  = 'progressive',
}
