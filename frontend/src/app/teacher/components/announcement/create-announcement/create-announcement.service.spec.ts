import { TestBed } from '@angular/core/testing';

import { CreateAnnouncementService } from './create-announcement.service';

describe('CreateAnnouncementService', () => {
  let service: CreateAnnouncementService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreateAnnouncementService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
