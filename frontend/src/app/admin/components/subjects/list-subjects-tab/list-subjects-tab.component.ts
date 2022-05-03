import { Component, OnInit } from '@angular/core';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { ListSubjectsTabService } from './list-subjects-tab.service';
import { MatDialog } from '@angular/material/dialog';
import { EditSubjectDialogComponent } from '../edit-subject-dialog/edit-subject-dialog.component';
import { AreYouSureDialogComponent } from '../../../../common/components/dialogs/are-you-sure-dialog/are-you-sure-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';

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
    private snack: MatSnackBar,
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

  reloadFromApi() {
    this.fetchFromApi(this.pageIndex.value, this.pageSize.value);
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
            this.reloadFromApi();
          }
        },
      });
  }

  deleteSubject(id: string) {
    this.dialog
      .open(AreYouSureDialogComponent, {
        data: {
          dialogTitle: 'Are you sure you want to delete subject?',
          yesButtonText: 'Delete',
        },
      })
      .afterClosed()
      .pipe(first())
      .subscribe({
        next: (value) => {
          if (value === 'yes') {
            this.service
              .deleteSubjectById(id)
              .pipe(first())
              .subscribe({
                next: (_) => {
                  this.snack.open('Successfully deleted subject');
                  this.reloadFromApi();
                },
                error: () => {
                  this.snack.open('Error while deleting subject');
                },
              });
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
