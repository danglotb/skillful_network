import {Component, Input, OnInit} from '@angular/core';
import {ApiHelperService} from '../../../../../shared/services/api-helper.service';
import {ActivatedRoute} from '@angular/router';



@Component({
  selector: 'app-candidater',
  templateUrl: './candidater.component.html',
  styleUrls: ['./candidater.component.scss']
})
export class CandidaterComponent implements OnInit {
    @Input() post;
    agree: any;

    constructor(private api: ApiHelperService, private route: ActivatedRoute) { }

    ngOnInit(): void {
    }
}
