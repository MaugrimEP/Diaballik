import {Player} from './Player';

export class ShortGameInfo {


  constructor(public joueur1: Player, public joueur2: Player, public date: Date) {
  }

  static from(json: string): ShortGameInfo[] {
    const games: ShortGameInfo[] = [];
    const parsed = JSON.parse(json);
    for (const elem of parsed) {
      games.push(new ShortGameInfo(
        Player.from(JSON.stringify(elem.joueur1)),
        Player.from(JSON.stringify(elem.joueur2)),
        new Date(+elem.date))
      );
    }
    return games;
  }
}
