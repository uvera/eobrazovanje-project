import { TestBed } from '@angular/core/testing';

import { EditProfesorDialogService } from './edit-profesor-dialog.service';

describe('EditProfesorDialogService', () => {
  let service: EditProfesorDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EditProfesorDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
