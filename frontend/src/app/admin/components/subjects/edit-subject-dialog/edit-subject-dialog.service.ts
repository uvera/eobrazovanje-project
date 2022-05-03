import { Injectable } from '@angular/core';
import { ApiService } from '../../../../common/service/api.service';
import { EditSubjectData } from './edit-subject-dialog.dto';

@Injectable({
  providedIn: 'root',
})
export class EditSubjectDialogService {
  constructor(private api: ApiService) {}

  getSubjectById(id: string) {
    return this.api.get<EditSubjectData>(`/api/admin/subject/${id}`);
  }

  editSubjectById(id: string, data: Record<string, unknown>) {
    return this.api.put(`/api/admin/subject/${id}`, data);
  }
}
