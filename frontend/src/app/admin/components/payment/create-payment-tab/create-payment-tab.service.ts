import { Injectable } from '@angular/core';
import { ApiService } from '../../../../common/service/api.service';

@Injectable({
  providedIn: 'root',
})
export class CreatePaymentTabService {
  constructor(private readonly api: ApiService) {}

  createPayment(form: Record<string, unknown>) {
    return this.api.post('/api/payment', form);
  }

  getStudents() {
    return this.api.get('/api/admin/student/all');
  }
}
