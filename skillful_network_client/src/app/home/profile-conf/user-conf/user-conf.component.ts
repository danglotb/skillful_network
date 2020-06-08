import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { DateAdapter } from '@angular/material/core';

@Component({
  selector: 'app-user-conf',
  templateUrl: './user-conf.component.html',
  styleUrls: ['./user-conf.component.scss']
})

export class UserConfComponent {

  public title : string = 'Informations'
  @Input() userInfoGroup: FormGroup;

  constructor(private _adapter: DateAdapter<any>) {
    this._adapter.setLocale('fr');
  }

  ngOnInit() { }

}
