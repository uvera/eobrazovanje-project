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
import { MatSelectModule } from '@angular/material/select';
import { MatTabsModule } from '@angular/material/tabs';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NgxMatDatetimePickerModule, NgxMatTimepickerModule, NgxMatNativeDateModule } from '@angular-material-components/datetime-picker';

import { PaymentComponent } from '../student/components/payment/payment.component';
import { ListStudentPaymentsTabComponent } from './components/payment/list-student-payments-tab/list-student-payments-tab.component';
import { CreateStudentPaymentTabComponent } from './components/payment/create-student-payment-tab/create-student-payment-tab.component';
import { SubjectsComponent } from './components/subjects/subjects.component';
import { ExamsComponent } from './components/exams/exams.component';
import { ProfileComponent } from './components/profile/profile.component';


const routes: Routes = [
  {
    path: 'dashboard',
    component: StudentDashboardComponent,
    children: [
      {
        path: 'profile',
        component: ProfileComponent
      },
      {
        path: 'subjects',
        component: SubjectsComponent
      },
      {
        path: 'payments',
        component: PaymentComponent,
        children: [
          { path: 'list-student-payments-tab', component: ListStudentPaymentsTabComponent },
          { path: 'create-student-payment-tab', component: CreateStudentPaymentTabComponent },
          { path: '', pathMatch: 'full', redirectTo: 'list-student-payments-tab' },
        ],
      },
      {
        path: 'exams',
        component: ExamsComponent
      },
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'subjects',
      },
    ]
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
    ListStudentPaymentsTabComponent,
    CreateStudentPaymentTabComponent,
    SubjectsComponent,
    ExamsComponent,
    ProfileComponent
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
    NgxMatNativeDateModule
  ],
})
export class StudentModule {}
