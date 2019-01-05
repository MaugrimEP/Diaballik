import {ShortGameInfo} from '../model/ShortGameInfo';
import {Player} from '../model/Player';
import {ResultOfAPlay} from '../model/ResultOfAPlay';
import {PlateauType} from '../model/PlateauType';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Action} from '../model/Action';

@Injectable()
export class RequesterBackEndService {

  static idInit = 0;

  constructor(private httpClient: HttpClient) {
  }

  getListShortGame(): Promise<ShortGameInfo[]> {
    return new Promise<ShortGameInfo[]>((resolve, reject) => {
        this.httpClient
          .get('/game/games')
          .subscribe(
            (json: any) => {
              resolve(ShortGameInfo.from(json));
            },
            (error) => {
              reject();
            }
          );
      }
    );
  }

  initGame(j1: Player, j2: Player, plateau: PlateauType) {
    let typePartie = 'TYPE_J_VS_J';
    if (j2.typeIA) {
      typePartie = 'TYPE_J_VS_IA';
    }

    const req = '{\n' +
      ' "@id" : ' + (RequesterBackEndService.idInit++) + ',' +
      '  "type": "InitGameClasses",\n' +
      '  "j1": ' + j1.toJSON() + ',' +
      '  "j2":' + j2.toJSON() + ',' +
      '  "plateau": { "@id" : 1, "type": "' + plateau + '"},\n' +
      '  "typePartie": [\n' +
      '    "TypePartie",\n' +
      '    "' + typePartie + '"\n' +
      '  ]\n' +
      '}';

    return new Promise((resolve, reject) => {
      this.httpClient
        .put('/game/init', JSON.parse(req))
        .subscribe(
          (response) => {
            resolve(response);
          },
          (error) => { // for test, TODO delete à la fin
            // resolve(RequesterBackEndService.getHardCodeRepInit());
          }
        );
    });
  }

  play(action: Action): Promise<ResultOfAPlay> {
    let req = '{\n' +
      `   "@id" : ${action.id},` +
      '  "type": "Action",\n' +
      '  "depart": {\n' +
      `   "@id" : ${action.depart.id},` +
      '    "type": "Coordonnee",\n' +
      `   "ligne": ${action.depart.ligne},\n` +
      `    "colonne": ${action.depart.colonne}\n` +
      '  },\n' +
      '  "arrivee": {\n' +
      `   "@id" : ${action.arrivee.id},` +
      '    "type": "Coordonnee",\n' +
      `    "ligne": ${action.arrivee.ligne},\n` +
      `    "colonne": ${action.arrivee.colonne}\n` +
      '  },\n' +
      '  "plateau": null\n' + // TODO test si on delete
      '}';
    console.log(JSON.parse(req));
    return new Promise((resolve, reject) => {
      this.httpClient
        .put('/game/play', JSON.parse(req))
        .subscribe(
          (response: any) => {
            resolve(ResultOfAPlay.fromJSON(response));
          },
          (error) => { // for test, TODO delete à la fin
            // resolve(RequesterBackEndService.getHardCodeRepInit());
          }
        );
    });
    /*return new Promise((resolve, reject) => {
        resolve(ResultOfAPlay.fromJSON(JSON.parse(RequesterBackEndService.getHardCodeRepPlay())));
      }
    );*/

  }


  save() {
    return new Promise(((resolve, reject) => {
      this.httpClient
        .get('/game/save')
        .subscribe(
          () => {
            resolve();
          },
          (error) => {
            reject();
          }
        );
    }));
  }

  getGameRegistrered(gameId: string) {
    return new Promise(((resolve, reject) => {
      this.httpClient
        .get(`/game/${gameId}`)
        .subscribe(
          (res: any) => {
            resolve(res);
          },
          (error) => {
            reject();
          }
        );
    }));
  }

  deleteGameRegistrered(gameId: string) {
    return new Promise(((resolve, reject) => {
      this.httpClient
        .delete(`/game/${gameId}`)
        .subscribe(
          (res: string) => {
            resolve(JSON.parse(res));
          },
          (error) => {
            reject();
          }
        );
    }));
  }


