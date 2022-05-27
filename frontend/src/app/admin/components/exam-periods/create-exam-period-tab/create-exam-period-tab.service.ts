import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/common/service/api.service';
import { SubjectExecutionViewDTO } from '../../subject-executions/edit-subject-executions-dialog/edit-subject-executions-dialog.component';
import { SubjectExecutionTableViewDTO } from '../../subject-executions/list-subject-executions-tab/list-subject-executions-tab.component';

@Injectable({
  providedIn: 'root',
})
export class CreateExamPeriodTabService {
  constructor(private readonly api: ApiService) {}

  createExamPeriod(form: Record<string, unknown>) {
    return this.api.post('/api/admin/exam-period', form);
  }

  getSubjectExecutions() {
    return this.api.get<SubjectExecutionViewDTO[]>(
      '/api/admin/subject-execution/all'
    );
  }
}
