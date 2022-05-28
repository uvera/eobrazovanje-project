import { NgModule } from '@angular/core';
import { MatSidenavModule } from '@angular/material/sidenav';
import { CommonModule } from '@angular/common';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatTabsModule } from '@angular/material/tabs';
import { MatSelectModule } from '@angular/material/select';
import { MatNativeDateModule } from '@angular/material/core';
import { NzTableModule } from 'ng-zorro-antd/table';
import {
  NgxMatDatetimePickerModule,
  NgxMatTimepickerModule,
  NgxMatNativeDateModule,
} from '@angular-material-components/datetime-picker';
import { TeacherDashboardComponent } from './components/teacher-dashboard/teacher-dashboard.component';
import { LogoutComponent } from '../common/components/logout/logout.component';
import { RouterModule, Routes } from '@angular/router';
import { MySubjectsComponent } from './components/my-subjects/my-subjects.component';
import { StudentPreExamActivitiesComponent } from './components/student-pre-exam-activities/student-pre-exam-activities.component';
import { ListStudentsComponent } from './components/student-pre-exam-activities/list-students/list-students.component';
import { AddPreExamActivityResultDialogComponent } from './components/student-pre-exam-activities/add-pre-exam-activity-result-dialog/add-pre-exam-activity-result-dialog.component';
import { MyExamPeriodsComponent } from './components/my-exam-periods/my-exam-periods.component';
import { ListExamPeriodsComponent } from './components/my-exam-periods/list-exam-periods/list-exam-periods.component';
import { ScheduleExamDialogComponent } from './components/my-exam-periods/schedule-exam-dialog/schedule-exam-dialog.component';

const routes: Routes = [
  {
    path: 'dashboard',
    component: TeacherDashboardComponent,
    children: [
      {
        path: 'my-subjects',
        component: MySubjectsComponent,
      },
      {
        path: 'my-students',
        component: StudentPreExamActivitiesComponent,
        children: [
          {
            path: 'view-students',
            component: ListStudentsComponent
          },
          {
            path: '',
            pathMatch: 'full',
            redirectTo: 'view-students'
          }
        ]
      },
      {
        path: 'exam-periods',
        component: MyExamPeriodsComponent,
        children: [
          {
            path: 'list-exam-periods',
            component: ListExamPeriodsComponent
          },
          {
            path: '',
            pathMatch: 'full',
            redirectTo: 'list-exam-periods'
          }
        ]
      },
      {
        path: 'logout',
        component: LogoutComponent,
      },
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'my-subjects',
      },
    ],
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'dashboard',
  },
  {
    path: 'logout',
    component: LogoutComponent,
  },
];

@NgModule({
  declarations: [TeacherDashboardComponent, MySubjectsComponent, StudentPreExamActivitiesComponent, ListStudentsComponent, AddPreExamActivityResultDialogComponent, MyExamPeriodsComponent, ListExamPeriodsComponent, ScheduleExamDialogComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    MatSidenavModule,
    MatButtonModule,
    NzTableModule,
    MatTabsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    NgxMatDatetimePickerModule,
    NgxMatTimepickerModule,
    FlexLayoutModule,
    MatDialogModule,
    MatSelectModule,
    MatDatepickerModule,
    NgxMatNativeDateModule,
    MatNativeDateModule
  ],
})
export class TeacherModule { }
