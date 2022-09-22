import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../models/user.model';
import {Observable} from 'rxjs';
import {catchError} from "rxjs/operators";
import {ErrorHandlerService} from "./error-handler.service";

@Injectable()
export class UserService {

  private usersUrl: string;

  public loginnedUser: User = new User();

  constructor(private http: HttpClient, private errorHandlerService: ErrorHandlerService) {
    this.usersUrl = '/api/v1/user';
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  public register(user: User) {
    return this.http.post(this.usersUrl + '/register', user, {responseType: 'text'}).pipe(catchError(this.errorHandlerService.handleError));
  }

  public login(user: User) {
    return this.http.post(this.usersUrl + '/login', user, {responseType: 'text'}).pipe(catchError(this.errorHandlerService.handleError));
  }

  public logout() {
    return this.http.get(this.usersUrl + '/logout');
  }
}
