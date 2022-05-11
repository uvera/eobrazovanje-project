import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/common/service/api.service';
import { StudyProgramsViewDTO } from '../list-study-programs-tab/list-study-programs-tab.component';

@Injectable({
  providedIn: 'root'
})
export class EditStudyProgramDialogService {
  constructor(private readonly api: ApiService) {}

  editStudyProgramById(id: string, data: Record<string, unknown>) {
    return this.api.put(`/api/admin/study-program/${id}`, data);
  }

  getStudyProgramById(id: string) {
    return this.api.get<StudyProgramsViewDTO>(`/api/admin/study-program/${id}`)
  }
}
