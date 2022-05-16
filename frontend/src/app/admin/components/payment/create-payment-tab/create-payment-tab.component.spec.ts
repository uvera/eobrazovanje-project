import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatePaymentTabComponent } from './create-payment-tab.component';

describe('CreatePaymentTabComponent', () => {
  let component: CreatePaymentTabComponent;
  let fixture: ComponentFixture<CreatePaymentTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreatePaymentTabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatePaymentTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
