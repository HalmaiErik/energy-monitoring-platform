import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { WebSocketApi } from '../webSocketApi';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isLoggedIn: boolean;
  isAdmin: boolean

  constructor(private userService: UserService, private webSocketApi: WebSocketApi, private router: Router) { }

  ngOnInit(): void {
    this.userService.loggedIn.subscribe((data: boolean) => this.isLoggedIn = data);
    this.isLoggedIn = this.userService.isLoggedIn();
    this.userService.adminCheck.subscribe((data: boolean) => this.isAdmin = data);
    this.isAdmin = this.userService.isAdmin();
  }

  goAdminHome() {
    this.router.navigate([`/admin`]);
  }

  goClientHome() {
    let username: string = this.userService.getUsername();
    this.router.navigate([`/client/${username}`]);
  }

  logout() {
    this.webSocketApi._disconnect();
    this.userService.logout();
    this.isLoggedIn = false;
    this.isAdmin = false;
  }

}
