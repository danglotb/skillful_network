import { TrainingService } from '../../../shared/services/training.service';
import { Component, OnInit } from '@angular/core';
import { ListElement } from 'src/app/shared/components/list/list-element';

@Component({
  selector: 'app-formation-list',
  templateUrl: './formation-list.component.html',
  styleUrls: ['./formation-list.component.scss']
})

export class FormationListComponent implements OnInit {

  displayedColumns: string[] = ['name', 'organization', 'dateBeg', 'durationHours'];
  listElements: ListElement[] = [
    new ListElement("Nom", "name"),
    new ListElement("Formateur", "organization"),
    new ListElement("Date de début", "dateBeg"),
    new ListElement("Durée", "durationHours")
  ]
  constructor(public service: TrainingService) {

  }

  ngOnInit() {

  }
}




