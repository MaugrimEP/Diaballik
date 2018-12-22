import {ShortGameInfo} from '../model/ShortGameInfo';

export class RequesterBackEndService {

  getListShortGame(): Promise<ShortGameInfo[]> {
    return new Promise<ShortGameInfo[]>((resolve, reject) => {
        setTimeout(
          () => {
            const json = '[\n' +
              '  {\n' +
              '    "type": "SmallerGameManager",\n' +
              '    "@id": 1,\n' +
              '    "joueur1": {\n' +
              '      "type": "JoueurHumain",\n' +
              '      "@id": 1,\n' +
              '      "couleur": {\n' +
              '        "code": "#FF0000"\n' +
              '      },\n' +
              '      "pseudo": "ringo"\n' +
              '    },\n' +
              '    "joueur2": {\n' +
              '      "type": "JoueurHumain",\n' +
              '      "@id": 2,\n' +
              '      "couleur": {\n' +
              '        "code": "#000000"\n' +
              '      },\n' +
              '      "pseudo": "start"\n' +
              '    },\n' +
              '    "date": 1543950017516\n' +
              '  }\n' +
              ']';
            resolve(ShortGameInfo.from(json));
          }, 2000
        );
      }
    );
  }


}
