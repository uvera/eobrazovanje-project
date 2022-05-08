import { Injectable } from '@angular/core';
import { ApiService } from '../../../../common/service/api.service';

@Injectable({
  providedIn: 'root',
})
export class CreateStudentTabService {
  constructor(private readonly api: ApiService) {}

  createStudent(form: Record<string, unknown>) {
    return this.api.post('/api/admin/student', { data: [form] });
  }
}
