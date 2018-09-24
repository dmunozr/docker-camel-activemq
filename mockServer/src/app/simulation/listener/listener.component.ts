import {Component} from '@angular/core';
import {animate, state, style, transition, trigger} from '@angular/animations';
import { SimulationMessageResponse } from '../model/simulation-message-response.model';

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
export class ListenerComponent {
  dataSource = ELEMENT_DATA;
  columnsToDisplay = ['Car Info', 'Client Info', 'Additional Info', 'Image'];
  expandedElement: SimulationMessageResponse;
}

const ELEMENT_DATA: SimulationMessageResponse[] = [
  {
    car: {
      imageUrl: 'https://www.hdcarwallpapers.com/walls/2017_peugeot_5008_gt_line_4k-HD.jpg',
      brand: 'Peugeot',
      model: '5008',
      version: 'GT Line',
      year: '2017'
    }, additionalInfo: {
      minPrice: 44929.2,
      maxPrice: 50118.395,
      availability: 'HIGH'
    }, client: {
      firstName: 'Daniel',
      lastName: 'Muñoz Rivas',
      telephone: '690525252',
      email: 'daniel@gmail.com'
    }, transactionInfo: {
      sponsorId: 'www.coches.net'
    }
  }, {
    car: {
      imageUrl: null,
      brand: 'Peugeot',
      model: '5008',
      version: 'GT Line',
      year: '2017'
    }, additionalInfo: {
      minPrice: 44929.2,
      maxPrice: 50118.395,
      availability: 'HIGH'
    }, client: {
      firstName: 'Daniel',
      lastName: 'Muñoz Rivas',
      telephone: '690525252',
      email: 'daniel@gmail.com'
    }, transactionInfo: {
      sponsorId: 'www.coches.net'
    }
  }
];
