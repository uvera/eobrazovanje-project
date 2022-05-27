import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditExamPeriodDialogComponent } from './edit-exam-period-dialog.component';

describe('EditExamPeriodDialogComponent', () => {
  let component: EditExamPeriodDialogComponent;
  let fixture: ComponentFixture<EditExamPeriodDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditExamPeriodDialogComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditExamPeriodDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
