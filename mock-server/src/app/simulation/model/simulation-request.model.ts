import { Car } from './car.model';
import { Client } from './client.model';

export class SimulationRequest {

    public sponsor: string;
    public car: Car;
    public client: Client;

    constructor(sponsor: string, car: Car, client: Client) {
        this.sponsor = sponsor;
        this.car = car;
        this.client = client;
    }
}
