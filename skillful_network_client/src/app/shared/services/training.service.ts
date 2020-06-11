import {Injectable} from '@angular/core';
import {Training} from '../models/application/training';
import { ApiHelperService } from './api-helper.service';
import { SearchService } from './abstract-search.service';


@Injectable({
    providedIn: 'root'
})
export class TrainingService extends SearchService<Training> {
    public trainings: Training[];

    constructor(protected api: ApiHelperService) {
        super(api);
    }

    public findById(id: number): Training{
        return this.trainings[id];
    }



// Import depuis le Backend
    public findAll(): Promise<any> {
        let promise = new Promise((resolve, reject) => {
            this.api.get({ endpoint: '/trainings' })
              .then(
                res => { 
                  resolve(res);
                },
                msg => { 
                  reject(msg);
                  }
              ).catch((error) => {
              });
          });
          return promise;
    }

    public findAllByPage(page:number, 
                        size:number, 
                        sortOrder:String,
                        fieldToSort:String): Promise<any> {
        let promise = new Promise((resolve, reject) => {
            this.api.get({ endpoint: '/trainings/' ,
              queryParams: {"page":page, "size":size, "sortOrder":sortOrder, "field":fieldToSort}})
              .then(
                res => { 
                  resolve(res);
                },
                msg => { 
                  reject(msg);
                  }
              ).catch((error) => {
              });
          });
          return promise;
    }

    public getBySearch(keyword: string, page: number, size: number, sortOrder: string, field: string): Promise<Training> {
        return super._getBySearch('trainings', keyword, page, size, sortOrder, field);
    }

    public getTrainingBySearch(keyword: string, page: number, size: number, order: string, field: string): Promise<any> {
        let promise = new Promise((resolve, reject) => {
            this.api.get({ endpoint: `/trainings/search`, queryParams: { keyword: keyword, page: page, size: size, sortOrder: order, field: field } })
                .then(
                    res => {
                        resolve(res);
                    },
                    msg => {
                        reject(msg);
                    }
                ).catch((error) => {
                    console.log("request not found")
                });
        });
        return promise;
    }

}
