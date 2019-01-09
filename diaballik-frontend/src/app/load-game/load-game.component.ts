import {Component, OnInit} from '@angular/core';
import {IA_level, Player} from '../../model/Player';
import {Transmetter} from '../../model/Transmetter';
import {Router} from '@angular/router';
import {RequesterBackEndService} from '../../service/RequesterBackEnd.service';

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

  constructor(private requester: RequesterBackEndService, private router: Router) {
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
    let response = this.requester.getGameRegistrered(this.date.getTime().toString());
    response.then(infos => {
      Transmetter.data = {
        'infos': infos
      };
      this.router.navigate(['board']);
    });
  }

  replay() {
    let response = this.requester.getGameRegistrered(this.date.getTime().toString());
    response.then(infos => {
      Transmetter.data = {
        'infos': infos
      };
      this.router.navigate(['replay-board']);
    });
  }
}
