import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DossierDelete } from './dossier-delete';

describe('DossierDelete', () => {
  let component: DossierDelete;
  let fixture: ComponentFixture<DossierDelete>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DossierDelete]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DossierDelete);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
