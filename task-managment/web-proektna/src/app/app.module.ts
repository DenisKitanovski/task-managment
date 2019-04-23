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

const ROUTES: Routes = [
  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent, },
  {path: 'user', component: UserComponent, canActivate: [AuthGuard]},
  {path: 'register', component: RegisterComponent},
  {path: 'success', component: SuccessComponent}

];


@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    LoginComponent,
    RegisterComponent,
    SuccessComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(ROUTES),
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [UserService, AuthGuard, TokenStorage, {
    provide: HTTP_INTERCEPTORS,
    useClass: Interceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
