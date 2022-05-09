import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateStudyProgramTabComponent } from './create-study-program-tab.component';

describe('CreateStudyProgramTabComponent', () => {
  let component: CreateStudyProgramTabComponent;
  let fixture: ComponentFixture<CreateStudyProgramTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateStudyProgramTabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateStudyProgramTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
