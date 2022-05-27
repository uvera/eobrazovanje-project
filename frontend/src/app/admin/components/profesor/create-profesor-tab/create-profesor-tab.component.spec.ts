import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateProfesorTabComponent } from './create-profesor-tab.component';

describe('CreateProfesorTabComponent', () => {
  let component: CreateProfesorTabComponent;
  let fixture: ComponentFixture<CreateProfesorTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreateProfesorTabComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateProfesorTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
