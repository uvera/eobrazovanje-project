import { TestBed } from '@angular/core/testing';

import { EnrollToSubjectDialogService } from './enroll-to-subject-dialog.service';

describe('EnrollToSubjectDialogService', () => {
  let service: EnrollToSubjectDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EnrollToSubjectDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
