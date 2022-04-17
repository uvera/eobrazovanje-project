import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppCommonModule } from './common/app-common.module';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';
import { Router, RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './common/components/dashboard/dashboard.component';
import { PrivateGuard } from './common/guard/private-guard.guard';
import { LoginComponent } from './common/components/login/login.component';
import { PublicGuard } from './common/guard/public-guard.guard';
import { AdminGuard } from './common/guard/admin-guard.guard';
import { NotFoundComponent } from './common/components/not-found/not-found.component';
import { StudentGuard } from './common/guard/student-guard.guard';
import { TeacherGuard } from './common/guard/teacher-guard.guard';
import { AuthInterceptor } from './common/http/auth-interceptor';
import { SessionService } from './common/service/session.service';
import { ApiService } from './common/service/api.service';

const routes: Routes = [
  {
    path: 'teacher',
    canLoad: [TeacherGuard],
    canActivate: [TeacherGuard],
    canActivateChild: [TeacherGuard],
    loadChildren: () =>
      import('./teacher/teacher.module').then((m) => m.TeacherModule),
  },
  {
    path: 'student',
    canLoad: [StudentGuard],
    canActivate: [StudentGuard],
    canActivateChild: [StudentGuard],
    loadChildren: () =>
      import('./student/student.module').then((m) => m.StudentModule),
  },
  {
    path: 'admin',
    canLoad: [AdminGuard],
    canActivate: [AdminGuard],
    canActivateChild: [AdminGuard],
    loadChildren: () =>
      import('./admin/admin.module').then((m) => m.AdminModule),
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [PrivateGuard],
    canLoad: [PrivateGuard],
    canActivateChild: [PrivateGuard],
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [PublicGuard],
    canLoad: [PublicGuard],
    canActivateChild: [PublicGuard],
  },
  { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
  {
    path: '404',
    component: NotFoundComponent,
  },
  {
    path: '**',
    redirectTo: '/404',
  },
];

@NgModule({
  declarations: [AppComponent],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    FormsModule,
    FlexLayoutModule,
    BrowserAnimationsModule,
    CommonModule,
    AppCommonModule,
    MatSnackBarModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      multi: true,
      deps: [Router, SessionService, ApiService],
      useFactory: function (
        router: Router,
        session: SessionService,
        api: ApiService
      ) {
        return new AuthInterceptor(router, session, api);
      },
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
