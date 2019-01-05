import {Injectable} from '@angular/core';

export class Coordonnee {
  public _ligne: number;
  public _colonne: number;

  static cptId = 0;
  public id: number;

  constructor(ligne: number, colonne: number) {
    this._ligne = ligne;
    this._colonne = colonne;
    this.id = Coordonnee.cptId++;
  }


  get ligne(): number {
    return this._ligne;
  }

  set ligne(value: number) {
    this._ligne = value;
  }

  get colonne(): number {
    return this._colonne;
  }

  set colonne(value: number) {
    this._colonne = value;
  }

  static fromJSON(coord: any): Coordonnee {
    return new Coordonnee(coord.ligne, coord.colonne);
  }
}
