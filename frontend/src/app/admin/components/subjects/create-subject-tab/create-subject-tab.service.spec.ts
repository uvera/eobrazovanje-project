import { TestBed } from '@angular/core/testing';

import { CreateSubjectTabService } from './create-subject-tab.service';

describe('CreateSubjectTabService', () => {
  let service: CreateSubjectTabService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreateSubjectTabService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
