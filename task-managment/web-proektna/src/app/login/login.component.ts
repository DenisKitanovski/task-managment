import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {UserService} from '../user.service';
import {TokenStorage} from '../core/token.storage';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import * as $ from 'jquery';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loginError: string;
  credentials = {username: '', password: ''};
  username: string;
  email: string;
  constructor(private router: Router, private userService: UserService, private token: TokenStorage) { }

  ngOnInit() {
    this.createForm();
  }

  private createForm() {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
  }

  login() {
    this.userService.login(this.loginForm.controls['username'].value, this.loginForm.controls['password'].value).subscribe(data => {
      //  this.token.saveToken(data.token);
     //   console.log(sessionStorage.getItem('AuthToken'));
        console.log(data);
        this.token.saveToken(data.token);
        localStorage.setItem('username', data.user.username);
        localStorage.setItem('email', data.user.email);
        localStorage.setItem('dashboardId', data.user.dashboardId);
        localStorage.setItem('companyName', data.companyName);
      //  this.userService.createActivity(' ', 'Signed In');
        this.router.navigate(['user']);
      },
      error => {
        console.log(error);
      }
    );
  }

  me() {
    this.userService.me().subscribe(data => {
      console.log(data);
      this.username = data.username;
      this.email = data.email;
      sessionStorage.setItem('username', this.username);
      sessionStorage.setItem('email', this.email);
      // console.log(sessionStorage.getItem('username'));
      this.userService.createActivity(' ', 'Signed In');
    });
  }

}
