import { identifierModuleUrl } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Device } from '../model/device';
import { DeviceEdit } from '../model/deviceEdit';
import { User } from '../model/user';
import { DeviceService } from '../service/device.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit {

  public devices: Device[] = [];
  public clients: User[] = [];
  public clientUsername: string;
  public client: User;
  public editDevice: Device;
  public isAdmin: boolean;

  constructor(private deviceService: DeviceService, private router: Router,
    private route: ActivatedRoute, private userService: UserService) { }

  ngOnInit(): void {
    this.route.params.subscribe(
      params => {
        this.clientUsername = params['username'];
      }
    );
    this.getData();
    this.isAdmin = this.userService.isAdmin();
  }

  public getData(): void {
    this.userService.getClients().subscribe(
      (resp: User[]) => {
        this.clients = resp;
        console.log(this.clients);
        for (let c of this.clients) {
          if (c.username == this.clientUsername) {
            this.client = c;
            console.log('Clients ID: ' + this.client);
            this.getDevices();
            return;
          }
        }
      }
    );
  }

  private getDevices(): void {
    this.deviceService.getDevicesOfClient(this.client.id).subscribe(
      (resp: Device[]) => {
        this.devices = resp;
        console.log('Devices of client: ' + this.devices);
      }
    )
  }

  public onAddDevice(addForm: NgForm): void {
    document.getElementById('add-device-form')!.click();
    let device: Device = addForm.value;
    device.idUser = this.client.id;
    console.log(device);
    this.deviceService.addDevice(device).subscribe(
      (response: Number) => {
        console.log(response);
        this.getDevices();
        addForm.reset();
      },
    );
  }

  public onEditDevice(editForm: NgForm, id: number): void {
    document.getElementById('edit-device-form')!.click();
    let deviceEdit: DeviceEdit = editForm.value;
    deviceEdit.id = id;
    console.log(deviceEdit);
    this.deviceService.editDevice(deviceEdit).subscribe(
      (response: Number) => {
        console.log(response);
        this.getDevices();
        editForm.reset();
      },
    );
  }

  public onDeleteDevice(id: Number, event: Event) {
    event.stopPropagation();
    this.deviceService.deleteDevice(id).subscribe(
      (response: Number) => {
        console.log(response);
        this.getDevices();
      }
    );
  }

  public onOpenModal(device: Device, mode: string, event: Event): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addDeviceModal');
    }
    else if (mode === 'edit') {
      this.editDevice = device;
      button.setAttribute('data-target', '#editDeviceModal')
      event.stopPropagation();
    }
    container!.appendChild(button);
    button.click();
  }

  public goSensor(id: Number) {
    this.router.navigate([`/client/${this.clientUsername}/${id}`]);
  }

}
