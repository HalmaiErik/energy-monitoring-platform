import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Device } from '../model/device';
import { Sensor } from '../model/sensor';
import { SensorEdit } from '../model/sensorEdit';
import { DeviceService } from '../service/device.service';
import { SensorService } from '../service/sensor.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-device',
  templateUrl: './device.component.html',
  styleUrls: ['./device.component.css']
})
export class DeviceComponent implements OnInit {

  public sensor: Sensor;
  public devices: Device[] = [];
  public deviceId: number;
  public editSensor: Sensor;
  public hasSensor: boolean = false;
  public isAdmin: boolean;

  constructor(private sensorService: SensorService, private router: Router,
    private route: ActivatedRoute, private deviceService: DeviceService, 
    private userService: UserService) { }

  ngOnInit(): void {
    this.route.params.subscribe(
      params => {
        this.deviceId = params['device'];
      }
    );
    this.getData();
    this.isAdmin = this.userService.isAdmin();
  }

  public getData(): void {
    this.deviceService.getAllDevices().subscribe(
      (resp: Device[]) => {
        this.devices = resp;
        console.log(this.devices);
        this.getSensor();
      }
    );
  }

  private getSensor(): void {
    this.sensorService.getSensorOfDevice(this.deviceId).subscribe(
      (resp: Sensor) => {
        this.sensor = resp;
        console.log(this.sensor);
        if (this.sensor != null) {
          this.hasSensor = true;
        }
        else {
          this.hasSensor = false;
        }
      }
    )
  }

  public onAddSensor(addForm: NgForm): void {
    document.getElementById('add-sensor-form')!.click();
    let s: Sensor = addForm.value;
    console.log(s);
    this.sensorService.addSensor(s).subscribe(
      (response: Number) => {
        console.log(response);
        this.getSensor();
        addForm.reset();
      },
    );
  }

  public onEditSensor(editForm: NgForm, id: number): void {
    document.getElementById('edit-sensor-form')!.click();
    let sensorEdit: SensorEdit = editForm.value;
    sensorEdit.id = id;
    console.log(sensorEdit);
    this.sensorService.editSensor(sensorEdit).subscribe(
      (response: Number) => {
        console.log(response);
        this.getSensor();
        editForm.reset();
      },
    );
  }

  public onDeleteSensor(id: Number, event: Event) {
    event.stopPropagation();
    this.sensorService.deleteSensor(id).subscribe(
      (response: Number) => {
        console.log(response);
        this.getSensor();
      }
    );
  }

  public onOpenModal(sensor: Sensor, mode: string, event: Event): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addSensorModal');
    }
    else if (mode === 'edit') {
      this.editSensor = sensor;
      button.setAttribute('data-target', '#editSensorModal')
      event.stopPropagation();
    }
    container!.appendChild(button);
    button.click();
  }

  public goSensorLogs(id: Number) {
    this.router.navigate([this.router.url + `/${id}`]);
  }

}
