import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RdvCancel } from './rdv-cancel';

describe('RdvCancel', () => {
  let component: RdvCancel;
  let fixture: ComponentFixture<RdvCancel>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RdvCancel]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RdvCancel);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
