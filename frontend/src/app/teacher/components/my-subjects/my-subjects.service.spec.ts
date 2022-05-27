import { TestBed } from '@angular/core/testing';

import { MySubjectsService } from './my-subjects.service';

describe('MySubjectsService', () => {
  let service: MySubjectsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MySubjectsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
