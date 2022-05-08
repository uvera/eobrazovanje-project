import { TestBed } from '@angular/core/testing';

import { ListStudentsTabService } from './list-students-tab.service';

describe('ListStudentsTabService', () => {
  let service: ListStudentsTabService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListStudentsTabService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
