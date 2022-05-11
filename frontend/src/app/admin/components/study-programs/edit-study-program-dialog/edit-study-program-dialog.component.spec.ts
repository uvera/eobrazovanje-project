import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditStudyProgramDialogComponent } from './edit-study-program-dialog.component';

describe('EditStudyProgramDialogComponent', () => {
  let component: EditStudyProgramDialogComponent;
  let fixture: ComponentFixture<EditStudyProgramDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditStudyProgramDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditStudyProgramDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
