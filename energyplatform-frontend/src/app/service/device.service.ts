import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Device } from '../model/device';
import { DeviceEdit } from '../model/deviceEdit';

@Injectable({
  providedIn: 'root'
})
export class DeviceService {

  constructor(private http: HttpClient) { }

  public addDevice(device: Device): Observable<Number> {
    return this.http.post<Number>(`${environment.apiBaseUrl}/new/device`, device);
  }

  public editDevice(deviceEdit: DeviceEdit): Observable<Number> {
    return this.http.post<Number>(`${environment.apiBaseUrl}/edit/device`, deviceEdit);
  }

  public deleteDevice(id: Number): Observable<Number> {
    return this.http.delete<Number>(`${environment.apiBaseUrl}/delete/device/${id}`);
  }

  public getDevicesOfClient(idClient: Number): Observable<Device[]> {
    return this.http.get<Device[]>(`${environment.apiBaseUrl}/view/devices/by-client/${idClient}`);
  }

  public getAllDevices(): Observable<Device[]> {
    return this.http.get<Device[]>(`${environment.apiBaseUrl}/view/devices`);
  }
}
