import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MaterialModule} from "./material-module";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {authInterceptorProvider} from "./helper/auth-interceptor.service";
import {authErrorInterceptor} from "./helper/error-interceptor.service";
import { RegisterComponent } from './component/register/register.component';
import { LoginComponent } from './component/login/login.component';
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatRippleModule} from "@angular/material/core";

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatRippleModule
  ],
  providers: [authInterceptorProvider, authErrorInterceptor],
  bootstrap: [AppComponent]
})
export class AppModule { }
