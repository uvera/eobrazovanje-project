import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ExamPeriodsViewDTO } from 'src/app/admin/components/exam-periods/list-exam-periods-tab/list-exam-periods-tab.component';
import { PageEntity } from 'src/app/common/http/page-entity';
import { ApiService } from 'src/app/common/service/api.service';
import { HeldExamViewDTO } from './list-exam-periods.component';

@Injectable({
  providedIn: 'root'
})
export class ListExamPeriodsService {
  constructor(private readonly api: ApiService) {}

  fetchPaged(pageIndex: number, pageSize: number, id: string) {
    const params = new HttpParams()
      .set('page', pageIndex)
      .set('records', pageSize)
      .set('id', id);
    return this.api.get<PageEntity>(
      `/api/admin/exam-period/${id}/professor-exams`,
      params
    );
  }

  enrollInExam(examPeriodID: string, subjectExecutionID: string) {
    return this.api.post(
      `/api/admin/exam-period/${examPeriodID}/${subjectExecutionID}/enroll`
    );
  }

  getExamPeriods() {
    return this.api.get<ExamPeriodsViewDTO[]>('/api/admin/exam-period/all');
  }
  
  fetchHeldExamIfExists(examPeriodID: string, subjExId: string) {
    return this.api.get<HeldExamViewDTO>(`/api/held-exam/${examPeriodID}/${subjExId}`);
  }

  fetchEnrolledStudents(examPeriodID: string, subjExId: string) {
    return this.api.get<any>(`/api/held-exam/${examPeriodID}/${subjExId}/enrolled-students`)
  }
}
