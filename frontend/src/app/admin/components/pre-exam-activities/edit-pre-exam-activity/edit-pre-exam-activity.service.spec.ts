import { TestBed } from '@angular/core/testing';

import { EditPreExamActivityService } from './edit-pre-exam-activity.service';

describe('EditPreExamActivityService', () => {
  let service: EditPreExamActivityService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EditPreExamActivityService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
