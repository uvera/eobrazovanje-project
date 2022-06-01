import { TestBed } from '@angular/core/testing';

import { AddStudentGradesService } from './add-student-grades.service';

describe('AddStudentGradesService', () => {
  let service: AddStudentGradesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddStudentGradesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
