import { TestBed } from '@angular/core/testing';

import { SensorlogService } from './sensorlog.service';

describe('SensorlogService', () => {
  let service: SensorlogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SensorlogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
