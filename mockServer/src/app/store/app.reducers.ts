import { ActionReducerMap } from '@ngrx/store';

import * as fromSimulation from '../simulation/store/simulation.reducers';

export interface AppState {
  simulations: fromSimulation.State
}

export const reducers: ActionReducerMap<AppState> = {
    simulations: fromSimulation.simulationReducer
};
