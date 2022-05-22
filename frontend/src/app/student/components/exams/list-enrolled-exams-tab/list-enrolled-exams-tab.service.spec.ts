import { TestBed } from '@angular/core/testing';

import { ListEnrolledExamsTabService } from './list-enrolled-exams-tab.service';

describe('ListEnrolledExamsTabService', () => {
  let service: ListEnrolledExamsTabService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListEnrolledExamsTabService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
