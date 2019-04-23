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
  name: string;
  addRoom: any = [];
  lastClicked: any;
  taskName: string;
  taskDescription: string;
  dueDate: string;
  tasks: any = [];
  channelId: string;
  progressWidth = 0;
  progressBar: string;
  progress: Progress;
  roomExistsAlert = false;
  selectedItem: any;
  username: string;
  activities: any = [];
  email: string;
  roomNumber: number;
  taskNumber: number;
  formatDate: string;

  constructor(private router: Router, private userService: UserService, private token: TokenStorage, private sanitizer: DomSanitizer) {
  }



  ngOnInit() {
    this.findAllRooms();
   // this.me();
    this.username = sessionStorage.getItem('username');
    this.findAllActivities();


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
          this.addRoom.push(new RoomDet(data.id, this.name));
          this.name = '';
        }
      );
    }
  }

  findAllRooms() {
    this.userService.findAllRooms().subscribe(
      data => {
        // console.log(JSON.stringify(data));
        for (const d of data) {
          this.addRoom.push(new RoomDet(d.id, d.name));
        }
      }
    );
  }


  getRoomMessages(roomId: string) {
    this.channelId = '';
    this.tasks = [];
    // console.log('work!!! ' + roomId);
    this.channelId = roomId;
    this.findAllTasks();
  }

  createTask() {
    console.log(this.taskName + '' + this.taskDescription + ' ' + this.channelId);
    if (this.taskName) {
      this.userService.createTask(this.taskName, this.taskDescription, this.channelId, this.dueDate).subscribe(
        data => {
          console.log(data);
          const date = data.dueDate.split('-');
          this.formatDate = date[2] + '/' + date[1] + '/' + date[0];
          this.tasks.push(new Task(data.id, data.taskName, data.taskDescription, this.channelId, data.progressBar, this.formatDate));
          this.router.navigate(['user']);
          this.taskName = '';
          this.taskDescription = '';
          this.dueDate = '';
        }
      );
    }
  }

  findAllTasks() {
    this.userService.findAllTasks(this.channelId).subscribe(
      data => {
        // console.log(data);
        for (const t of data) {
          const date = t.dueDate.split('-');
          this.formatDate = date[2] + '/' + date[1] + '/' + date[0];
          this.tasks.push(new Task(t.id, t.taskName, t.taskDescription, t.channelId, t.progressBar, this.formatDate));
        }

      }
    );

  }

  minusBtnClicked(id: string, taskName: string) {
    for (const p of this.tasks) {
      if (p.id === id) {
        //   console.log(p);
        //   console.log('Found ' + p.progressBar);
        this.progressWidth = +p.progressBar;
        if (this.progressWidth > 0) {
          this.progressWidth -= 10;
          this.userService.minusBtnClicked(id, this.progressWidth.toString(), taskName).subscribe(
            (data) => {

              p.progressBar = data.progressBar;

            }
          );
        }
      }

    }
  }

  plusBtnClicked(id: string, taskName: string) {
    for (const p of this.tasks) {
      if (p.id === id) {
        //  console.log(p);
        //  console.log('Found ' + p.progressBar);
        this.progressWidth = +p.progressBar;
        if (this.progressWidth < 100) {
          this.progressWidth += 10;
          this.userService.plusBtnClicked(id, this.progressWidth.toString(), taskName).subscribe(
            (data) => {

              p.progressBar = data.progressBar;

            }
          );
        }
      }
    }

  }

  deleteByTaskId(id: string, index: string, deletedTaskName: string) {
    this.tasks[index].show = true;
    this.userService.deleteByTaskId(id, deletedTaskName).subscribe(
      data => {
        console.log(data);
       // this.findAllTasks();
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
    this.username = sessionStorage.getItem('username');
    this.email = sessionStorage.getItem('email');
    this.taskNumber = this.tasks.length;
  }
}

