import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListExamPeriodsTabComponent } from './list-exam-periods-tab.component';

describe('ListExamPeriodsTabComponent', () => {
  let component: ListExamPeriodsTabComponent;
  let fixture: ComponentFixture<ListExamPeriodsTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListExamPeriodsTabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListExamPeriodsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
