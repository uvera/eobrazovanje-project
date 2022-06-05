import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PageEntity } from 'src/app/common/http/page-entity';
import { ApiService } from 'src/app/common/service/api.service';

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

  fetchStudentSubjects(id: string) {
    return this.api.get<StudentViewSubjectsDTO[]>(
      '/api/student/subjects', {id: id}
    );
  }

  fetchCurrentStudent(email: string) {
    const params = new HttpParams().set('email', email);
    return this.api.get<CurrentStudentDTO>('/api/student/whoami', params);
  }
}

export interface CurrentStudentDTO {
  id: string;
  transcriptNumber: string;
  identificationNumber: string;
  user: {
    firstName: string;
    email: string;
    lastName: string;
  };
}

interface StudentViewSubjectsDTO {
  id: string;
  year: number;
  subjectExecution: {
    place: string;
    time: string;
    weekDay: string;
    subject: {
      id: string;
      espb: number;
      name: string;
      year: number;
    };
  };
}