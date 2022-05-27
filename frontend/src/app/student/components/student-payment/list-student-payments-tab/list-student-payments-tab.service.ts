import { Injectable } from '@angular/core';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { ApiService } from '../../../../common/service/api.service';
import { HttpParams } from '@angular/common/http';
import { PageEntity } from '../../../../common/http/page-entity';

@Injectable({
  providedIn: 'root',
})
export class ListStudentPaymentTabService {
  constructor(private readonly api: ApiService) {}

  fetchStudentPaymentsPaged(pageIndex: number, pageSize: number, id: string) {
    const params = new HttpParams()
      .set('page', pageIndex)
      .set('records', pageSize)
      .set('id', id);
    return this.api.get<PageEntity>('/api/payment/paged', params);
  }

  fetchCurrentUser() {
    const params = new HttpParams();
    return this.api.get<CurrentTokenInfoDTO>('/api/auth/whoami', params);
  }

  fetchCurrentStudent(email: string) {
    const params = new HttpParams().set('email', email);
    return this.api.get<CurrentStudentDTO>('/api/student/whoami', params);
  }
}

export interface CurrentTokenInfoDTO {
  email: string;
  role: Number;
}

export interface CurrentStudentDTO {
  id: string;
  transcriptNumber: string;
  identificationNumber: string;
  user: {
    firstName: string;
    email: string;
    lastName: string;
  };
}
