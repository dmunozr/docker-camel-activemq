import { SimulationMessageResponse } from '../model/simulation-message-response.model';
import * as SimulationActions from './simulation.actions';

const initialState: SimulationMessageResponse[] = [];

export function simulationReducer(simulationsState = initialState, action: SimulationActions.SimulationActions): SimulationMessageResponse[] {
  switch (action.type) {
    case (SimulationActions.APPEND_SIMULATION_MESSAGES):
      return [...simulationsState, ...action.payload.messages ];
    default:
      return simulationsState;
  }
}