  static getHardCodeRepInit(): string {
    return '{\n' +
      '  "type": "GameManager",\n' +
      '  "@id": 1,\n' +
      '  "joueur1": {\n' +
      '    "type": "JoueurHumain",\n' +
      '    "@id": 1,\n' +
      '    "couleur": {\n' +
      '      "code": "#FF0000"\n' +
      '    },\n' +
      '    "pseudo": "ringo"\n' +
      '  },\n' +
      '  "joueur2": {\n' +
      '    "type": "JoueurHumain",\n' +
      '    "@id": 2,\n' +
      '    "couleur": {\n' +
      '      "code": "#000000"\n' +
      '    },\n' +
      '    "pseudo": "start"\n' +
      '  },\n' +
      '  "automate": {\n' +
      '    "type": "AutomateGameManager",\n' +
      '    "@id": 2,\n' +
      '    "etatCourant": {\n' +
      '      "type": "EtatJ1vsJ2",\n' +
      '      "@id": 3,\n' +
      '      "nbCoupRestant": 3\n' +
      '    }\n' +
      '  },\n' +
      '  "plateau": {\n' +
      '    "type": "PlateauStandard",\n' +
      '    "@id": 4,\n' +
      '    "lesCases": {\n' +
      '      "2:1": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 5,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "6:5": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 6,\n' +
      '        "pion": {\n' +
      '          "type": "Pion",\n' +
      '          "@id": 1,\n' +
      '          "joueur": 1\n' +
      '        },\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "2:2": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 7,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "6:6": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 8,\n' +
      '        "pion": {\n' +
      '          "type": "Pion",\n' +
      '          "@id": 2,\n' +
      '          "joueur": 1\n' +
      '        },\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "2:3": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 9,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "2:4": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 10,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "2:5": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 11,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "2:6": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 12,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "3:0": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 13,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "3:1": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 14,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "3:2": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 15,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "3:3": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 16,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "3:4": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 17,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "3:5": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 18,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "3:6": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 19,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "4:0": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 20,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "4:1": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 21,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "4:2": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 22,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "4:3": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 23,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "0:0": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 24,\n' +
      '        "pion": {\n' +
      '          "type": "Pion",\n' +
      '          "@id": 3,\n' +
      '          "joueur": 2\n' +
      '        },\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "4:4": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 25,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "0:1": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 26,\n' +
      '        "pion": {\n' +
      '          "type": "Pion",\n' +
      '          "@id": 4,\n' +
      '          "joueur": 2\n' +
      '        },\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "4:5": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 27,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "0:2": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 28,\n' +
      '        "pion": {\n' +
      '          "type": "Pion",\n' +
      '          "@id": 5,\n' +
      '          "joueur": 2\n' +
      '        },\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "4:6": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 29,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "0:3": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 30,\n' +
      '        "pion": {\n' +
      '          "type": "Pion",\n' +
      '          "@id": 6,\n' +
      '          "joueur": 2\n' +
      '        },\n' +
      '        "balle": {\n' +
      '          "type": "Balle",\n' +
      '          "@id": 31,\n' +
      '          "joueur": 2\n' +
      '        }\n' +
      '      },\n' +
      '      "0:4": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 32,\n' +
      '        "pion": {\n' +
      '          "type": "Pion",\n' +
      '          "@id": 7,\n' +
      '          "joueur": 2\n' +
      '        },\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "0:5": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 33,\n' +
      '        "pion": {\n' +
      '          "type": "Pion",\n' +
      '          "@id": 8,\n' +
      '          "joueur": 2\n' +
      '        },\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "0:6": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 34,\n' +
      '        "pion": {\n' +
      '          "type": "Pion",\n' +
      '          "@id": 9,\n' +
      '          "joueur": 2\n' +
      '        },\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "5:0": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 35,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "5:1": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 36,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "5:2": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 37,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "5:3": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 38,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "1:0": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 39,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "5:4": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 40,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "1:1": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 41,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "5:5": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 42,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "1:2": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 43,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "5:6": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 44,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "1:3": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 45,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "1:4": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 46,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "1:5": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 47,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "1:6": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 48,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "6:0": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 49,\n' +
      '        "pion": {\n' +
      '          "type": "Pion",\n' +
      '          "@id": 10,\n' +
      '          "joueur": 1\n' +
      '        },\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "6:1": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 50,\n' +
      '        "pion": {\n' +
      '          "type": "Pion",\n' +
      '          "@id": 11,\n' +
      '          "joueur": 1\n' +
      '        },\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "6:2": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 51,\n' +
      '        "pion": {\n' +
      '          "type": "Pion",\n' +
      '          "@id": 12,\n' +
      '          "joueur": 1\n' +
      '        },\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "6:3": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 52,\n' +
      '        "pion": {\n' +
      '          "type": "Pion",\n' +
      '          "@id": 13,\n' +
      '          "joueur": 1\n' +
      '        },\n' +
      '        "balle": {\n' +
      '          "type": "Balle",\n' +
      '          "@id": 53,\n' +
      '          "joueur": 1\n' +
      '        }\n' +
      '      },\n' +
      '      "2:0": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 54,\n' +
      '        "pion": null,\n' +
      '        "balle": null\n' +
      '      },\n' +
      '      "6:4": {\n' +
      '        "type": "Case",\n' +
      '        "@id": 55,\n' +
      '        "pion": {\n' +
      '          "type": "Pion",\n' +
      '          "@id": 14,\n' +
      '          "joueur": 1\n' +
      '        },\n' +
      '        "balle": null\n' +
      '      }\n' +
      '    }\n' +
      '  },\n' +
      '  "commandes": []\n' +
      '}';
  }

  static getHardCodeRepPlay(): string {
    return `{
  "type": "ResultOfAPlay",
  "@id": 1,
  "actions": [
    {
      "type": "Action",
      "@id": 2,
      "depart": {
        "type": "Coordonnee",
        "@id": 3,
        "ligne": 6,
        "colonne": 0
      },
      "arrivee": {
        "type": "Coordonnee",
        "@id": 4,
        "ligne": 5,
        "colonne": 0
      }
    }
   ],
  "gameWon": false,
  "winner": 1
  }`;
  }
}
