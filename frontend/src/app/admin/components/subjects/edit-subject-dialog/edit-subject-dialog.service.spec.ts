import { TestBed } from '@angular/core/testing';

import { EditSubjectDialogService } from './edit-subject-dialog.service';

describe('EditSubjectDialogService', () => {
  let service: EditSubjectDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EditSubjectDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
