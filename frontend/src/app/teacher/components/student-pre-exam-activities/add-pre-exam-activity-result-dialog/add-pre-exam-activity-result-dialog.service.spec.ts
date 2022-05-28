import { TestBed } from '@angular/core/testing';

import { AddPreExamActivityResultDialogService } from './add-pre-exam-activity-result-dialog.service';

describe('AddPreExamActivityResultDialogService', () => {
  let service: AddPreExamActivityResultDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddPreExamActivityResultDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
