import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExistingAccountDialog } from './existing-account-dialog.component';

describe('ExistingAccountDialog', () => {
  let component: ExistingAccountDialog;
  let fixture: ComponentFixture<ExistingAccountDialog>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExistingAccountDialog ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExistingAccountDialog);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
