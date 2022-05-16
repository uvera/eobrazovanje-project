import { Injectable } from '@angular/core';
import { ApiService } from '../../../../common/service/api.service';
import { EditProfesorData } from './edit-profesor-dialog-dto';

@Injectable({
  providedIn: 'root'
})
export class EditProfesorDialogService {
  constructor(private api: ApiService) {}

  getProfesorById(id: string) {
    return this.api.get<EditProfesorData>(`/api/teacher/${id}`);
  }

  editProfesorById(id: string, data: Record<string, unknown>) {
    return this.api.put(`/api/teacher/${id}`, data);
  }
}
