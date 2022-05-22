import { TestBed } from '@angular/core/testing';

import { ListExamPeriodsTabService } from './list-exam-periods-tab.service';

describe('ListExamPeriodsTabService', () => {
  let service: ListExamPeriodsTabService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListExamPeriodsTabService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
