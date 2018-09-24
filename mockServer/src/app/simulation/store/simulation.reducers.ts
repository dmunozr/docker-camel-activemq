import { SimulationRequest } from '../model/simulation-request.model';
import { SimulationMessagesResponse } from '../model/simulation-messages-response.model';
import * as SimulationActions from './simulation.actions';
import * as fromApp from '../../store/app.reducers';


export interface FeatureState extends fromApp.AppState {
  simulations: State
}

export interface State {
  simulationsSent: SimulationRequest[];
  simulationsReceived: SimulationMessagesResponse[];
}

const initialState: State = {
  simulationsSent: [],
  simulationsReceived: []
};

export function simulationReducer(simulationState = initialState, action: SimulationActions.SimulationActions) {
  switch (action.type) {
    case (SimulationActions.SEND_SIMULATION_REQUEST):
      return {
        ...simulationState,
        simulationSent: [...simulationState.simulationsSent, action.payload]
      };
    case (SimulationActions.APPEND_SIMULATION_MESSAGES_RESPONSE):
      return {
        ...simulationState,
        simulationReceived: [...simulationState.simulationsReceived, ...action.payload.messages]
      };
    default:
      return simulationState;
  }
}
