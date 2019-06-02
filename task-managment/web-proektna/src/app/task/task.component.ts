import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TokenStorage} from "../core/token.storage";
import {DomSanitizer} from "@angular/platform-browser";
import {UserService} from "../user.service";
import {Progress} from "../model/Progress";
import {Task} from "../model/Task";
import {DragulaService} from "ng2-dragula";
import {Subscription} from "rxjs/Subscription";
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {
  lastClicked: any;
  taskName: string;
  taskDescription: string;
  dueDate: string;
  tasks = [];
  roomId: string;
  progressWidth = 0;
  progressBar: string;
  progress: Progress;
  formatDate: string;
  username: string;
  subs = new Subscription();
  oldPosition: string;
  oldColumn: string;
  serverUrl = 'http://localhost:8081/socket';
  stompClient: any;
  constructor(private router: Router, private route: ActivatedRoute, private userService: UserService,
              private token: TokenStorage, private sanitizer: DomSanitizer,  private dragulaService: DragulaService, private tokenStorage: TokenStorage) {

    this.subs.add(dragulaService.drag('task').subscribe((value) => {
        this.oldPosition = this.getElementIndex(value.el);
        this.oldColumn = value.el.parentElement.id;
        //console.log(this.oldPosition);
        //console.log(value.el.parentElement.id);
      })
    );

    this.subs.add(dragulaService.drop('task').subscribe((value) => {

     // console.log(value.el.parentElement.id);
      const newPosition = this.getElementIndex(value.el);
      const newColumn = value.el.parentElement.id;
      const id = value.el.id;
     // console.log(newPosition);
      //console.log(value.el.parentElement.id);
      //console.log(value.el.id);

      this.sendTask(id, this.oldColumn, this.oldPosition, newColumn, newPosition);
    /* this.userService.changeColumnAndTaskPosition(id, this.oldColumn, this.oldPosition, newColumn, newPosition).subscribe(value => {

      });*/
    }));

  }
  getElementIndex(el) {
    return [].slice.call(el.parentElement.children).indexOf(el);
  }

  ngOnInit() {
    this.username = sessionStorage.getItem('username');
    this.route.paramMap.subscribe(params => {
      this.roomId = params.get('id');
      this.findAllTasks();

      this.initializeWebSocketConnection(this.roomId);
    });

  }

  initializeWebSocketConnection(roomId) {
    const currentUser = this.tokenStorage.getToken();
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    let that = this;
    this.stompClient.connect( {}, function(frame) {
      that.stompClient.subscribe("/chat/" + roomId, (message) => {
        if(message.body) {
         // console.log('websocket in room: ' + JSON.stringify(message.body));
          const newColumnAndPosition = JSON.parse(message.body);

          that.tasks.map(task => {
            if (task.id === newColumnAndPosition['id']) {
              task.column = newColumnAndPosition['newColumn'];
              console.log(task);
            }
          });
        }
      });
    });
  }

  sendTask(id, oldColumn, oldPosition, newColumn, newPosition){
    this.stompClient.send("/app/task/" + this.roomId, {},
      JSON.stringify({
      'id': id,
      'oldColumn': oldColumn,
      'oldPosition': oldPosition,
      'newColumn': newColumn,
      'newPosition': newPosition
    })
    );
  }

 /* getRoomMessages(roomId: string) {
    this.channelId = '';
    this.tasks = [];
    // console.log('work!!! ' + roomId);
    this.channelId = roomId;
    this.findAllTasks();
  }*/

  createTask() {
   // console.log(this.taskName + '' + this.taskDescription + ' ' + this.roomId);
    if (this.taskName) {
      this.userService.createTask(this.taskName, this.taskDescription, this.roomId, this.dueDate).subscribe(
        data => {
         // console.log(data);
          const date = data.dueDate.split('-');
          this.formatDate = date[2] + '/' + date[1] + '/' + date[0];
          this.tasks.push(new Task(data.id, data.taskName, data.taskDescription, this.roomId, data.progressBar, this.formatDate, data.column, data.position));
          this.taskName = '';
          this.taskDescription = '';
          this.dueDate = '';
        }
      );
    }
  }

  findAllTasks() {
    this.userService.findAllTasks(this.roomId).subscribe(
      data => {
        this.tasks.length = 0;
       //  console.log(data);
        for (const t of data) {
          const date = t.dueDate.split('-');
          this.formatDate = date[2] + '/' + date[1] + '/' + date[0];
          this.tasks.push(new Task(t.id, t.taskName, t.taskDescription, t.channelId, t.progressBar, this.formatDate, t.column, t.position));
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
   // this.tasks[index].show = true;

    this.tasks.splice(+index, 1);
    this.userService.deleteByTaskId(id, deletedTaskName).subscribe(
      data => {
      //  console.log(data);
        // this.findAllTasks();
      }
    );
  }

}
