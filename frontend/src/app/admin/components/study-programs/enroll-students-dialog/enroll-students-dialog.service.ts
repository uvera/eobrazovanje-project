import { Injectable } from '@angular/core';
import { ApiService } from '../../../../common/service/api.service';
import { StudentsViewDTO } from '../../students/list-students-tab/list-students-tab.component';

@Injectable({
  providedIn: 'root'
})
export class EnrollStudentsDialogService {
  constructor(private readonly api: ApiService) {}

  enrollStudents(id: string, data: Record<string, unknown>) {
    return this.api.put(`/api/admin/study-program/${id}/enroll`, data);
  }

  getAvailableStudents() {
    return this.api.get<StudentsViewDTO[]>(`/api/admin/student/no-study-programs`)
  }
}

