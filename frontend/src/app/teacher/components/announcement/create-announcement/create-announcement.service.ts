import { Injectable } from '@angular/core';
import { ApiService } from '../../../../common/service/api.service';
import { HttpParams } from '@angular/common/http';
import { TeacherEnrollmentViewDTO } from './create-announcement.component';

@Injectable({
  providedIn: 'root'
})
export class CreateAnnouncementService {
  constructor(private readonly api: ApiService) {}

  createAnnouncement(form: Record<string, unknown>) {
    return this.api.post('/api/admin/announcement', form);
  }

  getSubjectExecutions(id: string) {
    return this.api.get<TeacherEnrollmentViewDTO[]>(
      '/api/teacher/subjects-all', {id: id}
    );
  }
}