import {Injectable} from '@angular/core';

@Injectable()
export class Player {


  constructor(public color: string,
              public name: string,
              public typeIA: boolean = false,
              public id: number = 0,
              public niveau_ia: IA_level = IA_level.Noob
  ) {

  }


  static from(joueur: string) {
    const j = JSON.parse(joueur);
    return new Player(j.couleur, j.pseudo, !(j.type === 'JoueurHumain'));
  }
}

export enum IA_level {
  Noob = 'noob',
  Starting = 'starting',
  Progressive = 'progressive',
}
