
import { Application } from '../models/application/application';
import { Injectable } from '@angular/core';
import { ApiHelperService } from './api-helper.service';

@Injectable({
  providedIn: 'root'
})
export class JobApplicationService {

  private rootEndpoint: string = '/applications/jobs/';

  constructor(private api: ApiHelperService) { }

  public getForCurrentUser(): Promise<Application[]> {
    return this.api.get({ endpoint: this.rootEndpoint + 'user' });
  }

  public getByUserId(id: number): Promise<Application[]> {
    return this.api.get({ endpoint: this.rootEndpoint + 'user/' + id });
  }
}
