import { TestBed } from '@angular/core/testing';

import { CreatePreExamActivityService } from './create-pre-exam-activity.service';

describe('CreatePreExamActivityService', () => {
  let service: CreatePreExamActivityService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreatePreExamActivityService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
