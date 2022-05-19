import { TestBed } from '@angular/core/testing';

import { CreatePaymentTabService } from './create-payment-tab.service';

describe('CreatePaymentTabService', () => {
  let service: CreatePaymentTabService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreatePaymentTabService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
