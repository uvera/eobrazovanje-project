import { TestBed } from '@angular/core/testing';

import { EditExamPeriodDialogService } from './edit-exam-period-dialog.service';

describe('EditExamPeriodDialogService', () => {
  let service: EditExamPeriodDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EditExamPeriodDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
