import { Car } from './car.model';
import { Client } from './client.model';
import { SimulationMessageResponse } from './simulation-message-response.model';

export class SimulationMessagesResponse {

    public messages: SimulationMessageResponse[];

    constructor(messages: SimulationMessageResponse[]) {
        this.messages = messages;
    }
}
