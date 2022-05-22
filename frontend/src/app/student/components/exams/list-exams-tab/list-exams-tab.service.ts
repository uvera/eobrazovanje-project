import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ExamPeriodsViewDTO } from 'src/app/admin/components/exam-periods/list-exam-periods-tab/list-exam-periods-tab.component';
import { PageEntity } from 'src/app/common/http/page-entity';
import { ApiService } from 'src/app/common/service/api.service';

@Injectable({
  providedIn: 'root'
})
export class ListExamsTabService {
  constructor(private readonly api: ApiService) {}

  fetchPaged(pageIndex: number, pageSize: number, id: string) {
    const params = new HttpParams()
      .set('page', pageIndex)
      .set('records', pageSize)
      .set('id', id);
    return this.api.get<PageEntity>(`/api/admin/exam-period/${id}/enrollments`, params);
  }

  enrollInExam(examPeriodID: string, subjectExecutionID: string) {
    return this.api.post(`/api/admin/exam-period/${examPeriodID}/${subjectExecutionID}/enroll`, {});
  }
  
  getExamPeriods() {
    return this.api.get<ExamPeriodsViewDTO[]>('/api/admin/exam-period/all')
  }
}
