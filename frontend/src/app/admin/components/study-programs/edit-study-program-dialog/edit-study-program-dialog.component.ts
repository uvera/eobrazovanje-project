import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { first } from 'rxjs';
import { SubjectViewDTO } from '../../subjects/list-subjects-tab/list-subjects-tab.component';
import { CreateStudyProgramTabService } from '../create-study-program-tab/create-study-program-tab.service';
import { EditStudyProgramDialogService } from './edit-study-program-dialog.service';

@Component({
  selector: 'app-edit-study-program-dialog',
  templateUrl: './edit-study-program-dialog.component.html',
  styleUrls: ['./edit-study-program-dialog.component.scss']
})
export class EditStudyProgramDialogComponent implements OnInit {
  form!: FormGroup;
  opSubjects: SubjectViewDTO[] = [];
  existentSubjectsIds: string[] = [];

  constructor(
    private service: EditStudyProgramDialogService,
    private mainService: CreateStudyProgramTabService,
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) private dialogData: { id: string },
    public readonly dialogRef: MatDialogRef<EditStudyProgramDialogComponent>,
    private snack: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.mainService.getAvailableSubjects().pipe(first()).subscribe((res) => {
      this.opSubjects = res?.body as Array<SubjectViewDTO>
    })
    this.form = this.fb.group({
      name: [null, [Validators.required]],
      codeName: [null, [Validators.required]],
      subjects: [null, [Validators.required]],
    });

    this.service.getStudyProgramById(this.dialogData.id).subscribe({
      next: (value) => {
        const body = value.body!;
        this.form.reset({
          name: body.name,
          codeName: body.codeName,
          subjects: body.subjects,
        });
        this.opSubjects = this.opSubjects.concat(body.subjects)
        this.existentSubjectsIds = body.subjects.map(a => a.id)
      },
      error: (_) => {
        this.snack.open('Error occurred fetching study program for editing');
        this.dialogRef.close('error');
      },
    });
  }

  submitForm(form: Record<string, unknown>) {
    this.service.editStudyProgramById(this.dialogData.id, form).subscribe({
      next: (_) => {
        this.snack.open('Study program edited successfully');
        this.dialogRef.close('success');
      },
      error: (_) => {
        this.snack.open('Error occurred editing study program');
      },
    });
  }
}
