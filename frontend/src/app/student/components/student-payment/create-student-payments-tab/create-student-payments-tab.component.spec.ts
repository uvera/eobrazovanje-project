import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateStudentPaymentsTabComponent } from './create-student-payments-tab.component';

describe('CreateStudentPaymentsTabComponent', () => {
  let component: CreateStudentPaymentsTabComponent;
  let fixture: ComponentFixture<CreateStudentPaymentsTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreateStudentPaymentsTabComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateStudentPaymentsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
