export class Perk {

    public id: number;
    public name: string;

    constructor(data: any) {
        this.id = data.id;
        this.name = data.name;
    }

    static convertToString(perks: Perk[]) : string[] {
        let converted : string[] = [];
        for (let i in perks) {
          converted.push(perks[i].name);
        }
        return converted;
      }

}