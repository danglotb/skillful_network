export class ListElement {
    public header: string;
    public matColumnDef: string;

    constructor(
        header: string,
        matColumnDef: string
    ) {
        this.header = header;
        this.matColumnDef = matColumnDef;
    }
}