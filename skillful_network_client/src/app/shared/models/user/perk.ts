export class Perk {

    private _id: number;
    private _name: string;

    constructor(data: any) {
        this.id = data.id;
        this.name = data.name;
    }

    public set id(id: number) {
        this._id = id;
    }

    public get id() : number {
        return this._id;
    }

    public set name(name: string) {
        this._name = name;
    }

    public get name() : string {
        return this._name;
    }

    static convertToString(perks: Perk[]) : string[] {
        let converted : string[] = [];
        for (let i in perks) {
          converted.push(perks[i].name);
        }
        return converted;
      }

}