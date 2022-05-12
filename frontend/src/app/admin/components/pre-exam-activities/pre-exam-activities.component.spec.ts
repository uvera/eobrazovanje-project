import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PreExamActivitiesComponent } from './pre-exam-activities.component';

describe('PreExamActivitiesComponent', () => {
  let component: PreExamActivitiesComponent;
  let fixture: ComponentFixture<PreExamActivitiesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PreExamActivitiesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PreExamActivitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
