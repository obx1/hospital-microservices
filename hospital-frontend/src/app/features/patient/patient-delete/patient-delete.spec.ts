import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientDelete } from './patient-delete';

describe('PatientDelete', () => {
  let component: PatientDelete;
  let fixture: ComponentFixture<PatientDelete>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PatientDelete]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PatientDelete);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
