import { TestBed } from '@angular/core/testing';

import { ListStudentsService } from './list-students.service';

describe('ListStudentsService', () => {
  let service: ListStudentsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListStudentsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
