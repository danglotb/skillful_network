
import { Application } from '../models/application';
import { Injectable } from '@angular/core';
import { ApiHelperService } from './api-helper.service';

@Injectable({
  providedIn: 'root'
})
export class JobApplicationService {

  private rootEndpoint : string = '/applications/jobs/';

  constructor(private api: ApiHelperService) { }

  public getJobApplicationsForCurrentUser() : Promise<Application[]> {
    return this.api.get( { endpoint: this.rootEndpoint + 'user' } );
  }

  async getAllUserApllications(userId) : Promise<Application[]>{
    let endPoint = '/applications/jobs/user/'+userId;
    try{
      let candidatures = await this.api.get({ endpoint:endPoint});
      return candidatures;
    }catch(ex){
      return null;
    }
    
    
  }
}
