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

const routes: Routes = [
  {
    path: 'dashboard',
    component: AdminDashboardComponent,
    children: [
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
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    MatSidenavModule,
    MatButtonModule,
    MatTabsModule,
    NzTableModule,
  ],
})
export class AdminModule {}
