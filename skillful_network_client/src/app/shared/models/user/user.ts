import { JobApplication } from '../application/job-application';
import { TrainingApplication } from '../application/training-application';

export class User {

  private _id: number;
  private _firstName: string;
  private _lastName: string;
  private _password: string;
  private _birthDate: Date;
  private _email: string;
  private _mobileNumber: string;
  private _validated: boolean;
  private _careerGoal: string;
  private _photo: boolean;
  private _skillSet: string[];
  private _qualificationSet: string[];
  private _subscriptionSet: string[];
  private _photoProfile: any;
  private _role: string[];
  private _jobApplications: JobApplication[];
  private _trainingApplications: TrainingApplication[];

  constructor(data: any) {
    this._id = data.id;
    this._firstName = data.firstName;
    this._email = data.email;
    this._lastName = data.lastName;
    this._password = data.password;
    this._birthDate = data.birthDate;
    this._email = data.email;
    this._mobileNumber = data.mobileNumber;
    this._validated = data.validated;
    this._photo = data.photo;
    this._skillSet = data.skillSet;
    this._qualificationSet = data.qualificationSet;
    this._subscriptionSet = data.subscriptionSet;
    this._photoProfile = data.photoProfile;
    this._careerGoal = data.careerGoal;
    this._role = ['user'];
    this.trainingApplications = data.trainingApplicationSet;
    this.jobApplications = data.jobApplicationSet;
  }

  public set jobApplications(jobApplications: JobApplication[]) {
    this._jobApplications = jobApplications;
  }

  public get jobApplications(): JobApplication[] {
    return this._jobApplications;
  }

  public set trainingApplications(trainingApplications: TrainingApplication[]) {
    this._trainingApplications = trainingApplications;
  }

  public get trainingApplications(): TrainingApplication[] {
    return this._trainingApplications;
  }

  public get id(): number {
    return this._id;
  }
  public set id(value: number) {
    this._id = value;
  }
  public get firstName(): string {
    return this._firstName;
  }
  public set firstName(value: string) {
    this._firstName = value;
  }
  public get lastName(): string {
    return this._lastName;
  }
  public set lastName(value: string) {
    this._lastName = value;
  }
  public get password(): string {
    return this._password;
  }
  public set password(value: string) {
    this._password = value;
  }
  get birthDate(): Date {
    return this._birthDate;
  }
  set birthDate(value: Date) {
    this._birthDate = value;
  }
  public get email(): string {
    return this._email;
  }
  public set email(value: string) {
    this._email = value;
  }
  public get mobileNumber(): string {
    return this._mobileNumber;
  }
  public set mobileNumber(value: string) {
    this._mobileNumber = value;

  }
  public get validated(): boolean {
    return this._validated;
  }
  public set validated(value: boolean) {
    this._validated = value;
  }
  public get photo(): boolean {
    return this._photo;
  }
  public set photo(value: boolean) {
    this._photo = value;
  }
  public get skillSet(): string[] {
    return this._skillSet;
  }
  public set skillSet(value: string[]) {
    this._skillSet = value;
  }
  public get qualificationSet(): string[] {
    return this._qualificationSet;
  }
  public set qualificationSet(value: string[]) {
    this._qualificationSet = value;
  }
  public get subscriptionSet(): string[] {
    return this._subscriptionSet;
  }
  public set subscriptionSet(value: string[]) {
    this._subscriptionSet = value;
  }
  public get photoProfile(): any {
    return this._photoProfile;
  }
  public set photoProfile(value: any) {
    this._photoProfile = value;
  }
  public get careerGoal(): string {
    return this._careerGoal;
  }
  public set careerGoal(value: string) {
    this._careerGoal = value;
  }
}
