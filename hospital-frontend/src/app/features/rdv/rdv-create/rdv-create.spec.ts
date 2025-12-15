import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RdvCreate } from './rdv-create';

describe('RdvCreate', () => {
  let component: RdvCreate;
  let fixture: ComponentFixture<RdvCreate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RdvCreate]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RdvCreate);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
