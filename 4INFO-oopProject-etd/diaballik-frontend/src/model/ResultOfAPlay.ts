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
    let actions: Action[] = [];
    for (let action of result.actions) {
      actions.push(Action.fromJSON(action));
    }
    return new ResultOfAPlay(actions, result.gameWon, result.winner);
  }
}
