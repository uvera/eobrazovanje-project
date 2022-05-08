import { TestBed } from '@angular/core/testing';

import { CreateStudentTabService } from './create-student-tab.service';

describe('CreateStudentTabService', () => {
  let service: CreateStudentTabService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreateStudentTabService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
