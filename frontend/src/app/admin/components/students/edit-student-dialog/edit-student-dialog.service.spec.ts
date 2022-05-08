import { TestBed } from '@angular/core/testing';

import { EditStudentDialogService } from './edit-student-dialog.service';

describe('EditStudentDialogService', () => {
  let service: EditStudentDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EditStudentDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
