
export class Car {

    public brand: string;
    public model: string;
    public version: string;
    public year: string;
    public imageUrl: string;

    constructor(brand: string, model: string, version: string, year: string, imageUrl: string) {
        this.brand = brand;
        this.model = model;
        this.version = version;
        this.year = year;
        this.imageUrl = imageUrl;
    }
}
