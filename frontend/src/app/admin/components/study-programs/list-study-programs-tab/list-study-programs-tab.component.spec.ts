import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListStudyProgramsTabComponent } from './list-study-programs-tab.component';

describe('ListStudyProgramsTabComponent', () => {
  let component: ListStudyProgramsTabComponent;
  let fixture: ComponentFixture<ListStudyProgramsTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListStudyProgramsTabComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListStudyProgramsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
