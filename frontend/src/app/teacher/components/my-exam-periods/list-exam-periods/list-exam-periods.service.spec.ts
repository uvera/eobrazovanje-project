import { TestBed } from '@angular/core/testing';

import { ListExamPeriodsService } from './list-exam-periods.service';

describe('ListExamPeriodsService', () => {
  let service: ListExamPeriodsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListExamPeriodsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
