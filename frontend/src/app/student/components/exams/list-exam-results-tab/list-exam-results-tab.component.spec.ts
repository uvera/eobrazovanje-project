import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListExamResultsTabComponent } from './list-exam-results-tab.component';

describe('ListExamResultsTabComponent', () => {
  let component: ListExamResultsTabComponent;
  let fixture: ComponentFixture<ListExamResultsTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListExamResultsTabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListExamResultsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
