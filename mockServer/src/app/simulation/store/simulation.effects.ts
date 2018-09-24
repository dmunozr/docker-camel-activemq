import {Injectable} from '@angular/core';
import {Actions, Effect} from '@ngrx/effects';
import {switchMap, withLatestFrom, map} from 'rxjs/operators';
import {HttpClient, HttpRequest, HttpHeaders} from '@angular/common/http';
import {Store} from '@ngrx/store';

import * as SimulationActions from './simulation.actions';
import { SimulationMessagesResponse } from '../model/simulation-messages-response.model';
import * as fromSimulation from './simulation.reducers';

@Injectable()
export class SimulationEffects {

    readonly requestEndpoint: string = 'http://localhost:8080/rest/v1/requests';
    readonly messagesEndpoint: string = 'http://localhost:8082/rest/v1/messages';

    constructor(private actions$: Actions,
      private httpClient: HttpClient,
      private store: Store<fromSimulation.FeatureState>) {
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

/*
    @Effect()
  recipeFetch = this.actions$
    .ofType(SimulationActions.FETCH_RECIPES)
    .pipe(switchMap((action: RecipeActions.FetchRecipes) => {
      return this.httpClient.get<Recipe[]>('https://ng-recipe-book-3adbb.firebaseio.com/recipes.json', {
        observe: 'body',
        responseType: 'json'
      });
    }), map(
      (recipes) => {
        console.log(recipes);
        for (let recipe of recipes) {
          if (!recipe['ingredients']) {
            recipe['ingredients'] = [];
          }
        }
        return {
          type: RecipeActions.SET_RECIPES,
          payload: recipes
        };
      }
    ));
*/

}
