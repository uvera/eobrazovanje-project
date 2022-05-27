import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListPreExamActivitiesComponent } from './list-pre-exam-activities.component';

describe('ListPreExamActivitiesComponent', () => {
  let component: ListPreExamActivitiesComponent;
  let fixture: ComponentFixture<ListPreExamActivitiesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListPreExamActivitiesComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListPreExamActivitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
