import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FactureCreate } from './facture-create';

describe('FactureCreate', () => {
  let component: FactureCreate;
  let fixture: ComponentFixture<FactureCreate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FactureCreate]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FactureCreate);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
