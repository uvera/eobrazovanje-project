import { TestBed } from '@angular/core/testing';

import { EditPaymentDialogService } from './edit-payment-dialog.service';

describe('EditPaymentDialogService', () => {
  let service: EditPaymentDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EditPaymentDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
