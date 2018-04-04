/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { TestGroupService } from './test-group.service';

describe('TestGroupService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TestGroupService]
    });
  });

  it('should ...', inject([TestGroupService], (service: TestGroupService) => {
    expect(service).toBeTruthy();
  }));
});
