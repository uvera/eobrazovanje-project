import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListProfesorTabComponent } from './list-profesor-tab.component';

describe('ListProfesorTabComponent', () => {
  let component: ListProfesorTabComponent;
  let fixture: ComponentFixture<ListProfesorTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListProfesorTabComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListProfesorTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
