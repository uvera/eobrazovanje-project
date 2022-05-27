import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EditSubjectDialogService } from '../../subjects/edit-subject-dialog/edit-subject-dialog.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EditProfesorDialogService } from './edit-profesor-dialog.service';

@Component({
  selector: 'app-edit-profesor-dialog',
  templateUrl: './edit-profesor-dialog.component.html',
  styleUrls: ['./edit-profesor-dialog.component.scss'],
})
export class EditProfesorDialogComponent implements OnInit {
  form!: FormGroup;
  teacherTypes = ['PROFESSOR', 'ASSISTANT'];

  constructor(
    private service: EditProfesorDialogService,
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) private dialogData: { id: string },
    public readonly dialogRef: MatDialogRef<EditProfesorDialogService>,
    private snack: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      teacherType: [null, [Validators.required]],
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
    });

    this.service.getProfesorById(this.dialogData.id).subscribe({
      next: (value) => {
        const body = value.body!;
        this.form.reset({
          teacherType: body.teacherType,
          firstName: body.user.firstName,
          lastName: body.user.lastName,
        });
      },
      error: (_) => {
        this.snack.open('Error occurred fetching profesor for editing');
        this.dialogRef.close('error');
      },
    });
  }

  submitForm(form: Record<string, unknown>) {
    this.service.editProfesorById(this.dialogData.id, form).subscribe({
      next: (_) => {
        this.snack.open('Profesor edited successfully');
        this.dialogRef.close('success');
      },
      error: (_) => {
        this.snack.open('Error occurred editing profesor');
      },
    });
  }
}
