import { Component, OnInit, SkipSelf } from '@angular/core';
import {RequesterBackEndService} from '../../service/RequesterBackEnd.service';
import { Player } from 'src/model/Player';
import { Board, Pion, TileInfo } from 'src/model/Board';
import { PlateauType } from 'src/model/PlateauType';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {

  joueur1:Player;
  joueur2:Player;
  board : Board;

  constructor(requesterBackEnd: RequesterBackEndService) {
    this.board = new Board();
    
    let reponse = requesterBackEnd.initGame(
        new Player("#FFF","Bob"),
        new Player("#000","Jean"),
        PlateauType.Standard,
      );

      let  boardThis = this;
      
      reponse.then((infos:any) => {
        this.joueur1 = Player.fromJSON(infos.joueur1);
        this.joueur2 = Player.fromJSON(infos.joueur2);

        for(let ligne=0;ligne<7;++ligne){
          for(let colonne=0;colonne<7;++colonne){
            let uneCase = infos.plateau.lesCases[`${ligne}:${colonne}`];
            if(uneCase.balle!=null){
              let joueurAssociated = uneCase.pion.joueur == 1 ? this.joueur1 : this.joueur2;
              this.board.addPion(ligne,colonne, Pion.ball,joueurAssociated);
            }if(uneCase.balle==null && uneCase.pion!=null){
              let joueurAssociated = uneCase.pion.joueur == 1 ? this.joueur1 : this.joueur2;
              this.board.addPion(ligne,colonne, Pion.pion, joueurAssociated);
            }
          }
        }

        boardThis.updateTiles();
        console.log("loading Fini");
      });
   }

  ngOnInit() {
  }

  updateTiles():void{
    for(let ligne=0;ligne<7;++ligne){
      for(let colonne=0;colonne<7;++colonne){
        let tile = this.board.get(ligne, colonne);
        let spanCase = document.querySelector(`li[data-ligne="${ligne}"][data-colonne="${colonne}"]>span`);
        spanCase.className = this.getClassTile(ligne,  colonne);
      }
    }
  }

  getLi(ligne:number, colonne:number){
    return document.querySelector(`li[data-ligne="${ligne}"][data-colonne="${colonne}"]`);
  }

  getClassTile(ligne:number, colonne:number):string {
    let tile = this.board.get(ligne, colonne);
    if(tile.isEmpty()){
      return "";
    }
    let joueurClass = this.getClassCouleurJoueur(tile.player);
    if(tile.isPion()){
      return `piece pion ${joueurClass}`;
    }
    if(tile.isBall()){
      return `piece ball ${joueurClass}`;
    }
  }

  getClassCouleurJoueur(player:Player){
    return player == this.joueur1 ? 'pieceJoueur1' : 'pieceJoueur2'; 
  }

  addPossibility(ligne:number, colonne:number, joueur:Player) : void {
    let li = this.getLi(ligne, colonne);
    li.querySelector(`span`).classList.add(`piece choice ${this.getClassCouleurJoueur(joueur)}`);
  }
}
