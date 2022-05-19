import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateStudentPaymentTabComponent } from './create-student-payment-tab.component';

describe('CreateStudentPaymentTabComponent', () => {
  let component: CreateStudentPaymentTabComponent;
  let fixture: ComponentFixture<CreateStudentPaymentTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateStudentPaymentTabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateStudentPaymentTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
