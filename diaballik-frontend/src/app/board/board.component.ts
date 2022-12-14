import {AfterContentInit, AfterViewChecked, AfterViewInit, Component, OnInit, SkipSelf} from '@angular/core';
import {RequesterBackEndService} from '../../service/RequesterBackEnd.service';
import {Player} from 'src/model/Player';
import {Board, Pion, TileInfo} from 'src/model/Board';
import {PlateauType} from 'src/model/PlateauType';
import {ActivatedRoute, Router} from '@angular/router';
import {Transmetter} from '../../model/Transmetter';
import {Etat} from '../../model/Etat';
import {Observable} from 'rxjs';
import {Coordonnee} from 'src/model/Coordonnee';
import {Action} from 'src/model/Action';
import {ResultOfAPlay} from 'src/model/ResultOfAPlay';
import { unescapeIdentifier } from '@angular/compiler';
import { listener } from '@angular/core/src/render3/instructions';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements AfterViewChecked {

  joueur1: Player;
  joueur2: Player;
  board: Board;

  selectedLigne: number;
  selectedColonne: number;
  etat: Etat;

  charged: boolean = false;

  setPion(ligne: number, colonne: number) {

  }

  getJoueurCourrant(): Player {
    if (this.etat.joueurCourant == this.joueur1.name) {
      return this.joueur1;
    }
    return this.joueur2;
  }

  tryToPlay(ligneD: number, colonneD: number, ligneA: number, colonneA: number) {
    let depart = new Coordonnee(ligneD, colonneD);
    let arrive = new Coordonnee(ligneA, colonneA);

    let action = new Action(depart, arrive);

    this.requesterBackEnd.play(action).then(resultOfAPlay => {
      let listeActions = resultOfAPlay.actions;
      if (listeActions.length === 0) {
        alert('Coup impossible !');
        return;
      }

      this.delayedActions(listeActions);

      if (resultOfAPlay.gameWon) {
        alert('Gagné par ' + resultOfAPlay.winner + ' !');
        return;
      }
    });
  }

  delayedActions(listActions : Action[]){
    let me = this;
    if(listActions.length == 0)
      return;

    me.board.doAction(listActions.shift());
    me.updateAutomate();
    this.updateTiles();
    setTimeout(function(){
      me.delayedActions(listActions);
  }, 500);
  }

  constructor(private requesterBackEnd: RequesterBackEndService, private router: Router) {

    if (Transmetter.data === undefined) {
      alert('Erreur de chargement');
      this.router.navigate(['menu']);
      return;
    }

    this.board = new Board();
    const infos = Transmetter.data.infos;

    this.joueur1 = Player.fromJSON(infos.joueur1);
    this.joueur2 = Player.fromJSON(infos.joueur2);
    this.etat = Etat.fromJSON(infos.automate.etatCourant);
  }

  ngAfterViewChecked() {
    if (this.charged)
      return;
    const infos = Transmetter.data.infos;
    for (let ligne = 0; ligne < 7; ++ligne) {
      for (let colonne = 0; colonne < 7; ++colonne) {
        let uneCase = infos.plateau.lesCases[`${ligne}:${colonne}`];
        if (uneCase.balle != null) {
          let joueurAssociated = uneCase.pion.joueur === 1 ? this.joueur1 : this.joueur2;
          this.board.addPion(ligne, colonne, Pion.ball, joueurAssociated);
        }
        if (uneCase.balle == null && uneCase.pion != null) {
          let joueurAssociated = uneCase.pion.joueur === 1 ? this.joueur1 : this.joueur2;
          this.board.addPion(ligne, colonne, Pion.pion, joueurAssociated);
        }
      }
    }

    this.updateTiles();
    this.setEtat();

    this.charged = true;
  }

  updateTiles(): void {
    for (let ligne = 0; ligne < 7; ++ligne) {
      for (let colonne = 0; colonne < 7; ++colonne) {
        let tile = this.board.get(ligne, colonne);
        this.getSpan(ligne, colonne).className = this.getClassTile(ligne, colonne);
        if(this.board.isEmpty(ligne, colonne)){
          this.getSpan(ligne, colonne).style.backgroundColor = "";
        }else{
          this.getSpan(ligne, colonne).style.backgroundColor = this.board.get(ligne, colonne).player.color;
        }
        let jeton1 = document.getElementsByClassName("joueur1-round") as HTMLCollectionOf<HTMLElement>;
        jeton1[0].style.backgroundColor = this.joueur1.color;
        let jeton2 = document.getElementsByClassName("joueur2-round") as HTMLCollectionOf<HTMLElement>;
        jeton2[0].style.backgroundColor = this.joueur2.color;
      }
    }
  }

  getLi(ligne: number, colonne: number) {
    return document.querySelector(`li[data-ligne="${ligne}"][data-colonne="${colonne}"]`);
  }

  getSpan(ligne: number, colonne: number) {
    return this.getLi(ligne, colonne).querySelector(`span`);
  }

  getClassTile(ligne: number, colonne: number): string {
    const tile = this.board.get(ligne, colonne);
    if (tile.isEmpty()) {
      return '';
    }
    const joueurClass = this.getClassCouleurJoueur(tile.player);
    if (tile.isPion()) {
      return `piece pion ${joueurClass}`;
    }
    if (tile.isBall()) {
      return `piece ball ${joueurClass}`;
    }
  }

  getClassCouleurJoueur(player: Player) {
    return player === this.joueur1 ? 'pieceJoueur1' : 'pieceJoueur2';
  }


  clickOnTile(ligne: number, colonne: number): void {
    const clickedSpan = this.getSpan(ligne, colonne);
    if (clickedSpan.classList.contains('choice') ||
      this.somethingSelected() && this.board.isBall(this.selectedLigne, this.selectedColonne) && this.board.isPion(ligne, colonne) ) { // dans ce cas on est sur un des mouvements possible et dans ce cas on envoit au serveur
      this.tryToPlay(this.selectedLigne, this.selectedColonne, ligne, colonne);
      return;
    }
    if (this.board.isEmpty(ligne, colonne)) {// l'utilisateur a cliqué sur une case vide dans ce cas on clear la liste des possibilitées
      this.clearPossibilty();
      this.deselect(this.selectedLigne,this.selectedColonne);
      this.selectedLigne = null;this.selectedColonne = null;
      return;
    }
    if (!this.board.isEmpty(ligne, colonne)) { // il faudra sélectioner la piece actuelle
      this.select(ligne, colonne);
      return;
    }
  }

  somethingSelected(): boolean {
    return this.selectedLigne != null && this.selectedColonne != null;
  }

  select(ligne: number, colonne: number) {
    if (this.somethingSelected()) {
      this.deselect(this.selectedLigne, this.selectedColonne);
      this.clearPossibilty();
      this.selectedLigne = null;
      this.selectedColonne = null;
    }
    {
      this.getSpan(ligne, colonne).classList.add('selected');
      this.selectedLigne = ligne;
      this.selectedColonne = colonne;

      let possibiltys = [];
      if (this.board.isPion(ligne, colonne)) {
        possibiltys = this.board.getPionPossibility(ligne, colonne);
      }
      if (this.board.isBall(ligne, colonne)) {
        possibiltys = this.board.getBallPossiblity(ligne, colonne);
      }
      for (let i = 0; i < possibiltys.length; ++i) {
        this.addPossibility(possibiltys[i].ligne,
          possibiltys[i].colonne,
          this.board.get(ligne, colonne).player,
          false);
      }
    }
  }

  deselect(ligne: number, colonne: number) {
    if(this.somethingSelected()){
      this.getSpan(ligne, colonne).classList.remove('selected');
    }
  }

  clearPossibilty() {
    for (let ligne = 0; ligne < 7; ++ligne) {
      for (let colonne = 0; colonne < 7; ++colonne) {
        if (this.board.isEmpty(ligne, colonne)) {
          this.getSpan(ligne, colonne).className = '';
        }
      }
    }
  }

  addPossibility(ligne: number, colonne: number, joueur: Player, isBall: boolean): void {
    this.getSpan(ligne, colonne).className = `piece choice ${this.getClassCouleurJoueur(joueur)} ${isBall ? 'ball' : ''}`;
  }

  save() {
    this.requesterBackEnd.save();
    alert('Partie bien sauvegardée');
  }

  menu() {
    this.router.navigate(['menu']);
  }


  private setEtat() {
    document.querySelector(`#tour`).textContent = `Tour de ${this.etat.joueurCourant}, ${this.etat.nbCoupRestant} coups restants`;

  }

  private updateAutomate() {
    if (--this.etat.nbCoupRestant === 0) {
      if (this.etat.joueurCourant === 'J1') {
        this.etat.joueurCourant = 'J2';
      } else {
        this.etat.joueurCourant = 'J1';
      }
      this.etat.nbCoupRestant = 3;
    }
    this.setEtat();
  }
}
