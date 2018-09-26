import { Injectable } from '@angular/core';
import { Actions, Effect } from '@ngrx/effects';
import { switchMap, map, concatMap } from 'rxjs/operators';
import { HttpClient, HttpRequest, HttpHeaders } from '@angular/common/http';
import { Store} from '@ngrx/store';

import * as SimulationActions from './simulation.actions';
import * as NotificationActions from '../../notifications/store/notification.actions';
import { Notification } from '../../notifications/model/notification.model';
import { SimulationMessageResponse } from '../model/simulation-message-response.model';
import { SimulationMessagesResponse } from '../model/simulation-messages-response.model';
import { empty, of } from 'rxjs';

@Injectable()
export class SimulationEffects {

    readonly requestEndpoint: string = 'http://localhost:8080/rest/v1/requests';
    readonly messagesEndpoint: string = 'http://localhost:8082/rest/v1/messages';

    constructor(private actions$: Actions,
      private httpClient: HttpClient,
      private notificationsStore: Store<Notification[]>,
      private simulationsStore: Store<SimulationMessageResponse[]>) {
    }

    @Effect({dispatch: false})
    sendSimulation = this.actions$
      .ofType(SimulationActions.SEND_SIMULATION_REQUEST)
      .pipe(
        concatMap(
          (simulationRequest: SimulationActions.SendSimulation) => {
            let sponsor = simulationRequest.payload.sponsor;
            delete simulationRequest.payload.sponsor;
            let requestBody = { "requests": [ simulationRequest.payload ] };
            const httpHeaders: HttpHeaders = new HttpHeaders({ 'SponsorWebsite': sponsor });
            const req = new HttpRequest('POST', this.requestEndpoint, requestBody, { headers: httpHeaders });
            
            this.httpClient.request(req).subscribe(
              result => {},
              error => { 
                const notification: Notification = new Notification('Unexpected error! Please, try again.', 'danger');
                this.notificationsStore.dispatch(new NotificationActions.CreateNotification(notification));
                return empty();
              },
              () => {
                const notification: Notification = new Notification('Request sent!', 'success');
                this.notificationsStore.dispatch(new NotificationActions.CreateNotification(notification));
                return empty();
              }
            )
            return empty();
          }
        )
      );

    @Effect({dispatch: false})
    simulationFetch = this.actions$
      .ofType(SimulationActions.RECEIVE_SIMULATION_MESSAGES)
      .pipe(switchMap((action: SimulationActions.ReceiveSimulationMessages) => {
        this.httpClient.get<SimulationMessagesResponse>(this.messagesEndpoint, {
          observe: 'body',
          responseType: 'json'
        }).subscribe(
          result => {
            this.simulationsStore.dispatch(new SimulationActions.AppendSimulationMessages(result)); 
          },
          error => {
            const notification: Notification = new Notification('Unexpected error while retrieving the requests!', 'danger');
            this.notificationsStore.dispatch(new NotificationActions.CreateNotification(notification));
            return empty();
          }
        )
        return empty();
      }));
  
}
