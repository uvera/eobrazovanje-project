import { Injectable } from '@angular/core';
import { ImageApiService } from '../../../../common/service/image.api.service';

@Injectable({
  providedIn: 'root',
})
export class CreateDocumentTabService {
  constructor(private readonly api: ImageApiService) {}

  upload(name: string, selectedFile: FileList) {
    const form = new FormData();
    form.set('name', name);
    const files = Array.from(selectedFile);
    for (const file of files) {
      form.append('files[]', file, file.name);
    }
    return this.api.postForm('/api/document', form);
  }
}
