import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SensorLog } from '../model/sensorLog';

@Injectable({
  providedIn: 'root'
})
export class SensorlogService {

  constructor(private http: HttpClient) { }

  public addSensorLog(sensorLog: SensorLog): Observable<Number> {
    return this.http.post<Number>(`${environment.apiBaseUrl}/new/sensorlog`, sensorLog);
  }

  public getLogsOfDevice(idSensor: Number): Observable<SensorLog[]> {
    return this.http.get<SensorLog[]>(`${environment.apiBaseUrl}/view/sensorlogs/by-sensor/${idSensor}`);
  }
}
