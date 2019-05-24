import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AbstractControl, FormControl, FormGroup, ValidatorFn, Validators} from '@angular/forms';
import {UserService} from '../user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  errorAlert: string;


  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {
    this.createForm();
  }

  private createForm() {
    this.registerForm = new FormGroup({
      username: new FormControl('', Validators.required),
      companyName: new FormControl('', Validators.required),
      password: new FormControl('', [Validators.required, Validators.min(7)]),
      email: new FormControl('', [Validators.required,
        this.patternValidator(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)])
    });
  }

  patternValidator(regexp: RegExp): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      const value = control.value;
      if (value === '') {
        return null;
      }
      return !regexp.test(value) ? { 'patternInvalid': { regexp } } : null;
    };
  }

  register() {
    this.userService.registerUser(this.registerForm.controls['username'].value,this.registerForm.controls['companyName'].value,
      this.registerForm.controls['password'].value,
      this.registerForm.controls['email'].value)
      .subscribe(data => {
          this.router.navigate(['success']);
          console.log(data.id);
        },
        error => {
          this.errorAlert = 'User With Such Username Already Exists';
        });
  }

}
