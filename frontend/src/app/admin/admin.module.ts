import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatSelectModule } from '@angular/material/select';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { RouterModule, Routes } from '@angular/router';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { SubjectsComponent } from './components/subjects/subjects.component';
import { StudyProgramsComponent } from './components/study-programs/study-programs.component';
import { ListSubjectsTabComponent } from './components/subjects/list-subjects-tab/list-subjects-tab.component';
import { MatTabsModule } from '@angular/material/tabs';
import { CreateSubjectTabComponent } from './components/subjects/create-subject-tab/create-subject-tab.component';
import { NzTableModule } from 'ng-zorro-antd/table';
import {
  NgxMatDatetimePickerModule,
  NgxMatTimepickerModule,
  NgxMatNativeDateModule,
} from '@angular-material-components/datetime-picker';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatDialogModule } from '@angular/material/dialog';
import { EditSubjectDialogComponent } from './components/subjects/edit-subject-dialog/edit-subject-dialog.component';
import { StudentsComponent } from './components/students/students.component';
import { ListStudentsTabComponent } from './components/students/list-students-tab/list-students-tab.component';
import { CreateStudentTabComponent } from './components/students/create-student-tab/create-student-tab.component';
import { EditStudentDialogComponent } from './components/students/edit-student-dialog/edit-student-dialog.component';
import { CreateStudyProgramTabComponent } from './components/study-programs/create-study-program-tab/create-study-program-tab.component';
import { ProfesorComponent } from './components/profesor/profesor.component';
import { ListProfesorTabComponent } from './components/profesor/list-profesor-tab/list-profesor-tab.component';
import { CreateProfesorTabComponent } from './components/profesor/create-profesor-tab/create-profesor-tab.component';
import { EditProfesorDialogComponent } from './components/profesor/edit-profesor-dialog/edit-profesor-dialog.component';
import { PaymentComponent } from './components/payment/payment.component';
import { ListPaymentTabComponent } from './components/payment/list-payment-tab/list-payment-tab.component';
import { EditPaymentDialogComponent } from './components/payment/edit-payment-dialog/edit-payment-dialog.component';
import { CreatePaymentTabComponent } from './components/payment/create-payment-tab/create-payment-tab.component';
import {
  ListStudyProgramsTabComponent,
  SubjectsNamePipe,
} from './components/study-programs/list-study-programs-tab/list-study-programs-tab.component';
import { EditStudyProgramDialogComponent } from './components/study-programs/edit-study-program-dialog/edit-study-program-dialog.component';
import { PreExamActivitiesComponent } from './components/pre-exam-activities/pre-exam-activities.component';
import { ListPreExamActivitiesComponent } from './components/pre-exam-activities/list-pre-exam-activities/list-pre-exam-activities.component';
import { CreatePreExamActivityComponent } from './components/pre-exam-activities/create-pre-exam-activity/create-pre-exam-activity.component';
import { EditPreExamActivityComponent } from './components/pre-exam-activities/edit-pre-exam-activity/edit-pre-exam-activity.component';
import { SubjectExecutionsComponent } from './components/subject-executions/subject-executions.component';
import { ListSubjectExecutionsTabComponent } from './components/subject-executions/list-subject-executions-tab/list-subject-executions-tab.component';
import { EditSubjectExecutionsDialogComponent } from './components/subject-executions/edit-subject-executions-dialog/edit-subject-executions-dialog.component';
import { CreateSubjectExecutionsTabComponent } from './components/subject-executions/create-subject-executions-tab/create-subject-executions-tab.component';
import { EnrollStudentsDialogComponent } from './components/study-programs/enroll-students-dialog/enroll-students-dialog.component';
import { EnrollToSubjectDialogComponent } from './components/subject-executions/enroll-to-subject-dialog/enroll-to-subject-dialog.component';
import { LogoutComponent } from '../common/components/logout/logout.component';
import { ExamPeriodsComponent } from './components/exam-periods/exam-periods.component';
import {
  ListExamPeriodsTabComponent,
  SubjectExecutionNamePipe,
} from './components/exam-periods/list-exam-periods-tab/list-exam-periods-tab.component';
import { CreateExamPeriodTabComponent } from './components/exam-periods/create-exam-period-tab/create-exam-period-tab.component';
import { EditExamPeriodDialogComponent } from './components/exam-periods/edit-exam-period-dialog/edit-exam-period-dialog.component';

