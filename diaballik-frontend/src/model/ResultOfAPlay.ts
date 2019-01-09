import {Action} from './Action';

export class ResultOfAPlay {
  actions: Action[];
  gameWon: boolean;
  winner: number;


  constructor(actions: Action[], gameWon: boolean, winner: number) {
    this.actions = actions;
    this.gameWon = gameWon;
    this.winner = winner;
  }

  static fromJSON(result): ResultOfAPlay {
    const actions: Action[] = [];
    const mapIdAction = {};
    for (const action of result.actions) {
      if (action.depart !== undefined) {

        const actionObj = Action.fromJSON(action);

        actions.push(actionObj);
        mapIdAction[action['@id']] = actionObj;

      } else {
        actions.push(mapIdAction[action['@id']]);
      }

    }
    return new ResultOfAPlay(actions, result.gameWon, result.winner);
  }
}
