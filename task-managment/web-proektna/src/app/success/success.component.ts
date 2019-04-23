import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent implements OnInit {

  successMessage = 'Your registration is successful! Now you can ';
  constructor() { }

  ngOnInit() {
  }

}
