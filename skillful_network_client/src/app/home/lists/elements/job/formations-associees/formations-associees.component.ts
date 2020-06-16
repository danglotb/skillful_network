import {Component, Input, OnInit} from '@angular/core';
import {ApiHelperService} from '../../../../../shared/services/api-helper.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-formations-associees',
  templateUrl: './formations-associees.component.html',
  styleUrls: ['./formations-associees.component.scss']
})
export class FormationsAssocieesComponent implements OnInit {
    @Input() post;


  constructor(private api: ApiHelperService, private route: ActivatedRoute) { }

  ngOnInit(): void {
  }
}
