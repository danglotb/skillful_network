import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators, FormBuilder } from "@angular/forms";
import { Publication } from 'src/app/shared/models/application/publication';

@Component({
  selector: 'app-dialogue-elements',
  templateUrl: './dialogue-elements.component.html',
  styleUrls: ['./dialogue-elements.component.scss']
})
export class DialogueElementsComponent implements OnInit {

  publicationControl: FormControl;
  public formPost: FormGroup;
  constructor( private fb: FormBuilder) { 
    this._buildForm();
  }

  onSubmit() {
  }

  private _buildForm() {
    this.formPost = this.fb.group({
      publication: ["", [Validators.required, Validators.minLength(5)]],
    });
  }
  ngOnInit(): void {
  }

}
