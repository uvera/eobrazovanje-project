import { Component, OnInit } from '@angular/core';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ListStudentPaymentTabService } from './list-student-payments-tab.service';

@Component({
  selector: 'app-list-student-payments-tab',
  templateUrl: './list-student-payments-tab.component.html',
  styleUrls: ['./list-student-payments-tab.component.scss']
})
export class ListStudentPaymentsTabComponent implements OnInit {
  readonly pageIndex = new BehaviorSubject<number>(1);
  readonly total = new BehaviorSubject<number>(1);
  readonly pageSize = new BehaviorSubject<number>(10);
  readonly dataSet = new BehaviorSubject<PaymentViewDTO[]>([]);
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private service: ListStudentPaymentTabService,
    private snack: MatSnackBar,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      email: [''],
    });

    this.fetchCurrentStudentsPayments(1, 10)
    
    this.pageNumberAndSizeCombined$.subscribe((value) => {
      this.fetchCurrentStudentsPayments(value.pageIndex, value.pageSize);
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

  fetchCurrentStudentsPayments(pageIndex: number, pageSize: number) {
    this.service.fetchCurrentUser()
    .pipe()
    .subscribe((res) => {
      const responseBody = res?.body;
      if (responseBody) {
        const { email }  = responseBody;
        this.service.
        fetchCurrentStudent(email)
          .subscribe((res) => {
            const responseBody = res?.body
            if (responseBody) {
            const { id }  = responseBody;
            this.service.
            fetchStudentPaymentsPaged(pageIndex, pageSize, id)
            .pipe(first())
            .subscribe((res) => {
              const responseBody = res?.body
              if(responseBody) {
                const { content, totalElements } = responseBody;
                this.total.next(totalElements);
                this.dataSet.next(content);
              }
            })
          }
        })
      }
    })
  }
  
  fetchStudentPaymentsFromApi() {
    console.log(this.form.get('email')?.value)
    
  }

  reloadFromApi() {
    this.fetchCurrentStudentsPayments(this.pageIndex.value, this.pageSize.value);
  }
  
  queryParamsChange(event: NzTableQueryParams) {
    this.pageSize.next(event.pageSize);
    this.pageIndex.next(event.pageIndex);
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