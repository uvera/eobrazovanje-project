import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditSubjectExecutionsDialogComponent } from './edit-subject-executions-dialog.component';

describe('EditSubjectExecutionsDialogComponent', () => {
  let component: EditSubjectExecutionsDialogComponent;
  let fixture: ComponentFixture<EditSubjectExecutionsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditSubjectExecutionsDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditSubjectExecutionsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
