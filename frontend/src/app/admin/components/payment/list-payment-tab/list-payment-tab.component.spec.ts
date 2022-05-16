import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListPaymentTabComponent } from './list-payment-tab.component';

describe('ListPaymentTabComponent', () => {
  let component: ListPaymentTabComponent;
  let fixture: ComponentFixture<ListPaymentTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListPaymentTabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListPaymentTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
