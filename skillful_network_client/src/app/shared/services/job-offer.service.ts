import { Injectable } from '@angular/core';
import { JobOffer } from '../models/application/job-offer';
import { ApiHelperService } from './api-helper.service';
import { SearchService } from './abstract-search.service';

const ROOT_ENDPOINT: string = '/jobs/';

@Injectable({
    providedIn: 'root'
})
export class JobOfferService extends SearchService<JobOffer> {

    constructor(protected api: ApiHelperService) {
        super(api);
    }

    public getById(id: number): Promise<JobOffer> {
        return this.api.get( { endpoint: ROOT_ENDPOINT + id });
    }

    public getBySearch(keyword: string, page: number, size: number, sortOrder: string, field: string): Promise<JobOffer> {
        return super._getBySearch('jobs', keyword, page, size, sortOrder, field);
    }

}

