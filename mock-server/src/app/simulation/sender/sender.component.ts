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
  sponsors: string[] = this.getSponsors();
  carBrands: string[] = this.getCarBrands();

  samples: SimulationRequest[] = this.getSamples();

  constructor(private route: ActivatedRoute,
              private router: Router,
              private store: Store<fromApp.AppState>) {
  }

  ngOnInit() {
    this.initForm();
  }

  getSponsors(){
    return [ 'www.autocasion.com', 'www.autoscout24.es', 'www.coches.net'];
  }

  getCarBrands(){
    return [ '', 'Alfa Romeo', 'Aston Martin', 'Audi', 'Austin', 'Bentley', 'BMW', 'Cadillac', 'Chevrolet', 'Chrysler', 'Citroen', 'Dacia', 'Daewoo', 'Daihatsu', 'Dodge', 'Ferrari', 'Fiat', 'Ford', 'Galloper', 'Honda', 'Hummer', 'Hyundai', 'Infiniti', 'Isuzu', 'Jaguar', 'Jeep', 'Kia', 'Lada', 'Lamborghini', 'Lancia', 'Land-Rover', 'Lexus', 'Lotus', 'Maserati', 'Mazda', 'Mercedes', 'MG', 'Mini', 'Mitsubishi', 'Nissan', 'Opel', 'Peugeot', 'Pontiac', 'Porsche', 'Renault', 'Rolls-Royce', 'Rover', 'Saab', 'Seat', 'Skoda', 'Smart', 'Ssangyong', 'Subaru', 'Suzuki', 'Talbot', 'Tata', 'Toyota', 'Volkswagen', 'Volvo' ];
  }

  onSubmit(){
    console.log('Sending the request: ' + JSON.stringify(this.sendForm.value));
    let sponsor: string = this.sendForm.value['sponsor'];
    let car: Car = new Car(this.sendForm.value['brand'],this.sendForm.value['model'],this.sendForm.value['version'],this.sendForm.value['year'], null);
    let client: Client = new Client(this.sendForm.value['firstName'],this.sendForm.value['lastName'],this.sendForm.value['telephone'],this.sendForm.value['email']);

    let simulationRequest : SimulationRequest = new SimulationRequest(sponsor,car,client);

    this.store.dispatch(new SimulationActions.SendSimulation(simulationRequest));
  }

  onCancel() {
    this.sendForm.reset();
    //this.router.navigate(['../'], {relativeTo: this.route});
  }

  private initForm() {
    let year : number = (new Date()).getFullYear();

    this.sendForm = new FormGroup({
      'sponsor': new FormControl(null, Validators.required),
      'brand': new FormControl(null, Validators.required),
      'model': new FormControl(null, Validators.required),
      'version': new FormControl(null),
      'year': new FormControl(year, [Validators.required, 
                                      Validators.pattern('^[1-2][0-9][0-9][0-9]$')]),
      'firstName': new FormControl(null, Validators.required),
      'lastName': new FormControl(null, Validators.required),
      'telephone': new FormControl(null, [Validators.required,Validators.pattern('^[0-9]+$')]),
      'email': new FormControl(null, [Validators.required, Validators.email]),
      'sample': new FormControl()
    });

  }

  onChangeBrand() {    
    this.sendForm.controls.model.reset();
    this.sendForm.controls.version.reset();
    this.sendForm.controls.year.reset();
  }

  getSamples(){
    let samples: SimulationRequest[] = [];

    let sponsor1: string = this.sponsors[0];
    let car1: Car = new Car('Peugeot','5008','GT Line','2018',null);
    let client1: Client = new Client('Daniel','Muñoz','690123123','daniel@test.com');
    samples[0] = new SimulationRequest(sponsor1,car1,client1);

    let sponsor2: string = this.sponsors[1];
    let car2: Car = new Car('Ford','Focus','Titanium','2017',null);
    let client2: Client = new Client('Elisabeth','Guisado','620111222','eli@test.com');
    samples[1] = new SimulationRequest(sponsor2,car2,client2);

    let sponsor3: string = this.sponsors[2];
    let car3: Car = new Car('Mercedes','GLA','200','2018',null);
    let client3: Client = new Client('Pablo','Muñoz','617555777','pablo@test.com');

    samples[2] = new SimulationRequest(sponsor3,car3,client3);

    return samples;
  }

  onLoadSample(){
    switch(this.sendForm.controls.sample.value){
      case this.samples[0]:
        this.loadSample(0);
        break;
      case this.samples[1]:
        this.loadSample(1);
        break;
      case this.samples[2]:
        this.loadSample(2);
        break;
      default:
        alert("Please, select a sample and then click the button 'Load Sample'.");
    }
    this.sendForm.controls.sample.reset();
  }

  loadSample(sampleId: number){
    const simulationRequest: SimulationRequest = this.samples[sampleId];

    this.sendForm.controls.sponsor.setValue(simulationRequest.sponsor);

    this.sendForm.controls.brand.setValue(simulationRequest.car.brand);
    this.sendForm.controls.model.setValue(simulationRequest.car.model);
    this.sendForm.controls.version.setValue(simulationRequest.car.version);
    this.sendForm.controls.year.setValue(simulationRequest.car.year);

    this.sendForm.controls.firstName.setValue(simulationRequest.client.firstName);
    this.sendForm.controls.lastName.setValue(simulationRequest.client.lastName);
    this.sendForm.controls.telephone.setValue(simulationRequest.client.telephone);
    this.sendForm.controls.email.setValue(simulationRequest.client.email);
  }
}
