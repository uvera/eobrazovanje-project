import { TestBed } from '@angular/core/testing';

import { EditStudyProgramDialogService } from './edit-study-program-dialog.service';

describe('EditStudyProgramDialogService', () => {
  let service: EditStudyProgramDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EditStudyProgramDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
