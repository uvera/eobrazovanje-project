import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { StudentsViewDTO } from '../../students/list-students-tab/list-students-tab.component';
import { EnrollStudentsDialogService } from './enroll-students-dialog.service';
import { Papa } from 'ngx-papaparse';

@Component({
  selector: 'app-enroll-students-dialog',
  templateUrl: './enroll-students-dialog.component.html',
  styleUrls: ['./enroll-students-dialog.component.scss']
})
export class EnrollStudentsDialogComponent implements OnInit {
  form!: FormGroup;
  opStudents: StudentsViewDTO[] = [];
  existentStudentIds: string[] = [];
  selectedFile: any = null

  constructor(
    private service: EnrollStudentsDialogService,
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) private dialogData: { id: string },
    public readonly dialogRef: MatDialogRef<EnrollStudentsDialogComponent>,
    private snack: MatSnackBar,
    private papa: Papa,
  ) { }

  ngOnInit(): void {
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0] ?? null;
  }

  onSubmit() {
    if (this.selectedFile) {
      this.papa.parse(this.selectedFile, {
        complete: (result) => {
          this.service.enrollStudents(this.dialogData.id, result?.data).subscribe({
            next: (_) => {
              this.snack.open('Students enrolled succesfully');
              this.dialogRef.close('success');
            },
            error: (_) => {
              this.snack.open('Error occurred enrolling students');
            },
          });
        },
        skipEmptyLines: 'greedy',
        header: true,
      })
    }
  }
}
