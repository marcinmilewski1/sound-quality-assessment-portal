/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { NoopDomSanitizerService } from './noop-dom-sanitizer.service';

describe('NoopDomSanitizerService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NoopDomSanitizerService]
    });
  });

  it('should ...', inject([NoopDomSanitizerService], (service: NoopDomSanitizerService) => {
    expect(service).toBeTruthy();
  }));
});
