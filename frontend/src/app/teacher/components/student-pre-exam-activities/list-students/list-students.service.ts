import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PageEntity } from 'src/app/common/http/page-entity';
import { ApiService } from 'src/app/common/service/api.service';
import { TeacherViewSubjectsDTO } from '../../my-subjects/my-subjects.component';
import { TeacherViewStudents } from './list-students.component';

@Injectable({
  providedIn: 'root'
})
export class ListStudentsService {
  constructor(private readonly api: ApiService) {}

  fetchTeacherSubjects(id: string) {
    const params = new HttpParams()
      .set('id', id)
    return this.api.get<TeacherViewStudents[]>('/api/teacher/subjects-all', params);
  }

  fetchPaged(pageIndex: number, pageSize: number, subjectExid: string) {
    const params = new HttpParams()
      .set('id', subjectExid)
      .set('page', pageIndex)
      .set('records', pageSize);
    return this.api.get<PageEntity>('/api/teacher/subject-students', params);
  }
}
