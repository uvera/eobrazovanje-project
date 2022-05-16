import { Injectable } from '@angular/core';
import { ApiService } from '../../../../common/service/api.service';
import { HttpParams } from '@angular/common/http';
import { PageEntity } from '../../../../common/http/page-entity';

@Injectable({
  providedIn: 'root'
})
export class ListProfesorsTabService {
  constructor(private readonly api: ApiService) {}

  fetchPaged(pageIndex: number, pageSize: number) {
    const params = new HttpParams()
      .set('page', pageIndex)
      .set('records', pageSize);
    return this.api.get<PageEntity>('/api/teacher/paged', params);
  }

  deleteProfesorById(id: string) {
    return this.api.delete(`/api/teacher/${id}`);
  }
}
