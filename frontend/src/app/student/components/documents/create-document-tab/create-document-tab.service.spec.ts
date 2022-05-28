import { TestBed } from '@angular/core/testing';

import { CreateDocumentTabService } from './create-document-tab.service';

describe('CreateDocumentTabService', () => {
  let service: CreateDocumentTabService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreateDocumentTabService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
