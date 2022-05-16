import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditProfesorDialogComponent } from './edit-profesor-dialog.component';

describe('EditProfesorDialogComponent', () => {
  let component: EditProfesorDialogComponent;
  let fixture: ComponentFixture<EditProfesorDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditProfesorDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditProfesorDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
