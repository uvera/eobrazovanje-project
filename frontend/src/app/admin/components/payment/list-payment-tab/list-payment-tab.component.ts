import { Component, OnInit } from '@angular/core';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { AreYouSureDialogComponent } from '../../../../common/components/dialogs/are-you-sure-dialog/are-you-sure-dialog.component';
import { ListPaymentTabService } from './list-payment-tab.service';
import { EditPaymentDialogComponent } from '../edit-payment-dialog/edit-payment-dialog.component';
import { StudentsViewDTO } from '../../students/list-students-tab/list-students-tab.component';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-list-payment-tab',
  templateUrl: './list-payment-tab.component.html',
  styleUrls: ['./list-payment-tab.component.scss']
})
export class ListPaymentTabComponent implements OnInit {
  readonly pageIndex = new BehaviorSubject<number>(1);
  readonly total = new BehaviorSubject<number>(1);
  readonly pageSize = new BehaviorSubject<number>(10);
  readonly dataSet = new BehaviorSubject<PaymentViewDTO[]>([]);
  opStudents: StudentsViewDTO[] = [];
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private service: ListPaymentTabService,
    private snack: MatSnackBar,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      id: [null],
    });
    this.service.getStudents().pipe(first()).subscribe((res) => {
      this.opStudents = res?.body as Array<StudentsViewDTO>
    })
    this.pageNumberAndSizeCombined$.subscribe((value) => {
      this.fetchFromApi(value.pageIndex, value.pageSize, this.form.get('id')?.value);
    });
    this.form.valueChanges.subscribe(x => {
      this.reloadFromApi()
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

  fetchFromApi(pageIndex: number, pageSize: number, id: string) {
    if (id) {
      this.service
        .fetchPaged(pageIndex, pageSize, id)
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
  }

  reloadFromApi() {
    this.fetchFromApi(this.pageIndex.value, this.pageSize.value, this.form.get('id')?.value);
  }

  queryParamsChange(event: NzTableQueryParams) {
    this.pageSize.next(event.pageSize);
    this.pageIndex.next(event.pageIndex);
  }

  editPayment(id: string) {
    this.dialog
      .open(EditPaymentDialogComponent, {
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

  deletePayment(id: string) {
    this.dialog
      .open(AreYouSureDialogComponent, {
        data: {
          dialogTitle: 'Are you sure you want to delete payment?',
          yesButtonText: 'Delete',
        },
      })
      .afterClosed()
      .pipe(first())
      .subscribe({
        next: (value) => {
          if (value === 'yes') {
            this.service
              .deletePaymentById(id)
              .pipe(first())
              .subscribe({
                next: (_) => {
                  this.snack.open('Successfully deleted payment');
                  this.reloadFromApi();
                },
                error: () => {
                  this.snack.open('Error while deleting payment');
                },
              });
          }
        },
      });
  }
}

export interface PaymentViewDTO {
  id: string;
  amount: Number;
  depositedAt: Date;
  student: {
    id: string;
    user: {
      firstName: string;
      lastName: string;
      email: string;
    }
  };
}
