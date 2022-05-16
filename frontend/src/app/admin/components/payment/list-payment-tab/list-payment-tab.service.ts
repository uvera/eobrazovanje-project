import { Injectable } from '@angular/core';
import { ApiService } from '../../../../common/service/api.service';
import { HttpParams } from '@angular/common/http';
import { PageEntity } from '../../../../common/http/page-entity';

@Injectable({
  providedIn: 'root'
})
export class ListPaymentTabService {
  constructor(private readonly api: ApiService) {}

  fetchPaged(pageIndex: number, pageSize: number, id: string) {
    const params = new HttpParams()
      .set('page', pageIndex)
      .set('records', pageSize)
      .set('id', id);
    return this.api.get<PageEntity>('/api/payment/paged', params);
  }

  deletePaymentById(id: string) {
    return this.api.delete(`/api/payment/${id}`);
  }
  
  getStudents() {
    return this.api.get('/api/admin/student/all')
  }
}
