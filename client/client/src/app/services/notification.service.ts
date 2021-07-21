import { Injectable } from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private bar: MatSnackBar) { }

  showSnackBar(str: string): void {
    this.bar.open(str, "!", {
      duration: 3000
    })
  }
}
