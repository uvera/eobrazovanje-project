import { TestBed } from '@angular/core/testing';

import { ListSubjectExecutionsTabService } from './list-subject-executions-tab.service';

describe('ListSubjectExecutionsTabService', () => {
  let service: ListSubjectExecutionsTabService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListSubjectExecutionsTabService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
