import { Injectable } from '@angular/core';
import { Training } from '../models/application/training';
import { ApiHelperService } from './api-helper.service';
import { SearchService } from './abstract-search.service';

const ROOT_ENDPOINT: string = '/trainings/';

@Injectable({
    providedIn: 'root'
})
export class TrainingService extends SearchService<Training> {

    constructor(protected api: ApiHelperService) {
        super(api);
    }

    public getById(id: number): Promise<Training> {
        return this.api.get({ endpoint: ROOT_ENDPOINT + id });
    }

    public getAll(): Promise<Training[]> {
        return this.api.get({ endpoint: ROOT_ENDPOINT });
    }

    public getBySearch(keyword: string, page: number, size: number, sortOrder: string, field: string): Promise<Training> {
        return super._getBySearch('trainings', keyword, page, size, sortOrder, field);
    }
    
}
