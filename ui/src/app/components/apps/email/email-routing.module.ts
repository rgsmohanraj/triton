import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BaseInputsComponent } from '../../forms/form-controls/base-inputs/base-inputs.component';
import { CheckboxRadioComponent } from '../../forms/form-controls/checkbox-radio/checkbox-radio.component';
import { FormValidationComponent } from '../../forms/form-controls/form-validation/form-validation.component';
import { InputGroupsComponent } from '../../forms/form-controls/input-groups/input-groups.component';
import { MegaOptionsComponent } from '../../forms/form-controls/mega-options/mega-options.component';
import { TouchspinComponent } from '../../forms/form-widgets/touchspin/touchspin.component';
import { Select2Component } from '../../forms/form-widgets/ngselect/select2.component';
import { SwitchComponent } from '../../forms/form-widgets/switch/switch.component';
import { ClipboardComponent } from '../../forms/form-widgets/clipboard/clipboard.component';
import { FormWizardComponent } from '../../forms/form-Layouts/form-wizard/form-wizard.component';
import { DefaultFormComponent } from '../../forms/form-Layouts/default-form/default-form.component';
import { FormWizardTwoComponent } from '../../forms/form-Layouts/form-wizard-two/form-wizard-two.component';
import { FormWizardThreeComponent } from '../../forms/form-Layouts/form-wizard-three/form-wizard-three.component';
import { FormWizardFourComponent } from '../../forms/form-Layouts/form-wizard-four/form-wizard-four.component';

// import { EmailComponent } from './email.component';
import { AnchorDetailsComponent } from './anchor-details/anchor-details.component';
import { NewAnchorDetailsComponent } from './new-anchor-details/new-anchor-details.component';
import { DisbursementDetailsComponent } from './disbursement-details/disbursement-details.component';
import { CollectionDetailsComponent } from './collection-details/collection-details.component';
import { ExploreProgramDetailsComponent } from './explore-program-details/explore-program-details.component';
import { AnchorRenewalComponent } from './anchor-renewal/anchor-renewal.component';
import { AnchorEnhancementComponent } from './anchor-enhancement/anchor-enhancement.component';
import { DocumentDetailsComponent } from './document-details/document-details.component';
import { DeferralComponent } from './deferral/deferral.component';
import { WorkflowComponent } from './workflow/workflow.component';

const routes: Routes = [
{
path: '',
children: [
     {
                path: "anchor-details",
                component: AnchorDetailsComponent
      },
      {
        path: "new-anchor",
        component: NewAnchorDetailsComponent
      },
       {
                      path: "disbursement",
                      component: DisbursementDetailsComponent
                    },
                    {
                           path: "collection",
                           component: CollectionDetailsComponent
                     },
       {
                            path: "exploreProgram",
                            component: ExploreProgramDetailsComponent
                             },
       {
                        path: "AnchorRenewal",
                         component: AnchorRenewalComponent
                          },
       {
                            path: "AnchorEnhancement",
                            component: AnchorEnhancementComponent
                             },
       {
                  path: "Document",
                  component: DocumentDetailsComponent
       },
       {
                 path: "Deferral",
                 component: DeferralComponent
       },
       {
                  path: "Workflow",
                  component: WorkflowComponent
        },
]
}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmailRoutingModule { }
