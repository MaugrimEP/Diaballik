import {Coordonnee} from './Coordonnee';

export class Action {
  public _depart: Coordonnee;
  public _arrivee: Coordonnee;

  constructor(depart: Coordonnee, arrivee: Coordonnee) {
    this._depart = depart;
    this._arrivee = arrivee;
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
    return new Action(Coordonnee.fromJSON(action.depart), Coordonnee.fromJSON(action.arrivee));
  }
}
