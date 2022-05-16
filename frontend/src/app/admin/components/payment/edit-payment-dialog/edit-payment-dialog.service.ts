import { Injectable } from '@angular/core';
import { ApiService } from '../../../../common/service/api.service';
import { PaymentViewDTO } from '../list-payment-tab/list-payment-tab.component';

@Injectable({
  providedIn: 'root'
})
export class EditPaymentDialogService {
  constructor(private api: ApiService) {}

  getById(id: string) {
    return this.api.get<PaymentViewDTO>(`/api/payment/${id}`);
  }

  editById(id: string, data: Record<string, unknown>) {
    return this.api.put(`/api/payment/${id}`, data);
  }
}
