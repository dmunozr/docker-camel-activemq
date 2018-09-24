import { SimulationRequest } from '../model/simulation-request.model';
import { SimulationMessageResponse } from '../model/simulation-message-response.model';
import * as SimulationActions from './simulation.actions';
import * as fromApp from '../../store/app.reducers';

const initialState: SimulationMessageResponse[] = [];

export function simulationReducer(simulationsState = initialState, action: SimulationActions.SimulationActions): SimulationMessageResponse[] {
  switch (action.type) {
    case (SimulationActions.APPEND_SIMULATION_MESSAGES):
      if(action.payload.messages.length > 0){
        console.log('appening! ' + JSON.stringify([...simulationsState, ...action.payload.messages ]));
      }
      return [...simulationsState, ...action.payload.messages ];
      /* orig
      return {
        ...simulationsState,
        simulationsReceived: [...simulationsState.simulationsReceived, ...action.payload.messages]
      };
      */
    default:
      return simulationsState;
  }
}
