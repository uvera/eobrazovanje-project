import { Injectable } from '@angular/core';
import { ApiService } from '../../../../common/service/api.service';
import { HttpParams } from '@angular/common/http';
import { SubjectExecutionViewDTO, TeacherEnrollmentViewDTO } from './create-announcement.component';

@Injectable({
  providedIn: 'root'
})
export class CreateAnnouncementService {
  constructor(private readonly api: ApiService) {}

  createAnnouncement(form: Record<string, unknown>) {
    return this.api.post('/api/admin/announcement', form);
  }

  fetchCurrentUser() {
    const params = new HttpParams();
    return this.api.get<CurrentTokenInfoDTO>('/api/auth/whoami', params);
  }

  fetchCurrentTeacher(email: string) {
    const params = new HttpParams().set('email', email);
    return this.api.get<CurrentTeacherDTO>('/api/teacher/whoami', params);
  }

  getSubjectExecutions() {
    return this.api.get<TeacherEnrollmentViewDTO[]>(
      '/api/teacher/subjects-all'
    );
  }
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