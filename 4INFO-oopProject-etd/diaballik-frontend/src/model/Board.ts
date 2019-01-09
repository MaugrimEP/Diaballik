import {Player} from './Player';
import { Action } from './Action';

export class Board {

  tiles = [];

  constructor() {
    for (let ligne = 0; ligne < 7; ++ligne) {
      this.tiles[ligne] = [];
      for (let colonne = 0; colonne < 7; ++colonne) {
        this.tiles[ligne][colonne] = new TileInfo(Pion.empty);
      }
    }
  }

  doAction(action:Action):void{
    let tileInfoDepart = this.get(action._depart._ligne, action._depart._colonne);
    let tileInfoArrive = this.get(action._arrivee._ligne, action._arrivee._colonne);

    if (this.isBall(action._depart._ligne, action._depart._colonne)) {// isBall
      this.addPion(action._arrivee._ligne, action._arrivee._colonne, Pion.ball, tileInfoDepart.player);
      this.addPion(action._depart._ligne, action._depart._colonne, Pion.pion, tileInfoDepart.player);
    } else {// isPion
      this.set(action._arrivee._ligne, action._arrivee._colonne, tileInfoDepart);
      this.set(action._depart._ligne, action._depart._colonne, tileInfoArrive);
    }
  }

  undoAction(action:Action):void{
    let actionReverse = new Action(action._arrivee, action._depart);
    this.doAction(actionReverse);
  }

  get(ligne: number, colonne: number): TileInfo {
    return this.tiles[ligne][colonne];
  }

  set(ligne: number, colonne: number, tileInfo:TileInfo): void {
    this.tiles[ligne][colonne] = tileInfo;
  }

  addPion(ligne: number, colonne: number, pion: Pion, player: Player): void {
    this.get(ligne, colonne).pion = pion;
    this.get(ligne, colonne).player = player;
  }

  removePion(ligne: number, colonne: number): void {
    this.get(ligne, colonne).pion = Pion.empty;
    this.get(ligne, colonne).player = undefined;
  }

  isEmpty(ligne: number, colonne: number): boolean {
    return this.get(ligne, colonne).pion == Pion.empty;
  }

  isBall(ligne: number, colonne: number): boolean {
    return this.get(ligne, colonne).pion == Pion.ball;
  }

  isPion(ligne: number, colonne: number): boolean {
    return this.get(ligne, colonne).pion == Pion.pion;
  }

  getPionPossibility(ligne: number, colonne: number): any[] {
    let possibilitys = [];
    for (let addLigne = -1; addLigne <= 1; ++addLigne) {
      for (let addColonne = -1; addColonne <= 1; ++addColonne) {
        try {
          if (this.get(ligne + addLigne, colonne + addColonne).isEmpty() && Math.abs(addLigne) + Math.abs(addColonne)<=1)
            possibilitys.push({ligne: ligne + addLigne, colonne: colonne + addColonne});
        } catch (e) {
        }
      }
    }
    return possibilitys;
  }

  getBallPossiblity(ligne: number, colonne: number): any[] {
    let possibilitys = [];
    return possibilitys;
  }
}

export class TileInfo {
  pion: Pion;
  player: Player;

  constructor(pion: Pion, player: Player = undefined) {
    this.pion = pion;
    this.player = player;
  }

  isEmpty(): boolean {
    return this.pion == Pion.empty;
  }

  isBall(): boolean {
    return this.pion == Pion.ball;
  }

  isPion(): boolean {
    return this.pion == Pion.pion;
  }
}

export enum Pion {
  empty,
  pion,
  ball,
}
