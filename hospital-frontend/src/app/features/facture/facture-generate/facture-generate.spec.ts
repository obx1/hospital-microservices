import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FactureGenerate } from './facture-generate';

describe('FactureGenerate', () => {
  let component: FactureGenerate;
  let fixture: ComponentFixture<FactureGenerate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FactureGenerate]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FactureGenerate);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
