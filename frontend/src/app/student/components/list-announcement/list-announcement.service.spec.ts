import { TestBed } from '@angular/core/testing';

import { ListAnnouncementService } from './list-announcement.service';

describe('ListAnnouncementService', () => {
  let service: ListAnnouncementService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListAnnouncementService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
