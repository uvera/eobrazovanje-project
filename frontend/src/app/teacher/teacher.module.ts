import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TeacherDashboardComponent } from './components/teacher-dashboard/teacher-dashboard.component';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'dashboard',
    component: TeacherDashboardComponent,
    pathMatch: 'full',
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'dashboard',
  },
];

@NgModule({
  declarations: [TeacherDashboardComponent],
  imports: [CommonModule, RouterModule.forChild(routes)],
})
export class TeacherModule {}
