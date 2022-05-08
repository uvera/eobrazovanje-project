import { Component, OnInit } from '@angular/core';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { EditSubjectDialogComponent } from '../../subjects/edit-subject-dialog/edit-subject-dialog.component';
import { AreYouSureDialogComponent } from '../../../../common/components/dialogs/are-you-sure-dialog/are-you-sure-dialog.component';
import { ListStudentsTabService } from './list-students-tab.service';
import { EditStudentDialogComponent } from '../edit-student-dialog/edit-student-dialog.component';

@Component({
  selector: 'app-list-students-tab',
  templateUrl: './list-students-tab.component.html',
  styleUrls: ['./list-students-tab.component.scss'],
})
export class ListStudentsTabComponent implements OnInit {
  readonly pageIndex = new BehaviorSubject<number>(1);
  readonly total = new BehaviorSubject<number>(1);
  readonly pageSize = new BehaviorSubject<number>(10);
  readonly dataSet = new BehaviorSubject<StudentsViewDTO[]>([]);

  constructor(
    private service: ListStudentsTabService,
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

  editStudent(id: string) {
    this.dialog
      .open(EditStudentDialogComponent, {
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

  deleteStudent(id: string) {
    this.dialog
      .open(AreYouSureDialogComponent, {
        data: {
          dialogTitle: 'Are you sure you want to delete student?',
          yesButtonText: 'Delete',
        },
      })
      .afterClosed()
      .pipe(first())
      .subscribe({
        next: (value) => {
          if (value === 'yes') {
            this.service
              .deleteStudentById(id)
              .pipe(first())
              .subscribe({
                next: (_) => {
                  this.snack.open('Successfully deleted student');
                  this.reloadFromApi();
                },
                error: () => {
                  this.snack.open('Error while deleting student');
                },
              });
          }
        },
      });
  }
}

export interface StudentsViewDTO {
  id: string;
  transcriptNumber: string;
  identificationNumber: string;
  user: {
    firstName: string;
    lastName: string;
  };
}
