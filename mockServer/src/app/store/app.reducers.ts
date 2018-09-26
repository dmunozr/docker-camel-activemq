import { ActionReducerMap } from '@ngrx/store';

import * as fromSimulation from '../simulation/store/simulation.reducers';
import { SimulationMessageResponse } from '../simulation/model/simulation-message-response.model';

import * as fromNotification from '../notifications/store/notification.reducers';
import { Notification } from '../notifications/model/notification.model';

export interface AppState {
  simulations: SimulationMessageResponse[],
  notifications: Notification[]
}

export const reducers: ActionReducerMap<AppState> = {
    simulations: fromSimulation.simulationReducer,
    notifications: fromNotification.notificationsReducer
};
