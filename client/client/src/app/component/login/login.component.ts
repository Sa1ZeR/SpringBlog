import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TokenStorageService} from "../../services/token-storage.service";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {NotificationService} from "../../services/notification.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public loginForm!: FormGroup;

  constructor(
    private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private notificationServ: NotificationService,
    private router: Router,
    private fb: FormBuilder,
    ) {
      if(this.tokenStorage.getUser()) {
        router.navigate(['/'])
      }
  }

  ngOnInit(): void {
    this.loginForm = this.createLoginForm();
  }

  createLoginForm():FormGroup {
      return this.fb.group({
        username: ['', Validators.compose([Validators.required])],
        password: ['', Validators.compose([Validators.required])]
      })
  }

  submit(): void {
    this.authService.auth({
      username: this.loginForm.value.username,
      password: this.loginForm.value.password
    }).subscribe(data => {
      console.log(data)

      this.tokenStorage.saveToken(data.token);
      this.tokenStorage.saveUser(data);
      this.notificationServ.showSnackBar("Успешная авторизация!");
      this.router.navigate(['/']);
      setTimeout(function (){
        window.location.reload();
      }, 3000);
    }, error => {
      console.log(error);
      this.notificationServ.showSnackBar(error);
    });

  }

}
