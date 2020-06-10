import { Injectable } from '@angular/core';
import { ApiHelperService } from './api-helper.service';
import { Perk } from '../models/user/perk';

@Injectable({
    providedIn: 'root'
})
export class PerkService {

    constructor(private api: ApiHelperService) { }

    public getCandidates(perk: string, keyword: string): Promise<Perk[]> {
        return this.api.get({ endpoint: `/${perk}/candidates`, queryParams: { keyword } });
    }

    public create(perk: string, newPerkName: string) : Promise<Perk> {
        return this.api.post( { endpoint: `/${perk}`, queryParams: { name: newPerkName } } );
    }

}