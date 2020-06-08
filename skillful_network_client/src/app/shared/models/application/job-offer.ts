import { JobApplication } from './job-application';

export class JobOffer {

    private _id: number;
    private _name: string;
    private _company: string;
    private _description: string;
    private _type: string;
    private _dateBeg: Date;
    private _dateEnd: Date;
    private _dateUpload: Date;
    private _keywords: string[];
    private _risk: string;
    private _complexity: string;
    private _jobApplications: JobApplication[];

    constructor(data: any) {
        this.id = data.id;
        this.name = data.name;
        this.company = data.company;
        this.description = data.description;
        this.type = data.type;
        this.dateBeg = data.dateBeg;
        this.dateEnd = data.dateEnd;
        this.dateUpload = data.dateUpload;
        this.keywords = data.keywords;
        this.risk = data.risk;
        this.complexity = data.complexity;
        this.jobApplications = data.jobApplicationSet;
    }

    public set jobApplications(jobApplications : JobApplication[]) {
        this._jobApplications = jobApplications;
    }

    public get jobApplications() : JobApplication[] {
        return this._jobApplications;
    }


    public get risk() : string {
        return this._risk;
    }

    public set risk(risk: string) {
        this._risk = risk;
    }

    public get complexity() : string {
        return this._complexity;
    }

    public set complexity(complexity : string) {
        this._complexity = complexity;
    }

    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
    }

    get name(): string {
        return this._name;
    }

    set name(value: string) {
        this._name = value;
    }

    get company(): string {
        return this._company;
    }

    set company(value: string) {
        this._company = value;
    }

    get description(): string {
        return this._description;
    }

    set description(value: string) {
        this._description = value;
    }

    get type(): string {
        return this._type;
    }

    set type(value: string) {
        this._type = value;
    }

    get dateBeg(): Date {
        return this._dateBeg;
    }

    set dateBeg(value: Date) {
        this._dateBeg = value;
    }

    get dateEnd(): Date {
        return this._dateEnd;
    }

    set dateEnd(value: Date) {
        this._dateEnd = value;
    }

    get dateUpload(): Date {
        return this._dateUpload;
    }

    set dateUpload(value: Date) {
        this._dateUpload = value;
    }

    get keywords(): string[] {
        return this._keywords;
    }

    set keywords(value: string[]) {
        this._keywords = value;
    }
}
