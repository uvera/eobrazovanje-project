import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateSubjectTabComponent } from './create-subject-tab.component';

describe('CreateSubjectTabComponent', () => {
  let component: CreateSubjectTabComponent;
  let fixture: ComponentFixture<CreateSubjectTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateSubjectTabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateSubjectTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
