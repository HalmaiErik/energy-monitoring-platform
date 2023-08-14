import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable, Output } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginRequest } from '../model/loginRequest';
import { User } from '../model/user';
import { LocalStorageService } from 'ngx-webstorage';
import { map } from 'rxjs/operators'
import { LoginResponse } from '../model/loginResponse';
import { UserEdit } from '../model/userEdit';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  @Output() loggedIn: EventEmitter<boolean> = new EventEmitter();
  @Output() adminCheck: EventEmitter<boolean> = new EventEmitter();

  constructor(private http: HttpClient, private localStorage: LocalStorageService) { }

  login(loginRequest: LoginRequest): Observable<boolean> {
    return this.http.post<LoginResponse>(`${environment.apiBaseUrl}/login`, loginRequest).pipe(map(data => {
      this.localStorage.store('username', data.username);
      this.localStorage.store('jwtToken', data.token);
      this.localStorage.store('roles', data.roles);
      this.loggedIn.emit(true);
      this.adminCheck.emit(this.isAdmin());
      return true;
    }));
  }

  logout() {
    this.localStorage.clear('username');
    this.localStorage.clear('jwtToken');
    this.localStorage.clear('roles');
  }

  isLoggedIn(): boolean {
    return this.getJwtToken() != null;
  }

  getUsername() {
    return this.localStorage.retrieve('username');
  }

  getJwtToken() {
    return this.localStorage.retrieve('jwtToken');
  }

  getRoles() {
    return this.localStorage.retrieve('roles');
  }

  isAdmin(): boolean {
    let roles: String[] = this.getRoles();
    for (let role of roles) {
      if (role == "ADMIN") {
        return true;
      }
    }
    return false;
  }

  public addUser(user: User): Observable<Number> {
    user.roles = ['CLIENT'];
    return this.http.post<Number>(`${environment.apiBaseUrl}/new/user`, user);
  }

  public editUser(userEdit: UserEdit): Observable<Number> {
    return this.http.post<Number>(`${environment.apiBaseUrl}/edit/user`, userEdit);
  }

  public deleteUser(id: Number): Observable<Number> {
    return this.http.delete<Number>(`${environment.apiBaseUrl}/delete/user/${id}`);
  }

  public getClients(): Observable<User[]> {
    return this.http.get<User[]>(`${environment.apiBaseUrl}/view/users`);
  }
}
