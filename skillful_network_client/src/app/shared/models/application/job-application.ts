import { Application } from './application';
import { JobOffer } from './job-offer';

export class JobApplication extends Application {

    private _jobOffer: JobOffer;

    constructor(data: any) {
       super(data);
       this._jobOffer = data.offer;
    }

    public set jobOffer(jobOffer: JobOffer) {
        this._jobOffer = jobOffer;
    }

    public get jobOffer() : JobOffer {
        return this._jobOffer;
    }

}

