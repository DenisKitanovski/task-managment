import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../user.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {TokenStorage} from '../core/token.storage';
import {DomSanitizer} from '@angular/platform-browser';
import {RoomDet} from '../model/roomDet';
import {forEach} from '@angular/router/src/utils/collection';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import * as $ from 'jquery';
import {Task} from '../model/Task';
import {Progress} from '../model/Progress';
import {Activity} from '../model/Activity';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  $: any;
  jquery: any;
  companyName: string;
  username: string;
  email: string;
  name: string;
  addRoom: any = [];
  lastClicked: any;
  taskName: string;
  taskDescription: string;
  dueDate: string;
  roomExistsAlert = false;
  selectedItem: any;
  activities: any = [];
  roomNumber: number;
  taskNumber: number;
  constructor(private router: Router, private userService: UserService, private token: TokenStorage, private sanitizer: DomSanitizer) {
  }



  ngOnInit() {
    this.findAllRooms();
   // this.me();
    // this.findAllActivities();
    this.username = localStorage.getItem('username');
    this.email = localStorage.getItem('email');
    this.companyName = localStorage.getItem('companyName');
    this.router.navigate(['/user/', this.addRoom[0].id]);
  }

  logout() {
    this.userService.logout();
    this.router.navigate(['login']);
  }

  createRoom() {
    for (const a of this.addRoom) {
      if (this.name === a.name) {
        this.roomExistsAlert = true;
        this.name = '';
      }
    }
    if (!this.roomExistsAlert && this.name) {
      this.userService.createRoom(this.name).subscribe(
        data => {
          this.addRoom.push(new RoomDet(data.id, data.name));
          this.name = '';
        }
      );
    }
  }

  findAllRooms() {
    this.userService.findAllRooms().subscribe(
      data => {
        for (const d of data) {
          this.addRoom.push(new RoomDet(d.id, d.name));
        }
      }
    );
  }




  listClick(event, newValue) {
    $('.room-name').hover(function () {
        $(this).css({
          'background': 'white',
          'color': 'black',
          'cursor': 'pointer',
          'font-size': '20px'

        });
      },
      function () {
        $(this).css({
          'background': '#404040',
          'color': 'white',
          'cursor': 'pointer',
          'font-size': '20px'

        });
      });

    if (this.selectedItem) {
      $('#' + this.selectedItem).css({
        'background': '#404040',
        'color': 'white',
        'border-bottom': '1px solid #404040',
        'border-top': '1px solid #404040',
        'border-left': '1px solid #404040',
        'border-right': '1px solid #404040',
        'overflow': 'hidden'

      });
    }
    this.selectedItem = newValue;
    $('#' + this.selectedItem).css({
      'background': 'white',
      'color': 'black',
      'font-size': '20px',
      'border-bottom': '1px solid white',
      'border-top': '1px solid white',
      'border-left': '1px solid white',
      'border-right': '1px solid white',
      'overflow': 'hidden'
    });

  }


  loadActivities() {
   this.activities =   this.userService.loadActivities();
  }

  findAllActivities() {
    this.activities = this.userService.findAllActivities();
  }

  profile() {
  }

}

