import  { Car } from './car.model';
import  { Client } from './client.model';
import { AdditionalInfo } from './additional-info.model';
import { TransactionInfo } from './transaction-info.model';

export class SimulationMessageResponse {

    public car: Car;
    public additionalInfo: AdditionalInfo;
    public client: Client;
    public transactionInfo: TransactionInfo;

    constructor(car: Car, additionalInfo: AdditionalInfo, client: Client, transactionInfo: TransactionInfo) {
        this.car = car;
        this.additionalInfo = additionalInfo;
        this.client = client;
        this.transactionInfo = transactionInfo;
    }
}
