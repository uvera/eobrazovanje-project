import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListDocumentsTabComponent } from './list-documents-tab.component';

describe('ListDocumentsTabComponent', () => {
  let component: ListDocumentsTabComponent;
  let fixture: ComponentFixture<ListDocumentsTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListDocumentsTabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListDocumentsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
