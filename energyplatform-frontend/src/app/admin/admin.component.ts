import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { UserEdit } from '../model/userEdit';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  public clients: User[] = [];
  public editClient: User;


  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    if (!(this.userService.isAdmin()) || !(this.userService.isLoggedIn())) {
      this.userService.logout();
      this.router.navigate([`/login`]);
    } 
    else {
      this.getClients();
    } 
  }

  public getClients(): void {
    this.userService.getClients().subscribe(
      (resp: User[]) => {
        this.clients = resp;
        console.log(this.clients);
      }
    )
  }

  public onAddClient(addForm: NgForm): void {
    document.getElementById('add-client-form')!.click();
    this.userService.addUser(addForm.value).subscribe(
      (response: Number) => {
        console.log(response);
        this.getClients();
        addForm.reset();
      },
    );
  }

  public onEditClient(editForm: NgForm, id: number): void {
    document.getElementById('edit-client-form')!.click();
    let userEdit: UserEdit = editForm.value;
    userEdit.id = id;
    this.userService.editUser(userEdit).subscribe(
      (response: Number) => {
        console.log(response);
        this.getClients();
        editForm.reset();
      },
    );
  }

  public onDeleteClient(id: Number, event: Event) {
    event.stopPropagation();
    this.userService.deleteUser(id).subscribe(
      (response: Number) => {
        console.log(response);
        this.getClients();
      }
    );
  }

  public onOpenModal(client: User, mode: string, event: Event): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addClientModal');
    }
    else if (mode === 'edit') {
      this.editClient = client;
      button.setAttribute('data-target', '#editClientModal')
      event.stopPropagation();
    }
    container!.appendChild(button);
    button.click();
  }

  public goDevices(username: String) {
    this.router.navigate([`/client/${username}`]);
  }

}
