import {Player} from './Player';

export class Board {

    tiles = []

    constructor(){
        for(let ligne=0; ligne<7; ++ligne){
            this.tiles[ligne]=[]
            for(let colonne=0; colonne<7; ++colonne){
                this.tiles[ligne][colonne]=new TileInfo(Pion.empty);
            }
        }
    }

    get(ligne:number, colonne: number): TileInfo {
        return this.tiles[ligne][colonne];
    }

    addPion(ligne:number, colonne:number, pion:Pion, player:Player) : void {
        this.get(ligne,colonne).pion = pion;
        this.get(ligne,colonne).player = player;
    }

    removePion(ligne:number, colonne:number):void{
        this.get(ligne, colonne).pion = Pion.empty;
        this.get(ligne, colonne).player = undefined;
    }

    isEmpty(ligne:number, colonne:number):boolean {
        return this.get(ligne, colonne).pion == Pion.empty;
    }

    isBall(ligne:number, colonne:number):boolean {
        return this.get(ligne, colonne).pion == Pion.ball; 
    }

    isPion(ligne:number, colonne:number):boolean {
        return this.get(ligne, colonne).pion == Pion.pion; 
    }
}

export class TileInfo {
    pion   : Pion;
    player : Player;

    constructor(pion:Pion, player:Player = undefined){
        this.pion   = pion;
        this.player = player;
    }

    isEmpty():boolean {
        return this.pion == Pion.empty;
    }

    isBall():boolean {
        return this.pion == Pion.ball; 
    }

    isPion():boolean {
        return this.pion == Pion.pion; 
    }
}

export enum Pion {
    empty,
    pion,
    ball,
}
