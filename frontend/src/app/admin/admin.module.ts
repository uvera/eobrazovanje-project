import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
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
    FlexLayoutModule,
    MatDialogModule,
  ],
})
export class AdminModule {}
