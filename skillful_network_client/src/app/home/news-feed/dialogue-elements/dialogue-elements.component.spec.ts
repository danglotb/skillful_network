import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogueElementsComponent } from './dialogue-elements.component';

describe('DialogueElementsComponent', () => {
  let component: DialogueElementsComponent;
  let fixture: ComponentFixture<DialogueElementsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogueElementsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogueElementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
