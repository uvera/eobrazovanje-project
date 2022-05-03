import { Injectable } from '@angular/core';
import { ApiService } from '../../../../common/service/api.service';
import { PageEntity } from '../../../../common/http/page-entity';
import { HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ListSubjectsTabService {
  fetchPaged(pageIndex: number, pageSize: number) {
    const params = new HttpParams()
      .set('page', pageIndex)
      .set('records', pageSize);
    return this.api.get<PageEntity>('/api/admin/subject', params);
  }

  constructor(private readonly api: ApiService) {}
}
