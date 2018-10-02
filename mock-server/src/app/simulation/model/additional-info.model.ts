
export class AdditionalInfo {

    public minPrice: number;
    public maxPrice: number;
    public availability: string;

    constructor(minPrice: number, maxPrice: number, availability: string) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.availability = availability;
    }
}
