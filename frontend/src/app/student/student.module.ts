import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StudentDashboardComponent } from './components/student-dashboard/student-dashboard.component';
import { RouterModule, Routes } from '@angular/router';
import { MatSidenavModule } from '@angular/material/sidenav';


const routes: Routes = [
  {
    path: 'dashboard',
    component: StudentDashboardComponent,
    pathMatch: 'full',
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'dashboard',
  },
];

@NgModule({
  declarations: [StudentDashboardComponent],
  imports: [CommonModule, MatSidenavModule, RouterModule.forChild(routes)],
})
export class StudentModule {}
