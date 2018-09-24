import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { FormGroup, FormControl, FormArray, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';

import * as SimulationActions from '../store/simulation.actions';
import { SimulationRequest } from '../model/simulation-request.model';
import * as fromApp from '../../store/app.reducers';
import { Car } from '../model/car.model';
import { Client } from '../model/client.model';

@Component({
  selector: 'app-sender',
  templateUrl: './sender.component.html',
  styleUrls: ['./sender.component.css']
})
export class SenderComponent implements OnInit {

  sendForm: FormGroup;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private store: Store<fromApp.AppState>) {
  }

  ngOnInit() {
    this.initForm();
  }

  onSubmit(){
    console.log('Sending the request: ' + JSON.stringify(this.sendForm.value));
    let sponsor: string = this.sendForm.value['sponsor'];
    let car: Car = new Car(this.sendForm.value['brand'],this.sendForm.value['model'],this.sendForm.value['version'],this.sendForm.value['year'], null);
    let client: Client = new Client(this.sendForm.value['firstName'],this.sendForm.value['lastName'],this.sendForm.value['telephone'],this.sendForm.value['email']);

    let simulationRequest : SimulationRequest = new SimulationRequest(sponsor,car,client);

    this.store.dispatch(new SimulationActions.SendSimulation(simulationRequest));
    // this.sendForm.reset();
  }

  onCancel() {
    this.router.navigate(['../'], {relativeTo: this.route});
  }

  private initForm() {
    let sponsor : string = 'www.coches.net';
    let brand : string = 'Peugeot';
    let model : string = '5008';
    let version : string = 'GT Line';
    let year : number = (new Date()).getFullYear();

    let firstName : string = 'Daniel';
    let lastName : string = 'Muñoz';
    let telephone : string = '123123123';
    let email : string = 'example@test.com';

    this.sendForm = new FormGroup({
      'sponsor': new FormControl(sponsor, Validators.required),
      'brand': new FormControl(brand, Validators.required),
      'model': new FormControl(model, Validators.required),
      'version': new FormControl(version),
      'year': new FormControl(year, [Validators.required, 
                                      Validators.pattern('^[1-2][0-9][0-9][0-9]$')]),
      'firstName': new FormControl(firstName, Validators.required),
      'lastName': new FormControl(lastName, Validators.required),
      'telephone': new FormControl(telephone, [Validators.required,Validators.pattern('^[0-9]+$')]),
      'email': new FormControl(email, [Validators.required, Validators.email])
    });

  }
}
