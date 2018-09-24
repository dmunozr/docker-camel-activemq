import { Action } from '@ngrx/store';
import { SimulationRequest } from '../model/simulation-request.model'; 
import { SimulationMessagesResponse } from '../model/simulation-messages-response.model';


export const SEND_SIMULATION_REQUEST = 'SEND_SIMULATION_REQUEST';
export const APPEND_SIMULATION_MESSAGES_RESPONSE = 'APPEND_SIMULATION_MESSAGES_RESPONSE';

export class SendSimulation implements Action {
    readonly type = SEND_SIMULATION_REQUEST;
  
    constructor(public payload: SimulationRequest) {}
}
  
export class AppendSimulationMessagesResponse implements Action {
  readonly type = APPEND_SIMULATION_MESSAGES_RESPONSE;

  constructor(public payload: SimulationMessagesResponse) {}
}

export type SimulationActions = SendSimulation | AppendSimulationMessagesResponse;
