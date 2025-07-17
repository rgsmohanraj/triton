import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgSelectModule } from '@ng-select/ng-select';
import { ArchwizardModule } from 'angular-archwizard';
import { SharedModule } from '../../../shared/shared.module';
import { BaseInputsComponent } from '../../forms/form-controls/base-inputs/base-inputs.component';
import { CheckboxRadioComponent } from '../../forms/form-controls/checkbox-radio/checkbox-radio.component';
import { FormValidationComponent } from '../../forms/form-controls/form-validation/form-validation.component';
import { InputGroupsComponent } from '../../forms/form-controls/input-groups/input-groups.component';
import { MegaOptionsComponent } from '../../forms/form-controls/mega-options/mega-options.component';
import { DefaultFormComponent } from '../../forms/form-Layouts/default-form/default-form.component';
import { FormWizardFourComponent } from '../../forms/form-Layouts/form-wizard-four/form-wizard-four.component';
import { FormWizardThreeComponent } from '../../forms/form-Layouts/form-wizard-three/form-wizard-three.component';
import { FormWizardTwoComponent } from '../../forms/form-Layouts/form-wizard-two/form-wizard-two.component';
import { FormWizardComponent } from '../../forms/form-Layouts/form-wizard/form-wizard.component';
import { ClipboardComponent } from '../../forms/form-widgets/clipboard/clipboard.component';
import { Select2Component } from '../../forms/form-widgets/ngselect/select2.component';
import { SwitchComponent } from '../../forms/form-widgets/switch/switch.component';
import { TouchspinComponent } from '../../forms/form-widgets/touchspin/touchspin.component';
import { TabsModule } from 'ngx-tabset';
import { EmailRoutingModule } from './email-routing.module';
// import { EmailComponent } from './email.component';
import { AnchorDetailsComponent } from './anchor-details/anchor-details.component';
import { NewAnchorDetailsComponent } from './new-anchor-details/new-anchor-details.component';
import { DisbursementDetailsComponent } from './disbursement-details/disbursement-details.component';
import { CollectionDetailsComponent } from './collection-details/collection-details.component';
import { ExploreProgramDetailsComponent } from './explore-program-details/explore-program-details.component';
import { AnchorRenewalComponent } from './anchor-renewal/anchor-renewal.component';
import { AnchorEnhancementComponent } from './anchor-enhancement/anchor-enhancement.component';
import { DocumentDetailsComponent } from './document-details/document-details.component';
import { NumberToWordsPipePipe } from 'src/app/Pipe/number-to-words-pipe.pipe';
import { DeferralComponent } from './deferral/deferral.component';
import { WorkflowComponent } from './workflow/workflow.component';
import { DatePipe } from '@angular/common';

@NgModule({
  declarations: [


//                      EmailComponent,
                      AnchorDetailsComponent,
                      NewAnchorDetailsComponent,
                      DisbursementDetailsComponent,
                      CollectionDetailsComponent,
                      ExploreProgramDetailsComponent,
                      AnchorRenewalComponent,
                      AnchorEnhancementComponent,
                      DocumentDetailsComponent,
                      DeferralComponent,
                      WorkflowComponent],
  imports: [
   CommonModule,
       FormsModule,
       HttpClientModule,
       ReactiveFormsModule,
       NgbModule,
       ArchwizardModule,
       SharedModule,
       NgSelectModule,
       TabsModule,
    EmailRoutingModule
  ],
     providers: [
     DatePipe
     ],
})
export class EmailModule { }
