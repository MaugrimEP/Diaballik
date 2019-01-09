import {Player} from './Player';

export class ShortGameInfo {


  constructor(public joueur1: Player, public joueur2: Player, public date: Date) {
  }

  static from(json: any): ShortGameInfo[] {
    const games: ShortGameInfo[] = [];
    const parsed = json;

    const mapsIdPlayer = {};

    for (const elem of parsed) {
      let j1;
      let j2;
      if (elem.joueur1.pseudo !== undefined) {
        j1 = Player.fromJSON(elem.joueur1);
        mapsIdPlayer[elem.joueur1['@id']] = j1;
      } else {
        j1 = mapsIdPlayer[elem.joueur1];
      }
      if (elem.joueur2.pseudo !== undefined) {
        j2 = Player.fromJSON(elem.joueur2);
        mapsIdPlayer[elem.joueur2['@id']] = j2;
      } else {
        j2 = mapsIdPlayer[elem.joueur2];
      }
      games.push(new ShortGameInfo(
        j1,
        j2,
        new Date(+elem.date))
      );
    }
    return games;
  }
}
