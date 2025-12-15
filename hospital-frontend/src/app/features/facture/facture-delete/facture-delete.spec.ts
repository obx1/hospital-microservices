import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FactureDelete } from './facture-delete';

describe('FactureDelete', () => {
  let component: FactureDelete;
  let fixture: ComponentFixture<FactureDelete>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FactureDelete]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FactureDelete);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
