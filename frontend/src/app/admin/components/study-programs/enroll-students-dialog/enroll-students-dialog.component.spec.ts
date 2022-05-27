import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EnrollStudentsDialogComponent } from './enroll-students-dialog.component';

describe('EnrollStudentsDialogComponent', () => {
  let component: EnrollStudentsDialogComponent;
  let fixture: ComponentFixture<EnrollStudentsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EnrollStudentsDialogComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EnrollStudentsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
