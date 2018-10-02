import { Car } from './car.model';
import { Client } from './client.model';

export class TransactionInfo {

    public sponsorId: string;

    constructor(sponsorId: string) {
        this.sponsorId = sponsorId;
    }
}
