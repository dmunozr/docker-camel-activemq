import { ActionReducerMap } from '@ngrx/store';

import * as fromSimulation from '../simulation/store/simulation.reducers';
import { SimulationMessageResponse } from '../simulation/model/simulation-message-response.model';

export interface AppState {
  simulations: SimulationMessageResponse[]
}

export const reducers: ActionReducerMap<AppState> = {
    simulations: fromSimulation.simulationReducer
};
