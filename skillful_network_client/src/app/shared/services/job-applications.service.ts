
import { Application } from '../models/application/application';
import { Injectable } from '@angular/core';
import { ApiHelperService } from './api-helper.service';

const ROOT_ENDPOINT: string = '/applications/jobs/';

@Injectable({
  providedIn: 'root'
})
export class JobApplicationService {

  

  constructor(private api: ApiHelperService) { }

  public getForCurrentUser(): Promise<Application[]> {
    return this.api.get({ endpoint: ROOT_ENDPOINT + 'user' });
  }

  public getByUserId(id: number): Promise<Application[]> {
    return this.api.get({ endpoint: ROOT_ENDPOINT + 'user/' + id });
  }
}
