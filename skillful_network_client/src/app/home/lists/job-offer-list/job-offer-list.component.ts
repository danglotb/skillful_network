import { Component, OnInit } from '@angular/core';
import { ListElement } from 'src/app/shared/components/list/list-element';
import { JobOfferService } from 'src/app/shared/services/job-offer.service';

@Component({
  selector: 'app-job-list',
  templateUrl: './job-offer-list.component.html',
  styleUrls: ['./job-offer-list.component.scss']
})

export class JobOfferListComponent implements OnInit {

  displayedColumns: string[] = ['name', 'company', 'dateUpload'];
  listElements: ListElement[] = [
    new ListElement("Nom", "name"),
    new ListElement("Entreprise", "company"),
    new ListElement("Date de mise en ligne", "dateUpload")
  ]

  constructor(public service: JobOfferService) {

  }

  ngOnInit() {

  }
}