import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { MustMatch } from '../../../../shared/validators/passwordMatch';

@Component({
  selector: 'app-form-wizard-three',
  templateUrl: './form-wizard-three.component.html',
  styleUrls: ['./form-wizard-three.component.scss']
})
export class FormWizardThreeComponent implements OnInit {
  thirdFormGroup: FormGroup;
  pan:any;name:any;constitution:any;state:any;city:any;source:any;subSource:any;rmName:any;customerName:any;
  customerMob:any;customerEmail:any;
  firstName:any;lastName:any;
  constructor(
   private _formBuilder: FormBuilder,
    private toaster: ToastrService
  ) { }


  ngOnInit(): void {
    this.thirdFormGroup = this._formBuilder.group({
      dd: [null, [Validators.required, Validators.pattern('[0-9]{2}')]],
      mm: [null, [Validators.required, Validators.pattern('[0-9]{2}')]],
      yyyy: [null, [Validators.required, Validators.pattern('[0-9]{4}')]],
    })
  }
  public finish() {
    this.toaster.success('Successfully Registered')
  }
}