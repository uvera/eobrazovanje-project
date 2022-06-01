import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddStudentGradesComponent } from './add-student-grades.component';

describe('AddStudentGradesComponent', () => {
  let component: AddStudentGradesComponent;
  let fixture: ComponentFixture<AddStudentGradesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddStudentGradesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddStudentGradesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
