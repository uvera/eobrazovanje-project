import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PageEntity } from 'src/app/common/http/page-entity';
import { ApiService } from 'src/app/common/service/api.service';

@Injectable({
  providedIn: 'root'
})
export class ListStudyProgramsTabService {
  constructor(private readonly api: ApiService) {}

  fetchPaged(pageIndex: number, pageSize: number) {
    const params = new HttpParams()
      .set('page', pageIndex)
      .set('records', pageSize);
    return this.api.get<PageEntity>('/api/admin/study-program/paged', params);
  }

  deleteStudyProgramById(id: string) {
    return this.api.delete(`/api/admin/study-program/${id}`);
  }
}
