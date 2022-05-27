import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateStudentTabComponent } from './create-student-tab.component';

describe('CreateStudentTabComponent', () => {
  let component: CreateStudentTabComponent;
  let fixture: ComponentFixture<CreateStudentTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreateStudentTabComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateStudentTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
