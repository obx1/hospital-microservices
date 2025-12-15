import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RdvEdit } from './rdv-edit';

describe('RdvEdit', () => {
  let component: RdvEdit;
  let fixture: ComponentFixture<RdvEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RdvEdit]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RdvEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
