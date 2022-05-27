import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EditPaymentDialogService } from './edit-payment-dialog.service';

@Component({
  selector: 'app-edit-payment-dialog',
  templateUrl: './edit-payment-dialog.component.html',
  styleUrls: ['./edit-payment-dialog.component.scss'],
})
export class EditPaymentDialogComponent implements OnInit {
  form!: FormGroup;

  constructor(
    private service: EditPaymentDialogService,
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) private dialogData: { id: string },
    public readonly dialogRef: MatDialogRef<EditPaymentDialogComponent>,
    private snack: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      amount: [null, [Validators.required]],
      depositedAt: [null, [Validators.required]],
    });

    this.service.getById(this.dialogData.id).subscribe({
      next: (value) => {
        const body = value.body!;
        this.form.reset({
          amount: body.amount,
          depositedAt: body.depositedAt,
        });
      },
      error: (_) => {
        this.snack.open('Error occurred fetching payment for editing');
        this.dialogRef.close('error');
      },
    });
  }

  submitForm(form: Record<string, unknown>) {
    this.service.editById(this.dialogData.id, form).subscribe({
      next: (_) => {
        this.snack.open('Payment edited successfully');
        this.dialogRef.close('success');
      },
      error: (_) => {
        this.snack.open('Error occurred editing payment');
      },
    });
  }
}
