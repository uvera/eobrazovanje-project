import { Injectable } from '@angular/core';
import { ImageApiService } from '../../../../common/service/image.api.service';

@Injectable({
  providedIn: 'root',
})
export class ListDocumentsTabService {
  constructor(private readonly api: ImageApiService) {}

  getMine() {
    return this.api.get('/api/document/me');
  }
}
