import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubjectExecutionsComponent } from './subject-executions.component';

describe('SubjectExecutionsComponent', () => {
  let component: SubjectExecutionsComponent;
  let fixture: ComponentFixture<SubjectExecutionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SubjectExecutionsComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubjectExecutionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
