import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScheduleExamDialogComponent } from './schedule-exam-dialog.component';

describe('ScheduleExamDialogComponent', () => {
  let component: ScheduleExamDialogComponent;
  let fixture: ComponentFixture<ScheduleExamDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScheduleExamDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ScheduleExamDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
