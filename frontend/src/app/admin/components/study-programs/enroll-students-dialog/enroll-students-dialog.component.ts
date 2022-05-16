import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { first } from 'rxjs';
import { StudentsViewDTO } from '../../students/list-students-tab/list-students-tab.component';
import { EnrollStudentsDialogService } from './enroll-students-dialog.service';

@Component({
  selector: 'app-enroll-students-dialog',
  templateUrl: './enroll-students-dialog.component.html',
  styleUrls: ['./enroll-students-dialog.component.scss']
})
export class EnrollStudentsDialogComponent implements OnInit {
  form!: FormGroup;
  opStudents: StudentsViewDTO[] = [];
  existentStudentIds: string[] = [];

  constructor(
    private service: EnrollStudentsDialogService,
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) private dialogData: { id: string },
    public readonly dialogRef: MatDialogRef<EnrollStudentsDialogComponent>,
    private snack: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.service.getAvailableStudents().pipe(first()).subscribe((res) => {
      this.opStudents = res?.body!
    })
    this.form = this.fb.group({
      studentIds: [null, [Validators.required]],
    });
  }

  submitForm(form: Record<string, unknown>) {
    this.service.enrollStudents(this.dialogData.id, form).subscribe({
      next: (_) => {
        this.snack.open('Students enrolled succesfully');
        this.dialogRef.close('success');
      },
      error: (_) => {
        this.snack.open('Error occurred enrolling students');
      },
    });
  }
}
