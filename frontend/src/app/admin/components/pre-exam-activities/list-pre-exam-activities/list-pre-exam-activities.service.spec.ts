import { TestBed } from '@angular/core/testing';

import { ListPreExamActivitiesService } from './list-pre-exam-activities.service';

describe('ListPreExamActivitiesService', () => {
  let service: ListPreExamActivitiesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListPreExamActivitiesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
