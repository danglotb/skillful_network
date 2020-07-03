import { Injectable } from '@angular/core';
import { ApiHelperService } from './api-helper.service';

const ROOT_ENDPOINT: string = '/roles';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor(protected api: ApiHelperService) {
  }

  public getRoles(): Promise<String> {
    return this.api.get({ endpoint: ROOT_ENDPOINT});
  }

}
