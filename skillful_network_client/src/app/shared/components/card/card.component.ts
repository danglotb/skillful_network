import { Component, OnInit, Input, TemplateRef } from '@angular/core';

@Component({
  selector: 'app-card-component',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.scss']
})
export class CardComponent implements OnInit {

  @Input() public cardRef: TemplateRef<any>;
  @Input() public title: string;

  constructor() {}

  public ngOnInit(): void {}

}
