import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { LoginRequest } from '../model/loginRequest';
import { UserService } from '../service/user.service';
import { WebSocketApi } from '../webSocketApi';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loginRequest: LoginRequest;

  constructor(private userService: UserService, private activatedRoute: ActivatedRoute,
    private router: Router, private toastr: ToastrService, private webSocketApi: WebSocketApi) { 
      this.loginRequest = {
        username: '',
        password: ''
      };
     }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
  }

  login() {
    this.loginRequest.username = this.loginForm.get('username').value;
    this.loginRequest.password = this.loginForm.get('password').value;

    this.userService.login(this.loginRequest).subscribe(data => {
      this.toastr.success('Login successful');
      this.webSocketApi._connect();

      let roles: String[] = this.userService.getRoles();
      for (let role of roles) {
        if (role.toString() == 'ADMIN') {
          this.router.navigateByUrl('/admin');
          return;
        }
      }
      this.router.navigate([`/client/${this.loginRequest.username}`]);
    }, error => {
      this.toastr.error("Unsuccessful login");
    })

  }

}
