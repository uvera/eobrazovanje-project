import { TestBed } from '@angular/core/testing';

import { ListExamsTabService } from './list-exams-tab.service';

describe('ListExamsTabService', () => {
  let service: ListExamsTabService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListExamsTabService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
