import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/common/service/api.service';

@Injectable({
  providedIn: 'root'
})
export class AddStudentGradesService {
  constructor(private readonly api: ApiService) {}

  createResults(data: any) {
    return this.api.post(`/api/held-exam/results`, data)
  }
}
