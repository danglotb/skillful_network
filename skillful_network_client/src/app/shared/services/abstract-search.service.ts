import { ApiHelperService } from './api-helper.service';

export abstract class SearchService<T> {

    constructor(protected api: ApiHelperService) {

    }

    public abstract getBySearch(keyword: string, page: number, size: number, sortOrder: string, field: string): Promise<T>;

    protected _getBySearch(type: string, keyword: string, page: number, size: number, sortOrder: string, field: string) : Promise<T> {
        return this.api.get({ endpoint: `/${type}/search`, queryParams: { keyword, page, size, sortOrder, field } });
    }
}