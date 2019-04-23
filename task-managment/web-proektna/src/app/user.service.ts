import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import {TokenStorage} from './core/token.storage';
import {Activity} from './model/Activity';

@Injectable()
export class UserService {

  username: string;
  activities: any = [];
  date = new Date();
  hours: string;
  minutes: string;
  seconds: string;

  constructor(private http: HttpClient, private token: TokenStorage) {
  }

  private userURL = 'http://localhost:8081/';


  login(username, password): Observable<any> {

    // console.log('attempAuth ::');
    return this.http.post(this.userURL + 'login', {'username': username, 'password': password});
  }

  registerUser(username, password, email): Observable<any> {

    return this.http.post(this.userURL + 'sign-up', {
      'username': username,
      'password': password,
      'email': email
    });
  }

  logout() {
    this.createActivity(' ', 'Logout');
    this.token.signOut();

  }

  createRoom(roomName: string): Observable<any> {
    this.createActivity(' Created Room', roomName);
    return this.http.post(this.userURL + 'me/room/create', {'name': roomName});
  }

  findAllRooms(): Observable<any> {
    return this.http.get(this.userURL + 'me/room/all');
  }

  createTask(taskName, taskDescription, channelId, dueDate): Observable<any> {
    this.createActivity(' Created Task', taskName);

    return this.http.post(this.userURL + 'task/save', {
      'taskName': taskName,
      'taskDescription': taskDescription,
      'channelId': channelId,
      'progressBar': '0',
      'dueDate': dueDate
    });
  }

  findAllTasks(channelId): Observable<any> {
    return this.http.post(this.userURL + 'channel/' + channelId, {});
  }

  minusBtnClicked(id: string, progressBar: string, taskName: string): Observable<any> {
    // this.createActivity(' Decreased 10% On Task', taskName);
    return this.http.post(this.userURL + 'progress', {'id': id, 'progressBar': progressBar});
  }

  plusBtnClicked(id: string, progressBar: string, taskName: string): Observable<any> {
    // this.createActivity(' Increased 10% On Task', taskName);
    return this.http.post(this.userURL + 'progress', {'id': id, 'progressBar': progressBar});
  }

  deleteByTaskId(id: string, deletedTaskName): Observable<any> {
    this.createActivity(' Removed Task', deletedTaskName);
    return this.http.delete(this.userURL + 'delete/' + id);
  }

  me(): Observable<any> {
    return this.http.get(this.userURL + 'me');
  }

  createActivity(action: string, text: string) {
    this.username = sessionStorage.getItem('username');

    console.log(this.username);
    console.log(action);
    this.hours = this.date.getHours().toString();
    this.minutes = this.date.getMinutes().toString();
    this.seconds = this.date.getSeconds().toString();

    if (this.hours.length === 1) {
      this.hours = '0' + this.hours;
    }
    if (this.minutes.length === 1) {
      this.minutes = '0' + this.minutes;
    }
    if (this.seconds.length === 1) {
      this.seconds = '0' + this.seconds;
    }
    const finalText = text + ' At ' + this.hours + ':' + this.minutes + ':' + this.seconds;
    return this.http.post<Activity>(this.userURL + 'activity/save', {
      'action': this.username + action,
      'text': finalText
    }).subscribe(data => {
      this.activities.push(new Activity(data.action, data.text));
    });
  }

  loadActivities() {
    return this.activities;
  }

  findAllActivities() {
    return this.http.get<Activity>(this.userURL + 'activity/all').subscribe(
      data => {
        this.activities = data;
      }
    );
  }
}
