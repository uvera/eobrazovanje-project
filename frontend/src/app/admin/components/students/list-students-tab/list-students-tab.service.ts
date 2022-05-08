import { Injectable } from '@angular/core';
import { ApiService } from '../../../../common/service/api.service';
import { HttpParams } from '@angular/common/http';
import { PageEntity } from '../../../../common/http/page-entity';

@Injectable({
  providedIn: 'root',
})
export class ListStudentsTabService {
  constructor(private readonly api: ApiService) {}

  fetchPaged(pageIndex: number, pageSize: number) {
    const params = new HttpParams()
      .set('page', pageIndex)
      .set('records', pageSize);
    return this.api.get<PageEntity>('/api/admin/student/paged', params);
  }

  deleteStudentById(id: string) {
    return this.api.delete(`/api/admin/student/${id}`);
  }
}
