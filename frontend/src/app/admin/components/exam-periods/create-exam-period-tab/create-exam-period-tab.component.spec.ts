import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateExamPeriodTabComponent } from './create-exam-period-tab.component';

describe('CreateExamPeriodTabComponent', () => {
  let component: CreateExamPeriodTabComponent;
  let fixture: ComponentFixture<CreateExamPeriodTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateExamPeriodTabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateExamPeriodTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
