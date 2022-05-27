import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/common/service/api.service';

@Injectable({
  providedIn: 'root',
})
export class CreateStudyProgramTabService {
  constructor(private readonly api: ApiService) {}

  createStudyProgram(form: Record<string, unknown>) {
    return this.api.post('/api/admin/study-program', form);
  }

  getAvailableSubjects() {
    return this.api.get('/api/admin/subject/available');
  }
}
