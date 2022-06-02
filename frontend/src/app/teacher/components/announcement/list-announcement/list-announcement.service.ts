import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ExamPeriodsViewDTO } from 'src/app/admin/components/exam-periods/list-exam-periods-tab/list-exam-periods-tab.component';
import { PageEntity } from 'src/app/common/http/page-entity';
import { ApiService } from 'src/app/common/service/api.service';
import { CurrentTokenInfoDTO, CurrentTeacherDTO } from '../create-announcement/create-announcement.service';
import { TeacherEnrollmentViewDTO } from '../create-announcement/create-announcement.component'

@Injectable({
  providedIn: 'root'
})
export class ListAnnouncementService {
  constructor(private readonly api: ApiService) {}

  fetchPaged(id: string, pageIndex: number, pageSize: number) {
    const params = new HttpParams()
      .set('id', id)
      .set('page', pageIndex)
      .set('records', pageSize);
    return this.api.get<PageEntity>('/api/admin/announcement', params);
  }

  getAnnouncements() {
    return this.api.get<PageEntity[]>('/api/admin/announcement');
  }

  fetchCurrentUser() {
    const params = new HttpParams();
    return this.api.get<CurrentTokenInfoDTO>('/api/auth/whoami', params);
  }

  fetchCurrentTeacher(email: string) {
    const params = new HttpParams().set('email', email);
    return this.api.get<CurrentTeacherDTO>('/api/teacher/whoami', params);
  }

  getSubjectExecutions(id: string) {
    return this.api.get<TeacherEnrollmentViewDTO[]>(
      '/api/teacher/subjects-all', {id: id}
    );
  }

  getSubjectExecutions2(id: string, pageIndex: number, pageSize: number) {
    const params = new HttpParams()
      .set('id', id)
      .set('page', pageIndex)
      .set('records', pageSize);
    return this.api.get<PageEntity>('/api/admin/announcement', params);
  }
}
