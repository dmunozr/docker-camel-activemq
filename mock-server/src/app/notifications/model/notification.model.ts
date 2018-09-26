
export class Notification {

    public message: string;
    public type: string;
    public creationTimeInMilliseconds: number;

    constructor(message: string, type: string) {
        this.message = message;
        this.type = type;
        this.creationTimeInMilliseconds = +new Date();
    }
}
