import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-are-you-sure-dialog',
  templateUrl: './are-you-sure-dialog.component.html',
  styleUrls: ['./are-you-sure-dialog.component.scss'],
})
export class AreYouSureDialogComponent implements OnInit {
  dialogTitle = '';
  yesButtonText = '';
  noButtonText = 'Cancel';

  constructor(
    @Inject(MAT_DIALOG_DATA)
    private dialogData: {
      dialogTitle?: string;
      yesButtonText?: string;
      noButtonText?: string;
    },
    public readonly dialogRef: MatDialogRef<AreYouSureDialogComponent>
  ) {
    this.dialogTitle = dialogData?.dialogTitle ?? '{{dialogTitle}}';
    this.yesButtonText = dialogData?.yesButtonText ?? '{{yesButtonText}}';
    this.noButtonText = dialogData?.noButtonText ?? 'Cancel';
  }

  ngOnInit(): void {}
}
