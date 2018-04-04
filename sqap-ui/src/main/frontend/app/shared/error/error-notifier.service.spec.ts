/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { ErrorNotifierService } from './error-notifier.service';

describe('ErrorNotifierService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ErrorNotifierService]
    });
  });

  it('should ...', inject([ErrorNotifierService], (service: ErrorNotifierService) => {
    expect(service).toBeTruthy();
  }));
});
