import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListStudentsTabComponent } from './list-students-tab.component';

describe('ListStudentsTabComponent', () => {
  let component: ListStudentsTabComponent;
  let fixture: ComponentFixture<ListStudentsTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListStudentsTabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListStudentsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
