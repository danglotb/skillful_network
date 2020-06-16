import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JobOfferService } from '../../../../shared/services/job-offer.service';
import { JobOffer } from '../../../../shared/models/application/job-offer';

@Component({
    selector: 'app-job',
    templateUrl: './job.component.html',
    styleUrls: ['./job.component.scss']
})
export class JobComponent implements OnInit {

    public job: JobOffer;

    constructor(
        private service: JobOfferService,    
        private router: Router
    ) { }

    async ngOnInit() {
        let splittedUrl = this.router.url.split('/');
        this.job = await Promise.resolve(this.service.getById(+ splittedUrl[splittedUrl.length - 1]));
    }

}
