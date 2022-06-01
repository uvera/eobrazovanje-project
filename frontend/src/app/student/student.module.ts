import { NgModule } from '@angular/core';
import { MatSidenavModule } from '@angular/material/sidenav';
import { CommonModule } from '@angular/common';
import { StudentDashboardComponent } from './components/student-dashboard/student-dashboard.component';
import { RouterModule, Routes } from '@angular/router';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatTabsModule } from '@angular/material/tabs';
import { MatSelectModule } from '@angular/material/select';
import { NzTableModule } from 'ng-zorro-antd/table';
import {
  NgxMatDatetimePickerModule,
  NgxMatTimepickerModule,
  NgxMatNativeDateModule,
} from '@angular-material-components/datetime-picker';
import { SubjectsComponent } from './components/subjects/subjects.component';
import { ExamsComponent } from './components/exams/exams.component';
import { ProfileComponent } from './components/profile/profile.component';
import { StudentPaymentComponent } from './components/student-payment/student-payment.component';
import { CreateStudentPaymentsTabComponent } from './components/student-payment/create-student-payments-tab/create-student-payments-tab.component';
import { ListStudentPaymentsTabComponent } from './components/student-payment/list-student-payments-tab/list-student-payments-tab.component';
import { LogoutComponent } from '../common/components/logout/logout.component';
import { ListExamsTabComponent } from './components/exams/list-exams-tab/list-exams-tab.component';
import { ListEnrolledExamsTabComponent } from './components/exams/list-enrolled-exams-tab/list-enrolled-exams-tab.component';
import { DocumentsComponent } from './components/documents/documents.component';
import { ListDocumentsTabComponent } from './components/documents/list-documents-tab/list-documents-tab.component';
import { CreateDocumentTabComponent } from './components/documents/create-document-tab/create-document-tab.component';
import { MatListModule } from '@angular/material/list';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { NzListModule } from 'ng-zorro-antd/list';
import { ListExamResultsTabComponent } from './components/exams/list-exam-results-tab/list-exam-results-tab.component';

const routes: Routes = [
  {
    path: 'dashboard',
    component: StudentDashboardComponent,
    children: [
      {
        path: 'profile',
        component: ProfileComponent,
      },
      {
        path: 'subjects',
        component: SubjectsComponent,
      },
      {
        path: 'payments',
        component: StudentPaymentComponent,
        children: [
          {
            path: 'list-student-payments-tab',
            component: ListStudentPaymentsTabComponent,
          },
          {
            path: 'create-student-payment-tab',
            component: CreateStudentPaymentsTabComponent,
          },
          {
            path: '',
            pathMatch: 'full',
            redirectTo: 'list-student-payments-tab',
          },
        ],
      },
      {
        path: 'exams',
        component: ExamsComponent,
        children: [
          {
            path: 'list-student-exam-enrollments-tab',
            component: ListExamsTabComponent,
          },
          {
            path: 'list-student-all-exam-enrollments-tab',
            component: ListEnrolledExamsTabComponent,
          },
          {
            path: 'list-student-all-exam-results-tab',
            component: ListExamResultsTabComponent,
          },
          {
            path: '',
            pathMatch: 'full',
            redirectTo: 'list-student-exam-enrollments-tab',
          },
        ],
      },

      {
        path: 'documents',
        component: DocumentsComponent,
        children: [
          {
            path: 'list-documents-tab',
            component: ListDocumentsTabComponent,
          },
          {
            path: 'create-documents-tab',
            component: CreateDocumentTabComponent,
          },
          {
            path: '',
            pathMatch: 'full',
            redirectTo: 'list-documents-tab',
          },
        ],
      },
      {
        path: 'logout',
        component: LogoutComponent,
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
    StudentDashboardComponent,
    StudentPaymentComponent,
    ListStudentPaymentsTabComponent,
    CreateStudentPaymentsTabComponent,
    SubjectsComponent,
    ExamsComponent,
    ProfileComponent,
    ListExamsTabComponent,
    ListEnrolledExamsTabComponent,
    DocumentsComponent,
    ListDocumentsTabComponent,
    CreateDocumentTabComponent,
    ListExamResultsTabComponent,
  ],
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
    MatListModule,
    MatGridListModule,
    MatCardModule,
    NzListModule,
  ],
})
export class StudentModule {}
