import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Sensor } from '../model/sensor';
import { SensorEdit } from '../model/sensorEdit';

@Injectable({
  providedIn: 'root'
})
export class SensorService {

  constructor(private http: HttpClient) { }

  public addSensor(sensor: Sensor): Observable<Number> {
    return this.http.post<Number>(`${environment.apiBaseUrl}/new/sensor`, sensor);
  }

  public editSensor(sensorEdit: SensorEdit): Observable<Number> {
    return this.http.post<Number>(`${environment.apiBaseUrl}/edit/sensor`, sensorEdit);
  }

  public deleteSensor(id: Number): Observable<Number> {
    return this.http.delete<Number>(`${environment.apiBaseUrl}/delete/sensor/${id}`);
  }

  public getSensorOfDevice(idDevice: Number): Observable<Sensor> {
    return this.http.get<Sensor>(`${environment.apiBaseUrl}/view/sensor/by-device/${idDevice}`);
  }
}
