/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { PoolingComponent } from './pooling.component';

describe('PoolingComponent', () => {
  let component: PoolingComponent;
  let fixture: ComponentFixture<PoolingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PoolingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PoolingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
