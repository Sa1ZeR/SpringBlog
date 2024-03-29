import { Component, OnInit } from '@angular/core';
import {User} from "../../models/User";
import {TokenStorageService} from "../../services/token-storage.service";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {


  isLoggedIn = false;
  dataIsLoaded = false;
  user!: User;

  constructor(private tokenService: TokenStorageService,
              private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenService.getToken()
    if(this.isLoggedIn) {
      this.userService.getCurrentUser().subscribe(
        data => {
          this.user = data;
          this.dataIsLoaded = true;
        }
      )
    }
  }

  logout() {
    this.tokenService.logout();
    this.router.navigate([
      '/login'
    ])
  }

}
