import { TestBed } from '@angular/core/testing';

import { ScheduleExamDialogService } from './schedule-exam-dialog.service';

describe('ScheduleExamDialogService', () => {
  let service: ScheduleExamDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ScheduleExamDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
