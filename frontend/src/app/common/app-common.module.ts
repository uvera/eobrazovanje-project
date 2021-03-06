import { NgModule } from '@angular/core';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LoginComponent } from './components/login/login.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ApiService } from './service/api.service';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { CurrentUserService } from './service/current-user.service';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { LogoutComponent } from './components/logout/logout.component';
import { AreYouSureDialogComponent } from './components/dialogs/are-you-sure-dialog/are-you-sure-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';

@NgModule({
  declarations: [
    DashboardComponent,
    LoginComponent,
    NotFoundComponent,
    LogoutComponent,
    AreYouSureDialogComponent,
  ],
  providers: [ApiService, CurrentUserService],
  imports: [
    FlexLayoutModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    ReactiveFormsModule,
    CommonModule,
    MatProgressSpinnerModule,
    MatDialogModule,
  ],
})
export class AppCommonModule {}
