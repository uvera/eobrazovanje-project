import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListSubjectExecutionsTabComponent } from './list-subject-executions-tab.component';

describe('ListSubjectExecutionsTabComponent', () => {
  let component: ListSubjectExecutionsTabComponent;
  let fixture: ComponentFixture<ListSubjectExecutionsTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListSubjectExecutionsTabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListSubjectExecutionsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
