import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PageEntity } from 'src/app/common/http/page-entity';
import { ApiService } from 'src/app/common/service/api.service';

@Injectable({
  providedIn: 'root',
})
export class ListEnrolledExamsTabService {
  constructor(private readonly api: ApiService) {}

  fetchPaged(pageIndex: number, pageSize: number, examPeriodID: string) {
    const params = new HttpParams()
      .set('page', pageIndex)
      .set('records', pageSize);
    return this.api.get<PageEntity>(
      `/api/held-exam/${examPeriodID}/upcoming-exams`,
      params
    );
  }
}
