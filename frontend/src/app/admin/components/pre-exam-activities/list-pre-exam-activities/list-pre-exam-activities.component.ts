import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { AreYouSureDialogComponent } from 'src/app/common/components/dialogs/are-you-sure-dialog/are-you-sure-dialog.component';
import { EditPreExamActivityComponent, PreExamActivityViewDTO } from '../edit-pre-exam-activity/edit-pre-exam-activity.component';
import { ListPreExamActivitiesService } from './list-pre-exam-activities.service';

@Component({
  selector: 'app-list-pre-exam-activities',
  templateUrl: './list-pre-exam-activities.component.html',
  styleUrls: ['./list-pre-exam-activities.component.scss']
})
export class ListPreExamActivitiesComponent implements OnInit {
  readonly pageIndex = new BehaviorSubject<number>(1);
  readonly total = new BehaviorSubject<number>(1);
  readonly pageSize = new BehaviorSubject<number>(10);
  readonly dataSet = new BehaviorSubject<PreExamActivityViewDTO[]>([]);

  constructor(
    private service: ListPreExamActivitiesService,
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

  editPreExamActivity(id: string) {
    this.dialog
      .open(EditPreExamActivityComponent, {
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

  deletePreExamActivity(id: string) {
    this.dialog
      .open(AreYouSureDialogComponent, {
        data: {
          dialogTitle: 'Are you sure you want to delete this pre exam activity?',
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
                  this.snack.open('Successfully deleted pre exam activity');
                  this.reloadFromApi();
                },
                error: () => {
                  this.snack.open('Error while deleting pre exam activity');
                },
              });
          }
        },
      });
  }
  }
