import { Injectable } from '@angular/core';
import { ApiService } from '../../../../common/service/api.service';
import { TeacherViewDTO } from './create-subject-executions-tab.component';

@Injectable({
  providedIn: 'root'
})
export class CreateSubjectExecutionsTabService {
  constructor(private readonly api: ApiService) {}

  createSubjectExecution(form: Record<string, unknown>) {
    return this.api.post('/api/admin/subject-execution', form);
  }

  getSubjects() {
    return this.api.get('/api/admin/subject/all')
  }

  getProfessors() {
    return this.api.get<TeacherViewDTO[]>('/api/teacher/all')
  }

  getPreExamActivities() {
    return this.api.get('/api/admin/pre-exam-activity/all')
  }
}
