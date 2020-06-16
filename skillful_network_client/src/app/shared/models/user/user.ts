import { JobApplication } from '../application/job-application';
import { TrainingApplication } from '../application/training-application';
import { Perk } from './perk';

export class User {

  public id: number = -1;
  public firstName: string;
  public lastName: string;
  public password: string;
  public birthDate: Date;
  public email: string;
  public mobileNumber: string;
  public validated: boolean;
  public careerGoal: string;
  public photo: boolean;
  public skillSet: Perk[];
  public qualificationSet: Perk[];
  public subscriptionSet: Perk[];
  public photoProfile: any;
  public role: string[];
  public jobApplications: JobApplication[];
  public trainingApplications: TrainingApplication[];

  constructor(data: any) {
    this.id = data.id;
    this.firstName = data.firstName;
    this.email = data.email;
    this.lastName = data.lastName;
    this.password = data.password;
    this.birthDate = data.birthDate;
    this.email = data.email;
    this.mobileNumber = data.mobileNumber;
    this.validated = data.validated;
    this.photo = data.photo;
    this.skillSet = data.skillSet;
    this.qualificationSet = data.qualificationSet;
    this.subscriptionSet = data.subscriptionSet;
    this.photoProfile = data.photoProfile;
    this.careerGoal = data.careerGoal;
    this.role = ['user'];
    this.trainingApplications = data.trainingApplicationSet;
    this.jobApplications = data.jobApplicationSet;
  }

}
