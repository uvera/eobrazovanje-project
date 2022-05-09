import { TestBed } from '@angular/core/testing';

import { ListStudyProgramsTabService } from './list-study-programs-tab.service';

describe('ListStudyProgramsTabService', () => {
  let service: ListStudyProgramsTabService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListStudyProgramsTabService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
