export class Etat {
  constructor(public nbCoupRestant: number, public joueurCourant: string) {
  }

  static fromJSON(etat: any): Etat {
    return new Etat(etat.nbCoupRestant, etat.type.toString().substring(4, 6));
  }
}
