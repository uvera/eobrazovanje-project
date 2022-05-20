import { Injectable } from '@angular/core';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { ApiService } from '../../../../common/service/api.service';
import { HttpParams } from '@angular/common/http';
import { PageEntity } from '../../../../common/http/page-entity';

@Injectable({
    providedIn: 'root'
  })
export class ListStudentPaymentTabService {
    constructor(private readonly api: ApiService) {}

    fetchStudentPaymentsPaged(pageIndex: number, pageSize: number, email: string) {
        const params = new HttpParams()
            .set('page', pageIndex)
            .set('records', pageSize)
            .set('email', email)
        return this.api.get<PageEntity>('/api/payment/student/paged', params)
    }

    fetchCurrentStudent() {
        const params = new HttpParams()
        return this.api.get<CurrentStudentDTO>('/api/auth/whoami', params)
    }
}

export interface CurrentStudentDTO {
    email: string;
    role: Number;
}