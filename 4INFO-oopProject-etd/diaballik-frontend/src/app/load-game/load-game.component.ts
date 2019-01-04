import {Component, OnInit} from '@angular/core';
import {IA_level, Player} from '../../model/Player';
import {Transmetter} from '../../model/Transmetter';
import {Router} from '@angular/router';

@Component({
  selector: 'app-load-game',
  templateUrl: './load-game.component.html',
  styleUrls: ['./load-game.component.css']
})
export class LoadGameComponent implements OnInit {
  joueur1: Player;
  joueur2: Player;
  date: Date;
  IA_level: any;

  constructor(private router: Router) {
    this.IA_level = IA_level;
    if (Transmetter.data !== undefined) {

      this.joueur1 = Transmetter.data.j1;
      this.joueur2 = Transmetter.data.j2;
      this.date = Transmetter.data.date;
    } else {
      alert('Erreur lors du chargement');
      router.navigate(['menu']);
    }
  }

  ngOnInit() {
  }

  loadAGame() {

  }

  replay() {

  }
}
