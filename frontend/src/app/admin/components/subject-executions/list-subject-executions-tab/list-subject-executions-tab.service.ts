import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PageEntity } from '../../../../common/http/page-entity';
import { ApiService } from '../../../../common/service/api.service';

@Injectable({
  providedIn: 'root'
})
export class ListSubjectExecutionsTabService {
  constructor(private readonly api: ApiService) {}

  fetchPaged(pageIndex: number, pageSize: number) {
    const params = new HttpParams()
      .set('page', pageIndex)
      .set('records', pageSize);
    return this.api.get<PageEntity>('/api/admin/subject-execution/paged', params);
  }

  deleteById(id: string) {
    return this.api.delete(`/api/admin/subject-execution/${id}`);
  }
}
