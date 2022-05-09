import { TestBed } from '@angular/core/testing';

import { CreateStudyProgramTabService } from './create-study-program-tab.service';

describe('CreateStudyProgramTabService', () => {
  let service: CreateStudyProgramTabService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreateStudyProgramTabService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
