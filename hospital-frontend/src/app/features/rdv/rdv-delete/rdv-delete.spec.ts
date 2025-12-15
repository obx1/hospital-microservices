import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RdvDelete } from './rdv-delete';

describe('RdvDelete', () => {
  let component: RdvDelete;
  let fixture: ComponentFixture<RdvDelete>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RdvDelete]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RdvDelete);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
