import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {UserService} from './user.service';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {UserComponent} from './user/user.component';
import {RouterModule, Routes} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Interceptor} from './core/interceptor';
import {TokenStorage} from './core/token.storage';
import {LoginComponent} from './login/login.component';
import {AuthGuard} from './core/AuthGuard';
import { RegisterComponent } from './register/register.component';
import { SuccessComponent } from './success/success.component';
import { TaskComponent } from './task/task.component';
import {DragulaModule} from "ng2-dragula";

const ROUTES: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'success', component: SuccessComponent},
  {path: 'user', component: UserComponent, children: [
    {path: ':id', component: TaskComponent}
  ]}

];


@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    LoginComponent,
    RegisterComponent,
    SuccessComponent,
    TaskComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(ROUTES),
    FormsModule,
    ReactiveFormsModule,
    DragulaModule.forRoot()
  ],
  providers: [UserService, AuthGuard, TokenStorage, {
    provide: HTTP_INTERCEPTORS,
    useClass: Interceptor,
    multi: true
  }],
  bootstrap: [AppComponent],
  exports: [RouterModule]
})
export class AppModule {
}
