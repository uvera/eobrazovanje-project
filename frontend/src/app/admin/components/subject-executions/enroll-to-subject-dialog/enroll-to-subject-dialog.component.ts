import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { StudentsViewDTO } from '../../students/list-students-tab/list-students-tab.component';
import { EnrollToSubjectDialogService } from './enroll-to-subject-dialog.service';

@Component({
  selector: 'app-enroll-to-subject-dialog',
  templateUrl: './enroll-to-subject-dialog.component.html',
  styleUrls: ['./enroll-to-subject-dialog.component.scss']
})
export class EnrollToSubjectDialogComponent implements OnInit {
  form!: FormGroup;
  opStudents: StudentsViewDTO[] = [];
  existentStudentIds: string[] = [];

  constructor(
    private service: EnrollToSubjectDialogService,
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) private dialogData: { id: string },
    public readonly dialogRef: MatDialogRef<EnrollToSubjectDialogService>,
    private snack: MatSnackBar
  ) {}

  ngOnInit(): void {
  //   this.service.getAvailableStudents().pipe(first()).subscribe((res) => {
  //     this.opStudents = res?.body!
  //   })
  //   this.form = this.fb.group({
  //     studentIds: [null, [Validators.required]],
  //   });
  }

  // submitForm(form: Record<string, unknown>) {
  //   this.service.enrollStudents(this.dialogData.id, form).subscribe({
  //     next: (_) => {
  //       this.snack.open('Students enrolled succesfully');
  //       this.dialogRef.close('success');
  //     },
  //     error: (_) => {
  //       this.snack.open('Error occurred enrolling students');
  //     },
  //   });
  // }
}
