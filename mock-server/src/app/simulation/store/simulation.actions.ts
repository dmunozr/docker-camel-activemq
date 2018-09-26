import { Action } from '@ngrx/store';
import { SimulationRequest } from '../model/simulation-request.model'; 
import { SimulationMessagesResponse } from '../model/simulation-messages-response.model';


export const SEND_SIMULATION_REQUEST = 'SEND_SIMULATION_REQUEST';
export const RECEIVE_SIMULATION_MESSAGES = 'RECEIVE_SIMULATION_MESSAGES';
export const APPEND_SIMULATION_MESSAGES = 'APPEND_SIMULATION_MESSAGES';

export class SendSimulation implements Action {
    readonly type = SEND_SIMULATION_REQUEST;
  
    constructor(public payload: SimulationRequest) {}
}
  
export class ReceiveSimulationMessages implements Action {
  readonly type = RECEIVE_SIMULATION_MESSAGES;

  constructor() {}
}

export class AppendSimulationMessages implements Action {
  readonly type = APPEND_SIMULATION_MESSAGES;

  constructor(public payload: SimulationMessagesResponse) {}
}

export type SimulationActions = SendSimulation | ReceiveSimulationMessages | AppendSimulationMessages;
