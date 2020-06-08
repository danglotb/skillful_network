import { Application } from './application';
import { Training } from './training';


export class TrainingApplication extends Application {

    private _training: Training;

    constructor(data: any) {
       super(data);
       this.training = data.offer;
    }

    public set training(training: Training) {
        this.training = training;
    }

    public get training() : Training {
        return this.training;
    }

}

