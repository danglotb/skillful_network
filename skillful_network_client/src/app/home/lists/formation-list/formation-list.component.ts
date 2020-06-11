import { TrainingService } from '../../../shared/services/training.service';
import { Training } from '../../../shared/models/application/training';
import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort, Sort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {PageEvent} from '@angular/material/paginator';
import { ListElement } from 'src/app/shared/components/list/list-element';

@Component({
  selector: 'app-formation-list',
  templateUrl: './formation-list.component.html',
  styleUrls: ['./formation-list.component.scss']
})

​export class FormationListComponent implements OnInit {
  
  displayedColumns: string[] = ['name', 'organization', 'dateBeg', 'duration'];
  listElements: ListElement[] = [
    new ListElement("Nom", "name"),
    new ListElement("Formateur", "organization"),
    new ListElement("Date de début", "dateBeg"),
    new ListElement("Durée", "duration")
  ]
  constructor() {

  }

  ngOnInit() {

  }
}


​

