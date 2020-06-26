import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DialogueElementsComponent } from '../dialogue-elements/dialogue-elements.component';

@Component({
  selector: 'app-add-publication',
  templateUrl: './add-publication.component.html',
  styleUrls: ['./add-publication.component.scss']
})
export class AddPublicationComponent implements OnInit {

  constructor(public dialog: MatDialog) { }
  openDialog() {
    this.dialog.open(DialogueElementsComponent);
  }

  ngOnInit(): void {
  }

}
