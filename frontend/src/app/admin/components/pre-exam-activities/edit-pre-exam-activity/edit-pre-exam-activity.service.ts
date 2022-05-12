import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/common/service/api.service';
import { PreExamActivityViewDTO } from './edit-pre-exam-activity.component';

@Injectable({
  providedIn: 'root'
})
export class EditPreExamActivityService {
  constructor(private readonly api: ApiService) {}

  editPreExamActivity(id: string, data: Record<string, unknown>) {
    return this.api.put(`/api/admin/pre-exam-activity/${id}`, data);
  }

  getPreExamActivityById(id: string) {
    return this.api.get<PreExamActivityViewDTO>(`/api/admin/pre-exam-activity/${id}`)
  }
}