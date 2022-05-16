import { TestBed } from '@angular/core/testing';

import { ListPaymentTabService } from './list-payment-tab.service';

describe('ListPaymentTabService', () => {
  let service: ListPaymentTabService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListPaymentTabService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
