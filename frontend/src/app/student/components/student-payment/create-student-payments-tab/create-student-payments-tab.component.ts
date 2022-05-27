import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { first, of } from 'rxjs';
import { CreatePaymentTabService } from './create-student-payment-tab.service';

@Component({
  selector: 'app-create-student-payments-tab',
  templateUrl: './create-student-payments-tab.component.html',
  styleUrls: ['./create-student-payments-tab.component.scss'],
})
export class CreateStudentPaymentsTabComponent implements OnInit {
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private readonly service: CreatePaymentTabService,
    private readonly snack: MatSnackBar,
    private readonly router: Router,
    private readonly ar: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      studentId: '',
      amount: [null, [Validators.required]],
      depositedAt: [null, [Validators.required]],
    });
  }

  submitForm() {
    this.service
      .fetchCurrentUser()
      .pipe()
      .subscribe((res) => {
        const responseBody = res?.body;
        if (responseBody) {
          const { email } = responseBody;
          this.service.fetchCurrentStudent(email).subscribe((res) => {
            const responseBody = res?.body;
            if (responseBody) {
              const { id } = responseBody;
              this.form.controls['studentId'].setValue(id);
              this.service.createPayment(this.form.value).subscribe({
                next: (_) => {
                  this.snack.open('Payment created');
                  of(
                    this.router.navigate(['list-student-payments-tab'], {
                      relativeTo: this.ar.parent,
                    })
                  );
                },
                error: (_) => {
                  this.snack.open('Error occurred');
                },
              });
            }
          });
        }
      });
  }
}
