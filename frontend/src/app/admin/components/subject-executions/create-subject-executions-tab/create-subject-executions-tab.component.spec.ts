import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateSubjectExecutionsTabComponent } from './create-subject-executions-tab.component';

describe('CreateSubjectExecutionsTabComponent', () => {
  let component: CreateSubjectExecutionsTabComponent;
  let fixture: ComponentFixture<CreateSubjectExecutionsTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateSubjectExecutionsTabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateSubjectExecutionsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
