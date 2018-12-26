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

  selectedLigne : number;
  selectedColonne : number;

  constructor(private requesterBackEnd: RequesterBackEndService) {
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
        this.getSpan(ligne, colonne).className = this.getClassTile(ligne,  colonne);
      }
    }
  }

  getLi(ligne:number, colonne:number){
    return document.querySelector(`li[data-ligne="${ligne}"][data-colonne="${colonne}"]`);
  }

  getSpan(ligne:number, colonne:number){
    return this.getLi(ligne, colonne).querySelector(`span`);
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

  clickOnTile(ligne: number, colonne:number):void {
    console.log("click on tile");
    let clickedSpan = this.getSpan(ligne, colonne);
    if(clickedSpan.classList.contains("choice")){//dans ce cas on est sur un des mouvements possible et dans ce cas on envoit au serveur
      console.log("coupe joué");
      return;
    }
    if(this.board.isEmpty(ligne, colonne)){//l'utilisateur a cliqué sur une case vide dans ce cas on clear la liste des possibilitées
      console.log("clear possibilitées")
      this.clearPossibilty();
      return;
    }
    if(!this.board.isEmpty(ligne, colonne)){ // il faudra sélectioner la piece actuelle
      console.log("selected");
      this.select(ligne, colonne);
      return;
    }
  }

  select(ligne: number, colonne:number){
    if(this.selectedLigne!=null && this.selectedColonne!=null){
      this.deselect(this.selectedLigne, this.selectedColonne);
      this.clearPossibilty();
      this.selectedLigne = null;
      this.selectedColonne = null;
    }
    {
      this.getSpan(ligne, colonne).classList.add("selected");
      this.selectedLigne   = ligne;
      this.selectedColonne = colonne;

      let possibiltys = [];
      if(this.board.isPion(ligne, colonne)){
        possibiltys = this.board.getPionPossibility(ligne,colonne);
      }
      if(this.board.isBall(ligne, colonne)){
        possibiltys = this.board.getBallPossiblity(ligne, colonne);
      }
      for(let i=0; i<possibiltys.length; ++i){
        this.addPossibility(possibiltys[i].ligne, 
                            possibiltys[i].colonne,
                            this.board.get(ligne, colonne).player,
                            false);
      }
    }
  }

  deselect(ligne: number, colonne:number){
    this.getSpan(ligne, colonne).classList.remove("selected");
  }

  clearPossibilty(){
    for(let ligne=0; ligne<7; ++ligne){
      for(let colonne=0; colonne<7; ++colonne){
        if(this.board.isEmpty(ligne,colonne)){
          this.getSpan(ligne, colonne).className ="";
        }
      }
    }
  }

  addPossibility(ligne:number, colonne:number, joueur:Player, isBall:boolean) : void {
    this.getSpan(ligne, colonne).className = `piece choice ${this.getClassCouleurJoueur(joueur)} ${isBall ? "ball" : ""}`;
  }
}
