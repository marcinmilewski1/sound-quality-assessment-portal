/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { GlobalRulesService } from './global-rules.service';

describe('GlobalRulesService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GlobalRulesService]
    });
  });

  it('should ...', inject([GlobalRulesService], (service: GlobalRulesService) => {
    expect(service).toBeTruthy();
  }));
});
