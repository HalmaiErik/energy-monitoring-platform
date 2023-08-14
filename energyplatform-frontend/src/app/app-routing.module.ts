import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { ClientComponent } from './client/client.component';
import { DeviceComponent } from './device/device.component';
import { LoginComponent } from './login/login.component';
import { SensorComponent } from './sensor/sensor.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'login', component: LoginComponent },
  { path: 'admin', component: AdminComponent },
  { path: 'client/:username', component: ClientComponent },
  { path: 'client/:username/:device', component: DeviceComponent },
  { path: 'client/:username/:device/:sensor', component: SensorComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
