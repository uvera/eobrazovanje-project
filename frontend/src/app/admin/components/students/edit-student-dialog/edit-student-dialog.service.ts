import { Injectable } from '@angular/core';
import { ApiService } from '../../../../common/service/api.service';
import { EditStudentData } from './edit-student-dialog.dto';

@Injectable({
  providedIn: 'root',
})
export class EditStudentDialogService {
  constructor(private api: ApiService) {}

  getStudentById(id: string) {
    return this.api.get<EditStudentData>(`/api/admin/student/${id}`);
  }

  editStudentById(id: string, data: Record<string, unknown>) {
    return this.api.put(`/api/admin/student/${id}`, data);
  }
}
