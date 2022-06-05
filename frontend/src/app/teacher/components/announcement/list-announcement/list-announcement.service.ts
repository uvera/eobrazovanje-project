import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ExamPeriodsViewDTO } from 'src/app/admin/components/exam-periods/list-exam-periods-tab/list-exam-periods-tab.component';
import { PageEntity } from 'src/app/common/http/page-entity';
import { ApiService } from 'src/app/common/service/api.service';
import { TeacherEnrollmentViewDTO } from '../create-announcement/create-announcement.component'

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
}
