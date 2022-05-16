import { TestBed } from '@angular/core/testing';

import { EnrollStudentsDialogService } from './enroll-students-dialog.service';

describe('EnrollStudentsDialogService', () => {
  let service: EnrollStudentsDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EnrollStudentsDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
