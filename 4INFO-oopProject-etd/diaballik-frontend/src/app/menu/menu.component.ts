import { Component, OnInit, Injectable} from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})


export class MenuComponent implements OnInit {

  IA_level = IA_level;

  joueur1 : Joueur;
  joueur2 : Joueur;

  constructor() {
    this.joueur1 = new Joueur();
    this.joueur2 = new Joueur();
  }

  ngOnInit() {
  }

  testFunction() : void {
    console.log(`nom joueur 1 ${this.joueur1.name}`);
  }
}

@Injectable()
export class Joueur {
  name      : string;
  color     : string;
  ia        : boolean;
  niveau_ia : IA_level;

  constructor() {
    this.ia         = false;
    this.niveau_ia  = IA_level.Noob;
  }
}

export enum IA_level {
  Noob         = 'noob',
  Starting     = 'starting',
  Progressive  = 'progressive',
}
