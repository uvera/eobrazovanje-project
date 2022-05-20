import { Injectable } from '@angular/core';
import { ApiService } from '../../../../common/service/api.service';
import { CurrentStudentDTO } from '../list-student-payments-tab/list-student-payments-tab.service';
import { HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CreatePaymentTabService {
  constructor(private readonly api: ApiService) {}

  createPayment(form: Record<string, unknown>) {
    return this.api.post('/api/payment/student', form);
  }

  fetchCurrentStudent() {
    const params = new HttpParams()
    return this.api.get<CurrentStudentDTO>('/api/auth/whoami', params)
  }
}
