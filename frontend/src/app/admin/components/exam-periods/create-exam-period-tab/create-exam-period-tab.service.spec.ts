import { TestBed } from '@angular/core/testing';

import { CreateExamPeriodTabService } from './create-exam-period-tab.service';

describe('CreateExamPeriodTabService', () => {
  let service: CreateExamPeriodTabService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreateExamPeriodTabService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
