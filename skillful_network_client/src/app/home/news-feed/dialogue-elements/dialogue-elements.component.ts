import { PublicationService } from './../../../shared/services/publication.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators, FormBuilder } from "@angular/forms";
import { Publication } from 'src/app/shared/models/application/publication';

@Component({
  selector: 'app-dialogue-elements',
  templateUrl: './dialogue-elements.component.html',
  styleUrls: ['./dialogue-elements.component.scss']
})
export class DialogueElementsComponent implements OnInit {
  public listPublication: Publication[];
  publicationControl: FormControl;
  public formPost: FormGroup;
  // publication: publication = new publication({});
  constructor( private fb: FormBuilder, public pub: PublicationService) { 
    this._buildForm();
  }

  onSubmit() {
    let publication = new Publication(this.formPost.value);
    this.pub.ajoutpublication(publication);
  }

  private _buildForm() {
    this.formPost = this.fb.group({
      publication: ["", [Validators.required, Validators.minLength(5)]],
    });
  }
  ngOnInit(): void {
  }

}
