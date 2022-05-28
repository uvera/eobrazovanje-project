import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListExamPeriodsComponent } from './list-exam-periods.component';

describe('ListExamPeriodsComponent', () => {
  let component: ListExamPeriodsComponent;
  let fixture: ComponentFixture<ListExamPeriodsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListExamPeriodsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListExamPeriodsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
