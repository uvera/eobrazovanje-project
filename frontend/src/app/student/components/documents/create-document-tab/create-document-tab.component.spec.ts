import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateDocumentTabComponent } from './create-document-tab.component';

describe('CreateDocumentTabComponent', () => {
  let component: CreateDocumentTabComponent;
  let fixture: ComponentFixture<CreateDocumentTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateDocumentTabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateDocumentTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
