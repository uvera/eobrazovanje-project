import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/common/service/api.service';

@Injectable({
  providedIn: 'root'
})
export class CreatePreExamActivityService {
  constructor(private readonly api: ApiService) {}

  createPreExamActivity(form: Record<string, unknown>) {
    return this.api.post('/api/admin/pre-exam-activity', form);
  }
  
}