export class Role {

    public value: string;
    public valueView: string;

    constructor(data: any) {
        this.value = data.value;
        this.valueView = data.valueView;
    }
}