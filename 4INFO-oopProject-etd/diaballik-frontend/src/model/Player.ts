import {Injectable} from '@angular/core';

@Injectable()
export class Player {

  static idCpt = 0;

  constructor(public color: string,
              public name: string,
              public typeIA: boolean = false,
              public id: number = -1,
              public niveau_ia: IA_level = IA_level.Noob
  ) {
    if (this.id === -1) {
      this.id = Player.idCpt++;
    }
  }

  public toString(): string {
    return `
      {
        name : ${this.name}
        color : ${this.color}
        ia : ${this.typeIA}
        niveau_ia : ${this.niveau_ia}
      }`;
  }

  public toJSON(): string {
    let strIa = '';
    if (this.typeIA) {
      strIa = `,\n"strategieIA": {
        "type": "${this.niveau_ia}"
        }`;
    }


    return `{
        "@id" : ${this.id},
        "type" : "${this.typeIA ? 'IA' : 'JoueurHumain'}",
        "couleur" : {
            "code" : "${this.color}"
          },
        "pseudo" : "${this.name}"
        ${strIa}
      }\n
    `;
  }


  /* static from(joueur: string) {
     const j = JSON.parse(joueur);
     return new Player(j.couleur.code, j.pseudo, !(j.type === 'JoueurHumain'), j.hasOwnProperty('@id') ? j['@id'] : 0);
   }*/

  static fromJSON(j) {
    return new Player(j.couleur.code, j.pseudo, !(j.type === 'JoueurHumain'), j.hasOwnProperty('@id') ? j['@id'] : 0);
  }
}

export enum IA_level {
  Noob = 'noob',
  Starting = 'starting',
  Progressive = 'progressive',
}
