import { Component, OnInit } from '@angular/core';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { ListSubjectsTabService } from './list-subjects-tab.service';
import { MatDialog } from '@angular/material/dialog';
import { EditSubjectDialogComponent } from '../edit-subject-dialog/edit-subject-dialog.component';

@Component({
  selector: 'app-list-subjects-tab',
  templateUrl: './list-subjects-tab.component.html',
  styleUrls: ['./list-subjects-tab.component.scss'],
})
export class ListSubjectsTabComponent implements OnInit {
  readonly pageIndex = new BehaviorSubject<number>(1);
  readonly total = new BehaviorSubject<number>(1);
  readonly pageSize = new BehaviorSubject<number>(10);
  readonly dataSet = new BehaviorSubject<SubjectViewDTO[]>([]);

  constructor(
    private service: ListSubjectsTabService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.pageNumberAndSizeCombined$.subscribe((value) => {
      this.fetchFromApi(value.pageIndex, value.pageSize);
    });
  }

  pageIndex$ = this.pageIndex.asObservable();
  pageSize$ = this.pageSize.asObservable();
  pageNumberAndSizeCombined$ = combineLatest([
    this.pageIndex$,
    this.pageSize$,
  ]).pipe(
    map((x) => ({
      pageIndex: x[0],
      pageSize: x[1],
    }))
  );

  fetchFromApi(pageIndex: number, pageSize: number) {
    this.service
      .fetchPaged(pageIndex, pageSize)
      .pipe(first())
      .subscribe((res) => {
        const responseBody = res?.body;
        if (responseBody) {
          const { content, totalElements } = responseBody;
          this.total.next(totalElements);
          this.dataSet.next(content);
        }
      });
  }

  queryParamsChange(event: NzTableQueryParams) {
    this.pageSize.next(event.pageSize);
    this.pageIndex.next(event.pageIndex);
  }

  editSubject(id: string) {
    this.dialog
      .open(EditSubjectDialogComponent, {
        data: {
          id: id,
        },
      })
      .afterClosed()
      .subscribe({
        next: (value) => {
          if (value === 'success') {
            this.fetchFromApi(this.pageIndex.value, this.pageSize.value);
          }
        },
      });
  }
}

interface SubjectViewDTO {
  id: string;
  espb: number;
  name: string;
  year: number;
}
