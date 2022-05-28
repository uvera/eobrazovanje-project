import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { first, of } from 'rxjs';
import { PreExamActivityViewDTO } from 'src/app/admin/components/pre-exam-activities/edit-pre-exam-activity/edit-pre-exam-activity.component';
import { AddPreExamActivityResultDialogService } from './add-pre-exam-activity-result-dialog.service';

@Component({
  selector: 'app-add-pre-exam-activity-result-dialog',
  templateUrl: './add-pre-exam-activity-result-dialog.component.html',
  styleUrls: ['./add-pre-exam-activity-result-dialog.component.scss']
})
export class AddPreExamActivityResultDialogComponent implements OnInit {
  form!: FormGroup;
  opActivities: PreExamActivityViewDTO[] = [];
  viewActivities: ResultViewDTO[] = [];
  isDisabled: boolean = false;

  constructor(
    private fb: FormBuilder,
    private readonly service: AddPreExamActivityResultDialogService,
    @Inject(MAT_DIALOG_DATA) private dialogData: { subjExId: string, studentId: string },
    public readonly dialogRef: MatDialogRef<AddPreExamActivityResultDialogComponent>,
    private readonly snack: MatSnackBar,
    private readonly router: Router,
    private readonly ar: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.service
      .fetchStudentPreExamActivitiesBySubject(this.dialogData.studentId, this.dialogData.subjExId)
      .pipe(first())
      .subscribe((res) => {
        if (res?.body && res.body[0]['preExamActivity']) {
          this.isDisabled = true;
          this.viewActivities = res?.body;
        }
        else {
          this.opActivities = res?.body as PreExamActivityViewDTO[];
          let results = res?.body.reduce((acc: any, i: PreExamActivityViewDTO) => {
            let key: string = `points-${i.id}`
            acc[key] = [null, [Validators.required]];
            return acc
          }, {})
          this.form = this.fb.group({
            ...results
          });
        }
      });
  }

  submitForm(form: Record<string, unknown>) {
    const formValues = Object.entries(form).map((obj) => {
      return { preExamActivityId: obj[0].replace("points-", ""), score: obj[1], studentId: this.dialogData.studentId }
    })
    this.service.createResults(formValues).subscribe({
      next: (_) => {
        this.snack.open('Results created');
        of(
          this.router.navigate(['view-students'], {
            relativeTo: this.ar.parent,
          })
        );
      },
      error: (_) => {
        this.snack.open('Error occurred');
      },
    });
  }
}

interface ResultViewDTO {
  id: string;
  preExamActivity: PreExamActivityViewDTO;
  score: number;
}
