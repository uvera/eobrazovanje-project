import { Injectable } from '@angular/core';
import { ApiService } from '../../../../common/service/api.service';

@Injectable({
  providedIn: 'root',
})
export class CreateSubjectTabService {
  constructor(private readonly api: ApiService) {}

  createSubject(form: Record<string, unknown>) {
    return this.api.post('/api/admin/subject', form);
  }
}
