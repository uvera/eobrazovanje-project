import { TestBed } from '@angular/core/testing';

import { CreateSubjectExecutionsTabService } from './create-subject-executions-tab.service';

describe('CreateSubjectExecutionsTabService', () => {
  let service: CreateSubjectExecutionsTabService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreateSubjectExecutionsTabService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
