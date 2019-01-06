import {Coordonnee} from './Coordonnee';

export class Action {
  public _depart: Coordonnee;
  public _arrivee: Coordonnee;
  public id: number;

  static cptId = 100;

  static allCoordinates = {};

  constructor(depart: Coordonnee, arrivee: Coordonnee) {
    this._depart = depart;
    this._arrivee = arrivee;
    this.id = Action.cptId++;
  }


  get depart(): Coordonnee {
    return this._depart;
  }

  set depart(value: Coordonnee) {
    this._depart = value;
  }

  get arrivee(): Coordonnee {
    return this._arrivee;
  }

  set arrivee(value: Coordonnee) {
    this._arrivee = value;
  }

  static fromJSON(action: any): Action {
    let dep;
    let arr;
    if (action.depart.ligne !== undefined) {
      dep = Coordonnee.fromJSON(action.depart);
      this.allCoordinates[action.depart['@id']] = dep;
    } else {
      dep = this.allCoordinates[action.depart];
    }

    if (action.arrivee.ligne !== undefined) {
      arr = Coordonnee.fromJSON(action.arrivee);
      this.allCoordinates[action.arrivee['@id']] = arr;
    } else {
      arr = this.allCoordinates[action.arrivee];
    }
    return new Action(dep, arr);
  }
}
