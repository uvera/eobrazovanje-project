import { Injectable } from '@angular/core';
import { PreExamActivityViewDTO } from 'src/app/admin/components/pre-exam-activities/edit-pre-exam-activity/edit-pre-exam-activity.component';
import { ApiService } from 'src/app/common/service/api.service';

@Injectable({
  providedIn: 'root'
})
export class AddPreExamActivityResultDialogService {
  constructor(private readonly api: ApiService) {}

  fetchStudentPreExamActivitiesBySubject(studentId: string, subjExId: string) {
    return this.api.get<any>(`/api/admin/pre-exam-activity/${subjExId}/${studentId}/student`);
  }

  createResults(data: any) {
    return this.api.post(`/api/admin/pre-exam-activity/add-results`, data)
  }
}
