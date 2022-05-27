import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditPreExamActivityComponent } from './edit-pre-exam-activity.component';

describe('EditPreExamActivityComponent', () => {
  let component: EditPreExamActivityComponent;
  let fixture: ComponentFixture<EditPreExamActivityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditPreExamActivityComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditPreExamActivityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
