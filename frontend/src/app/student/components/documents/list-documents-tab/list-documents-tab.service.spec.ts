import { TestBed } from '@angular/core/testing';

import { ListDocumentsTabService } from './list-documents-tab.service';

describe('ListDocumentsTabService', () => {
  let service: ListDocumentsTabService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListDocumentsTabService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
