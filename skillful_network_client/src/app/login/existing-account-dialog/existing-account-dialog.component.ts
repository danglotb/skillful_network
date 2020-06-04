import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MatDialog } from '@angular/material/dialog';
import { MAT_DIALOG_DATA } from '@angular/material/dialog'


@Component({
  selector: 'app-existing-account-dialog',
  templateUrl: './existing-account-dialog.component.html',
  styleUrls: ['./existing-account-dialog.component.scss']
})
export class ExistingAccountDialog implements OnInit {

  constructor(public thisDialogRef: MatDialogRef<ExistingAccountDialog>, @Inject(MAT_DIALOG_DATA) public data: string) { }

  ngOnInit(): void {
  }

  onCloseConfirm() {
    this.thisDialogRef.close('fermeture')
  }

}
