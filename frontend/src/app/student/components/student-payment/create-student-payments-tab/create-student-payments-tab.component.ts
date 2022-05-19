import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { first, of } from 'rxjs';
import { CreatePaymentTabService } from './create-student-payment-tab.service';

@Component({
  selector: 'app-create-student-payments-tab',
  templateUrl: './create-student-payments-tab.component.html',
  styleUrls: ['./create-student-payments-tab.component.scss']
})
export class CreateStudentPaymentsTabComponent implements OnInit {
  form!: FormGroup;

  constructor(    
    private fb: FormBuilder,
    private readonly service: CreatePaymentTabService,
    private readonly snack: MatSnackBar,
    private readonly router: Router,
    private readonly ar: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
    amount: [null, [Validators.required]],
    depositedAt: [null, [Validators.required]],
    studentId: [null, [Validators.required]],
  });
  }

  submitForm(form: Record<string, unknown>) {
    console.log('submit')
  }
}
