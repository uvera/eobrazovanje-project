import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EnrollToSubjectDialogComponent } from './enroll-to-subject-dialog.component';

describe('EnrollToSubjectDialogComponent', () => {
  let component: EnrollToSubjectDialogComponent;
  let fixture: ComponentFixture<EnrollToSubjectDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EnrollToSubjectDialogComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EnrollToSubjectDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
