import { TestBed } from '@angular/core/testing';

import { CreateProfesorTabService } from './create-profesor-tab.service';

describe('CreateProfesorTabService', () => {
  let service: CreateProfesorTabService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreateProfesorTabService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
