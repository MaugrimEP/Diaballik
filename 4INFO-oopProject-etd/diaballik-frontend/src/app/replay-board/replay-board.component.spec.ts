import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReplayBoardComponent } from './replay-board.component';

describe('ReplayBoardComponent', () => {
  let component: ReplayBoardComponent;
  let fixture: ComponentFixture<ReplayBoardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReplayBoardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReplayBoardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
