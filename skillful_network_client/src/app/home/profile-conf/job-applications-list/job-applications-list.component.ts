import { Component, OnInit, Input } from '@angular/core';
import { Application } from 'src/app/shared/models/application';
import { JobApplicationService } from 'src/app/shared/services/job-applications.service';

@Component({
  selector: 'job-applications-list',
  templateUrl: './job-applications-list.component.html',
  styleUrls: ['./job-applications-list.component.scss']
})
export class JobApplicationsList implements OnInit {

  public title: string = 'Candidatures';
  public jobApplicationsList: Application[];

  constructor(
    private jobApplicationService: JobApplicationService,
  ) { }

  ngOnInit(): void {
    this.jobApplicationService.getJobApplicationsForCurrentUser().then( data => {
        console.log(data);
        this.jobApplicationsList = data;
        console.log(this.jobApplicationsList);
      }).catch(error => {
          console.log(error);
      });
  }

}
