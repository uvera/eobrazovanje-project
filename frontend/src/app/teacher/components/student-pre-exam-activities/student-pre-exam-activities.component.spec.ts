import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentPreExamActivitiesComponent } from './student-pre-exam-activities.component';

describe('StudentPreExamActivitiesComponent', () => {
  let component: StudentPreExamActivitiesComponent;
  let fixture: ComponentFixture<StudentPreExamActivitiesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StudentPreExamActivitiesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentPreExamActivitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
