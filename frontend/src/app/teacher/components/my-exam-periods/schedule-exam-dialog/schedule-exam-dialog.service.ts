import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/common/service/api.service';

@Injectable({
  providedIn: 'root'
})
export class ScheduleExamDialogService {
  constructor(private readonly api: ApiService) { }

  fetchHeldExamIfExists(examPeriodID: string, subjExId: string) {
    return this.api.get<any>(`/api/held-exam/${examPeriodID}/${subjExId}`);
  }

  createHeldExam(examPeriodID: string, subjExId: string, date: Date) {
    return this.api.post(`/api/held-exam`, { examPeriodId: examPeriodID, subjectExecutionId: subjExId, date: date })
  }
}
