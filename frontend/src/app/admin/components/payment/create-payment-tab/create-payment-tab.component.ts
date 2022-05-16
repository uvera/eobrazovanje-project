import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { first, of } from 'rxjs';
import { PaymentViewDTO } from '../../payment/list-payment-tab/list-payment-tab.component';
import { CreatePaymentTabService } from './create-payment-tab.service';

@Component({
  selector: 'app-create-payment-tab',
  templateUrl: './create-payment-tab.component.html',
  styleUrls: ['./create-payment-tab.component.scss']
})
export class CreatePaymentTabComponent implements OnInit {
  form!: FormGroup;
  opStudents: Array<StudentViewDTO> = [];

  constructor(
    private fb: FormBuilder,
    private readonly service: CreatePaymentTabService,
    private readonly snack: MatSnackBar,
    private readonly router: Router,
    private readonly ar: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.service.getStudents().pipe(first()).subscribe((res) => {
      this.opStudents = res?.body as Array<StudentViewDTO>
    })
    this.form = this.fb.group({
      amount: [null, [Validators.required]],
      depositedAt: [null, [Validators.required]],
      id: [null, [Validators.required]],
    });
  }

  submitForm(form: Record<string, unknown>) {
    this.service.createPayment(form).subscribe({
      next: (_) => {
        this.snack.open('Payment created');
        of(
          this.router.navigate(['list-payments-tab'], {
            relativeTo: this.ar.parent,
          })
        );
      },
      error: (_) => {
        this.snack.open('Error occurred');
      },
    });
  }
}

interface StudentViewDTO {
    id: number,
}

