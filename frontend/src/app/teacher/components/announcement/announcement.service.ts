import { Injectable } from '@angular/core';
import { ApiService } from '../../../common/service/api.service';
import { HttpParams } from '@angular/common/http';
import { PageEntity } from 'src/app/common/http/page-entity';
import { SubjectExecutionViewDTO } from 'src/app/admin/components/subject-executions/edit-subject-executions-dialog/edit-subject-executions-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class AnnouncementService {
  constructor(private readonly api: ApiService) {}

  fetchTeacherAnnouncements(id: string, pageIndex: number, pageSize: number) {
    const params = new HttpParams()
      .set('id', id)
      .set('page', pageIndex)
      .set('records', pageSize);
    return this.api.get<PageEntity>('/api/admin/announcement', params);
  }

  fetchCurrentUser() {
    const params = new HttpParams();
    return this.api.get<CurrentTokenInfoDTO>('/api/auth/whoami', params);
  }

  fetchCurrentTeacher(email: string) {
    const params = new HttpParams().set('email', email);
    return this.api.get<CurrentTeacherDTO>('/api/teacher/whoami', params);
  }
}

export interface SubjectViewDTO {
  id: string;
  espb: number;
  name: string;
  year: number;
}

export interface CurrentTokenInfoDTO {
  email: string;
  role: Number;
}

export interface CurrentTeacherDTO {
  id: string;
  teacherType: string;
  user: {
    firstName: string;
    email: string;
    lastName: string;
  };
}