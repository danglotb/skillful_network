import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JobApplicationsList } from './job-applications-list.component';

describe('CandidaturesComponent', () => {
  let component: JobApplicationsList;
  let fixture: ComponentFixture<JobApplicationsList>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JobApplicationsList ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JobApplicationsList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