const routes: Routes = [
  {
    path: 'dashboard',
    component: AdminDashboardComponent,
    children: [
      {
        path: 'students',
        component: StudentsComponent,
        children: [
          { path: 'list-students-tab', component: ListStudentsTabComponent },
          { path: 'create-student-tab', component: CreateStudentTabComponent },
          { path: '', pathMatch: 'full', redirectTo: 'list-students-tab' },
        ],
      },
      {
        path: 'logout',
        component: LogoutComponent,
      },
      {
        path: 'profesors',
        component: ProfesorComponent,
        children: [
          { path: 'list-profesors-tab', component: ListProfesorTabComponent },
          {
            path: 'create-profesor-tab',
            component: CreateProfesorTabComponent,
          },
          { path: '', pathMatch: 'full', redirectTo: 'list-profesors-tab' },
        ],
      },
      {
        path: 'exam-periods',
        component: ExamPeriodsComponent,
        children: [
          {
            path: 'list-exam-periods-tab',
            component: ListExamPeriodsTabComponent,
          },
          {
            path: 'create-exam-period-tab',
            component: CreateExamPeriodTabComponent,
          },
          { path: '', pathMatch: 'full', redirectTo: 'list-exam-periods-tab' },
        ],
      },
      {
        path: 'payments',
        component: PaymentComponent,
        children: [
          { path: 'list-payments-tab', component: ListPaymentTabComponent },
          { path: 'create-payment-tab', component: CreatePaymentTabComponent },
          { path: '', pathMatch: 'full', redirectTo: 'list-payments-tab' },
        ],
      },
      {
        path: 'subjects',
        component: SubjectsComponent,
        children: [
          {
            path: 'create-subject-tab',
            component: CreateSubjectTabComponent,
          },
          {
            path: 'list-subjects-tab',
            component: ListSubjectsTabComponent,
          },
          {
            path: '',
            pathMatch: 'full',
            redirectTo: 'list-subjects-tab',
          },
        ],
      },
      {
        path: 'study-programs',
        component: StudyProgramsComponent,
        children: [
          {
            path: 'create-study-program-tab',
            component: CreateStudyProgramTabComponent,
          },
          {
            path: 'list-study-programs-tab',
            component: ListStudyProgramsTabComponent,
          },
          {
            path: '',
            pathMatch: 'full',
            redirectTo: 'list-study-programs-tab',
          },
        ],
      },
      {
        path: 'pre-exam-activities',
        component: PreExamActivitiesComponent,
        children: [
          {
            path: 'create-pre-exam-activity',
            component: CreatePreExamActivityComponent,
          },
          {
            path: 'list-pre-exam-activities',
            component: ListPreExamActivitiesComponent,
          },
          {
            path: '',
            pathMatch: 'full',
            redirectTo: 'list-pre-exam-activities',
          },
        ],
      },
      {
        path: 'subject-executions',
        component: SubjectExecutionsComponent,
        children: [
          {
            path: 'create-subject-executions-tab',
            component: CreateSubjectExecutionsTabComponent,
          },
          {
            path: 'list-subject-executions-tab',
            component: ListSubjectExecutionsTabComponent,
          },
          {
            path: '',
            pathMatch: 'full',
            redirectTo: 'list-subject-executions-tab',
          },
        ],
      },
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'subjects',
      },
    ],
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'dashboard',
  },
];

@NgModule({
  declarations: [
    AdminDashboardComponent,
    SubjectsComponent,
    StudyProgramsComponent,
    ListSubjectsTabComponent,
    CreateSubjectTabComponent,
    EditSubjectDialogComponent,
    StudentsComponent,
    ListStudentsTabComponent,
    CreateStudentTabComponent,
    EditStudentDialogComponent,
    ProfesorComponent,
    CreateProfesorTabComponent,
    ListProfesorTabComponent,
    EditProfesorDialogComponent,
    PaymentComponent,
    ListPaymentTabComponent,
    EditPaymentDialogComponent,
    CreatePaymentTabComponent,
    CreateStudyProgramTabComponent,
    ListStudyProgramsTabComponent,
    EditStudyProgramDialogComponent,
    SubjectsNamePipe,
    SubjectExecutionNamePipe,
    PreExamActivitiesComponent,
    ListPreExamActivitiesComponent,
    CreatePreExamActivityComponent,
    EditPreExamActivityComponent,
    SubjectExecutionsComponent,
    ListSubjectExecutionsTabComponent,
    EditSubjectExecutionsDialogComponent,
    CreateSubjectExecutionsTabComponent,
    EnrollStudentsDialogComponent,
    EnrollToSubjectDialogComponent,
    ExamPeriodsComponent,
    ListExamPeriodsTabComponent,
    CreateExamPeriodTabComponent,
    EditExamPeriodDialogComponent,
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    MatSidenavModule,
    MatButtonModule,
    MatTabsModule,
    NzTableModule,
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
    MatNativeDateModule,
  ],
})
export class AdminModule {}
