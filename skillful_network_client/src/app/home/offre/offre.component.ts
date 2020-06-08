import {Component, Input, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {JobDetails, Trainings} from './offre';
import { JobApplicationService } from '../../shared/services/job-applications.service';
import {ApiHelperService} from '../../shared/services/api-helper.service';

@Component({
  selector: 'app-offre',
  templateUrl: './offre.component.html',
  styleUrls: ['./offre.component.scss']
})
export class OffreComponent implements OnInit {
  @Input() public status: string;
  @Input() public titreOffre: string;
  public jobOfferId: number;
  public choixListe: string;
    public keywords: any;

  public trainings: Trainings;
  public jobDetails: JobDetails;

  constructor(private api: ApiHelperService, public cs: JobApplicationService, private route: ActivatedRoute) { }

  ngOnInit(): void {
      this.choixListe = this.route.snapshot.data.type;
      const {id} = this.route.snapshot.params;
      if (this.choixListe == 'emploi') {

          this.api.get({endpoint: `/offers/${id}`})
              .then(data => {
                  this.jobDetails = data;
              })
              .catch((error) => {
                  console.log('cette offre n\'existe pas');
              });
      } else {
          this.api.get({endpoint: `/trainings/${id}`})
              .then(data => {
                  this.jobDetails = data;
              })
              .catch((error) => {
                  console.log('cette offre n\'existe pas');
              });
      }
  }

}
