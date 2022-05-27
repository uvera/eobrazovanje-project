import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditSubjectDialogComponent } from './edit-subject-dialog.component';

describe('EditSubjectDialogComponent', () => {
  let component: EditSubjectDialogComponent;
  let fixture: ComponentFixture<EditSubjectDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditSubjectDialogComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditSubjectDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
