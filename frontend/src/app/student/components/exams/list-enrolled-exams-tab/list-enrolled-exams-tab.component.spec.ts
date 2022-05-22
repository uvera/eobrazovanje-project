import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListEnrolledExamsTabComponent } from './list-enrolled-exams-tab.component';

describe('ListEnrolledExamsTabComponent', () => {
  let component: ListEnrolledExamsTabComponent;
  let fixture: ComponentFixture<ListEnrolledExamsTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListEnrolledExamsTabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListEnrolledExamsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
