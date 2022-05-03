import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListSubjectsTabComponent } from './list-subjects-tab.component';

describe('ListSubjectsTabComponent', () => {
  let component: ListSubjectsTabComponent;
  let fixture: ComponentFixture<ListSubjectsTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListSubjectsTabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListSubjectsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
