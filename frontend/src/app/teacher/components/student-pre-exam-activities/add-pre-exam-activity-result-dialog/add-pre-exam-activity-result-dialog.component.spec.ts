import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPreExamActivityResultDialogComponent } from './add-pre-exam-activity-result-dialog.component';

describe('AddPreExamActivityResultDialogComponent', () => {
  let component: AddPreExamActivityResultDialogComponent;
  let fixture: ComponentFixture<AddPreExamActivityResultDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddPreExamActivityResultDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddPreExamActivityResultDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
