import { JobApplication } from '../application/job-application';
import { TrainingApplication } from '../application/training-application';
import { Perk } from './perk';
import { DomSanitizer } from '@angular/platform-browser';

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
  public skillSet: Perk[];
  public qualificationSet: Perk[];
  public subscriptionSet: Perk[];
  public jobApplicationSet: JobApplication[];
  public trainingApplicationSet: TrainingApplication[];
  public roles: string[];
  public profilePicture: any;

  constructor(
    data: any,
    // private sanitizer: DomSanitizer
  ) {
    this.id = data.id;
    this.firstName = data.firstName;
    this.email = data.email;
    this.lastName = data.lastName;
    this.password = data.password;
    this.birthDate = data.birthDate;
    this.email = data.email;
    this.mobileNumber = data.mobileNumber;
    this.validated = data.validated;
    this.skillSet = data.skillSet;
    this.qualificationSet = data.qualificationSet;
    this.subscriptionSet = data.subscriptionSet;
    if (data.profilePicture == null) {
      this.profilePicture = 'https://www.gravatar.com/avatar/' + this.id + '?s=128&d=identicon&r=PG'
    } else {
      this.profilePicture = data.profilePicture;
    }
    this.careerGoal = data.careerGoal;
    this.roles = ['user'];
    this.trainingApplicationSet = data.trainingApplicationSet;
    this.jobApplicationSet = data.jobApplicationSet;
  }

}
