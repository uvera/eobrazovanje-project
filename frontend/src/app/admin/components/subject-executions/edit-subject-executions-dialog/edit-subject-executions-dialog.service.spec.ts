import { TestBed } from '@angular/core/testing';

import { EditSubjectExecutionsDialogService } from './edit-subject-executions-dialog.service';

describe('EditSubjectExecutionsDialogService', () => {
  let service: EditSubjectExecutionsDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EditSubjectExecutionsDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
