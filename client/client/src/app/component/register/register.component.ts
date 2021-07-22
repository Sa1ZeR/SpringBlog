import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NotificationService} from "../../services/notification.service";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {TokenStorageService} from "../../services/token-storage.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  public formGroup!: FormGroup

  constructor(
    private notfication: NotificationService,
    private router: Router,
    private fb: FormBuilder,
    private authService: AuthService,
    private tokenStorage: TokenStorageService,
  ) {
    if(tokenStorage.getUser()) {
      router.navigate(['/'])
    }
  }

  ngOnInit(): void {
    this.formGroup = this.createRegForm();
  }

  createRegForm(): FormGroup {
    return this.fb.group({
      email: ['', Validators.compose([Validators.required, Validators.email])],
      firstname: ['', Validators.compose([Validators.required])],
      lastname: ['', Validators.compose([Validators.required])],
      username: ['', Validators.compose([Validators.required])],
      password: ['', Validators.compose([Validators.required, Validators.min(6)])],
      confPassword: ['', Validators.compose([Validators.required, Validators.min(6)])],
    })
  }

  submit():void {
    this.authService.registration({
      email: this.formGroup.value.email,
      firstname: this.formGroup.value.firstname,
      lastname: this.formGroup.value.lastname,
      username: this.formGroup.value.username,
      password: this.formGroup.value.password,
      confPassword: this.formGroup.value.confPassword,
    }).subscribe(data => {
      this.router.navigate(['/login']);
      this.notfication.showSnackBar("Успешная регистрация");
    },error => {
      console.log(error);
      this.notfication.showSnackBar(error);
    })
  }

}
