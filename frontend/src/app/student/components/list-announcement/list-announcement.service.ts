import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StudentEnrollmentViewDTO } from 'src/app/admin/components/subject-executions/edit-subject-executions-dialog/edit-subject-executions-dialog.component';
import { PageEntity } from 'src/app/common/http/page-entity';
import { ApiService } from 'src/app/common/service/api.service';
import { StudentViewSubjectsDTO } from '../subjects/subjects.component';

@Injectable({
  providedIn: 'root'
})
export class ListAnnouncementService {
  constructor(private readonly api: ApiService) { }

  fetchPaged(id: string, pageIndex: number, pageSize: number) {
    const params = new HttpParams()
      .set('page', pageIndex)
      .set('records', pageSize);
    return this.api.get<PageEntity>(`/api/admin/announcement/${id}`, params);
  }

  fetchStudentSubjects() {
    return this.api.get<StudentViewSubjectsDTO[]>(
      '/api/student/subjects-display'
    );
  }
}