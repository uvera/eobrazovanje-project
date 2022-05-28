import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyExamPeriodsComponent } from './my-exam-periods.component';

describe('MyExamPeriodsComponent', () => {
  let component: MyExamPeriodsComponent;
  let fixture: ComponentFixture<MyExamPeriodsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyExamPeriodsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MyExamPeriodsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
