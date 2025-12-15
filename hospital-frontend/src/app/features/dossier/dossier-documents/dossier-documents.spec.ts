import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DossierDocuments } from './dossier-documents';

describe('DossierDocuments', () => {
  let component: DossierDocuments;
  let fixture: ComponentFixture<DossierDocuments>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DossierDocuments]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DossierDocuments);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
