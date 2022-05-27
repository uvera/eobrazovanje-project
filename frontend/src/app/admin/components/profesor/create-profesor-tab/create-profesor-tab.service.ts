import { Injectable } from '@angular/core';
import { ApiService } from '../../../../common/service/api.service';

@Injectable({
  providedIn: 'root',
})
export class CreateProfesorTabService {
  constructor(private readonly api: ApiService) {}

  createProfesor(form: Record<string, unknown>) {
    return this.api.post('/api/teacher', form);
  }
}
