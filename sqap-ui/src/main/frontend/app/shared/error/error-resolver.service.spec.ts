/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { ErrorResolverService } from './error-resolver.service';

describe('ErrorResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ErrorResolverService]
    });
  });

  it('should ...', inject([ErrorResolverService], (service: ErrorResolverService) => {
    expect(service).toBeTruthy();
  }));
});
