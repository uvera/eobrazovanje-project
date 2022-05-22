import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListExamsTabComponent } from './list-exams-tab.component';

describe('ListExamsTabComponent', () => {
  let component: ListExamsTabComponent;
  let fixture: ComponentFixture<ListExamsTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListExamsTabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListExamsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
