import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListStudentPaymentsTabComponent } from './list-student-payments-tab.component';

describe('ListStudentPaymentsTabComponent', () => {
  let component: ListStudentPaymentsTabComponent;
  let fixture: ComponentFixture<ListStudentPaymentsTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListStudentPaymentsTabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListStudentPaymentsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
