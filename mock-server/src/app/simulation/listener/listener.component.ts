import {Component, OnInit, OnDestroy} from '@angular/core';
import {animate, state, style, transition, trigger} from '@angular/animations';
import { SimulationMessageResponse } from '../model/simulation-message-response.model';
import { interval, Observable, Subscription } from 'rxjs';
import { Store } from '@ngrx/store';

import * as fromApp from '../../store/app.reducers';
import * as SimulationActions from '../store/simulation.actions';

@Component({
  selector: 'app-listener',
  styleUrls: ['./listener.component.css'],
  templateUrl: './listener.component.html',
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0', display: 'none'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class ListenerComponent implements OnInit, OnDestroy {

  dataSource: Observable<SimulationMessageResponse[]>;
  columnsToDisplay = ['Car Info', 'Client Info', 'Additional Info', 'Image'];
  expandedElement: SimulationMessageResponse;
  timer: Observable<any>;
  timerSubscription: Subscription;

  constructor(private store: Store<fromApp.AppState>) {
  }

  ngOnInit() {
    this.dataSource = this.store.select('simulations');

    this.timer = interval(2000);
    this.timerSubscription = this.timer.subscribe( 
      () => {
        this.store.dispatch(new SimulationActions.ReceiveSimulationMessages());
    });
  }

  ngOnDestroy() {
    this.timerSubscription.unsubscribe();
  }  

  showBrandsInfo(){
    alert("Only the messages containing the brand 'Peugeot' and 'Ford' are processed by the 'final-consumer' application. \nTherefore, if you select another brand, the message will be rejected and it will not appear in the list below.");
  }
}
