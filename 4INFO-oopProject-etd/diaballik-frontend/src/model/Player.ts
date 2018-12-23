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

  public toString() : string {
    return `
      {
        name : ${this.name}
        color : ${this.color}
        ia : ${this.typeIA}
        niveau_ia : ${this.niveau_ia}
      }`;
  }


  static from(joueur: string) {
    const j = JSON.parse(joueur);
    return new Player(j.couleur.code, j.pseudo, !(j.type === 'JoueurHumain'), j.hasOwnProperty('@id') ? j['@id'] : 0);
  }

  static fromJSON(j){
    return new Player(j.couleur.code, j.pseudo, !(j.type === 'JoueurHumain'), j.hasOwnProperty('@id') ? j['@id'] : 0);
  }
}

export enum IA_level {
  Noob = 'noob',
  Starting = 'starting',
  Progressive = 'progressive',
}
