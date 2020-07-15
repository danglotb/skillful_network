import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListPublicationsComponent } from './list-publications.component';

describe('ListPublicationsComponent', () => {
  let component: ListPublicationsComponent;
  let fixture: ComponentFixture<ListPublicationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListPublicationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListPublicationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
