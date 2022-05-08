import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EditSubjectDialogService } from '../../subjects/edit-subject-dialog/edit-subject-dialog.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EditStudentDialogService } from './edit-student-dialog.service';

@Component({
  selector: 'app-edit-student-dialog',
  templateUrl: './edit-student-dialog.component.html',
  styleUrls: ['./edit-student-dialog.component.scss'],
})
export class EditStudentDialogComponent implements OnInit {
  form!: FormGroup;

  constructor(
    private service: EditStudentDialogService,
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) private dialogData: { id: string },
    public readonly dialogRef: MatDialogRef<EditStudentDialogComponent>,
    private snack: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      transcriptNumber: [null, [Validators.required]],
      identificationNumber: [null, [Validators.required]],
    });

    this.service.getStudentById(this.dialogData.id).subscribe({
      next: (value) => {
        const body = value.body!;
        this.form.reset({
          firstName: body.user.firstName,
          lastName: body.user.lastName,
          transcriptNumber: body.transcriptNumber,
          identificationNumber: body.identificationNumber,
        });
      },
      error: (_) => {
        this.snack.open('Error occurred fetching student for editing');
        this.dialogRef.close('error');
      },
    });
  }

  submitForm(form: Record<string, unknown>) {
    this.service.editStudentById(this.dialogData.id, form).subscribe({
      next: (_) => {
        this.snack.open('Student edited successfully');
        this.dialogRef.close('success');
      },
      error: (_) => {
        this.snack.open('Error occurred editing student');
      },
    });
  }
}
