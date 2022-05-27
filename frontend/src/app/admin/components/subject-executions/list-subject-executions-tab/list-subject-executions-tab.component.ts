import { Time } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { AreYouSureDialogComponent } from '../../../../common/components/dialogs/are-you-sure-dialog/are-you-sure-dialog.component';
import { EditPreExamActivityComponent } from '../../pre-exam-activities/edit-pre-exam-activity/edit-pre-exam-activity.component';
import { ListStudentsTabService } from '../../students/list-students-tab/list-students-tab.service';
import { EditSubjectExecutionsDialogComponent } from '../edit-subject-executions-dialog/edit-subject-executions-dialog.component';
import { ListSubjectExecutionsTabService } from './list-subject-executions-tab.service';

@Component({
  selector: 'app-list-subject-executions-tab',
  templateUrl: './list-subject-executions-tab.component.html',
  styleUrls: ['./list-subject-executions-tab.component.scss'],
})
export class ListSubjectExecutionsTabComponent implements OnInit {
  readonly pageIndex = new BehaviorSubject<number>(1);
  readonly total = new BehaviorSubject<number>(1);
  readonly pageSize = new BehaviorSubject<number>(10);
  readonly dataSet = new BehaviorSubject<SubjectExecutionTableViewDTO[]>([]);

  constructor(
    private service: ListSubjectExecutionsTabService,
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

  editSubjectExecution(id: string) {
    this.dialog
      .open(EditSubjectExecutionsDialogComponent, {
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

  deleteSubjectExecution(id: string) {
    this.dialog
      .open(AreYouSureDialogComponent, {
        data: {
          dialogTitle:
            'Are you sure you want to delete this subject execution?',
          yesButtonText: 'Delete',
        },
      })
      .afterClosed()
      .pipe(first())
      .subscribe({
        next: (value) => {
          if (value === 'yes') {
            this.service
              .deleteById(id)
              .pipe(first())
              .subscribe({
                next: (_) => {
                  this.snack.open('Successfully deleted subject execution');
                  this.reloadFromApi();
                },
                error: () => {
                  this.snack.open('Error while deleting subject execution');
                },
              });
          }
        },
      });
  }
}

export interface SubjectExecutionTableViewDTO {
  id: string;
  place: string;
  time: Time;
  weekDay: string;
}
