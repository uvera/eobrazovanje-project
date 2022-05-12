import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatePreExamActivityComponent } from './create-pre-exam-activity.component';

describe('CreatePreExamActivityComponent', () => {
  let component: CreatePreExamActivityComponent;
  let fixture: ComponentFixture<CreatePreExamActivityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreatePreExamActivityComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatePreExamActivityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
