import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../models/User";

const AUTH_API = "http://localhost:8080/api/auth/"

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  public auth(user:any):Observable<any> {
    return this.http.post(AUTH_API + "signin", {
      username: user.username,
      password: user.password,
    })
  }

  public registration(user:any):Observable<any> {
    return this.http.post(AUTH_API + "signup", {
      username: user.username,
      email: user.email,
      firstname: user.firstname,
      lastname: user.lastname,
      password: user.password,
      confPassword: user.confPassword
    })
  }
}
