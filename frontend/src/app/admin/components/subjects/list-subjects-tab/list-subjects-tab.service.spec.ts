import { TestBed } from '@angular/core/testing';

import { ListSubjectsTabService } from './list-subjects-tab.service';

describe('ListSubjectsTabService', () => {
  let service: ListSubjectsTabService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListSubjectsTabService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
