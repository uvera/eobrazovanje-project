import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EditPreExamActivityService } from './edit-pre-exam-activity.service';

@Component({
  selector: 'app-edit-pre-exam-activity',
  templateUrl: './edit-pre-exam-activity.component.html',
  styleUrls: ['./edit-pre-exam-activity.component.scss']
})
export class EditPreExamActivityComponent implements OnInit {
  form!: FormGroup;

  constructor(
    private service: EditPreExamActivityService,
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) private dialogData: { id: string },
    public readonly dialogRef: MatDialogRef<EditPreExamActivityComponent>,
    private snack: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      name: [null, [Validators.required]],
      points: [null, [Validators.required]],
    });

    this.service.getPreExamActivityById(this.dialogData.id).subscribe({
      next: (value) => {
        const body = value.body!;
        this.form.reset({
          name: body.name,
          points: body.points,
        });
      },
      error: (_) => {
        this.snack.open('Error occurred fetching pre exam activity for edit');
        this.dialogRef.close('error');
      },
    });
  }

  submitForm(form: Record<string, unknown>) {
    this.service.editPreExamActivity(this.dialogData.id, form).subscribe({
      next: (_) => {
        this.snack.open('Pre exam activity edited successfully');
        this.dialogRef.close('success');
      },
      error: (_) => {
        this.snack.open('Error occurred editing pre exam activity');
      },
    });
  }
}

export interface PreExamActivityViewDTO {
  id: string,
  name: string,
  points: number
}