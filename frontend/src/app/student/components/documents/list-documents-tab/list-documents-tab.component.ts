import { Component, OnInit } from '@angular/core';
import { ListDocumentsTabService } from './list-documents-tab.service';
import { take } from 'rxjs';

@Component({
  selector: 'app-list-documents-tab',
  templateUrl: './list-documents-tab.component.html',
  styleUrls: ['./list-documents-tab.component.scss'],
})
export class ListDocumentsTabComponent implements OnInit {
  items: any = [];
  constructor(private readonly service: ListDocumentsTabService) {}

  parseBody(value: any) {
    const ret = value.map((i: { files: any[] }) => {
      console.log(i);
      return {
        ...i,
        files: [
          ...i.files.map((f) => {
            return {
              ...f,
              url: f.url
                .replace('minio-db-lb', 'localhost')
                .replace(/\?.*$/, ''),
            };
          }),
        ],
      };
    });
    console.log(ret);
    return ret;
  }

  ngOnInit(): void {
    this.service
      .getMine()
      .pipe(take(1))
      .subscribe({
        next: (v) => {
          this.items = this.parseBody(v.body);
        },
      });
  }
}
