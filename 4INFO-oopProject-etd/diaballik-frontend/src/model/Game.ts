import {IA_level, Player} from './Player';

class Game {
  constructor(public joueur1: Player,
              public joueur2: Player,
              public typeIA: boolean = false,
              public id: number = 0,
              public niveau_ia: IA_level = IA_level.Noob
  ) {

  }


  static from(game: string) {// TODO
    const g = JSON.parse(game);


  }
}
