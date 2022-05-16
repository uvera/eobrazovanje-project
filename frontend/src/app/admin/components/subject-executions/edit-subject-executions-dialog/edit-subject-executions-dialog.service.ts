import { Injectable } from '@angular/core';
import { ApiService } from '../../../../common/service/api.service';
import { SubjectExecutionViewDTO } from './edit-subject-executions-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class EditSubjectExecutionsDialogService {
  constructor(private api: ApiService) {}

  getSubjectById(id: string) {
    return this.api.get<SubjectExecutionViewDTO>(`/api/admin/subject-execution/${id}`);
  }

  editSubjectById(id: string, data: Record<string, unknown>) {
    return this.api.put(`/api/admin/subject-execution/${id}`, data);
  }
}
