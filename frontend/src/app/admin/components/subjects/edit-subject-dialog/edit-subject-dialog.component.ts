import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { EditSubjectDialogService } from './edit-subject-dialog.service';
import { EditSubjectData } from './edit-subject-dialog.dto';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { of } from 'rxjs';

@Component({
  selector: 'app-edit-subject-dialog',
  templateUrl: './edit-subject-dialog.component.html',
  styleUrls: ['./edit-subject-dialog.component.scss'],
})
export class EditSubjectDialogComponent implements OnInit {
  form!: FormGroup;

  constructor(
    private service: EditSubjectDialogService,
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) private dialogData: { id: string },
    public readonly dialogRef: MatDialogRef<EditSubjectDialogComponent>,
    private snack: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      name: [null, [Validators.required]],
      espb: [null, [Validators.required, Validators.pattern('([0-9])*')]],
      year: [null, [Validators.required, Validators.pattern('([0-9])*')]],
    });

    this.service.getSubjectById(this.dialogData.id).subscribe({
      next: (value) => {
        this.form.reset(value.body);
      },
      error: (_) => {
        this.snack.open('Error occurred fetching subject for editing');
        this.dialogRef.close('error');
      },
    });
  }

  submitForm(form: Record<string, unknown>) {
    this.service.editSubjectById(this.dialogData.id, form).subscribe({
      next: (_) => {
        this.snack.open('Subject edited successfully');
        this.dialogRef.close('success');
      },
      error: (_) => {
        this.snack.open('Error occurred editing subject');
      },
    });
  }
}
