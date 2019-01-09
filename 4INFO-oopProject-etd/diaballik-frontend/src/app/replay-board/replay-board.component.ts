import {AfterContentInit, AfterViewChecked, AfterViewInit, Component, OnInit, SkipSelf} from '@angular/core';
import {RequesterBackEndService} from '../../service/RequesterBackEnd.service';
import {Player} from 'src/model/Player';
import {Board, Pion, TileInfo} from 'src/model/Board';
import {ActivatedRoute, Router} from '@angular/router';
import {Transmetter} from '../../model/Transmetter';
import {Etat} from '../../model/Etat';
import {Coordonnee} from 'src/model/Coordonnee';
import {Action} from 'src/model/Action';

@Component({
  selector: 'app-replay-board',
  templateUrl: './replay-board.component.html',
  styleUrls: ['./replay-board.component.css']
})
export class ReplayBoardComponent implements AfterViewChecked {

  joueur1: Player;
  joueur2: Player;
  board: Board;

  actions = [];
  indexCurrentAction : number;
  
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
    
    this.actions = [];
    for(let commande of infos.commandes){
      this.actions.push(Action.fromJSON(commande.action));
    }
    this.indexCurrentAction = this.actions.length-1;
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

    this.charged = true;
  }

  updateTiles(): void {
    for (let ligne = 0; ligne < 7; ++ligne) {
      for (let colonne = 0; colonne < 7; ++colonne) {
        let tile = this.board.get(ligne, colonne);
        this.getSpan(ligne, colonne).className = this.getClassTile(ligne, colonne);
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



  actionSuivante() {
    if(this.indexCurrentAction<this.actions.length-1){
      this.board.doAction(this.actions[++this.indexCurrentAction]);
      this.updateTiles();
    }
  }

  actionPrecedente() {
    if(this.indexCurrentAction>=0){
      this.board.undoAction(this.actions[this.indexCurrentAction--]);
      this.updateTiles();
    }
  }

  menu() {
    this.router.navigate(['menu']);
  }
}
