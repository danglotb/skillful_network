import { TrainingApplication } from './training-application';

export class Training {

    private _id: number;
    private _name: string;
    private _organization: string;
    private _description: string;
    private _dateBeg: Date;
    private _dateEnd: Date;
    private _durationHours: number;
    private _dateUpload: Date;
    private _keywords: string[];
    private _trainingApplications: TrainingApplication[];

    constructor(data: any) {
        this.id = data.id;
        this.name = data.name;
        this.organization = data.organization;
        this.description = data.description;
        this.dateBeg = data.dateBeg;
        this.dateEnd = data.dateEnd;
        this.durationHours = data.durationHours;
        this.dateUpload = data.dateUpload;
        this.keywords = data.keywords;
        this.trainingApplications = data.trainingApplicationSet;
    }

    public set trainingApplications(trainingApplications : TrainingApplication[]) {
        this._trainingApplications = trainingApplications;
    }

    public get trainingApplications() : TrainingApplication[] {
        return this._trainingApplications;
    }

    public get organization() : string {
        return this._organization;
    }
    
    public set organization(organization: string) {
        this._organization = organization;
    }

    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
    }

    get description(): string {
        return this._description;
    }

    set description(value: string) {
        this._description = value;
    }

    get keywords(): string[] {
        return this._keywords;
    }

    set keywords(value: string[]) {
        this._keywords = value;
    }

    get dateUpload(): Date {
        return this._dateUpload;
    }

    set dateUpload(value: Date) {
        this._dateUpload = value;
    }

    get durationHours(): number {
        return this._durationHours;
    }

    set durationHours(value: number) {
        this._durationHours = value;
    }

    get dateEnd(): Date {
        return this._dateEnd;
    }

    set dateEnd(value: Date) {
        this._dateEnd = value;
    }

    get name(): string {
        return this._name;
    }

    set name(value: string) {
        this._name = value;
    }

    get dateBeg(): Date {
        return this._dateBeg;
    }

    set dateBeg(value: Date) {
        this._dateBeg = value;
    }

}