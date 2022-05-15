import { TestBed } from '@angular/core/testing';

import { ListProfesorsTabService } from './list-profesors-tab.service';

describe('ListProfesorsTabService', () => {
  let service: ListProfesorsTabService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListProfesorsTabService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
