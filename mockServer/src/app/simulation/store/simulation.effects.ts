import {Injectable} from '@angular/core';
import {Actions, Effect} from '@ngrx/effects';
import {switchMap, withLatestFrom, map} from 'rxjs/operators';
import {HttpClient, HttpRequest, HttpHeaders} from '@angular/common/http';
import {Store} from '@ngrx/store';

import * as SimulationActions from './simulation.actions';
import { SimulationMessagesResponse } from '../model/simulation-messages-response.model';
import * as fromSimulation from './simulation.reducers';
import { SimulationMessageResponse } from '../model/simulation-message-response.model';

@Injectable()
export class SimulationEffects {

    readonly requestEndpoint: string = 'http://localhost:8080/rest/v1/requests';
    readonly messagesEndpoint: string = 'http://localhost:8082/rest/v1/messages';

    constructor(private actions$: Actions,
      private httpClient: HttpClient,
      private store: Store<SimulationMessageResponse[]>) {
    }

    @Effect({dispatch: false})
    simulationStore = this.actions$
        .ofType(SimulationActions.SEND_SIMULATION_REQUEST)
        .pipe(switchMap((simulationRequest: SimulationActions.SendSimulation) => {
            console.log(JSON.stringify(simulationRequest.payload));
            let sponsor = simulationRequest.payload.sponsor;
            delete simulationRequest.payload.sponsor;
            let requestBody = { "requests": [ simulationRequest.payload ] };
            // let headersValue = { 'SponsorWebsite': sponsor, 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*'};
   //         const httpHeaders: HttpHeaders = new HttpHeaders({'SponsorWebsite': sponsor});
            const httpHeaders: HttpHeaders = new HttpHeaders({ 'SponsorWebsite': sponsor });
            //httpHeaders.append('SponsorWebsite',sponsor);
            const req = new HttpRequest('POST', this.requestEndpoint, requestBody, { headers: httpHeaders });
            return this.httpClient.request(req);
        }));


    @Effect()
    simulationFetch = this.actions$
      .ofType(SimulationActions.RECEIVE_SIMULATION_MESSAGES)
      .pipe(switchMap((action: SimulationActions.ReceiveSimulationMessages) => {
        return this.httpClient.get<SimulationMessagesResponse>(this.messagesEndpoint, {
          observe: 'body',
          responseType: 'json'
        });
      }), map(
        (simulationMessagesResponse) => {
          //console.log(simulationMessagesResponse);
          return {
            type: SimulationActions.APPEND_SIMULATION_MESSAGES,
            payload: simulationMessagesResponse
          };
        }
      ));

}
