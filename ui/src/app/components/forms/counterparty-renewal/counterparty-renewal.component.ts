import { Component, OnInit ,ViewChild, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { MustMatch } from '../../../shared/validators/passwordMatch';
import * as accordion from '../../../shared/data/faq/accordion'
import { ApiRequestService } from 'src/app/shared/services/api-request.service';
import { NgbDateStruct, NgbDate, NgbCalendar, NgbDatepickerConfig} from '@ng-bootstrap/ng-bootstrap';
import { environment } from '../../../../environments/environment';
import { Router } from '@angular/router';
import { CommonModule,DatePipe } from '@angular/common';
import { Observable } from 'rxjs/internal/Observable';
import { of } from 'rxjs/internal/observable/of';
import $ from "jquery";
import { AuthService } from 'src/app/shared/services/firebase/auth.service';
import {WizardComponent, ConfigurableNavigationMode} from 'angular-archwizard';
import { Subscription } from 'rxjs';

declare var require
const Swal = require('sweetalert2')


@Component({
  selector: 'app-counterparty-renewal',
  templateUrl: './counterparty-renewal.component.html',
  styleUrls: ['./counterparty-renewal.component.scss']
})
export class CounterpartyRenewalComponent implements OnInit {
@ViewChild(WizardComponent)
public wizard: WizardComponent;
private subscription: Subscription;
public isDropdownDisabled: boolean = true;

    showLoader: boolean = false;
    public show = true;
    constructor(
        public authService: AuthService,
        public router: Router,
        private _formBuilder: FormBuilder,
        private requestapi:ApiRequestService,
        private toaster: ToastrService,
        public datePipe:DatePipe
    ) {

        const currentDate = new Date();
        var min = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate() + 2);
        var max = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate() + 32);
        this.minDate = min.toISOString().split('T')[0];
        this.maxDate = max.toISOString().split('T')[0];

        this.subscription = this.requestapi.getRenewalEnhancementParams().subscribe(data => {
            if(data){
                if(data.appStatus){
                    this.oldAppId = data.appId;
                }else{
                    this.newAppId = data.appId;
                }
              this.custId = data.custId;
              this.renewalEnhancement = data.renewalEnhancement;
              this.appStatus = data.appStatus;
              this.deferralStatus = data.deferralStatus;
            }
        });

        this.options$=of(["Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","chhatisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttarakhand","Uttar Pradesh","West Bengal"]);
    }

    ngOnInit(): void {
        if(this.oldAppId == undefined && this.newAppId == undefined){
            this.show = false;
        }else{
            this.show = true;
            this.getPending();
            this.getDocumentMaster();
            this.getUnderWriterList();
            this.getRMNames();
            this.debtProfileArray.push(this.newDebtAttribute);
            this.newDebtAttribute = {};
            this.roleBasedFun();
            this.currentStepperFun();
            this.getCustomerInfoDetails();
            this.getCollateralMaster();
            this.getRemarks();
            console.log("oldAppId",this.oldAppId);
            console.log("newAppId",this.newAppId);
            console.log("customerId",this.custId);
            if(this.appStatus){
                this.createApplication();
            }else{
                this.getApplicationIds(this.custId);
            }
        }
//         this.requestapi.changeRenewalEnhancementParam(null);
        this.emailId=localStorage.getItem('email')
        if(this.emailId==null){
            localStorage.clear();
            this.router.navigate(['/auth/login']);
        }
    }

    ngOnDestroy() {
        this.requestapi.clearRenewalEnhancementParams();
        this.subscription.unsubscribe();
    }


    emailId:any;
    minDate:any; maxDate:any;
    renewalEnhancement:any;
    refresh_token:any;
    custId:any; oldAppId:any; newAppId:any; appStatus:any; deferralStatus:any;
    options$: Observable<string[]>;

ApplicationId:any;

//Stages Status
initialComplete:any; initialWip:any; initialReturn:any;
firstComplete:any; firstWip:any; firstReturn:any;
secondComplete:any; secondWip:any; secondReturn:any;
thirdComplete:any; thirdWip:any; thirdReturn:any;
fourthComplete:any; fourthWip:any; fourthReturn:any;
fifthComplete:any; fifthWip:any; fifthReturn:any;
sixComplete:any; sixWip:any; sixReturn:any;
sevenComplete:any; sevenWip:any; sevenReturn:any;
eightComplete:any; eightWip:any; eightReturn:any;
nineComplete:any; nineWip:any; nineReturn:any;
tenComplete:any; tenWip:any; tenReturn:any;
elevenComplete:any; elevenWip:any; elevenReturn:any;
twelveComplete:any; twelveWip:any; twelveReturn:any;

//RBAC Status
cp0View=false; cp0Submit=false; cp0Return= false; cp0Approve= false; cp0Reject= false; cp0Edit=false;
cp1View=false; cp1Submit=false; cp1Return= false; cp1Approve= false; cp1Reject= false; cp1Edit=false;
cp2View=false; cp2Submit=false; cp2Return= false; cp2Approve= false; cp2Reject= false; cp2Edit=false;
cp3View=false; cp3Submit=false; cp3Return= false; cp3Approve= false; cp3Reject= false; cp3Edit=false;
cp4View=false; cp4Submit=false; cp4Return= false; cp4Approve= false; cp4Reject= false; cp4Edit=false;
cp5View=false; cp5Submit=false; cp5Return= false; cp5Approve= false; cp5Reject= false; cp5Edit=false;
cp6View=false; cp6Submit=false; cp6Return= false; cp6Approve= false; cp6Reject= false; cp6Edit=false;
cp7View=false; cp7Submit=false; cp7Return= false; cp7Approve= false; cp7Reject= false; cp7Edit=false;
cp8View=false; cp8Submit=false; cp8Return= false; cp8Approve= false; cp8Reject= false; cp8Edit=false;
cp9View=false; cp9Submit=false; cp9Return= false; cp9Approve= false; cp9Reject= false; cp9Edit=false;
cp10View=false; cp10Submit=false; cp10Return= false; cp10Approve= false; cp10Reject= false; cp10Edit=false;
cp11View=false; cp11Submit=false; cp11Return= false; cp11Approve= false; cp11Reject= false; cp11Edit=false;
cp12View=false; cp12Submit=false; cp12Return= false; cp12Approve= false; cp12Reject= false; cp12Edit=false;



// Next && Previous
//Initial CPA
public initialCreditCpaCPDetailsNP = true;public initialDocChecklistCpaNp = false;public initialCreditCpaRemarksNp = false;
nextInitialCreditCpaCpDetails(){
    this.initialCreditCpaCPDetailsNP = false;
    this.initialDocChecklistCpaNp = true;
}
previousInitialDocChecklist(){
    this.initialDocChecklistCpaNp = false;
    this.initialCreditCpaCPDetailsNP = true;
}
nextInitialDocChecklist(){
    this.initialDocChecklistCpaNp = false;
    this.initialCreditCpaRemarksNp = true;
}
previousInitialRemarksCpa(){
    this.initialCreditCpaRemarksNp = false;
    this.initialDocChecklistCpaNp = true;
}

anchorList:any;
deleteStatus:any;assessmentKycFlag:any;
resultStatus:any;
ProposalList: Array<any> = [];
 proposalArray: Array<any> = [];
 fundReqDetails:[]=[];
creditProposalList: Array<any> = [];
newProposalAttribute: any = {};
flagDealers:any;
flagVendor:any;
anchorProduct:any;
anchorStatus:any;
anchorValid:any;
approveCreditPolicyValue:any;
rejectCreditPolicyValue:any;

softPolicyValue:any;softPolicyId:any;enableSoftPolicy:any;anchorType:any;
fundReqValue:any;fundReqId:any;enableFundReq:any;

//Commercial Cc

eve:any;eveAnchor:any;eveRegular:any;eveAdhoc:any;eveCredit:any;eveDoor:any;eveInvoice:any;eveMargin:any;eveInterest:any;evePf:any;eveRenewal:any;eveInterestType:any;eveRenewalPeriod:any;
checkProd:any;

//cpbasic
cpTypeValue:any;
public cpBasicValidate = false;
public creditCheck = false;public goToRisk = false;public cpProposed1 = true; public cpProposed2 = false;public proposalValidate = false; public disableNextProposed =false;
//soft Policy
public saveSoftPolicyFlag = true;public updateSoftPolicyFlag = false;
//credit Policy
 public creditPolicyView = false;public updateCreditPolicyFlag = false;public saveCreditPolicyFlag = true;public creditPolicyCheck = true;cpType:any;creditPolicyValue:any;creditPolicyId:any;enableCreditPolicy:any;
//Business Stage
public deDupeNp = true;public proposalNp = false;public uploadDataNp = false;public businessRemarks = false;

//Soft policy Master
    SoftPolicyMasterList: Array<any> = [];

//Fund Requirement
    public saveFundReqFlag = false;
    public fundRequirementControl= false;

 fundReqArray: Array<any> = [];
 fundQuestion: Array<any> = [];

//limitEligibility
public cpLimitEligibility1 = true;
public cpLimitEligibility2 = false;
public limitValidate = false;
public termSheetValidate = false;

limitEligibilityList:[]=[];
limitEligibilityArray: Array<any> = [];
limitEligibilityMultipleArray:Array<any>=[];
newLimitAttribute: any = {};

//TermSheet

termSheetArray: Array<any> = [];
newTermSheetAttribute: any = {};

public cpTermSheet1 = true;
public cpTermSheet2 = false;


 //Credit Norms
    creditNormDetails: Array<any> = [];


//Soft policy Details
    softPolicyResult:Array<any> = [];
    softPolicyDealerResult:Array<any> = [];
    softPolicyVendorResult:Array<any> = [];
    softPolicyArray: Array<any> = [];

//credit policy Details
     creditPolicyMasterList: Array<any> = [];
     creditPolicyArray: Array<any> = [];
     creditPolicyFilterList:Array<any>=[];
     creditPolicyResults:Array<any> = [];
     creditPolicyCheckResults:any;
     creditPolicyArrayss:Array<any> = [];

//Commercial
     commercialArray: Array<any> = [];
     newCommercialAttribute: any = {};
     commercialCcList:[]=[];
     public commercial2 = false;
     public commercial1 = true;

nextDeDupe(){
    this.deDupeNp = false;
    this.proposalNp = true;
    window.scrollTo(0, 0);
    this.getProposedProductDetails(true,this.newAppId)
}
previousProposal(){
    this.proposalNp = false;
    this.deDupeNp = true;
    window.scrollTo(0, 0);
}
nextProposal(){
    this.proposalNp = false;
    this.uploadDataNp = true;
    window.scrollTo(0, 0);
}
previousUploadData(){
    this.uploadDataNp = false;
    this.proposalNp = true;
    window.scrollTo(0, 0);
    this.getProposedProductDetails(true,this.newAppId)
}
nextUploadDataRemarks(){
    this.uploadDataNp = false;
    this.businessRemarks = true;
    window.scrollTo(0, 0);
}
previousBusinessRemarks(){
    this.uploadDataNp = true;
    this.businessRemarks = false;
    window.scrollTo(0, 0);
}

//Under Writer
public cPDetailsNp = true;public assignUnderwriterNp = false;assignUnderwriterRemarks = false;
nextCpDetails(){
    this.cPDetailsNp = false;
    this.assignUnderwriterNp = true;
    window.scrollTo(0, 0);
}
previousAssignUnderwriter(){
    this.assignUnderwriterNp = false;
    this.cPDetailsNp = true;
    window.scrollTo(0, 0);
}
nextAssignUnderwriter(){
    this.assignUnderwriterNp = false;
    this.assignUnderwriterRemarks = true;
    window.scrollTo(0, 0);
}
previousAssignUnderwriterRemarks(){
    this.assignUnderwriterNp = true;
    this.assignUnderwriterRemarks = false;
    window.scrollTo(0, 0);
}

//Credit CPA
public creditCpaCPDetailsNP = true; public proposaDetailsNp =false;public docChecklistNp = false;public softPolicyNp = false;public creditCpaRemarks = false;
nextCreditCpaCpDetails(){
    this.creditCpaCPDetailsNP = false;
    this.proposaDetailsNp = true;
    window.scrollTo(0, 0);
}
previousProposalDetails(){
    this.proposaDetailsNp = false;
    this.creditCpaCPDetailsNP = true;
    window.scrollTo(0, 0);
}
nextProposalDetails(){
    this.proposaDetailsNp = false;
    this.docChecklistNp = true;
    window.scrollTo(0, 0);
}
previousDocChecklist(){
    this.docChecklistNp = false;
    this.proposaDetailsNp = true;
    window.scrollTo(0, 0);
}
nextDocChecklist(){
    this.docChecklistNp = false;
    this.softPolicyNp = true;
    window.scrollTo(0, 0);
    this.getUpdateSoftPolicyDetails(this.newAppId);
    console.log("response---+++ ---");
}
previousSoftPolicy(){
    this.softPolicyNp = false;
    this.docChecklistNp = true;
    window.scrollTo(0, 0);
}
nextSoftPolicy(){
    this.creditCpaRemarks = true;
    this.softPolicyNp = false;
    window.scrollTo(0, 0);
}
previousCreditCpaRemarks(){
    this.softPolicyNp = true;
    this.creditCpaRemarks = false;
    window.scrollTo(0, 0);
}


//overrideSoftPolicy
public overridePolicyCPDetailsNp = true;public overridePolicy = false; public overrideSoftPolicyRemarks = false;
nextOverrideCPBasic(){
    this.overridePolicyCPDetailsNp = false;
    this.overridePolicy = true;
    window.scrollTo(0, 0);
}
previousOverrideCPBasic(){
    this.overridePolicy = false;
    this.overridePolicyCPDetailsNp = true;
    window.scrollTo(0, 0);
}
nextOverrideRemarks(){
    this.overridePolicy = false;
    this.overrideSoftPolicyRemarks = true;
    window.scrollTo(0, 0);
}
previousOverrideRemarks(){
    this.overridePolicy = true;
    this.overrideSoftPolicyRemarks = false;
    window.scrollTo(0, 0);
}

//CAM Upload
public camCPDetailsNp = true;public camOverrideSoftPolicyNp = false;public camDebtProfileNp = false; public CAMUploadNp = false;public CAMRemarks = false;
nextCamCpDetails(){
    this.camCPDetailsNp = false;
    this.camOverrideSoftPolicyNp = true;
    window.scrollTo(0, 0);
}
previousOverrideSoftPolicy(){
    this.camOverrideSoftPolicyNp = false;
    this.camCPDetailsNp = true;
    window.scrollTo(0, 0);
}
nextOverrideSoftPolicy(){
    this.camOverrideSoftPolicyNp = false;
    this.camDebtProfileNp = true;
    window.scrollTo(0, 0);
}
previousDebtProfile(){
    this.camDebtProfileNp = false;
    this.camOverrideSoftPolicyNp = true;
    window.scrollTo(0, 0);
}
nextDebtProfile(){
    this.camDebtProfileNp = false;
    this.CAMUploadNp = true;
    window.scrollTo(0, 0);
}
previousCamUpload(){
    this.CAMUploadNp = false;
    this.camDebtProfileNp = true;
    window.scrollTo(0, 0);
}
nextCamUpload(){
    this.CAMUploadNp = false;
    this.CAMRemarks = true;
    window.scrollTo(0, 0);
}
previousCamRemarks(){
    this.CAMUploadNp = true;
    this.CAMRemarks = false;
    window.scrollTo(0, 0);
}

//Under Writer Review
public uwrCPDetailsNp = true;public uwrViewCamNp = false;public uwrLimitEligibilityNp = false;public uwrTermSheetNp = false;public uwrCollateralNp = false;public uwrCreditPolicyNp = false;public uwrRemarks = false;
nextUWRCpDetails(){
    this.uwrCPDetailsNp = false;
    this.uwrViewCamNp = true;
    window.scrollTo(0, 0);
}
previousUWRViewCam(){
    this.uwrViewCamNp = false;
    this.uwrCPDetailsNp = true;
    window.scrollTo(0, 0);
}
// nextUWRViewCam(){
//     this.uwrViewCamNp = false;
//     this.uwrLimitEligibilityNp = true;
//     window.scrollTo(0, 0);
// }

nextUWRViewCam(){
this.uwrViewCamNp = false;
this.uwrLimitEligibilityNp = true;
window.scrollTo(0, 0);
this.getUpdateFundRequirement(this.newAppId);

this.getMultipleLimitEligibility();

}

previousUWRLimitEligibility(){
    this.uwrLimitEligibilityNp = false;
    this.uwrViewCamNp = true;
    window.scrollTo(0, 0);
}
// nextUWRLimitEligibility(){
//     this.uwrLimitEligibilityNp = false;
//     this.uwrTermSheetNp = true;
//     window.scrollTo(0, 0);
// }
nextUWRLimitEligibility(){
this.uwrLimitEligibilityNp = false;
this.uwrTermSheetNp = true;
window.scrollTo(0, 0);
this.getCpTermSheetById('1',this.newAppId);
}

previousUWRTermSheet(){
    this.uwrTermSheetNp = false;
    this.uwrLimitEligibilityNp = true;
    window.scrollTo(0, 0);
}
nextUWRTermSheet(){
    this.uwrTermSheetNp = false;
    this.uwrCollateralNp = true;
    window.scrollTo(0, 0);
}
previousUWRCollateral(){
    this.uwrCollateralNp = false;
    this.uwrTermSheetNp = true;
    window.scrollTo(0, 0);
}
nextUWRCollateral(){
    this.uwrCollateralNp = false;
    this.uwrCreditPolicyNp = true;
    window.scrollTo(0, 0);
}
previousUWRCreditPolicy(){
    this.uwrCreditPolicyNp = false;
    this.uwrCollateralNp = true;
    window.scrollTo(0, 0);
}
nextUWRCreditPolicy(){
    this.uwrCreditPolicyNp = false;
    this.uwrRemarks = true;
    window.scrollTo(0, 0);
}
previousUWRRemarks(){
    this.uwrCreditPolicyNp = true;
    this.uwrRemarks = false;
    window.scrollTo(0, 0);
}

//Under Writer PD Review
public uwPdRCpDetailsNp = true;public uwPdRDueDiligenceNp = false;public uwPdRemarks = false;
nextUwPdRCpDetails(){
    this.uwPdRCpDetailsNp = false;
    this.uwPdRDueDiligenceNp = true;
    window.scrollTo(0, 0);
}
previousDueDiligence(){
    this.uwPdRDueDiligenceNp = false;
    this.uwPdRCpDetailsNp = true;
    window.scrollTo(0, 0);
}
nextDueDiligence(){
    this.uwPdRDueDiligenceNp = false;
    this.uwPdRemarks = true;
    window.scrollTo(0, 0);
}
previousUwRemarks(){
    this.uwPdRDueDiligenceNp = true;
    this.uwPdRemarks = false;
    window.scrollTo(0, 0);
}


// Commercial/Credit
public commercialCreditCpDetailsNp = true;public commercialCreditNp=false;public commercialCCApprovalNp = false;public creditCCApprovalNp = false;public CommercialCreditRemarks = false;
nextCommercialCreditCPDetails(){
    this.commercialCreditCpDetailsNp = false;
    this.commercialCCApprovalNp = true;
    window.scrollTo(0, 0);
}
nextCommercialCreditApproval(){
    this.commercialCreditCpDetailsNp = false;
    this.commercialCCApprovalNp = false;
    this.creditCCApprovalNp = false;
    this.commercialCreditNp=true;
    window.scrollTo(0, 0);
}
previousCommercialCreditCPDetails(){
    this.commercialCreditCpDetailsNp = true;
    this.commercialCCApprovalNp = false;
    this.creditCCApprovalNp = false;
    this.commercialCreditNp=false;
    window.scrollTo(0, 0);
}
nextCommercialCreditRemApproval(){
    this.commercialCreditCpDetailsNp = false;
    this.commercialCCApprovalNp = false;
    this.creditCCApprovalNp = false;
    this.commercialCreditNp=false;
    this.CommercialCreditRemarks = true;
    window.scrollTo(0, 0);
}
prevCommercialCreditApproval(){
    this.commercialCreditCpDetailsNp = false;
    this.commercialCCApprovalNp = false;
    this.creditCCApprovalNp = false;
    this.commercialCreditNp=true;
    this.CommercialCreditRemarks = false;
    window.scrollTo(0, 0);
}
nextCreditCPDetails(){
    this.commercialCreditCpDetailsNp = false;
    this.creditCCApprovalNp = true;
    window.scrollTo(0, 0);
}
previousCreditCPDetails(){
    this.commercialCreditCpDetailsNp = true;
    this.creditCCApprovalNp = false;
    window.scrollTo(0, 0);
}
previousCommercialApproval(){
    this.commercialCCApprovalNp = false;
    this.commercialCreditCpDetailsNp = true;
    window.scrollTo(0, 0);
}
nextCommercialApproval(){
    this.commercialCCApprovalNp = false;
    this.creditCCApprovalNp = true;
    window.scrollTo(0, 0);
}
previousCreditApproval(){
    this.creditCCApprovalNp = false;
    this.commercialCCApprovalNp = true;
    window.scrollTo(0, 0);
}
nextCreditApproval(){
    this.creditCCApprovalNp = false;
    this.CommercialCreditRemarks = true;
    window.scrollTo(0, 0);
}
previousCreditRemarks(){
    this.creditCCApprovalNp = true;
    this.CommercialCreditRemarks = false;
    window.scrollTo(0, 0);
}
nextCommerciaApproval(){
    this.commercialCCApprovalNp = false;
    this.CommercialCreditRemarks = true;
    window.scrollTo(0, 0);
}
previousCommercialRemarks(){
    this.commercialCCApprovalNp = true;
    this.CommercialCreditRemarks = false;
    window.scrollTo(0, 0);
}

//Debt Profile
    debtProfileArray: Array<any> = [];
    newDebtAttribute: any = {};
    addDebtProfileValue() {
        if(this.debtProfileArray.length <= 19){
            this.debtProfileArray.push(this.newDebtAttribute)
            this.newDebtAttribute = {};
            this.debtProfileValidate = false;
//             this.DebtProfile1 = true;
        }else{
            this.toaster.error("Maximum allowed debt profile details reached...");
        }
    }
    deleteDebtProfileValue(id,index) {
        if(this.debtProfileArray.length > 1){
            if(id != undefined){
                this.deleteDebtProfile(index,id,true);
            }else{
                this.debtProfileArray.splice(index, 1);
            }
        }else{
            if(id != undefined){
                this.deleteDebtProfile(index,id,false);
            }else{
                this.toaster.error("Deleting the debt profile structure is not allowed...");
            }
        }

    }

//RM Customer Acceptance
public rmCustCpDetailsNp = true;public rmCustCommercialCCNp = false;public rmCustRemarksCCNp=false;
nextRmCustCpDetails(){
    this.rmCustCpDetailsNp = false;
    this.rmCustCommercialCCNp = true;
    window.scrollTo(0, 0);
    this.getCpTermSheetById('0',this.newAppId)
}
previousRmCustCommercialCC(){
    this.rmCustCommercialCCNp = false;
    this.rmCustCpDetailsNp = true;
    window.scrollTo(0, 0);
}
nextRmCustCommercialCC(){
    this.rmCustRemarksCCNp = true;
    this.rmCustCommercialCCNp = false;
    window.scrollTo(0, 0);
}
previousRmCustRemarksCC(){
    this.rmCustRemarksCCNp = false;
    this.rmCustCommercialCCNp = true;
    window.scrollTo(0, 0);
}

//Operation Maker
public OpsMakerCpDetailsNp = true;public OpsMakerBeneficiaryNp = false;public OpsMakerDocUploadNp = false;public OpsMakerRemarksNp = false;
nextOpsMakerCpDetails(){
    this.OpsMakerCpDetailsNp = false;
    this.OpsMakerBeneficiaryNp = true;
    window.scrollTo(0, 0);
}
previousOpsMakerBeneficiary(){
    this.OpsMakerBeneficiaryNp = false ;
    this.OpsMakerCpDetailsNp = true;
    window.scrollTo(0, 0);
}
nextOpsMakerBeneficiary(){
    this.OpsMakerBeneficiaryNp = false;
    this.OpsMakerDocUploadNp = true;
    window.scrollTo(0, 0);
}
previousOpsMakerDocUpload(){
    this.OpsMakerDocUploadNp = false;
    this.OpsMakerBeneficiaryNp = true;
    window.scrollTo(0, 0);
}
nextOpsMakerDocUpload(){
    this.OpsMakerDocUploadNp = false;
    this.OpsMakerRemarksNp = true;
    window.scrollTo(0, 0);
}
previousOpsMakerRemarks(){
    this.OpsMakerDocUploadNp = true;
    this.OpsMakerRemarksNp = false;
    window.scrollTo(0, 0);
}
//Operation Checker
public OpsCheckerCpDetailsNp = true;public OpsCheckerDocDownloadNp = false;public OpsCheckerRemarksNp =false;
nextOpsCheckerCpDetails(){
    this.OpsCheckerCpDetailsNp = false;
    this.OpsCheckerDocDownloadNp = true;
    window.scrollTo(0, 0);
}
previousOpsCheckerDocDownload(){
    this.OpsCheckerDocDownloadNp = false;
    this.OpsCheckerCpDetailsNp = true;
    window.scrollTo(0, 0);
}

nextOpsCheckerRemarks(){
    this.OpsCheckerDocDownloadNp = false;
    this.OpsCheckerRemarksNp = true;
    window.scrollTo(0, 0);
}

previousOpsCheckerRemarks(){
    this.OpsCheckerDocDownloadNp = true;
    this.OpsCheckerRemarksNp = false;
    window.scrollTo(0, 0);
}
//Deferral Committee
public deferralBasicDetails = true;public deferralApprovalNp = false;public deferralRemarksNp=false;
nextBasicDeferral(){
    this.deferralBasicDetails = false;
    this.deferralApprovalNp = true;
    window.scrollTo(0, 0);
}
previousDeferral(){
    this.deferralApprovalNp = false;
    this.deferralBasicDetails = true;
    window.scrollTo(0, 0);
}
nextDeferral(){
    this.deferralApprovalNp = false;
    this.deferralRemarksNp = true;
    window.scrollTo(0, 0);
}
previousDeferralRemarks(){
    this.deferralRemarksNp = false;
    this.deferralApprovalNp = true;
    window.scrollTo(0, 0);
}

//New Application Create
createApplication():void{
    let response;
    let fileName="anchor/applicationDetails";
    let data={ type:2,appType:this.renewalEnhancement,custId:this.custId}
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.newAppId = res.entityId;
        console.log("this.newAppId",this.newAppId);
        console.log("this.renewalEnhancement",this.renewalEnhancement);
        this.getApplicationIds(this.custId);
        this.workflowFunction(6,0,"Work in Progress","CP_BUSINESS_LEAD");
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}


//Workflow function
cpaRemark:any;
cpaWithOutPopUp(stageId,status,nextApproverInfo){
    if(this.cpaRemark != undefined && this.cpaRemark != null && this.cpaRemark != ''){
        Swal.fire({
          title: 'Are you sure?',
          type: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          confirmButtonText: 'Submit'
        }).then((result) => {
          if (result.value) {
                this.workflowFunction(stageId,status,this.cpaRemark,nextApproverInfo);
          }
        })
    }else{
        this.toaster.error("Kindly fill the Remark");
    }
}

popupWorkflow(stageId,status,nextApproverInfo){
    let title = 'Are you sure to submit?';
    if(status == -1){
        title = 'Are you sure to Return?'
    }else if(status == 2){
        title = 'Are you sure to submit?'
    }else if(status == -2){
        title = 'Are you sure to Reject?'
    }
    Swal.fire({
        title: title,
        text: 'Remark',
        input: 'textarea',
        inputAttributes: {
            autocapitalize: 'off'
        },
        showCancelButton: true,
        confirmButtonText: 'Submit',
        showLoaderOnConfirm: true,
        preConfirm: (login) => {
            if(login==''){
                Swal.showValidationMessage(
                    `Please enter remarks`
                )
            }
        },
        allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
        if (result.value) {
            if(this.deferralStatus && stageId == 21){
                this.workflowFunction(stageId,status,result.value,'CP_DEFERRAL_COMMITTEE_LEAD');
            }else if(stageId == 28){
                this.updateDeferralReports(stageId,status,result.value,nextApproverInfo);
            }else{
                this.workflowFunction(stageId,status,result.value,nextApproverInfo);
            }
        }
    })
}
workflowFunction(stageId,status,remarks,nextApproverInfo){
    this.showLoader = true;
    let response;
    let fileName="wfApprovalStatus/saveWFRenewalFlow/"+false;
    let data={ stageId:stageId,status:status,approverInfo:this.emailId,appId:this.newAppId,remarks:remarks
    ,nextApproverInfo:nextApproverInfo}
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        if(status != 0 && stageId != this.twelveId){
            this.router.navigate(['/dashboard/inbox']);
            setTimeout(() => {
                window.location.reload();
            }, 1000);
        }else if(stageId == this.twelveId){
            if(status != -2){
                this.deferralWorkFlow();
            }else{
                this.router.navigate(['/dashboard/inbox']);
                setTimeout(() => {
                    window.location.reload();
                }, 1000);
            }
        }else{
            this.currentStepperFun();
            this.getPending();
        }
        this.showLoader = false;
    },error=>{
        this.showLoader = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400)
        {
            if(error.error.message == null || error.error.message == ''){
            this.toaster.error('Some Technical Error')
            }else{
                this.toaster.error(error.error.message);
            }
        }
    })
}


//Get Application ID's
getApplicationIds(id):void{
    let response;
    let fileName="anchor/applicationIds/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        for(let item of res.appList){
            this.newAppId = item.newAppId[0].appId;
            this.oldAppId = item.oldAppId[0].appId;
        }
        this.getRenewalEnhancementDocReports();
        this.getCounterPartyById(this.newAppId);
        this.getProposalById(this.newAppId);
        this.getProposedProductDetails(false,this.newAppId);
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}
initialId:any; firstId:any; secondId:any; thirdId:any; fourthId:any; fifthId:any; sixId:any; sevenId:any; eightId:any; nineId:any; tenId:any; elevenId:any; twelveId:any;
anchorName:any;

getPending():void {
    let response;
    let fileName="wfApprovalStatus/getFinalWFStatus/"+localStorage.getItem('email');
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        if(res.status==true){
            for(let i=0;i<res.wfTableDataList.length;i++){
                if(res.wfTableDataList[i].currentStage=='CP0' && res.wfTableDataList[i].appId==this.newAppId){
                    this.initialId=res.wfTableDataList[i].nextStageId;
                }else if(res.wfTableDataList[i].currentStage=='CP1' && res.wfTableDataList[i].appId==this.newAppId){
                    this.firstId=res.wfTableDataList[i].nextStageId;
                }else if(res.wfTableDataList[i].currentStage=='CP2' && res.wfTableDataList[i].appId==this.newAppId){
                    this.secondId=res.wfTableDataList[i].nextStageId;
                }else if(res.wfTableDataList[i].currentStage=='CP3' && res.wfTableDataList[i].appId==this.newAppId){
                    this.thirdId=res.wfTableDataList[i].nextStageId;
                }else if(res.wfTableDataList[i].currentStage=='CP4' && res.wfTableDataList[i].appId==this.newAppId){
                    this.fourthId=res.wfTableDataList[i].nextStageId;
                }else if(res.wfTableDataList[i].currentStage=='CP5' && res.wfTableDataList[i].appId==this.newAppId){
                    this.fifthId=res.wfTableDataList[i].nextStageId;
                }else if(res.wfTableDataList[i].currentStage=='CP6' && res.wfTableDataList[i].appId==this.newAppId){
                    this.sixId=res.wfTableDataList[i].nextStageId;
                }else if(res.wfTableDataList[i].currentStage=='CP7' && res.wfTableDataList[i].appId==this.newAppId){
                    this.sevenId=res.wfTableDataList[i].nextStageId;
                }else if(res.wfTableDataList[i].currentStage=='CP8' && res.wfTableDataList[i].appId==this.newAppId){
                    this.eightId=res.wfTableDataList[i].nextStageId;
                }else if(res.wfTableDataList[i].currentStage=='CP9' && res.wfTableDataList[i].appId==this.newAppId){
                    this.nineId=res.wfTableDataList[i].nextStageId;
                }else if(res.wfTableDataList[i].currentStage=='CP10' && res.wfTableDataList[i].appId==this.newAppId){
                    this.tenId=res.wfTableDataList[i].nextStageId;
                }else if(res.wfTableDataList[i].currentStage=='CP11' && res.wfTableDataList[i].appId==this.newAppId){
                    this.elevenId=res.wfTableDataList[i].nextStageId;
                }else if(res.wfTableDataList[i].currentStage=='CP12' && res.wfTableDataList[i].appId==this.newAppId){
                    this.twelveId=res.wfTableDataList[i].nextStageId;
                }
            }
        }
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

documentMaster:Array<any> = [];

getDocumentMaster(): void{
    this.showLoader = true;
    let response;
    let fileName="dms/documentType";
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.showLoader = false;
        this.documentMaster = res;
    },error=>{
        this.showLoader = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

//RM Names
rmNameArray: Array<any> = [];

getRMNames():void{
    let response;
    let fileName="group/getKeycloakUserByGroupName/RELATIONSHIP_MANAGER";
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.rmNameArray=res.userDetails[0];
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
            }else{
                this.toaster.error(error.error.message);
            }
        }
    })
}

//Remark
// remarkArray:Array<any>=[];
remarkArray :[] = [];
getRemarks():void{
    this.showLoader=true;
    let response;
    let fileName="wfApprovalStatus/getRemarks/"+this.newAppId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.showLoader=false;
        this.remarkArray=res;
        console.log("CPA Remarks Given:",this.remarkArray);

    },error=>{
        this.showLoader=true;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

//Stages Info
viewData: Array<any> = [];
public initialStageStatus = true;

currentStepperFun(){
    let response;
    let fileName="wfApprovalStatus/getHistoryOfWFStatus/"+this.newAppId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.getDocumentMaster();
        this.viewData=res;
        console.log("viewData :",res);
        for(let i=0;i<res.length;i++){
            if(res[i].stage.stageId=='CP0' && res[i].status==2){
                this.initialStageStatus = true;
                this.initialComplete='allow';
            }else if(res[i].stage.stageId=='CP0' && res[i].status==0){
                this.initialStageStatus = true;
                this.initialWip='allow';
                this.wizard.goToStep(0);
                this.getCounterPartyById(this.newAppId);
                this.getDocumentReports();
                this.getLimitEligibilityById(this.newAppId);
                this.getCpTermSheetById('1',this.newAppId);
                this.getCollateralById(this.newAppId);
                this.getDebtProfileById(this.newAppId);
                this.getDueDiligenceById(this.newAppId);
                this.getBeneficiaryById(this.newAppId);
                console.log("goToStep",res[i].stage.stageId)
            }else if(res[i].stage.stageId=='CP0' && res[i].status==null){
                this.initialComplete='';
                this.initialWip='';
            }else if(res[i].stage.stageId != 'CP0' && i==0){
                this.initialStageStatus = false;
            }
            console.log("this.initialStageStatus",this.initialStageStatus)
            if(res[i].stage.stageId=='CP1' && res[i].status==2){
                this.firstComplete='allow';
            }else if(res[i].stage.stageId=='CP1' && res[i].status==0){
                this.firstComplete='';
                this.firstWip='allow';
                console.log("goToStep",res[i].stage.stageId)
                if(this.initialStageStatus){
                    this.wizard.goToStep(0);
                    this.wizard.goToStep(1);
                }else{
                    this.wizard.goToStep(1);
                }
            }else if(res[i].stage.stageId=='CP1' && res[i].status==null){
                this.firstComplete='';
                this.firstWip='';
            }else if(res[i].stage.stageId=='CP2' && res[i].status==2){
                this.secondComplete='allow';
            }else if(res[i].stage.stageId=='CP2' && res[i].status==0){
                this.secondWip='allow';
                this.getCounterPartyById(this.newAppId);
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
            }else if(res[i].stage.stageId=='CP2' && res[i].status==-1){
                this.firstComplete='';
                this.firstWip='allow';
            }else if(res[i].stage.stageId=='CP2' && res[i].status==null){
                this.secondComplete='';
                this.secondWip='';
            }else if(res[i].stage.stageId=='CP3' && res[i].status==2){
                this.thirdComplete='allow';
            }else if(res[i].stage.stageId=='CP3' && res[i].status==0){
                this.thirdComplete='';
                 this.thirdWip='allow';
                 this.getSoftPolicyMaster();
                this.getDocumentReports();
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
            }else if(res[i].stage.stageId=='CP3' && res[i].status==null){
                this.thirdComplete='';
                this.thirdWip='';
            }else if(res[i].stage.stageId=='CP4' && res[i].status==2){
                this.firstComplete='allow';
                this.secondComplete='allow';
                this.thirdComplete='allow';
                this.fourthComplete='allow';

            }else if(res[i].stage.stageId=='CP4' && res[i].status==0){
                this.fourthWip='allow';
                this.getSoftPolicyDetails(this.newAppId);
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
                this.wizard.goToStep(4);
            }else if(res[i].stage.stageId=='CP4' && res[i].status==-1){
                this.fourthComplete='';
                this.fourthWip='allow';

            }else if(res[i].stage.stageId=='CP4' && res[i].status==null){
                this.fourthComplete='';
                this.fourthWip='';
            }else if(res[i].stage.stageId=='CP5' && res[i].status==2){
            this.getSoftPolicyDetails(this.newAppId);
                this.fifthComplete='allow';
                this.getSoftPolicyMaster();
            }else if(res[i].stage.stageId=='CP5' && res[i].status==0){
                this.fifthWip='allow';
                this.getDocumentReports();
                this.getDebtProfileById(this.newAppId);
                this.getSoftPolicyDetails(this.newAppId);
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
                this.wizard.goToStep(4);
                this.wizard.goToStep(5);
            }else if(res[i].stage.stageId=='CP5' && res[i].status==-1){
                this.fourthComplete='';
                this.fourthWip='allow';
            }else if(res[i].stage.stageId=='CP5' && res[i].status==null){
                this.fifthComplete='';
                this.fifthWip='';
            }else if(res[i].stage.stageId=='CP6' && res[i].status==2){
                this.sixComplete='allow';
                this.getCollateralById(this.newAppId);

            }else if(res[i].stage.stageId=='CP6' && res[i].status==0){
                this.sixWip='allow';
                this.getFundRequirementQuestion();
                this.getDocumentReports();
                this.getDebtProfileById(this.newAppId);
                this.getCreditPolicy(this.newAppId);
                this.getProposedProductDetails(false,this.newAppId);
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
                this.wizard.goToStep(4);
                this.wizard.goToStep(5);
                this.wizard.goToStep(6);
            }else if(res[i].stage.stageId=='CP6' && res[i].status==-1){
                this.fifthComplete='';
                this.fifthWip='allow';
            }else if(res[i].stage.stageId=='CP6' && res[i].status==null){
                this.sixComplete='';
                this.sixWip='';
            }else if(res[i].stage.stageId=='CP7' && res[i].status==2){
                this.sevenComplete='allow';
                this.getDueDiligenceById(this.newAppId);
            }else if(res[i].stage.stageId=='CP7' && res[i].status==0){
                this.sevenWip='allow';
                this.getCollateralById(this.newAppId);
                this.getDebtProfileById(this.newAppId);
                this.getDueDiligenceMaster();
                this.getCreditPolicyDetails(this.newAppId);
                this.getLimitEligibilityById(this.newAppId);
                this.getCpTermSheetById('1',this.newAppId);
                this.getFundRequirement();
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
                this.wizard.goToStep(4);
                this.wizard.goToStep(5);
                this.wizard.goToStep(6);
                this.wizard.goToStep(7);
            }else if(res[i].stage.stageId=='CP7' && res[i].status==-1){
                this.sixComplete='';
                this.sixWip='allow';
            }else if(res[i].stage.stageId=='CP7' && res[i].status==null){
                this.sevenComplete='';
                this.sevenWip='';
            }else if(res[i].stage.stageId=='CP8' && res[i].status==2){
                this.eightComplete='allow';
            }else if(res[i].stage.stageId=='CP8' && res[i].status==0){
                this.eightWip='allow';
                this.eightComplete='';
                this.getCollateralById(this.newAppId);
                this.getDebtProfileById(this.newAppId);
                this.getDueDiligenceById(this.newAppId);
                this.getCreditPolicyDetails(this.newAppId);
                this.getLimitEligibilityById(this.newAppId);
                this.getCpTermSheetById('1',this.newAppId);
                this.getFundRequirement();
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
                this.wizard.goToStep(4);
                this.wizard.goToStep(5);
                this.wizard.goToStep(6);
                this.wizard.goToStep(7);
                this.wizard.goToStep(8);
            }else if(res[i].stage.stageId=='CP8' && res[i].status==-1){
                this.sevenComplete='';
                this.sevenWip='allow';
            }else if(res[i].stage.stageId=='CP8' && res[i].status==null){
                this.eightComplete='';
                this.eightWip='';
            }else if(res[i].stage.stageId=='CP9' && res[i].status==2){
                this.nineComplete='allow';
            }else if(res[i].stage.stageId=='CP9' && res[i].status==0){
                this.nineWip='allow';
                this.getCollateralById(this.newAppId);
                this.getDebtProfileById(this.newAppId);
                this.getLimitEligibilityById(this.newAppId);
                this.getCpTermSheetById('1',this.newAppId);
                this.getDueDiligenceById(this.newAppId);
                this.getCreditPolicyDetails(this.newAppId);
                this.getFundRequirement();
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
                this.wizard.goToStep(4);
                this.wizard.goToStep(5);
                this.wizard.goToStep(6);
                this.wizard.goToStep(7);
                this.wizard.goToStep(8);
                this.wizard.goToStep(9);
            }else if(res[i].stage.stageId=='CP9' && res[i].status==-1){
                this.nineComplete='';
                this.nineWip='allow';
            }else if(res[i].stage.stageId=='CP9' && res[i].status==null){
                this.nineComplete='';
                this.nineWip='';
            }else if(res[i].stage.stageId=='CP10' && res[i].status==2){
                this.tenComplete='allow';
            }else if(res[i].stage.stageId=='CP10' && res[i].status==0){
                this.tenWip='allow';
                this.getBeneficiaryById(this.newAppId);
                this.getBankDetails();
                this.getCollateralById(this.newAppId);
                this.getDebtProfileById(this.newAppId);
                this.getDueDiligenceById(this.newAppId);
                this.getLimitEligibilityById(this.newAppId);
                this.getCpTermSheetById('1',this.newAppId);
                this.getFundRequirement();
                this.wizard.goToStep(0);
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
                this.wizard.goToStep(4);
                this.wizard.goToStep(5);
                this.wizard.goToStep(6);
                this.wizard.goToStep(7);
                this.wizard.goToStep(8);
                this.wizard.goToStep(9);
                this.wizard.goToStep(10);
            }else if(res[i].stage.stageId=='CP10' && res[i].status==null){
                this.tenComplete='';
                this.tenWip='';
            }else if(res[i].stage.stageId=='CP11' && res[i].status==2){
                this.elevenComplete='allow';
            }else if(res[i].stage.stageId=='CP11' && res[i].status==0){
                this.elevenWip='allow';
                this.getCollateralById(this.newAppId);
                this.getDebtProfileById(this.newAppId);
                this.getDueDiligenceById(this.newAppId);
                this.getLimitEligibilityById(this.newAppId);
                this.getCpTermSheetById('1',this.newAppId);
                this.getFundRequirement();
                this.getBeneficiaryById(this.newAppId);
                this.getDeferralReport(this.newAppId);
                this.wizard.goToStep(0);
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
                this.wizard.goToStep(4);
                this.wizard.goToStep(5);
                this.wizard.goToStep(6);
                this.wizard.goToStep(7);
                this.wizard.goToStep(8);
                this.wizard.goToStep(9);
                this.wizard.goToStep(10);
                this.wizard.goToStep(11);
            }else if(res[i].stage.stageId=='CP11' && res[i].status==-1){
                this.tenComplete='';
                this.tenWip='allow';
            }else if(res[i].stage.stageId=='CP11' && res[i].status==null){
                this.elevenComplete='';
                this.elevenWip='';
            }else if(res[i].stage.stageId=='CP12' && res[i].status==2){
                this.twelveComplete='allow';
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
                this.wizard.goToStep(4);
                this.wizard.goToStep(5);
                this.wizard.goToStep(6);
                this.wizard.goToStep(7);
                this.wizard.goToStep(8);
                this.wizard.goToStep(9);
                this.wizard.goToStep(10);
                this.wizard.goToStep(11);
                this.wizard.goToStep(12);
            }else if(res[i].stage.stageId=='CP12' && res[i].status==0){
                this.twelveWip='allow';
                this.getDebtProfileById(this.newAppId);
                this.getCollateralById(this.newAppId);
                this.getDueDiligenceById(this.newAppId);
                this.getBeneficiaryById(this.newAppId);
                this.getLimitEligibilityById(this.newAppId);
                this.getCpTermSheetById('1',this.newAppId);
                this.getFundRequirement();
                this.getDocDeferralReport();
                this.getOtherDocDeferralReport();
                this.wizard.goToStep(0);
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
                this.wizard.goToStep(4);
                this.wizard.goToStep(5);
                this.wizard.goToStep(6);
                this.wizard.goToStep(7);
                this.wizard.goToStep(8);
                this.wizard.goToStep(9);
                this.wizard.goToStep(10);
                this.wizard.goToStep(11);
                this.wizard.goToStep(12);
            }else if(res[i].stage.stageId=='CP12' && res[i].status==-1){
                this.elevenComplete='';
                this.elevenWip='allow';
            }else if(res[i].stage.stageId=='CP12' && res[i].status==null){
                this.twelveComplete='';
                this.twelveWip='';
            }
        }
        this.roleBasedFun();
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

//Role Based Access Control
public commercialCC = false;
public creditCC = false;
rbacArray: Array<any> = [];

roleBasedFun(): void{
    let roles=localStorage.getItem('roles');
    let response;
    let fileName="permission/permission/";
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        for(let i=0;i<res.length;i++){
            if(res[i].stageId.clientType==2){
                this.rbacArray.push(res[i]);
            }
        }
        for(let j=0;j<this.rbacArray.length;j++){
            if(this.rbacArray[j].stageId.stageId=='CP0' && this.initialWip=='allow'){
                if(this.rbacArray[j].role=='CPA' ){
                    this.cp0Submit=true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP0' && this.initialComplete=='allow'){
                if(this.rbacArray[j].role=='CPA' ){
                    this.cp0View= true;
                    this.cp0Submit=false;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP1' && this.firstWip=='allow'){
                if(this.rbacArray[j].role=='BUSINESS' ){
                    this.cp1Submit=true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP1' && this.firstComplete=='allow'){
                if(this.rbacArray[j].role=='BUSINESS' ){
                    this.cp1View= true;
                    this.cp1Submit=false;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP2' && this.secondWip=='allow'){
                if(this.rbacArray[j].role=='CREDIT HEAD' || this.rbacArray[j].role=='RISK HEAD'){
                    this.cp2Submit = true;
                    this.cp2Return = true;
                    this.cp2Reject = true;
                    this.cp1View = true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP2' && this.secondComplete=='allow'){
                if(this.rbacArray[j].role=='CREDIT HEAD' || this.rbacArray[j].role=='RISK HEAD'){
                    this.cp2View= true;
                    this.cp2Submit=false;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP3' && this.thirdWip=='allow'){
                if(this.rbacArray[j].role=='CPA'){
                    this.cp3Submit=true;
                    this.cp1View= true;
                    this.cp2View= true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP3' && this.thirdComplete=='allow'){
                if(this.rbacArray[j].role=='CPA'){
                    this.cp2View=true;
                    this.cp3View= true;
                    this.cp3Submit=false;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP4' && this.fourthWip=='allow'){
                if(this.rbacArray[j].role=='CREDIT UNDERWRITER' || this.rbacArray[j].role=='RISK UNDERWRITER'){
                    this.cp4Submit = true;
                    this.cp4Reject = true;
                    this.cp4Return = true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP4' && this.fourthComplete=='allow'){
                if(this.rbacArray[j].role=='CREDIT UNDERWRITER' || this.rbacArray[j].role=='RISK UNDERWRITER'){
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp4Submit=false;
                    this.cp4Reject=false;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP5' && this.fifthWip=='allow'){
                if(this.rbacArray[j].role=='CPA'){
                    this.cp5Submit = true;
                    this.cp5Return = true;
                    this.cp2View = true;
                    this.cp3View = true;
                    this.cp4View = true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP5' && this.fifthComplete=='allow'){
                if(this.rbacArray[j].role=='CPA' || this.rbacArray[j].role=='CREDIT COMMITTEE'){
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp5View= true;
                    this.cp5Submit=false;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP6' && this.sixWip=='allow'){
                if(this.rbacArray[j].role=='CPA' || this.rbacArray[j].role=='CREDIT UNDERWRITER' || this.rbacArray[j].role=='RISK UNDERWRITER'){
                    this.cp6Submit=true;
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp5View= true;
                }
                if(this.rbacArray[j].role=='CREDIT UNDERWRITER' || this.rbacArray[j].role=='RISK UNDERWRITER'){
                    this.cp6Return = true;
                    this.cp6Reject = true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP6' && this.sixComplete=='allow'){
                if(this.rbacArray[j].role=='CPA' || this.rbacArray[j].role=='CREDIT UNDERWRITER' || this.rbacArray[j].role=='RISK UNDERWRITER'){
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp5View= true;
                    this.cp6View= true;
                    this.cp6Submit=false;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP7' && this.sevenWip=='allow'){
                if(this.rbacArray[j].role=='CPA' || this.rbacArray[j].role=='CREDIT UNDERWRITER' || this.rbacArray[j].role=='RISK UNDERWRITER'){
                    this.cp7Submit=true;
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp5View= true;
                    this.cp6View= true;
                }
                if(this.rbacArray[j].role=='CREDIT UNDERWRITER' || this.rbacArray[j].role=='RISK UNDERWRITER'){
                    this.cp7Return = true;
                    this.cp7Reject = true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP7' && this.sevenComplete=='allow'){
                if(this.rbacArray[j].role=='CPA' || this.rbacArray[j].role=='CREDIT UNDERWRITER' || this.rbacArray[j].role=='RISK UNDERWRITER'){
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp5View= true;
                    this.cp6View= true;
                    this.cp7View= true;
                    this.cp7Submit=false;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP8' && this.eightWip=='allow'){
                if(this.rbacArray[j].role=='CREDIT APPROVAL COMMITTEE'){
                    this.cp8Submit = true;
                    this.cp8Return = true;
                    this.cp8Reject = true;

                    this.creditCC = true;

                    this.cp2View = true;
                    this.cp3View = true;
                    this.cp4View = true;
                    this.cp5View = true;
                    this.cp6View = true;
                    this.cp7View = true;
                }
                if(this.rbacArray[j].role=='COMMERCIAL APPROVAL COMMITTEE'){
                    this.cp8Submit = true;
                    this.cp8Return = true;
                    this.cp8Reject = true;

                    this.commercialCC = true;

                    this.cp2View = true;
                    this.cp3View = true;
                    this.cp4View = true;
                    this.cp5View = true;
                    this.cp6View = true;
                    this.cp7View = true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP8' && this.eightComplete=='allow'){
                if(this.rbacArray[j].role=='CREDIT APPROVAL COMMITTEE' || this.rbacArray[j].role=='COMMERCIAL APPROVAL COMMITTEE'){
                    this.cp2View = true;
                    this.cp3View = true;
                    this.cp4View = true;
                    this.cp5View = true;
                    this.cp6View = true;
                    this.cp7View = true;
                    this.cp8View = true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP9' && this.nineWip=='allow'){
                if(this.rbacArray[j].role=='BUSINESS'){
                    this.cp9Reject=false;
                    this.cp9Return=true;
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp5View= true;
                    this.cp6View= true;
                    this.cp7View= true;
                    this.cp8View= true;
                    this.cp9Submit=true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP9' && this.nineComplete=='allow'){
                this.cp2View=true;
                this.cp3View=true;
                this.cp4View= true;
                this.cp5View= true;
                this.cp6View= true;
                this.cp7View= true;
                this.cp8View= true;
                this.cp9View=true;
            }
            if(this.rbacArray[j].stageId.stageId=='CP10' && this.tenWip=='allow'){
                if(this.rbacArray[j].role=='OPERATION MAKER'){
                    this.cp10Submit=true;
                    this.cp10Return = true;
                    this.cp10Reject= true;
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp5View= true;
                    this.cp6View= true;
                    this.cp7View= true;
                    this.cp8View= true;
                    this.cp9View=true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP10' && this.tenComplete=='allow'){
                if(this.rbacArray[j].role=='OPERATION MAKER'){
                    this.cp10Submit=false;
                    this.cp10Return=false;
                    this.cp10Reject=false;
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp5View= true;
                    this.cp6View= true;
                    this.cp7View= true;
                    this.cp8View= true;
                    this.cp9View=true;
                    this.cp10View=true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP11' && this.elevenWip=='allow'){
                if(this.rbacArray[j].role=='OPERATION CHECKER'){
                    this.cp11Submit=true;
                    this.cp11Return =true;
                    this.cp11Reject=true;
                    this.cp11Approve=true;
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp5View= true;
                    this.cp6View= true;
                    this.cp7View= true;
                    this.cp8View= true;
                    this.cp9View=true;
                    this.cp10View=true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP11' && this.elevenComplete=='allow'){
                if(this.rbacArray[j].role=='OPERATION CHECKER'){
                    this.cp11Submit=false;
                    this.cp11Return=false;
                    this.cp11Reject=false;
                    this.cp11View=true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP12' && this.twelveWip=='allow'){
                if(this.rbacArray[j].role=='DEFERRAL COMMITTEE'){
                    this.cp12Submit=true;
                    this.cp12Return =true;
                    this.cp12Reject=true;
                    this.cp12Approve=true;
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp5View= true;
                    this.cp6View= true;
                    this.cp7View= true;
                    this.cp8View= true;
                    this.cp9View=true;
                    this.cp10View=true;
                    this.cp11View=true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP12' && this.twelveComplete=='allow'){
                if(this.rbacArray[j].role=='DEFERRAL COMMITTEE'){
                    this.cp12Submit=false;
                    this.cp12Return=false;
                    this.cp12Reject=false;
                    this.cp12View=true;
                }
            }
        }
    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}


//CP Basic Details
cpPkId:any; panNumber:any; companyName:any; gstNumber:any; cinNumber:any; constitution:any; cpCIty:any; cpState:any; source:any; subSource:any; rmName:any; docRMName:any;activity:any; totalInwardCheques:any; assessmentType:any; anchorRelationShip:any;
cinNumber2:any;panNumber2:any;customerName1:any;cpPan:any;cpCin:any;gstNumber1:any;custFacilityTenure:any;contactName:any;contactMobile:any;contactEmail:any;cpId:any;cusId:any;cpCustId:any;anchorId:any;
custInfoId:any;
// anchorProduct:any;
// public flagDealers = false;
// public flagVendor = false;

counterPartyList: Array<any> = [];
proposalList:Array<any>=[];
debtProfileList:Array<any>=[];
// limitEligibilityList:Array<any>=[];
termSheetList:Array<any>=[];


//KeyPress validation
allowAlphabetWithSpace(e){
    let k;
    document.all ? k = e.keyCode : k = e.which;
    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 32);
}
allowNumbersWithDot(e){
    let k;
    document.all ? k = e.keyCode : k = e.which;
    return ((k >= 48 && k <= 57) || k == 46)
}

allowNumbers(e){
    let k;
    document.all ? k = e.keyCode : k = e.which;
    return ((k >= 48 && k <= 57))
}

allowAlphabetNumbersWithSpace(e){
    let k;
    document.all ? k = e.keyCode : k = e.which;
    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32 || (k >= 48 && k <= 57));
}

public cPBasicFlag1 = true;
public cPBasicFlag2 = false;

getCounterPartyById(id):void {
    let response;
    let fileName="counterParty/cpBasicDetails/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        if(res.length != 0){
            this.counterPartyList=res;

            if(id == this.newAppId){
                this.cPBasicFlag1 = false;
                this.cPBasicFlag2 = true;
            }
            for(var i=0;i< res.length;i++){
                this.cpPkId = res[i].id;
                this.panNumber = res[i].panNumber;
                this.companyName = res[i].companyName;
                this.gstNumber = res[i].gstNumber;
                if(this.gstNumber==""){
                    this.gstNumber=null;
                }
                this.cinNumber = res[i].cinNumber;
                this.constitution = res[i].constitution;
                this.cpCIty = res[i].city;
                this.cpState = res[i].state;
                this.source = res[i].source;
                this.subSource = res[i].subSource;
                this.rmName = res[i].rmName;
                if(this.elevenWip !='allow'){
                    this.docRMName = res[i].rmName;
                }
                this.contactName = res[i].cusContName;
                this.contactMobile = res[i].cusContactNumber;
                this.contactEmail = res[i].cusContactEmail;
                this.activity = res[i].activity;
                this.totalInwardCheques = res[i].totalInwardCheques;
                this.custFacilityTenure = res[i].applicationEntity.appType;
                this.custInfoId = res[i].applicationEntity.customerInfoEntity.id
                console.log(" Masterrrrr Type ", this.custFacilityTenure );
                if(this.renewalEnhancement==2){
                this.cpType = "Renewal"
                }else{
                this.cpType = "Enhancement"}
                if(res[i].assessmentType != null){
                    this.assessmentType = res[i].assessmentType;
                    console.log("this.assessmentTypeIF",this.assessmentType);
                }else{
                    console.log("this.assessmentTypeELSE",this.assessmentType);
                }
                this.anchorRelationShip= res[i].anchorRelationShip;
            }
        }else{
            this.getCounterPartyById(this.oldAppId);
        }
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

//CP Basic Save and Updated
//  public cinMand = false;
//  public constitutionVal:any;
    constitutionVal:any;
    cinMand=false;
//     public deDupeEnable = false;
    flag:any;
         checkContitution(event):void{
//                 this.deDupeEnable = true;
//                 this.panNumber2 = "";
//                 this.cinNumber = "";
                this.flag=0;
                this.constitutionVal=event.target.value;
                console.log(this.constitutionVal,'this.constitutionVal');
                if (this.constitutionVal =='Public Company Listed' || this.constitutionVal == 'Private Company' || this.constitutionVal == 'Public Company Unlisted'){
                    this.cinMand=true;
                }
                else{
                    this.cinMand=false;
                }
            }
 public cpBasicDetailsSubmit(key) {

        this.cpBasicValidate = false;
        this.cpBasicValidate = !this.cpBasicValidate;

        const cusname =/^[a-zA-Z ]*$/;
        const cusPan =/([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}$/;
        const custCin = /([L|U]{1})([0-9]{5})([A-Za-z]{2})([0-9]{4})([A-Za-z]{3})([0-9]{6})$/;

        const gst = /([0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1})$/;
        const email =/^([a-zA-Z0-9]+[a-zA-Z0-9_.+-]+)@([a-zA-Z0-9_.+-]+\.(com|co.in|in)$)/;

        var flag = true;

        if (this.constitution =='Proprietorship' || this.constitution == 'Registered Partnership' || this.constitution == 'Unregistered Partnership' || this.constitution == 'Registered LLP' || this.constitution == 'Unregistered LLP'){
                this.cinMand=false;
            }
                 if (this.cinMand)
                 {
                        if(!custCin.test(this.cinNumber)){
                        this.toaster.error('Please Enter Valid CIN Number')
                        this.cpBasicValidate = false;
                        this.cPBasicFlag1 = true;
                        flag = false;
                        }
                        if(this.cinNumber == "" || this.cinNumber == null){
                            this.cpBasicValidate = false;
                            flag = false;
                        }
                }

                else if (!this.cinMand)
                {
                  if(this.cinNumber == null || this.cinNumber == "")
                            {
                             flag = true;
                            }
                    else
                       {
                          if(!custCin.test(this.cinNumber))
                           {
                             this.toaster.error('Please Enter valid CIN Number');
                             this.cpBasicValidate = false;
//                              this.cPBasicFlag1 = true;
//                              flag = false;
                           }
                       }
                }
         if(!cusname.test(this.constitution)){
           this.cPBasicFlag1 = true;
           flag = false;
         }
         if(!cusname.test(this.cpCIty)){
         this.cPBasicFlag1 = true;
           flag = false;
         }
         if(!cusname.test(this.cpState)){
         this.cPBasicFlag1 = true;
           flag = false;
         }
         if(!cusname.test(this.source)){
         this.cPBasicFlag1 = true;
           flag = false;
         }
         if(!cusname.test(this.subSource)){
         this.cPBasicFlag1 = true;
           flag = false;
         }
         if(!cusname.test(this.contactName)){
         this.cPBasicFlag1 = true;
           flag = false;
         }
        if(this.companyName=="" || this.companyName==null){
        this.toaster.error('Please Enter Valid Company Name');
        this.cPBasicFlag1 = true;
            flag=false;
        }
        if(this.gstNumber == null || this.gstNumber == "")
                {
                   flag = true;
                }
        else
           {
              if(!gst.test(this.gstNumber))
               {
                 this.toaster.error('Please Enter valid GST Number');
                 this.cPBasicFlag1 = true;
                 flag = false;
               }
           }

        if(this.constitution=="" || this.constitution==null){
        this.toaster.error('Please Enter Valid Constitution');
        this.cPBasicFlag1 = true;
            flag=false;
        }
        if(this.cpCIty=="" || this.cpCIty==null){
        this.toaster.error('Please Enter Valid Cp City');
        this.cPBasicFlag1 = true;
            flag=false;
        }
        if(this.cpState=="" || this.cpState==null){
        this.toaster.error('Please Enter Valid State');
        this.cPBasicFlag1 = true;
            flag=false;
        }
        if(this.source=="" || this.source==null){
        this.toaster.error('Please Enter Valid Source');
        this.cPBasicFlag1 = true;
            flag=false;
            this.toaster.error("Please Select Source");
        }
        if(this.subSource=="" || this.subSource==null){
        this.toaster.error('Please Enter Valid Sub Source');
        this.cPBasicFlag1 = true;
            flag=false;
        }
        if(this.rmName=="" || this.rmName==null){
        this.toaster.error('Please Enter Valid RM Name');
        this.cPBasicFlag1 = true;
            flag=false;
        }
        if(this.contactName=="" || this.contactName==null){
        this.toaster.error('Please Enter Valid Name');
        this.cPBasicFlag1 = true;
            flag=false;
        }
        if(this.contactMobile=="" || this.contactMobile==null){
        this.cPBasicFlag1 = true;
        this.toaster.error('Please Enter Valid Mobile Number');
            flag=false;
        }
        if(this.contactEmail=="" || this.contactEmail==null){
        this.cPBasicFlag1 = true;
        this.toaster.error('Please Enter Valid Email Id');
            flag=false;
        }
        if(!email.test(this.contactEmail)){
                this.cPBasicFlag1 = true;
                this.toaster.error('Please Enter Valid Email Id');
                flag = false;
                }
        if(this.cpBasicValidate==true && flag == true )
        {
          if(key == 'save'){
             this.saveCpBasic();
            }else if(key == 'update'){
               this.updateCPBasicDetails(true);
            }

        }
    }



saveCpBasic(): void {
    this.showLoader=true;
    let response;
    let fileName="counterParty/cpRenewalBasicDetails/";
    let data={ appId : this.newAppId,custId : this.custId,panNumber:this.panNumber,companyName:this.companyName,gstNumber:this.gstNumber,cinNumber:this.cinNumber,constitution:this.constitution,facilityTenure:this.custFacilityTenure,
        city:this.cpCIty,state:this.cpState,source:this.source,subSource:this.subSource,rmName:this.rmName,cusContName:this.contactName,assessmentType:this.assessmentType,
        cusContactNumber:this.contactMobile,cusContactEmail:this.contactEmail,createdBy:"",updatedBy:"",activity:this.activity,counterPartyType:this.cpType,
    }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader=false;
        this.cPBasicFlag1 = false;
        this.cPBasicFlag2 = true;
        this.toaster.success('Successfully Saved');
        this.getCounterPartyById(this.newAppId);
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error');
                this.cPBasicFlag1 = true;
                this.cPBasicFlag2 = false;
            }else{
                this.toaster.error(error.error.message);
                this.cPBasicFlag1 = true;
                this.cPBasicFlag2 = false;
            }
        }
    })
}


updateCPBasicDetails(key): void {
    this.showLoader=true;
    let response;
    let fileName="counterParty/cpBasicDetails";
    let data={id:this.cpPkId, appId:this.newAppId, custId:this.custId, panNumber:this.panNumber,companyName:this.companyName,gstNumber:this.gstNumber,cinNumber:this.cinNumber,constitution:this.constitution,
    city:this.cpCIty,state:this.cpState,source:this.source,subSource:this.subSource,rmName:this.rmName,cusContName:this.contactName,
    cusContactNumber:this.contactMobile,cusContactEmail:this.contactEmail,createdBy:"",updatedBy:"",activity:this.activity,assessmentType:this.assessmentType,facilityTenure:this.custFacilityTenure,counterPartyType:this.cpType
    }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader=false;
        this.getCounterPartyById(this.newAppId);
        if(key){
            this.toaster.success('Successfully Updated');
        }
        this.cPBasicFlag1 = false;
        this.cPBasicFlag2 = true;
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);

        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
                this.cPBasicFlag1 = true;
                this.cPBasicFlag2 = false;
            }else{
                this.toaster.error(error.error.message);
                this.cPBasicFlag1 = true;
                this.cPBasicFlag2 = false;
            }
        }
    })
}

getProposalById(id): void {
    let response;
    let fileName="counterParty/proposedProductsById/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        if(res.length != 0){
            this.proposalList=res;
            this.gotoRiskOrCredit();
        }else{
            this.getProposalById(this.oldAppId);
        }
    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

gotoRiskOrCredit(): void{
    this.showLoader=true;
    let response;
    let fileName="counterParty/gotoRiskOrCredit/"+this.newAppId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.showLoader=false;
        console.log("Res-->",res);
        for(let item of res.riskOrCredit){
            this.goToRisk = item.goto;
            console.log("item.goto-->",item.goto);
        }
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

//     getLimitEligibilityById(id):void {
//
//         let response;
//         let fileName="counterParty/limitEligibilityById/"+id;
//         response = this.requestapi.getData(fileName);
//         response.subscribe((res: any) => {
//         if(res.length != 0){
//             this.limitEligibilityList=res;
//             }else{
//             this.getLimitEligibilityById(this.oldAppId);
//             }
//         },error=>{
//
//             if(error.status==401){
//                 this.refresh_token=localStorage.getItem('refresh_token')
//                 this.authService.SignOut(this.refresh_token);
//             }
//
//         })
//     }
//
//     getTermSheetById(uploadId):void {
//
//         let response;
//         let fileName="counterParty/termSheetGet/"+uploadId;
//         response = this.requestapi.getData(fileName);
//         response.subscribe((res: any) => {
//         if(res.length != 0){
//         this.termSheetList=res;
//         }else{
//         this.getTermSheetById(this.oldAppId);
//         }
//
//         },error=>{
//
//             if(error.status==401){
//                 this.refresh_token=localStorage.getItem('refresh_token')
//                 this.authService.SignOut(this.refresh_token);
//             }
//
//         })
//     }

//Business Stage
businessSubmit(){
    if(this.goToRisk){
        this.popupWorkflow(6,2,'CP_RISK_HEAD_LEAD');
    }else{
        this.popupWorkflow(6,2,'CP_CREDIT_HEAD_LEAD');
    }
}
//Credit or Risk Head
assignTo:any; assignUwPkId:any;
public assignUwFlag1 = true;
public assignUwFlag2 = false;
UnderWriterList: Array<any> = [];
getUnderWriterList(): void {
    this.showLoader=true;
    let response;
    let fileName="group/getCreditUnderWriterUsers";
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.showLoader=false;
        console.log("resresresres",res);
        console.log("userDetails",res.userDetails);
        for(let i=0;i<res.userDetails.length;i++){
            for(let j=0;j<res.userDetails[i].length;j++){
                this.UnderWriterList.push(res.userDetails[i][j]);
            }
        }
        console.log("this.UnderWriterList",this.UnderWriterList);
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

saveAssignUnderwriter():void {
    this.showLoader=true;
    let response;
    let fileName = "counterParty/assignUnderwriter";
    let data={ appId:this.newAppId,assignTo:this.assignTo,createdBy:"",updatedBy:""}
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader=false;
        this.assignUwFlag1 = false;
        this.assignUwFlag2 = true;
        this.toaster.success('Successfully Saved');
        this.getAssignUnderwriter();
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == " "){
                this.assignUwFlag1  = true;
                this.toaster.error('Some Technical Error')
            }else{
                this.toaster.error(error.error.message)
                this.assignUwFlag1  = true;
            }
        }
        this.assignUwFlag1  = true;
    })
}


updateAssignUnderwriter(): void {
    this.showLoader=true;
    let response;
    let fileName="counterParty/assignUnderwriter/";
    let data={id:this.assignUwPkId, appId:this.newAppId,assignTo:this.assignTo,createdBy:"",updatedBy:"" }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader=false;
        this.assignUwFlag1 = false;
        this.assignUwFlag2 = true;
        this.toaster.success('Successfully Updated');
        this.getAssignUnderwriter();
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error');
                this.assignUwFlag1 = true;
            }else{
                this.toaster.error(error.error.message);
                this.assignUwFlag1 = true;
            }
        }
    })
}

getAssignUnderwriter(): void{
    let response;
    let fileName="counterParty/assignUnderwriter/"+this.newAppId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        console.log("Response",res);
        if(res != null){
            this.assignUwPkId = res.id;
            this.assignTo =res.assignTo;
            this.assignUwFlag1 = false;
            this.assignUwFlag2 = true;
        }else{
            this.assignUwFlag1 = true;
            this.assignUwFlag2 = false;
        }
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

assignUwSubmit(stageId,status){
    if(this.goToRisk){
        this.popupWorkflow(stageId,status,'CP_RISK_HEAD_LEAD');
    }else{
        this.popupWorkflow(stageId,status,'CP_CREDIT_HEAD_LEAD');
    }
}

//Credit CPA
creditCpaSubmit(){
    if(this.goToRisk){
        this.popupWorkflow(9,2,'CP_RISK_UNDERWRITER_LEAD');
    }else{
        this.popupWorkflow(9,2,'CP_CREDIT_UNDERWRITER_LEAD');
    }
}

// CAM Upload
camUploadSubmit(){
    if(this.goToRisk){
        this.popupWorkflow(24,2,'CP_RISK_UNDERWRITER_LEAD');
    }else{
        this.popupWorkflow(24,2,'CP_CREDIT_UNDERWRITER_LEAD');
    }
}

//Under writer Review
underWriterReviewSubmit(){
    if(this.goToRisk){
        this.popupWorkflow(22,2,'CP_RISK_UNDERWRITER_LEAD');
    }else{
        this.popupWorkflow(22,2,'CP_CREDIT_UNDERWRITER_LEAD');
    }
}

//Collateral
public collateralValidate = false;
public cpCollateral1 = true;
public cpCollateral2 = false;

collateralMaster: Array<any> = [];
collateralArray: Array<any> = [];
collateralValueList: Array<any> = [];

getCollateralMaster(): void{
    this.creditNormDetails = [];
    let response;
    let fileName="counterParty/collateralMaster";
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.collateralMaster = res;
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

getCollateralById(id):void {
    let response;
    let fileName="counterParty/collateral/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        if(res.length != 0){
            if(this.newAppId == id){
                this.cpCollateral1 = false;
                this.cpCollateral2 = true;
            }
            this.collateralValueList=res;
        }else{
            this.getCollateralById(this.oldAppId);
        }
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

storeCollateral(id,event):void{
    let value = event.target.value;
    let enableCollateral = 1;
    if(this.collateralArray.length != 0){
        for(let items of this.collateralArray){
            if(items.cmId == id){
                let index = this.collateralArray.indexOf(items);
                if(this.cpCollateral2){
                    var pId = items.id;
                    const a1 = { id: pId, appId: this.newAppId ,cmId : id,value : value,}
                    this.collateralArray[index] = a1;
                }else{
                    const a1 = { appId: this.newAppId ,cmId : id,value : value,}
                    this.collateralArray[index] = a1;
                }
               enableCollateral=1;
                break;
            }else{
                enableCollateral=0;
            }
        }
    }else{
        const a1 = { appId: this.newAppId ,cmId : id,value : value,}
        this.collateralArray.push(a1);
    }
    if(enableCollateral==0){
        const a1 = { appId: this.newAppId ,cmId : id,value : value,}
        this.collateralArray.push(a1);
    }
}

public collateralSubmit(key) {
    this.collateralValidate = false;
    this.collateralValidate = !this.collateralValidate;
    var collateralFlag = true;
    if(this.collateralArray.length == this.collateralMaster.length){
        collateralFlag=true;
    }else{
        collateralFlag=false;
    }
    if(this.collateralValidate==true && collateralFlag == true ){
        if(key == 'save'){
            this.saveCollateral();
        }else if(key == 'update'){
            this.updateCollateral();
        }
    }
}

saveCollateral(): void {
   this.showLoader=true;
    let response;
    let fileName="counterParty/collateralDetails";
    let data={ collateralDetailsDataList : this.collateralArray }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader=false;
        this.cpCollateral1 = false;
        this.cpCollateral2 = true;
        this.toaster.success('Successfully Saved');
        this.getCollateralDetails(this.newAppId);
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
            this.toaster.error('Some Technical Error')
            this.cpCollateral1 = true;
            }else{
            this.toaster.error(error.error.message);
            this.cpCollateral1 = true;
            }
        }
    })
}

updateCollateral(): void {
     this.showLoader=true;
      let response;
      let fileName="counterParty/collateralDetails";
      let data={ collateralDetailsDataList : this.collateralArray }
      response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
      response.subscribe((res: any) => {
      this.showLoader=false;
     this.toaster.success('Successfully Updated');
      this.getCollateralDetails(this.newAppId);
      },error=>{
      this.showLoader=false;
      if(error.status==401){
             this.refresh_token=localStorage.getItem('refresh_token')
             this.authService.SignOut(this.refresh_token);
       }
       if(error.status==400)
            {
                if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
                }else{
                this.toaster.error(error.error.message);
                }
            }
      })
    }

getCollateralDetails(id): void{
    this.showLoader=true;
    let response;
    let fileName="counterParty/collateral/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.showLoader=false;
        this.collateralValueList = res;
        if(res.length != 0){
            if(this.newAppId == id){
                this.cpCollateral1 = false;
                this.cpCollateral2 = true;
            }
            this.collateralArray = [];
            for(let item of this.collateralMaster){
                for(var i=0; i<res.length; i++){
                    if(item.id == res[i].collateralMasterEntity.id){
                        (<HTMLInputElement>document.getElementById(item.name)).value = res[i].value;
                        const a1 = { id:res[i].id, appId: this.newAppId ,cmId : res[i].collateralMasterEntity.id,value : res[i].value }
                        this.collateralArray.push(a1);
                    }
                }
            }
        }else{
            this.getCollateralDetails(this.oldAppId);
        }
    },error=>{
    this.showLoader=false;
    if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
    }

    })
}

//Fund Requirement Details

getFundRequirementQuestion(): void{
    let response;
    let fileName="counterParty/fundReqMaster";
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.fundQuestion = res;
    },error=>{
    if(error.status==401){
                   this.refresh_token=localStorage.getItem('refresh_token')
                           this.authService.SignOut(this.refresh_token);
                       }

    })
}

getFundRequirement(): void{
    let response;
    let fileName="counterParty/fundReqDetails/"+this.newAppId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.fundReqDetails = res;
    },error=>{
    if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
                       }

    })
}

// getUpdateFundRequirement(id): void{
//     this.showLoader=true;
//         let response;
//         let fileName="counterParty/fundReqDetails/"+id;
//         response = this.requestapi.getData(fileName);
//         response.subscribe((res:any) => {
//             this.showLoader=false;
//             if(res.length !=0){
//                 this.saveFundReqFlag=true;
//                 this.fundReqArray = [];
//                 for(let item of this.fundQuestion){
//                     for(var i=0; i<res.length; i++){
//                         if(item.id == res[i].fundRequirementMasterEntity.id){
//                             (<HTMLInputElement>document.getElementById(item.id)).value = res[i].value;
//                             const b = { id:res[i].id, appId: this.newAppId ,cpFrmId : res[i].fundRequirementMasterEntity.id,value : res[i].value}
//                             this.fundReqArray.push(b);
//                         }
//                     }
//                 }
//             }else{
//                 if(id == this.newAppId){
//                     this.getUpdateFundRequirement(this.oldAppId);
//                 }
//             }
//         },error=>{
//             this.showLoader=false;
//         if(error.status==401){
//             this.refresh_token=localStorage.getItem('refresh_token')
//                     this.authService.SignOut(this.refresh_token);
//                            }
//
//         })
//     }

getUpdateFundRequirement(id): void{
    this.showLoader=true;
        let response;
        let fileName="counterParty/fundReqDetails/"+id;
        response = this.requestapi.getData(fileName);
        response.subscribe((res:any) => {
            this.showLoader=false;
            if(res.length !=0){
//                 this.saveFundReqFlag=true;
                this.fundReqArray = [];
                for(let item of this.fundQuestion){
                    for(var i=0; i<res.length; i++){
                        if(item.id == res[i].fundRequirementMasterEntity.id){
                            (<HTMLInputElement>document.getElementById(item.id)).value = res[i].value;
                            const b = { id:res[i].id, appId: this.newAppId ,cpFrmId : res[i].fundRequirementMasterEntity.id,value : res[i].value}
                            this.fundReqArray.push(b);
                        }
                    }
                }
            }else{
                if(id == this.newAppId){
                    this.getUpdateFundRequirement(this.oldAppId);
                }
            }
            if(id ==this.oldAppId){
            this.saveFundReqFlag=false;
            this.fundRequirementControl = false;
            }else{
            this.saveFundReqFlag=true;
            this.fundRequirementControl = false;
            }
        },error=>{
            this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                           }

        })
    }

    storeAnswer(item: any,id):void{
        if(Event.prototype.isPrototypeOf(item)){
            this.fundReqValue=item.target.value;
        }else{
            this.fundReqValue=item.toString();
        }
        this.fundReqId=id;
        this.enableFundReq=1;
        if(this.fundReqArray.length != 0){
            for(let itemId of this.fundReqArray){
                if(itemId.cpFrmId == this.fundReqId){
                    let index = this.fundReqArray.indexOf(itemId);
                    if(this.saveFundReqFlag){
                        var pId = itemId.id;
                        const b = { id:pId, appId: this.newAppId ,cpFrmId : this.fundReqId,value : this.fundReqValue}
                        this.fundReqArray[index] = b;
                    }else{
                        const b = { appId: this.newAppId ,cpFrmId : this.fundReqId,value : this.fundReqValue,}
                        this.fundReqArray[index] = b;
                    }
                    this.enableFundReq=1;
                    break;
                }else{
                    this.enableFundReq=0;
                }
            }
        }else{
            const b = { appId: this.newAppId ,cpFrmId : this.fundReqId,value : this.fundReqValue,}
            this.fundReqArray.push(b);
        }
        if(this.enableFundReq==0){
            const b = { appId: this.newAppId ,cpFrmId : this.fundReqId,value : this.fundReqValue,}
            this.fundReqArray.push(b);
        }
    }


public fundRequirementSubmit(key) {
        this.fundRequirementControl = true;
        var fundRequirementFlag = true;
        if(this.fundReqArray.length == this.fundQuestion.length){
            fundRequirementFlag=true;
        }else{
            fundRequirementFlag=false;
            this.fundRequirementControl = false;
        }
        if(fundRequirementFlag == true ){
            if(key == 'save'){
                this.saveFundRequirement();
            }else if(key == 'update'){
                this.updateFundRequirement();
            }
        }else{
            this.fundRequirementControl = false;
            this.toaster.error('Kindly fill all field');
        }
    }

    saveFundRequirement(): void {
       this.showLoader=true;
        let response;
        let fileName="counterParty/fundReqDetails";
        let data={ fundReqDetailsDataList : this.fundReqArray}
        response = this.requestapi.postData(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
            this.saveFundReqFlag=true;
            this.fundRequirementControl = false;
            this.getUpdateFundRequirement(this.newAppId);
            this.toaster.success('Successfully Saved')
        },error=>{
        this.showLoader=false;
        this.fundRequirementControl = false;
        if(error.status==401){
                     this.refresh_token=localStorage.getItem('refresh_token')
                             this.authService.SignOut(this.refresh_token);
                           }

        })
    }

    updateFundRequirement(): void {
            this.showLoader=true;
            let response;
            let fileName="counterParty/fundReqDetails";
            let data={ fundReqDetailsDataList : this.fundReqArray}
            response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
            response.subscribe((res: any) => {
            this.showLoader=false;
            this.getUpdateFundRequirement(this.newAppId);
            this.fundRequirementControl = false;
                this.toaster.success('Successfully Updated');
            },error=>{
            this.showLoader=false;
            this.fundRequirementControl = false;
            if(error.status==401){
                         this.refresh_token=localStorage.getItem('refresh_token')
                                 this.authService.SignOut(this.refresh_token);
                               }

            })
        }

//LimitEligibility
public limitEligibilitySubmit(key) {
    this.limitValidate = false;
    this.limitValidate = !this.limitValidate;
           var limitFlag = true;
            for(let limitEligibility of this.limitEligibilityArray){
            if(limitEligibility.custId=="" || limitEligibility.custId==null){
            limitFlag=false;
            console.log("1")
            }
            if(limitEligibility.product=="" || limitEligibility.product==null){
            limitFlag=false;
            console.log("2")
            }
            if(limitEligibility.currentLimit=="" || limitEligibility.currentLimit==null){
            limitFlag=false;
            console.log("3")
            }
            if(limitEligibility.proposedLimit=="" || limitEligibility.proposedLimit==null){
            limitFlag=false;
            console.log("4")
            }
            if(limitEligibility.eligibleLimit=="" || limitEligibility.eligibleLimit==null){
            limitFlag=false;
            console.log("5")
            }
            if(limitEligibility.adhocLimit=="" || limitEligibility.adhocLimit==null){
            limitFlag=false;
            console.log("6")
            }
            if(limitEligibility.creditPeriod=="" || limitEligibility.creditPeriod==null){
            limitFlag=false;
            console.log("7")
            }
            if(limitEligibility.doortoDoor==""){
            limitFlag=false;
            console.log("00")
             }
             if(limitEligibility.invoiceAgeing==""){
             limitFlag=false;
             }
            if(limitEligibility.expectedGrowth=="" || limitEligibility.expectedGrowth==null){
            limitFlag=false;
            console.log("8")
            }
            if(limitEligibility.monthlyAverage=="" || limitEligibility.monthlyAverage==null){
            limitFlag=false;
            console.log("9")
            }
            if(limitEligibility.approtionedLimits=="" || limitEligibility.approtionedLimits==null){
            limitFlag=false;
            console.log("10")
            }
            if(limitEligibility.existingScfLimits=="" || limitEligibility.existingScfLimits==null){
            limitFlag=false;
            console.log("11")
            }
            if(limitEligibility.modelLimit=="" || limitEligibility.modelLimit==null){
            limitFlag=false;
            console.log("12")
            }
            if(limitEligibility.anchorRecommendedAmount=="" || limitEligibility.anchorRecommendedAmount==null){
            limitFlag=false;
            console.log("13")
            }
            if(limitEligibility.creditor=="" ){
               limitFlag=false;
            }
            if(limitEligibility.expectedMonthlyTurnOverWithAnchor==""||limitEligibility.expectedMonthlyTurnOverWithAnchor==null){
            limitFlag=false;
            console.log("14")
            }
            if(limitEligibility.modelAdhocLimit== "" || limitEligibility.modelAdhocLimit==null){
            limitFlag=false;
            console.log("15")
            }
            }
                if(this.limitValidate==true && limitFlag == true ){
                if(key == 'save'){
                this.saveLimitEligibility();
                }else if(key == 'update'){
                this.updateLimitEligibility();
               }
              }
            }
saveLimitEligibility(): void{

        this.showLoader=true;
        let response;
        let fileName="counterParty/limitEligibility/"+this.newAppId;
        let data={ limitEligibilityDetailsData:this.limitEligibilityArray }
            response = this.requestapi.postData(fileName,JSON.stringify(data));
            response.subscribe((res: any) => {
            this.showLoader=false;
                this.cpLimitEligibility1 = false;
                this.cpLimitEligibility2 = true;
            this.getLimitEligibilityById(this.newAppId);
            this.toaster.success('Successfully Saved')
            },error=>{
            this.showLoader=false;
            if(error.status==401){
              this.refresh_token=localStorage.getItem('refresh_token')
              this.authService.SignOut(this.refresh_token);
            }
            if(error.status==400)
            {
            if(error.error.message == null || error.error.message == ''){
              this.toaster.error('Some Technical Error')
              this.cpLimitEligibility1 = true;
            }else{
            var msg =error.error.message;
            for(let i=0 ; i<this.limitEligibilityArray.length;i++){
            // Use of String replace() Method
              msg = msg.replaceAll("limitEligibilityDetailsData["+i+"]", i+1);
            }
            this.toaster.error(msg);
            this.cpLimitEligibility1 = true;
            }
           }

          })
         }
getLimitEligibilityById(UploadID):void {
        let response;
        let fileName="counterParty/limitEligibilityById/"+UploadID;
            response = this.requestapi.getData(fileName);
            response.subscribe((res: any) => {
            this.limitEligibilityList=res;
            console.log("this.limitEligibilityMultipleArray::::",this.limitEligibilityMultipleArray)
            if(res.length != 0){
            if(UploadID == this.oldAppId){
            this.cpLimitEligibility1 = true;
            this.cpLimitEligibility2=false;
            }else{
            this.cpLimitEligibility1 = false;
            this.cpLimitEligibility2=true;
            }
            for(var j=0;j<this.limitEligibilityMultipleArray.length;j++){
                    for(var i=0;i<res.length;i++){
                    this.limitEligibilityArray[i].id = res[i].id;
                    this.limitEligibilityArray[i].custId = res[i].customerInfoEntity.id;
                    this.limitEligibilityArray[i].anchorName = res[i].customerInfoEntity.customerName;
                    this.limitEligibilityArray[i].appId = res[i].applicationEntity.id;
                    this.limitEligibilityArray[i].product = res[i].product;
                    this.limitEligibilityArray[i].currentLimit = res[i].currentLimit;
                    this.limitEligibilityArray[i].proposedLimit = res[i].proposedLimit;
                    this.limitEligibilityArray[i].eligibleLimit = res[i].eligibleLimit;
                    this.limitEligibilityArray[i].adhocLimit = res[i].adhocLimit;
                    this.limitEligibilityArray[i].creditPeriod = res[i].creditPeriod;
                    this.limitEligibilityArray[i].doortoDoor = res[i].doorToDoor;
                    this.limitEligibilityArray[i].invoiceAgeing = res[i].invoiceAgeing;
                    this.limitEligibilityArray[i].margin = res[i].margin;
                    this.limitEligibilityArray[i].expectedGrowth = res[i].expectedGrowth;
                    this.limitEligibilityArray[i].monthlyAverage = res[i].monthlyAverage;
                    this.limitEligibilityArray[i].calculatedLimitWoSetOff = res[i].calculatedLimitWoSetOff;
                    this.limitEligibilityArray[i].approtionedLimits = res[i].approtionedLimits;
                    this.limitEligibilityArray[i].existingScfLimits = res[i].existingScfLimits;
                    this.limitEligibilityArray[i].modelLimit = res[i].modelLimit;
                    this.limitEligibilityArray[i].loginLimitAmount = res[i].customerRequestedAmount;
                    this.limitEligibilityArray[i].anchorRecommendedAmount = res[i].anchorRecommendedAmount;
                    this.limitEligibilityArray[i].receivables = res[i].receivables;
                    this.limitEligibilityArray[i].inventory = res[i].inventory;
                    this.limitEligibilityArray[i].creditor = res[i].creditor;
                    this.limitEligibilityArray[i].expectedMonthlyTurnOverWithAnchor = res[i].expectedMonthlyTurnOverWithAnchor;
                    this.limitEligibilityArray[i].modelAdhocLimit = res[i].modelAdhocLimit;
                    this.limitEligibilityArray[i].loginLimitAmount = res[i].customerRequestedAmount;
                    this.termSheetArray[i].custId = this.limitEligibilityMultipleArray[i].customerId;
                    this.termSheetArray[i].anchorName = this.limitEligibilityArray[i].anchorName;
                    this.termSheetArray[i].product = this.limitEligibilityArray[i].product;
                    this.termSheetArray[i].regularLimit = this.limitEligibilityArray[i].proposedLimit;
                    this.termSheetArray[i].adhocLimit = this.limitEligibilityArray[i].adhocLimit;
                    this.termSheetArray[i].creditPeriod = this.limitEligibilityArray[i].creditPeriod;
                    this.termSheetArray[i].doorToDoor = this.limitEligibilityArray[i].doortoDoor;
                    this.termSheetArray[i].invoiceAgeing = this.limitEligibilityArray[i].invoiceAgeing;
                    this.termSheetArray[i].applyOfInterest = this.limitEligibilityMultipleArray[j].applicationInterest;
                    this.termSheetArray[i].interestBorneBy = this.limitEligibilityMultipleArray[j].interestBorneBy;
                    this.termSheetArray[i].penaltyBorneBy = this.limitEligibilityMultipleArray[j].interestBorneBy;
                    this.termSheetArray[i].renewalPeriod = 12;
            }
            }
            }else{
            this.getLimitEligibilityById(this.oldAppId)
            }
            console.log("limitEligibilityArray ::::",this.limitEligibilityArray)
            },error=>{
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
        })
      }
updateLimitEligibility(): void {
        this.showLoader=true;
            let response;
            let fileName="counterParty/limitEligibility";
            let data={ limitEligibilityDetailsData : this.limitEligibilityArray }
                response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
                response.subscribe((res: any) => {
                this.showLoader=false;
            this.getLimitEligibilityById(this.newAppId);
            this.toaster.success('Successfully Updated');
            },error=>{
            this.showLoader=false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
            if(error.status==400)
            {
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
            }else{
            var msg =error.error.message;
            for(let i=0 ; i<this.limitEligibilityArray.length;i++){
            // Use of String replace() Method
              msg = msg.replaceAll("limitEligibilityDetailsData["+i+"]", i+1);
            }
            this.toaster.error(msg);
            }
            }
        })
      }
getMultipleLimitEligibility():void{

    let response;
    let fileName="counterParty/limitEligibilityList/"+this.newAppId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
        this.limitEligibilityMultipleArray=res.LimitEligibility;
        this.limitEligibilityArray = [];
        this.termSheetArray =[];
        console.log("limitEligibilityMultipleArray :::",this.limitEligibilityMultipleArray)
    if(this.limitEligibilityMultipleArray.length > 0){
        for(var j=0;j<this.limitEligibilityMultipleArray.length;j++){
    // if(this.limitEligibilityArray.length >0){
    this.addLimitEligibleValue();
    this.addTermSheetValue();
       }
    for(var i=0;i<this.limitEligibilityMultipleArray.length;i++){
        this.limitEligibilityArray[i].custId = this.limitEligibilityMultipleArray[i].customerId
        this.limitEligibilityArray[i].product = this.limitEligibilityMultipleArray[i].productName
        this.limitEligibilityArray[i].anchorName = this.limitEligibilityMultipleArray[i].anchorName
        this.limitEligibilityArray[i].creditPeriod = this.limitEligibilityMultipleArray[i].tenure
        this.limitEligibilityArray[i].loginLimitAmount = this.limitEligibilityMultipleArray[i].loginLimitAmount
   }
    this.getLimitEligibilityById(this.newAppId);
  }
    console.log("this.limitEligibilityArray",this.limitEligibilityArray)
    console.log("MultiLimit",this.limitEligibilityMultipleArray);
  },error=>{
    if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
        }
       })


    }

 addLimitEligibleValue() {
        this.limitEligibilityArray.push(this.newLimitAttribute)
        this.newLimitAttribute = {};
    }

 deleteLimitEligibleValue(index) {
        this.limitEligibilityArray.splice(index, 1);
    }

//TermSheet

public termSheetSubmit(key) {

    this.termSheetValidate = false;
    this.termSheetValidate = !this.termSheetValidate;

    const invoiceFund =/^\d{1,3}(\.\d+)?$/;
    const graceDay = /^[0-9]{1,4}$/;

     var termSheetFlag = true;
     for(let termSheet of this.termSheetArray){
         // termSheet.interestRateType='Fixed';
    console.log("termSheet.graceDays",termSheet.graceDays);
    if(!invoiceFund.test(termSheet.invoiceFunding)){
    this.toaster.error('Please Enter valid Invoice Funding');
    termSheetFlag = false;
    }
    if(!graceDay.test(termSheet.graceDays))
    { this.toaster.error('Please Enter valid graceDays');
    termSheetFlag = false;
    }
    if(termSheet.product=="" || termSheet.product==null){
    this.toaster.error('Please Enter valid Product');
    termSheetFlag=false;
    }
    if(termSheet.regularLimit=="" || termSheet.regularLimit==null){
    this.toaster.error('Please Enter valid Regular Limit');
    termSheetFlag=false;
    }
    if(termSheet.adhocLimit=="" || termSheet.adhocLimit==null){
    this.toaster.error('Please Enter valid Adhoc Limit');
    termSheetFlag=false;
    }
    if(termSheet.creditPeriod=="" || termSheet.creditPeriod==null){
    this.toaster.error('Please Enter valid Credit Period');
    termSheetFlag=false;
    }

// if(termSheet.doorToDoor=="" || termSheet.doorToDoor==null){

// termSheetFlag=false;

// }

// if(termSheet.invoiceAgeing=="" || termSheet.invoiceAgeing==null){

// termSheetFlag=false;

// }
    if(termSheet.margin=="" || termSheet.margin==null){
    this.toaster.error('Please Enter valid Margin');
    termSheetFlag=false;
    }
    if(termSheet.interestRate=="" || termSheet.interestRate==null){
    this.toaster.error('Please Enter valid Interest Rate');
    termSheetFlag=false;
    }
    if(termSheet.pf=="" || termSheet.pf==null){
    this.toaster.error('Please Enter valid PF');
    termSheetFlag=false;
    }
    if(termSheet.renewal=="" || termSheet.renewal==null){
    this.toaster.error('Please Enter valid Renewal');
    termSheetFlag=false;
    }
    if(termSheet.renewalPeriod=="" || termSheet.renewalPeriod==null){
    this.toaster.error('Please Enter valid Renewal Period');
    termSheetFlag=false;
    }
    if(termSheet.invoiceFunding=="" || termSheet.invoiceFunding==null){
    this.toaster.error('Please Enter valid Invoice Funding');
    termSheetFlag=false;
    }
   }
        if(this.termSheetValidate==true && termSheetFlag == true ){
            if(key == 'save'){
            this.saveTermSheet();
            }else if(key == 'update'){
            this.updateTermSheet();
           }
    }
  }

saveTermSheet(): void {

    this.showLoader=true;
    let response;
    let fileName="counterParty/termSheet/"+this.newAppId;
    let data={ termSheetDataList:this.termSheetArray }

    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
    this.showLoader=false;
    this.cpTermSheet1 = false;
    this.cpTermSheet2 = true;
    this.toaster.success('Successfully Saved')
    this.getCpTermSheetById('1',this.newAppId);

 },error=>{

    this.showLoader=false;
    if(error.status==401){

    this.refresh_token=localStorage.getItem('refresh_token')
    this.authService.SignOut(this.refresh_token);
    }
    if(error.status==400)
    {
    if(error.error.message == null || error.error.message == ''){
    this.toaster.error('Some Technical Error')
    this.cpTermSheet1 = true;
    }else{
    var msg =error.error.message;
    for(let i=0 ; i<this.termSheetArray.length;i++){
    // Use of String replace() Method
    msg = msg.replaceAll("termSheetDataList["+i+"]", i+1);
    }
    this.toaster.error(msg);
    this.cpTermSheet1 = true;
    }
   }
    })
 }

updateTermSheet(): void {
    this.showLoader=true;
    let response;
    let fileName="counterParty/updateTermSheet";
    let data={ termSheetDataList:this.termSheetArray }

        response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
        this.toaster.success('Successfully Updated');
        this.getCpTermSheetById('1',this.newAppId);
        },error=>{

            this.showLoader=false;
            if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
            }
            if(error.status==400)
            {
            if(error.error.message == null || error.error.message == ''){
            this.toaster.error('Some Technical Error')
            }else{
            var msg =error.error.message;
                for(let i=0 ; i<this.termSheetArray.length;i++){

            // Use of String replace() Method

            msg = msg.replaceAll("termSheetDataList["+i+"]", i+1);
            }
            this.toaster.error(msg);
           }
          }
        })

    }

getCpTermSheetById(key,uploadId): void{

    this.showLoader=true;
    this.termSheetList = [];
    let response;
    let fileName="counterParty/termSheetGet/"+uploadId;

        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
        this.showLoader=false;
        this.termSheetList=res;
        console.log("3")
        // console.log("Answer",this.commercial2)
        if(key == '0'){
        for(var j=0;j<res.length;j++){
        this.addCommercialCcValue()
        }
this.getCommercialCcGetById(this.newAppId);
            }
            if(res.length != 0){
            if(uploadId == this.oldAppId){
            this.cpTermSheet1 = true;
            this.cpTermSheet2 = false;
            }else{
            this.cpTermSheet1 = false;
            this.cpTermSheet2 = true;
            }
            for(var i=0;i< res.length;i++){

            this.termSheetArray[i].id = res[i].id;
            this.termSheetArray[i].appId = res[i].applicationEntity.id;
            this.termSheetArray[i].custId = res[i].customerInfoEntity.id;
            this.termSheetArray[i].anchorName = res[i].customerInfoEntity.customerName;
            this.termSheetArray[i].product = res[i].product;
            this.termSheetArray[i].regularLimit = res[i].regularLimit;
            this.termSheetArray[i].adhocLimit = res[i].adhocLimit;
            this.termSheetArray[i].creditPeriod = res[i].creditPeriod;
            this.termSheetArray[i].doorToDoor = res[i].doorToDoor;
            this.termSheetArray[i].invoiceAgeing = res[i].invoiceAgeing;
            this.termSheetArray[i].margin = res[i].margin;
            this.termSheetArray[i].interestRate = res[i].interestRate;
            this.termSheetArray[i].pf = res[i].pf;
            this.termSheetArray[i].renewal = res[i].renewal;
            this.termSheetArray[i].interestRateType = res[i].interestRateType;
            this.termSheetArray[i].renewalPeriod = res[i].renewalPeriod;
            this.termSheetArray[i].applyOfInterest = res[i].applyOfInterest;
            this.termSheetArray[i].interestBorneBy = res[i].interestBorneBy;
            this.termSheetArray[i].penaltyBorneBy = res[i].penaltyBorneBy;
            this.termSheetArray[i].invoiceFunding = res[i].invoiceFunding;
            this.termSheetArray[i].graceDays = res[i].graceDays;
           }

            }else{
            this.getCpTermSheetById('1',this.oldAppId)
            }
            },error=>{
            this.showLoader=false;
            if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
            }
           })

}

addTermSheetValue() {
  this.termSheetArray.push(this.newTermSheetAttribute)
  this.newTermSheetAttribute = {};
}

deleteTermSheetValue(index) {
 this.termSheetArray.splice(index, 1);
}

addCommercialCcValue(){
        this.commercialArray.push(this.newCommercialAttribute)
          this.newCommercialAttribute = {};
        }
        deleteCommercialValue(index) {
          this.commercialArray.splice(index, 1);
        }

//Due Diligence
public dueDiligenceValidate = false;
public dueDiligence1 = true;
public dueDiligence2 = false;

dueDiligenceMaster: Array<any> = [];
dueDiligenceArray: Array<any> = [];
dueDiligenceValues:[]=[];

getDueDiligenceMaster(): void{
    let response;
    let fileName="counterParty/dueDiligenceMaster";
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.dueDiligenceMaster = res;
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

getDueDiligenceById(id): void {
    let response
    let fileName="counterParty/dueDiligenceDetails/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        if(res.length > 0){
            if(this.newAppId == id){
                for(var i=0; i<res.length; i++){
                    if(res[i].dueDiligenceMasterEntity.datatype == 'TimeStamp'){
                        res[i].value = this.datePipe.transform(res[i].value, 'dd-MM-yyyy');
                    }
                }
            }
             this.dueDiligenceValues=res;
          }else{
            this.getDueDiligenceById(this.oldAppId);
        }
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

storeDueDiligence(id,event,dataType):void{
    let value = event.target.value;
    let enableDueDiligence = 1;
    if(this.dueDiligenceArray.length != 0){
        for(let itemId of this.dueDiligenceArray){
            if(itemId.ddId == id){
                let          index = this.dueDiligenceArray.indexOf(itemId);
                if(dataType == 'TimeStamp'){
                    value =this.datePipe.transform(value, 'MM-dd-yyyy');
                }
                if(this.dueDiligence2){
                    var pId = itemId.id;
                    const a1 = { id: pId, appId: this.newAppId ,ddId : id,value : value,}
                    this.dueDiligenceArray[index] = a1;
                }else{
                    const a1 = { appId: this.newAppId ,ddId : id,value : value,}
                    this.dueDiligenceArray[index] = a1;
                }
                enableDueDiligence = 1;
                break;
            }else{
                enableDueDiligence = 0;
            }
        }
    }else{
        if(dataType == 'TimeStamp'){
            value =this.datePipe.transform(value, 'MM-dd-yyyy');
        }
        const a1 = { appId: this.newAppId ,ddId : id,value : value,}
            this.dueDiligenceArray.push(a1);
    }
    if(enableDueDiligence == 0){
        if(dataType == 'TimeStamp'){
            value =this.datePipe.transform(value, 'MM-dd-yyyy');
        }
        const a1 = { appId: this.newAppId ,ddId : id,value : value,}
            this.dueDiligenceArray.push(a1);
    }
}

public dueDiligenceSubmit(key) {
    this.dueDiligenceValidate = false;
    this.dueDiligenceValidate = !this.dueDiligenceValidate;
    var dueDiligenceFlag = true;
    if(this.dueDiligenceArray.length == this.dueDiligenceMaster.length){
        dueDiligenceFlag=true;
    }else{
        dueDiligenceFlag=false;
    }
    if(this.dueDiligenceValidate==true && dueDiligenceFlag == true ){
        if(key == 'save'){
            this.saveDueDiligence();
        }else if(key == 'update'){
            this.updateDueDiligence();
        }
    }
}

saveDueDiligence(): void {
    this.showLoader=true;
    let response;
    let fileName="counterParty/dueDiligenceDetails";
    let data={ dueDiligenceDetailsDataList : this.dueDiligenceArray }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader=false;
        if(res.message === 'Validation Success'){
            this.dueDiligence1 = false;
            this.dueDiligence2 = true;
            this.toaster.success('Successfully Saved');
            this.getUpdateDueDiligence(this.newAppId);
        }else{
            this.toaster.error(res.message)
            this.dueDiligence1 = true;
            this.dueDiligence2 = false;
        }
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
                this.dueDiligence1 = true;
            }else{
                this.toaster.error(error.error.message);
                this.dueDiligence1 = true;
            }
        }
    })
}

updateDueDiligence(): void {
    this.showLoader=true;
    let response;
    let fileName="counterParty/dueDiligenceDetails";
    let data={ dueDiligenceDetailsDataList : this.dueDiligenceArray };
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader=false;
        this.toaster.success('Successfully Updated');
        this.getUpdateDueDiligence(this.newAppId);
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
            }else{
                this.toaster.error(error.error.message);
            }
        }
    })
}

getUpdateDueDiligence(id): void {
    this.showLoader=true;
    let response
    let fileName="counterParty/dueDiligenceDetails/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.showLoader=false;
        if(res.length != 0){
            if(this.newAppId == id){
                this.dueDiligence1 = false;
                this.dueDiligence2 = true;
            }
            this.dueDiligenceArray = [];
                for(let item of this.dueDiligenceMaster){
                    for(var i=0; i<res.length; i++){
                        if(item.id == res[i].dueDiligenceMasterEntity.id){
                            if(item.datatype == 'TimeStamp'){
                                res[i].value =this.datePipe.transform(res[i].value, 'yyyy-MM-dd');
                            }
                            (<HTMLInputElement>document.getElementById(item.name)).value = res[i].value;
                            const a1 = { id:res[i].id, appId: this.newAppId ,ddId : res[i].dueDiligenceMasterEntity.id,value : res[i].value }
                            this.dueDiligenceArray.push(a1);
                        }
                    }
                }
            this.dueDiligenceValues=res;
        }else{
            this.getUpdateDueDiligence(this.oldAppId);
        }
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

//Beneficiary Details
public beneficiaryValidate = false;
public editBeneficiary = false;
public beneficiary1 = true;
public beneficiary2 = false;
bankDetails:any;
benPkId:any;beneficiaryType:any;beneficiaryName:any;bankName:any;bankCode:any;bankAccountNumber:any;bankIfscCode:any;bankBranchName:any;bankBranchCode:any;

beneficiaryList: Array<any> = [];
bankList: Array<any> = [];
branchList: Array<any> = [];

getBankDetails():void {
    let response;
    let fileName = "anchor/bankDetails";
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.bankList = res.bankDetailsList;
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

getBranchDetails(id):void{
    this.bankBranchName = null;
    this.bankIfscCode = null;
    this.branchList = [];
    let response;
    let fileName = "anchor/branchDetails/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.branchList = res.branchDetailsList;
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

getBankNameId(event):void{
    var values = event.split(",", 2);
    this.bankCode = values[0];
    this.bankName = values[1];
    this.getBranchDetails( this.bankCode );
}

getBankDetailsWithIFSC(event):void{
    let ifsc = event.target.value;
    let response;
    let fileName = "anchor/bankDetails/"+ifsc+"/"+this.bankCode;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        let result = res.bankDetailsList;
        if (result){
            this.bankBranchName = result[0].BBM_DESC;
            this.bankBranchCode = result[0].BBM_CD;
        }else{
            this.toaster.error("Enter Valid IFSC Code");
            this.bankBranchName = null;
        }
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

getBeneficiaryById(id):void {
   this.showLoader=true;
    let response;
    let fileName="anchor/anchorBeneficiaryFile/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.showLoader=false;
        this.beneficiaryList=res;
        if(res.length != 0){
            if(this.newAppId == id){
                this.beneficiary1 = false;
                this.beneficiary2 = true;
            }
            for(var i=0;i<res.length;i++){
                this.benPkId = res[i].id;
                this.beneficiaryName = res[i].benName;
                this.bankCode = res[i].bankCode;
                this.bankDetails = res[i].bankCode+","+res[i].bankName;
                this.bankName = res[i].bankName;
                this.getBranchDetails(this.bankCode);
                this.bankIfscCode = res[i].bankifscCode;
                this.bankAccountNumber = res[i].bankAcctNumber;
                this.bankBranchName = res[i].bankBranchName;
                this.bankBranchCode = res[i].bankBranchCode;
            }
        }else{
            this.getBeneficiaryById(this.oldAppId);
        }
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

public beneficiarySubmit(key) {
    this.beneficiaryValidate = false;
    this.beneficiaryValidate = !this.beneficiaryValidate;
    const benefiType =/^[a-zA-Z ]*$/;
    const benefiName =/^[a-zA-Z ]*$/;
    const benefiBankAccnt =/^[a-zA-Z0-9]+$/;
    var flag =true;
    if(this.beneficiaryName==""  || this.beneficiaryName==null){
        flag=false;
        this.toaster.error('Please Enter Valid Beneficiary Name');
    }else if(this.bankName==""  || this.bankName==null){
        flag=false;
        this.toaster.error("Please Select Bank Name");
    }else if(this.bankAccountNumber==""  || this.bankAccountNumber==null){
        flag=false;
        this.toaster.error('Please Enter Valid bank Account Number');
    }else if(this.bankIfscCode==""  || this.bankIfscCode==null){
        flag=false;
        this.toaster.error('Please Enter Valid IFSC Code');
    }else if(this.bankBranchName==""  || this.bankBranchName==null){
        flag=false;
    }else if(!benefiBankAccnt.test(this.bankAccountNumber)){
        this.cPBasicFlag1 = true;
        this.toaster.error('Please Enter Valid bank Account Number');
        flag = false;
    }
    if(this.beneficiaryValidate && flag){
        if(key == 'save'){
            setTimeout(() => {
                this.saveBeneficiaryDetails();
            }, 1000);
        }else if(key == 'update'){
            setTimeout(() => {
            this.updateBeneficiaryDetails();
            }, 1000);
        }

    }
}

saveBeneficiaryDetails(): void {
    this.showLoader=true;
    let response;
    let fileName="anchor/anchorBeneficiary";
    let data={ appId:this.newAppId,benType:"Dealer",benName:this.beneficiaryName,bankCode : this.bankCode,bankName:this.bankName,
            bankAcctNumber:this.bankAccountNumber,bankIfscCode:this.bankIfscCode,bankBranchCode : this.bankBranchCode,
            bankBranchName:this.bankBranchName,
            }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader=false;
        this.beneficiary1 = false;
        this.beneficiary2 = true;
        this.toaster.success('Successfully Saved');
        this.getBeneficiaryById(this.newAppId);
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
                this.beneficiary1 = true;
                this.beneficiary2 = false;
            }else{
                this.toaster.error(error.error.message);
                this.beneficiary1 = true;
                this.beneficiary2 = false;
            }
        }
        if(error.status==500){
            this.toaster.error("Some Technical Error");
        }
    })
}

updateBeneficiaryDetails(): void {
    this.showLoader=true;
    let response;
    let fileName="anchor/anchorBeneficiary";
    let data={ id:this.benPkId,appId:this.newAppId,benType:"Dealer",benName:this.beneficiaryName,bankCode : this.bankCode,
               bankName:this.bankName,bankAcctNumber:this.bankAccountNumber,bankIfscCode:this.bankIfscCode,
               bankBranchCode : this.bankBranchCode,bankBranchName:this.bankBranchName,
    }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
    this.showLoader=false;
        this.toaster.success('Successfully Updated');
        this.getBeneficiaryById(this.newAppId);
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
            }else{
                this.toaster.error(error.error.message);
            }
        }
        if(error.status==500){
            this.toaster.error("Some Technical Error");
        }
    })
}

///////DMS//////
public loading = false;
public assessmentTypeFlag = false;
docReports:Array<any> = [];
renewalEnhancementReport: Array<any> = [];

getDocumentReports(): void{
    this.showLoader=true;
    let response;
    let fileName="dms/documentReports?appId="+this.newAppId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.showLoader=false;
        if(res.documentReports.length != 0){
            this.docReports = res.documentReports;
            for(let doc of res.documentReports){
                if(doc.customerType == 3){
                    this.assessmentTypeFlag=true;
                    break;
                }else{
                    this.assessmentTypeFlag=false;
                }
            }
        }else{
            this.docReports = null;
            this.assessmentTypeFlag=false;
        }
    },error=>{
        this.showLoader=false;
        if(error.status==401){
              this.refresh_token=localStorage.getItem('refresh_token')
                      this.authService.SignOut(this.refresh_token);
            }
        if(error.error.text=="No Value present"){
            this.assessmentTypeFlag=false;
        }
        this.docReports = null;
    })
}


selectAssessmentType(event):void{
    console.log("anchorProduct--->",this.anchorProduct);
    this.assessmentType=event.target.value;
    console.log("assessmentType--->",this.assessmentType);
//     this.enableUploadDataCredit = true;
//     this.enableUploadDataKyc = true;
}

uploadDocument(event,docTypeId,docTypeName,docCategoryId,docCategoryName,docListId,docListName,key){
    this.loading = true;
    const file = event.target.files[0];
    if(this.assessmentType=='KYC' || this.assessmentType=='Financial' || this.assessmentType=='Cashflow'){
        this.assessmentTypeFlag=true;
    }
    if(file != undefined){
        if(file.size <= 105906176){
            let response;
            let formData = new FormData();
            let url="dms/uploadFile";
            const data={ appId:this.newAppId, docTypeId:docTypeId, docTypeName:docTypeName,docCategoryId:docCategoryId,
                         docCategoryName:docCategoryName,docListId:docListId,docListName:docListName, key:key
                        }
            const documentReports = JSON.stringify(data);
            formData.append('file',file);
            formData.append('documentReportsData',documentReports);
            response = this.requestapi.postFileData(url,formData);
            response.subscribe((res: any) => {
                this.loading = false;
                this.toaster.success('Successfully Uploaded');
                this.getDocumentReports();
            },error=>{
                this.loading = false;
                if(error.status==401){
                    this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                }
                if(error.status==400){
                   if(error.error.message == null || error.error.message == ""){
                   this.toaster.error('Some Technical Error');
                   }else{
                    this.toaster.error(error.error.message);
                    }
                }

                (<HTMLInputElement>document.getElementById(docListId)).value = null;
            })
        }else{
            this.loading = false;
            this.toaster.error('Supported File Size is 100MB');
            (<HTMLInputElement>document.getElementById(docListId)).value = null;
        }
    }else{
        this.loading = false;
    }
}

deleteUploadedFile(docTypeId,docTypeName,docCategoryId,docCategoryName,docListId,docListName,key,fileName):void{

    let response;
    let url="dms/deleteFile";
    let data ={ appId:this.newAppId, docTypeId:docTypeId, docTypeName:docTypeName, docCategoryId:docCategoryId,
                docCategoryName:docCategoryName, docListId:docListId, docListName:docListName, fileName:fileName, key:key }
    response = this.requestapi.postData(url,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.toaster.success('Successfully Deleted');
        this.getDocumentReports();
        (<HTMLInputElement>document.getElementById(docListId)).value = null;
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
            this.toaster.error('Some Technical Error')
            }else{
            this.toaster.error(error.error.message);
            }
        }
    })
}

downloadDocument(appId,docMainName,docSubMainName,fileName){
    let response;
    let url="dms/download/"+appId+"/"+docMainName+"/"+docSubMainName+"/"+fileName;
    $.ajax({
        type: "GET",
        url: environment.baseUrl+url,
        headers: {
            'Authorization': localStorage.getItem('access_token')
        },
        xhrFields: {
            responseType: 'blob'
        },
        success: function (blob) {
            var windowUrl = window.URL || window.webkitURL;
            var url = windowUrl.createObjectURL(blob);
            var anchor = document.createElement('a');
            anchor.href = url;
            anchor.download = fileName;
            anchor.click();
        },
        error: function (error) {

        }
    });
}

docValidationDataArray: Array<any> = [];
docCheckbox(event,docTypeId,docCategoryId,docListId): void{
    let mandatory = event.target.checked;
    let flag = 1;
    if(this.docValidationDataArray.length != 0){
        for(let item of this.docValidationDataArray){
            if(item.docTypeId == docTypeId && item.docCategoryId == docCategoryId && item.docListId == docListId){
                let index = this.docValidationDataArray.indexOf(item);
                const a = { appId:this.newAppId, mandatory:mandatory, docTypeId:docTypeId, docCategoryId:docCategoryId , docListId:docListId }
                this.docValidationDataArray[index] = a;
                flag=1;
                break;
            }else{
                flag=0;
            }
        }
    }else{
        const a = { appId:this.newAppId, mandatory:mandatory, docTypeId:docTypeId, docCategoryId:docCategoryId , docListId:docListId }
        this.docValidationDataArray.push(a);
    }
    if(flag==0){
        const a = { appId:this.newAppId, mandatory:mandatory, docTypeId:docTypeId, docCategoryId:docCategoryId , docListId:docListId }
        this.docValidationDataArray.push(a);
    }
}

public docValidationCheck = false;
documentValidation(key,val): void{
    this.showLoader=true;
    this.docValidationCheck = true;
    if(key == 4 || key == 5){
        if(this.assessmentType == null || this.assessmentType == ''){
            this.assessmentType="KYC";
        }
    }

    let response;
    let fileName="dms/conDocValidation";
    let data={ appId : this.newAppId, customerType :key, docValidationData:this.docValidationDataArray,constitution : this.constitution, anchorType:this.anchorProduct, assessmentType:this.assessmentType }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res:any) => {
        this.getRemarks();
        this.showLoader=false;
        this.docValidationCheck=false;
        if(key==2){
            this.updateCPBasicDetails(false);
            this.uploadDataNp = false;
            this.businessRemarks = true;
//             this.nextBusinessRemarks();
        }
        if(key==4){
            this.CAMUploadNp = false;
            this.CAMRemarks = true;
        }
        if(key==5){
            if(val == 'save'){
                this.saveDeferralDocumentReports();
            }else if(val == 'update'){
                this.updateDeferralDocumentReports();
            }
        }
    },error=>{
        this.showLoader=false;
        this.docValidationCheck=false;
        if(this.assessmentType=='KYC' || this.assessmentType=='Financial' || this.assessmentType=='Cashflow'){
        }else{
            this.toaster.error("Kindly select assessment type");
        }
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
       if(error.status==400)
       		{
       			if(error.error.message == null || error.error.message == ''){
       			this.toaster.error('Some Technical Error')
       			}else{
       			this.toaster.error(error.error.message);
       			}
       		}

    })
}


getRenewalEnhancementDocReports(): void{
    let response;
    let fileName="dms/getRenewalEnhancementDocReports?custId="+this.custId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        if(res.documentReports.length>0){
            this.renewalEnhancementReport = res.documentReports;
            console.log("this.renewalEnhancementReport",this.renewalEnhancementReport);
        }
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        this.renewalEnhancementReport = null;
    })
}

// DMS for Other Document
public otherDocLabelView = false;
public otherDocDefLabelView = false;
OtherDocReports: Array<any> = [];
otherDocumentArray: Array<any> = [];
newOtherDocumentAttribute: any = {};
addOtherDocument(docListId,deferral) {
    this.newOtherDocumentAttribute ={
        appId : this.newAppId,
        status : -1,
        docListId : docListId,
        deferral : deferral
    };
    this.otherDocumentArray.push(this.newOtherDocumentAttribute)
    console.log("otherDocumentArray",this.otherDocumentArray);
}

deleteOtherDocument(index,id) {
    if(id != undefined){
        this.deleteOtherDocRecord(index,id);
    }else{
        this.otherDocumentArray.splice(index, 1);
    }
    console.log("otherDocumentArray",this.otherDocumentArray);
}

saveOtherDocuments(){
    this.showLoader = true;
    let response;
    let fileName="dms/otherDocumentMaster";
    let data={ appId : this.newAppId, otherDocumentDataList : this.otherDocumentArray, customerType : 1, rmName : this.docRMName, constitution : this.constitution }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader = false;
        this.OpsMakerDocUploadNp = false;
        this.OpsMakerRemarksNp = true;
        this.getOtherDocMaster();
        console.log("Response:::",res);
    },error=>{
    this.showLoader = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
            }else{
            this.toaster.error(error.error.message);
            }
        }
        if(error.status==500){
            this.toaster.error("Some Technical Error Contact admin.");
        }
    })
}

updateOtherDocuments(): void {
    this.showLoader=true;
    let response;
    let fileName="dms/otherDocumentMaster";
    let data={ appId : this.newAppId, otherDocumentDataList : this.otherDocumentArray, customerType : 1, rmName : this.docRMName, constitution : this.constitution }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader = false;
        this.OpsMakerDocUploadNp = false;
        this.OpsMakerRemarksNp = true;
        this.getOtherDocMaster();
        console.log("Response:::",res);
    },error=>{
        this.showLoader = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
            }else{
            this.toaster.error(error.error.message);
            }
        }
        if(error.status==500){
            this.toaster.error("Some Technical Error Contact admin.");
        }
    })
}


getOtherDocMaster(): void{
    this.showLoader = true;
    let response;
    let fileName="dms/otherDocumentMaster/"+this.newAppId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.showLoader = false;
        this.otherDocumentArray = [];
        if(res.length != 0){
            for(let val of res){
                if(val.deferralType == 1){
                    this.otherDocDefLabelView = true;
                }else if(val.deferralType == 0){
                    this.otherDocLabelView = true;
                }
            }
        }
        for(let item of res){
            this.docRMName = item.rmName;
            if(item.initialTime != null){
                var dateString = item.initialTime[0]+"-"+item.initialTime[1]+"-"+item.initialTime[2];
                var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
                item.initialTime = date;
            }
            const a = { id : item.id, appId: item.applicationEntity.id ,docListId : item.documentListEntity.id,displayName : item.displayName,initialTime : item.initialTime,status : item.status.toString(),deferralType : item.deferralType,deferral : item.documentListEntity.deferral,}
            this.otherDocumentArray.push(a);
        }
        this.getOtherDocDownload();
        console.log("otherDocumentArray",this.otherDocumentArray);
    },error=>{
        this.showLoader = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

getOtherMasterWithPId(id,index): void{
    this.showLoader = true;
    let response;
    let fileName="dms/getOtherMaster/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        if(res.initialTime != null){
            var dateString = res.initialTime[0]+"-"+res.initialTime[1]+"-"+res.initialTime[2];
            var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
            res.initialTime = date;
        }
        const a = { id : res.id, appId: res.applicationEntity.id ,docListId : res.documentListEntity.id,displayName : res.displayName,initialTime : res.initialTime,status : res.status.toString(),deferralType : res.deferralType,deferral : res.documentListEntity.deferral,}
        this.otherDocumentArray[index] = a;
        console.log("otherDocumentArray",this.otherDocumentArray);
        this.showLoader = false;
    },error=>{
        this.showLoader = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

uploadOtherDocument(event,docTypeId,docTypeName,docCategoryId,docCategoryName,docListId,docListName,otherDocMasterId,otherDisplayName,key,index){
    console.log("otherDocMasterId--->",otherDocMasterId);
    console.log("otherDisplayName--->",otherDisplayName);
    this.loading = true;
    const file = event.target.files[0];
    if(file != undefined){
        if(file.size <= 105906176){
            let response;
            let formData = new FormData();
            let url="dms/otherUploadFile";
            const data={ appId:this.newAppId, otherDocumentDataList : this.otherDocumentArray,docTypeId:docTypeId, docTypeName:docTypeName,docCategoryId:docCategoryId,
                         docCategoryName:docCategoryName,docListId:docListId,docListName:docListName, otherDocMasterId:otherDocMasterId, otherDisplayName:otherDisplayName, key:key, rmName : this.rmName
                        }
            const otherDocumentReports = JSON.stringify(data);
            formData.append('file',file);
            formData.append('otherDocumentReportsData',otherDocumentReports);
            response = this.requestapi.postFileData(url,formData);
            response.subscribe((res: any) => {
                this.loading = false;
                this.toaster.success('Successfully Uploaded');
                const jsonString = res.FileName;
                const json = JSON.parse(jsonString);
                console.log("json--->",json);
                console.log("otherDocMasterId",json[0].otherDocMasterId);
                if(otherDocMasterId == undefined){
                    this.getOtherMasterWithPId(json[0].otherDocMasterId,index);
                }
                this.getOtherDocDownload();
            },error=>{
                this.loading = false;
                if(error.status==401){
                    this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                }
                if(error.status==400){
                   if(error.error.message == null || error.error.message == ""){
                   this.toaster.error('Some Technical Error');
                   }else{
                    this.toaster.error(error.error.message);
                    }
                }
                (<HTMLInputElement>document.getElementById(docListId+"F"+index)).value = null;
            })
        }else{
            this.loading = false;
            this.toaster.error('Supported File Size is 100MB');
            (<HTMLInputElement>document.getElementById(docListId+"F"+index)).value = null;
        }
    }else{
        this.loading = false;
    }
}

deleteOtherUploadedFile(docTypeId,docTypeName,docCategoryId,docCategoryName,docListId,docListName,otherDocMasterId,otherDisplayName,key,fileName,index):void{
    let response;
    let url="dms/deleteOtherFile";
    const data={ appId:this.newAppId, otherDocumentDataList : this.otherDocumentArray,docTypeId:docTypeId, docTypeName:docTypeName,docCategoryId:docCategoryId,
                 docCategoryName:docCategoryName,docListId:docListId,docListName:docListName, otherDocMasterId:otherDocMasterId, otherDisplayName:otherDisplayName,fileName:fileName, key:key, rmName : this.rmName
                }
    response = this.requestapi.postData(url,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.toaster.success('Successfully Deleted');
        this.getOtherDocDownload();
        (<HTMLInputElement>document.getElementById(docListId+"F"+index)).value = null;
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
            this.toaster.error('Some Technical Error')
            }else{
            this.toaster.error(error.error.message);
            }
        }
    })
}

deleteOtherDocRecord(index,id): void{
    this.showLoader=true;
    let response;
    let fileName="dms/deleteOtherDocRecord/"+this.newAppId+"/"+id;
    response = this.requestapi.deleteData(fileName);
    response.subscribe((res:any) => {
        this.otherDocumentArray.splice(index, 1);
//         this.getOtherDocMaster();
        this.getOtherDocDownload();
        this.showLoader=false;
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            this.toaster.error(error.error.message);
        }
    })
}

getOtherDocDownload(): void{
    let response;
    let fileName="dms/customerOtherDocReports?appId="+this.newAppId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.OtherDocReports = res.otherDocReports;
        console.log("this.OtherDocReports",this.OtherDocReports);
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        this.OtherDocReports = null;
    })
}

downloadOtherDocument(appId,docMainName,docSubMainName,otherDocName,fileName){
    let response;
    let customerId = this.newAppId;
    let url="dms/otherDownload/"+appId+"/"+docMainName+"/"+docSubMainName+"/"+otherDocName+"/"+fileName;
    $.ajax({
        type: "GET",
        url: environment.baseUrl+url,
        headers: {
            'Authorization': localStorage.getItem('access_token')
        },
        xhrFields: {
            responseType: 'blob'
        },
        success: function (blob) {
            var windowUrl = window.URL || window.webkitURL;
            var url = windowUrl.createObjectURL(blob);
            var anchor = document.createElement('a');
            anchor.href = url;
            anchor.download = fileName;
            anchor.click();
        },
        error: function (error) {

        }
    });
}
//Deferral Document
public deferralCheck = false;
public deferralDocView = false;
deferralDocumentArray: Array<any> = [];
deferralDocReportArray: Array<any> = [];

setDocDate(): void{
    this.deferralDocumentArray = [];
    for(let docType of this.documentMaster){
        if(docType.type == 5){
            for(let docCategory of docType.documentCategoryEntities){
                for(let docList of docCategory.documentListEntities){
                    if(docList.deferral == 2 && docList.status == 1 && docList.type == 0){
                        var date = new Date();
                        var days = docList.deferralTime+1;
                        date.setDate(date.getDate() + days);
                        var date1 = (<HTMLInputElement>document.getElementById(docList.id+'C'));
                        var dateString = date.toISOString().split('T')[0];
                        date1.value = dateString;
                        (<HTMLInputElement>document.getElementById(docList.id+"E")).style.display = "none";
                        (<HTMLInputElement>document.getElementById(docList.id+"F")).style.display = "none";
                        const b = { appId: this.newAppId ,docListId : docList.id,initialTime : dateString,status : 0,deferralType : docList.type}
                        this.deferralDocumentArray.push(b);
                    }else if(docList.deferral == 1 && docList.status == 1 && docList.type == 0 && docList.mandatory == 1){
                        (<HTMLInputElement>document.getElementById(docList.id+"B")).checked = true;
                        (<HTMLInputElement>document.getElementById(docList.id+"C")).style.display = "none";
                        const a = { appId: this.newAppId ,docListId : docList.id ,initialTime : null ,status : 2, deferralType : docList.deferral,}
                        this.deferralDocumentArray.push(a);
                    }
                }
            }
        }
    }
}

storeDeferral(event,docListId,deferral,deferralType):void{
    let status = 0;
    let flag = 1;
        console.log("event.target.value",event.target.value);
    if(deferral == 2){
        if(event.target.value == 'No'){
            (<HTMLInputElement>document.getElementById(docListId+"E")).style.display = "block";
            (<HTMLInputElement>document.getElementById(docListId+"F")).style.display = "block";
            (<HTMLInputElement>document.getElementById(docListId)).style.display = "block";
        }else if(event.target.value == 'Yes'){
              (<HTMLInputElement>document.getElementById(docListId+"E")).style.display = "none";
              (<HTMLInputElement>document.getElementById(docListId+"F")).style.display = "none";
              (<HTMLInputElement>document.getElementById(docListId)).style.display = "none";
        }
    }
    if(event.target.value == 'Yes'){
        (<HTMLInputElement>document.getElementById(docListId+"C")).style.display = "block";
        status = 0;
    }else if(event.target.value == 'No'){
        (<HTMLInputElement>document.getElementById(docListId+"C")).style.display = "none";
        status = 2;
    }else if (event.target.value == -1){
       (<HTMLInputElement>document.getElementById(docListId+"C")).style.display = "none";
       status = -1;
    }
    if(this.deferralDocumentArray.length != 0){
        for(let itemId of this.deferralDocumentArray){
            if(itemId.docListId == docListId){
                let index = this.deferralDocumentArray.indexOf(itemId);
                itemId.status = status;
                this.deferralDocumentArray[index] = itemId;
               flag=1;
                break;
            }else{
                flag=0;
            }
        }
    }else{
        const b = { appId: this.newAppId ,docListId : docListId,status : status,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
    if(flag==0){
        const b = { appId: this.newAppId ,docListId : docListId,status : status,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
    console.log("deferralDocumentArray",this.deferralDocumentArray);
}
onKeyPressEvent(event: any){
event.preventDefault();
   console.log(event.target.value);
}

storeDeferralDate(event,docListId,deferralType):void{
event.preventDefault();
    let flag = 1;
    let date = event.target.value;
    if(this.deferralDocumentArray.length != 0){
        for(let itemId of this.deferralDocumentArray){
            if(itemId.docListId == docListId){
                let index = this.deferralDocumentArray.indexOf(itemId);
                itemId.initialTime = date;
                this.deferralDocumentArray[index] = itemId;
               flag=1;
                break;
            }else{
                flag=0;
            }
        }
    }else{
        const b = { appId: this.newAppId ,docListId : docListId,initialTime : date,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
    if(flag==0){
        const b = { appId: this.newAppId ,docListId : docListId,initialTime : date,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
}

saveDeferralDocumentReports(): void {
    this.showLoader=true;
    let response;
    let fileName="dms/deferralReport";
    let data={ appId : this.newAppId, deferralReportsDataList : this.deferralDocumentArray, customerType : 2, rmName : this.docRMName, docProductCheck : true, constitution : 'Private Company',};
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
    this.showLoader=false;
        this.getDeferralDocReports();
        this.updateOtherDocuments();
//             this.OpsMakerDocUploadNp = false;
//             this.OpsMakerRemarksNp = true;
        this.docValidationCheck = false;
    },error=>{
    this.showLoader=false;
        this.docValidationCheck = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
            }else{
            this.toaster.error(error.error.message);
            }
        }
    })
}

updateDeferralDocumentReports(): void {
    this.showLoader=true;
    let response;
    let fileName="dms/deferralReport";
    let data={ appId : this.newAppId, deferralReportsDataList : this.deferralDocumentArray, customerType : 2, rmName : this.docRMName, docProductCheck : true, constitution : 'Private Company',};
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader=false;
        this.getDeferralDocReports();
        this.updateOtherDocuments();
        //             this.OpsMakerDocUploadNp = false;
        //             this.OpsMakerRemarksNp = true;
        this.docValidationCheck = false;
    },error=>{
        this.showLoader=false;
        this.docValidationCheck = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
            }else{
                this.toaster.error(error.error.message);
            }
        }
    })
}

getDeferralDocumentReports(): void{
    if(this.deferralDocumentArray.length == 0){
        let response;
        let fileName="dms/deferralReport/"+this.newAppId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res:any) => {

            this.deferralDocumentArray = [];
            if(res.length==0){
                this.setDocDate();
            }else{
                this.deferralCheck = true;
            }
            for(var item of res){
                this.docRMName = item.rmName;
                if(item.documentListEntity.deferral == 1){
                    if(item.status == 0){
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"A")).checked = true;
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"C")).style.display = "block";
                    }else if(item.status == 2){
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"B")).checked = true;
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"C")).style.display = "none";
                    }
                }
                if(item.documentListEntity.deferral == 2){
                    if(item.status == 0){
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"D")).value = "Yes";
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"E")).style.display = "none";
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"F")).style.display = "none";
                    }else if(item.status == 2){
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"D")).value = "No";
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"E")).value = item.documentId;

                        var dateString = item.docCompletionDate[0]+"-"+item.docCompletionDate[1]+"-"+item.docCompletionDate[2];
                        var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"F")).value = date;
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"E")).style.display = "block";
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"F")).style.display = "block";
                    }
                }
                if(item.initialTime != null){
                    var dateString = item.initialTime[0]+"-"+item.initialTime[1]+"-"+item.initialTime[2];
                    var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
                    (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"C")).value = date;
                }
                const a = { id : item.id, appId: item.applicationEntity.id ,docListId : item.documentListEntity.id,initialTime : item.initialTime,status : item.status, documentId : item.documentId, docCompletionDate : item.docCompletionDate,deferralType : item.documentListEntity.type,}
                this.deferralDocumentArray.push(a);
            }
            console.log("this.deferralDocumentArray1",this.deferralDocumentArray);
        },error=>{

                this.setDocDate();
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }

        })
    }
}

getDeferralReport(id): void{
    this.showLoader=true;
    let response;
    let fileName="dms/deferralReports/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.showLoader=false;
        console.log("Deferral Report :",res);
        if(res.deferralReports.length != 0){
            this.deferralDocReportArray = res.deferralReports;
            for(let item of res.deferralReports){
                if(item.initialTime != null){
                    let dateString = item.initialTime;
//                     var dateString = item.initialTime[0]+"-"+item.initialTime[1]+"-"+item.initialTime[2];
                    var date = this.datePipe.transform(dateString, 'dd-MM-yyyy');
                    item.initialTime = date;
                }
                if(item.docCompletionDate != null){
                    let dateString = item.docCompletionDate ;
//                     var dateString = item.docCompletionDate[0]+"-"+item.docCompletionDate[1]+"-"+item.docCompletionDate[2];
                    var date = this.datePipe.transform(dateString, 'dd-MM-yyyy');
                    item.docCompletionDate = date;
                }
            }
            for(let item of res.deferralReports){
                if(item.rmName != null){
                    this.docRMName = item.rmName;
                }
                this.deferralDocView = true;
            }
        }
    },error=>{
    this.showLoader=false;
    if(error.status==401){
          this.refresh_token=localStorage.getItem('refresh_token')
                  this.authService.SignOut(this.refresh_token);
        }

    })
}

getDeferralDocReports(): void{
    let response;
    let fileName="dms/deferralReport/"+this.newAppId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.deferralDocumentArray = [];
        if(res.length==0){
            this.setDocDate();
        }else{
            this.deferralCheck = true;
        }
        for(var item of res){
            const a = { id : item.id, appId: item.applicationEntity.id ,docListId : item.documentListEntity.id,initialTime : item.initialTime,status : item.status, documentId : item.documentId, docCompletionDate : item.docCompletionDate,deferralType : item.documentListEntity.type,}
            this.deferralDocumentArray.push(a);
        }
        console.log("this.deferralDocumentArray1",this.deferralDocumentArray);
    },error=>{
        this.setDocDate();
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}


//Deferral Committee Stage
deferralDocCheck:any;
deferralOtherDocCheck:any;
DeferralDocArray: Array<any> = [];
OtherDeferralDocArray: Array<any> = [];
newDeferralAttribute:any ={};
newOtherDeferralAttribute:any ={};

addDeferralDocArray(){
    this.DeferralDocArray.push(this.newDeferralAttribute)
    this.newDeferralAttribute = {};
}
addOtherDeferralDocArray(){
    this.OtherDeferralDocArray.push(this.newOtherDeferralAttribute)
    this.newOtherDeferralAttribute = {};
}

getDocDeferralReport(){
    let response;
    let fileName="dms/deferralReport/"+this.newAppId+"/0";
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        console.log("Response::",res);
        if(res.length>0){
            this.deferralDocCheck=true
        }
        for(var i=0;i<res.length;i++){
            this.addDeferralDocArray();
            this.DeferralDocArray[i].id = res[i].id;
            this.DeferralDocArray[i].appId = res[i].applicationEntity.id;
            this.DeferralDocArray[i].docListId = res[i].documentListEntity.id;
            this.DeferralDocArray[i].documentName = res[i].documentListEntity.displayName;

            var dateString = res[i].initialTime[0]+"-"+res[i].initialTime[1]+"-"+res[i].initialTime[2];
            var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
            this.DeferralDocArray[i].initialTime = date;

            this.DeferralDocArray[i].revisedTime = res[i].revisedTime;
            this.DeferralDocArray[i].deferralType = res[i].documentListEntity.deferralType;
            this.DeferralDocArray[i].deferral = res[i].documentListEntity.deferral;
            this.DeferralDocArray[i].status = res[i].status
            if(res[i].rmName != null){
                this.rmName = res[i].rmName;
                this.docRMName = res[i].rmName;
            }
        }
        console.log("this.DeferralDocArray",this.DeferralDocArray);
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

storeDeferralStatus(deferralDocId,status){
    for(let item of this.DeferralDocArray){
        if(item.id == deferralDocId){
            let index = this.DeferralDocArray.indexOf(item);
            item.status = status;
            this.DeferralDocArray[index] = item;
            if(status == 1){
                (<HTMLInputElement>document.getElementById(deferralDocId+'1')).disabled = true;
                (<HTMLInputElement>document.getElementById(deferralDocId+'3')).disabled = false;
                this.toaster.success("Decision made as Approve");
            }else {
                (<HTMLInputElement>document.getElementById(deferralDocId+'3')).disabled = true;
                (<HTMLInputElement>document.getElementById(deferralDocId+'1')).disabled = false;
                this.toaster.success("Decision made as WaiveOff");
            }
        }
    }
    console.log("this.DeferralDocArray",this.DeferralDocArray);
}

getOtherDocDeferralReport(): void{
    let response;
    let fileName="dms/othersDeferralDocuments/"+this.newAppId+"/0";
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        console.log(res,"OtherDocuments");
        if(res.length>0){
            this.deferralOtherDocCheck = true
        }
        for(var i=0;i<res.length;i++){
            this.addOtherDeferralDocArray();
            this.OtherDeferralDocArray[i].id = res[i].id;
            this.OtherDeferralDocArray[i].appId = res[i].applicationEntity.id;
            this.OtherDeferralDocArray[i].docListId = res[i].documentListEntity.id;
            this.OtherDeferralDocArray[i].displayName = res[i].displayName;
            console.log(this.OtherDeferralDocArray[i].displayName,"this.OtherDeferralDocArray[i].displayName");

            var dateString = res[i].initialTime[0]+"-"+res[i].initialTime[1]+"-"+res[i].initialTime[2];
            var date = this.datePipe.transform(dateString, 'dd-MM-yyyy');
            this.OtherDeferralDocArray[i].initialTime = date;

            this.OtherDeferralDocArray[i].revisedTime = res[i].revisedTime;
            this.OtherDeferralDocArray[i].status = res[i].status;
            if(res[i].rmName != null){
                this.rmName = res[i].rmName;
                this.docRMName = res[i].rmName;
            }
        }
    })
}

storeOtherDocDeferralStatus(otherDeferralDocId,status){
    for(let item of this.OtherDeferralDocArray){
        if(item.id == otherDeferralDocId){
            let index = this.OtherDeferralDocArray.indexOf(item);
            item.status = status;
            this.OtherDeferralDocArray[index] = item;
            if(status == 1){
                (<HTMLInputElement>document.getElementById(otherDeferralDocId+'1')).disabled = true;
                (<HTMLInputElement>document.getElementById(otherDeferralDocId+'3')).disabled = false;
                this.toaster.success("Decision made as Approve");
            }else {
                (<HTMLInputElement>document.getElementById(otherDeferralDocId+'3')).disabled = true;
                (<HTMLInputElement>document.getElementById(otherDeferralDocId+'1')).disabled = false;
                this.toaster.success("Decision made as WaiveOff");
            }
        }
    }
    console.log("this.OtherDeferralDocArray",this.OtherDeferralDocArray);
}

nextDeferralApproval(){
    let bool = false;
    for(let item of this.DeferralDocArray){
        if(item.status == 0){
            bool = true;
        }
    }

    for(let item of this.OtherDeferralDocArray){
        if(item.status == 0){
            bool = true;
        }
    }

    if(bool){
        this.toaster.error("Please make Decision for all documents");
    }else{
        this.deferralApprovalNp = false;
        this.deferralRemarksNp = true;
        window.scrollTo(0, 0);
    }
}

deferralWorkFlow(){
    let bool = false;
    for(let item of this.DeferralDocArray){
        if(item.status == 1){
            bool = true;
        }
    }

    for(let item of this.OtherDeferralDocArray){
        if(item.status == 1){
            bool = true;
        }
    }
    if(bool){
        let response1;
        let fileName1="deferralWorkflow/saveDeferralWorkflow";
        let data1={ stageId:32,status:2,approverInfo:this.emailId,appId:this.newAppId,remarks:"Deferral Workflow Started"
        ,nextApproverInfo:"CP_DEFERRAL_COMMITTEE_LEAD"}
        response1 = this.requestapi.postData(fileName1,JSON.stringify(data1));
        response1.subscribe((res: any) => {
            window.location.reload();
            this.router.navigate(['/dashboard/inbox']);
            this.showLoader = false;
        },error=>{
            this.showLoader = false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
            if(error.status==400){
                if(error.error.message == null || error.error.message == ''){
                    this.toaster.error('Some Technical Error')
                }else{
                    this.toaster.error(error.error.message);
                }
            }
        })
    }else{
        this.showLoader = false;
        window.location.reload();
        this.router.navigate(['/dashboard/inbox']);
    }

}

updateDeferralReports(stageId,status,value,nextApproverInfo){
    this.showLoader=true;
    let response;
    let fileName="dms/saveNewDeferralDate";
    let data={ appId: this.newAppId,deferralReportsDataList:this.DeferralDocArray,rmName : this.rmName}
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.updateOtherDeferralReports(stageId,status,value,nextApproverInfo);
        this.showLoader=false;
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status == 400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error');
            }else{
                this.toaster.error(error.error.message);
            }
        }
    })
}

updateOtherDeferralReports(stageId,status,value,nextApproverInfo){
    this.showLoader=true;
    let response;
    let fileName="dms/updateOtherDeferralStatus";
    let data={ appId: this.newAppId,otherDocumentDataList:this.OtherDeferralDocArray,rmName : this.docRMName}
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.workflowFunction(stageId,status,value,nextApproverInfo);
        this.showLoader=false;
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status == 400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error');
            }else{
                this.toaster.error(error.error.message);
            }
        }
    })
}

getCustomerInfoDetails(): void {

    let response;
    let fileName="wfApprovalStatus/onBoardedCustomers/1";
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.anchorList=res;

//         this.getProposedProductDetails(true);
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

getAnchorProducts(anchorId,i,product,key):void{
    let response;
    let fileName="anchor/programsDetailsByCustId/"+anchorId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        if(key){
            this.proposalArray[i].product = null;
//             this.cpProposed1 = true;
        }
        const optionsArray = res.programNormsArray;
        const selectElement = document.getElementById("product"+i) as HTMLSelectElement;
        while (selectElement.options.length > 1) {
            selectElement.remove(1);
        }
        optionsArray.forEach(option => {
            const optionElement = document.createElement("option");
            optionElement.value = option.subProduct;
            optionElement.textContent = option.subProduct;
            selectElement.appendChild(optionElement);
        });
        (<HTMLInputElement>document.getElementById("product"+i)).value = product;
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

 addProposalValue() {

        if(this.proposalArray.length <= 19){
            this.newProposalAttribute = { appId :this.newAppId };
            this.proposalArray.push(this.newProposalAttribute)
            this.proposalValidate = false;
            this.cpProposed1 = true;
        }else{
            this.toaster.error("Maximum allowed proposal details reached...");
        }
    }

deleteProposalValue(id,index,appId,custId,product,type,proposed,vintageWithAnchor,minMonthlyAnchor,anchorRelationship) {
    if(this.proposalArray.length == 1){
    this.toaster.error("Minimum 1 Anchor Required");
    }else{
     if(id == null){
     this.proposalArray.splice(index, 1);
    }else{
      this.deleteProposedProduct(index,id,true,appId,custId,product,type,proposed,vintageWithAnchor,minMonthlyAnchor,anchorRelationship);
       }
//     this.toaster.error("Minimum of one proposal detail is required...");
 }
}

deleteProposedProduct(index,id,clearKey,appId,custId,product,type,proposed,vintageWithAnchor,minMonthlyAnchor,anchorRelationship): void{
    this.showLoader=true;
    console.log("id",id)
    console.log("asdfghjkl",this.custInfoId);
    let response;
    let fileName="counterParty/proposedProductDelete/"+this.custInfoId;
    let data={id:id,appId:appId,custId:custId,product:product,type:type,proposed:proposed,vintageWithAnchor:vintageWithAnchor,minMonthlyAnchor:minMonthlyAnchor,anchorRelationship:anchorRelationship }

    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res:any) => {
     this.deleteStatus =res.result;
     for(let delStatus of this.deleteStatus){
     this.resultStatus = delStatus.message
     }
     console.log("Answer",this.resultStatus);
    if(this.resultStatus == "success"){
        this.proposalArray.splice(index, 1);
    }
    else if(this.resultStatus == "fail")
    {
         this.toaster.error("Existing Anchor Can Not Delete")
    }
        this.showLoader=false;
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            this.toaster.error(error.error.message);
        }
    })
}

getProposedProductDetails(key,id): void {
    this.ApplicationId = id;
    this.creditProposalList = [];
    this.ProposalList = [];
    console.log(id,'Id!')
    let response;
    let fileName="counterParty/proposedProductsById/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
    if(res.length ==0){
    this.getProposedProductDetails(true,this.oldAppId)
    }else{
        this.proposalArray = [];
        this.newProposalAttribute = {};
        this.proposalArray.push(this.newProposalAttribute)

//         if(res.length > 0 && res.length != this.proposalArray.length){
            for(var j=1;j<res.length;j++){
          this.addProposalValue();
        }
        this.creditProposalList = res;
        this.ProposalList=res;
        console.log("Prosposla arrya",this.proposalArray);
        if(res.length != 0){
//             this.cpProposed2=true;

//                 this.cpProposed1 = false;
              for(var i=0;i<res.length;i++){
                this.proposalArray[i].id = res[i].id;
                this.proposalArray[i].appId = res[i].applicationEntity.id;
                this.proposalArray[i].custId = res[i].customerInfoEntity.id;
                this.proposalArray[i].name = res[i].customerInfoEntity.customerName;
                if(key){
                    this.getAnchorProducts(res[i].customerInfoEntity.id,i,res[i].product,false);
                }
                this.proposalArray[i].product = res[i].product;
                this.proposalArray[i].proposed = res[i].proposed;
                this.proposalArray[i].status = res[i].customerInfoEntity.status;
                if(res[i].customerInfoEntity.status == false){
                  this.disableNextProposed =true;
                }else{
                if(this.disableNextProposed ==true){
                this.disableNextProposed =false;
                 }
                }
                if(res[i].vintageWithAnchor != null ||res[i].minMonthlyAnchor != null ||res[i].anchorRelationship != null){
                    this.creditCheck = true;
                }
            }
             if(this.ApplicationId == this.oldAppId){
               this.cpProposed2 = false;
               this.cpProposed1 = true;
             }else{
             this.cpProposed2 = true;
             this.cpProposed1 = false;
             }
            console.log("Prosposla arrya",this.proposalArray);
            this.gotoRiskOrCredit();
        }else{
            this.proposalArray = [];
            this.newProposalAttribute = {};
            this.proposalArray.push(this.newProposalAttribute)
            this.cpProposed2=false;
            this.cpProposed1=true;
//             this.cpProposed1 = true;
        }
        }
    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

getProProductDetails(): void {
    this.ProposalList = [];
    let response;
    let fileName="counterParty/proposedProductsById/"+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.ProposalList=res;
        if(res.length != 0){
//             this.cpProposed2=true;
//             this.cpProposed1 = false;
            for(var i=0;i<res.length;i++){
                this.proposalArray[i].id = res[i].id;
                this.proposalArray[i].appId = res[i].applicationEntity.id;
                this.proposalArray[i].custId = res[i].customerInfoEntity.id;
                this.proposalArray[i].product = res[i].product;
//                 this.proposalArray[i].type = res[i].type;
                this.proposalArray[i].proposed = res[i].proposed;
                if(res[i].vintageWithAnchor != null ||res[i].minMonthlyAnchor != null ||res[i].anchorRelationship != null){
                    this.creditCheck = true;
                }
            }
            this.gotoRiskOrCredit();
        }else{
//              this.cpProposed1 = true;
        }
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

proposalAnchorValidation(){
    let response;
    let fileName="dms/assessmentCheck?constitution="+this.constitution+"&appId="+this.newAppId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        console.log("res -- >",res);
        if(res.status){
            this.anchorProduct = res.productType;
            this.assessmentKycFlag = res.assessmentKycFlag;
            this.deleteCreditDocPopUp();
        }else{
            this.assessmentKycFlag = res.assessmentKycFlag;
            this.anchorProduct = res.productType;
            if(res.assessmentType == "none" || res.assessmentType == ""){
                if(!res.assessmentKycFlag){
                    this.assessmentType = "Financial";
                }
            }else{
                this.assessmentType = res.assessmentType;
            }
            this.getDocumentReports();
            this.nextProposal();
        }
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

deleteCreditDocPopUp(){
    Swal.fire({
      title: 'Are you sure to submit?',
      text: 'The chosen Assessment Type does not match the criteria, so the uploaded documents will be deleted.Do you want to proceed?',
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Submit'
    }).then((result) => {
      if (result.value) {
            this.deleteCreditDocuments();
      }
    })
}

deleteCreditDocuments(){
    let response;
    let fileName="dms/deleteCreditDocuments?appId="+this.newAppId;
    response = this.requestapi.deleteData(fileName);
    response.subscribe((res: any) => {
        console.log("res -- >",res);
        this.getDocumentReports();
        this.proposalNp = false;
        this.uploadDataNp = true;
        window.scrollTo(0, 0);
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

public proposalSubmit(key) {
        this.proposalValidate = false;
        this.proposalValidate = !this.proposalValidate;
        var proposalFlag = true;
        for(var i = 0 ; i<this.proposalArray.length;i++){
            for(var j = i+1; j<this.proposalArray.length;j++){
                if(this.proposalArray[i].custId == this.proposalArray[j].custId && this.proposalArray[i].custId != undefined){
                    if(this.proposalArray[i].product == this.proposalArray[j].product && this.proposalArray[i].product != undefined){
                        this.toaster.error("Same anchor with same product not allowed...");
                        proposalFlag = false;
                    }
                }
            }
        }
        if(proposalFlag){
            for(let proposals of this.proposalArray){
                this.anchorId = proposals.custId;
                if(proposals.custId=="" || proposals.custId==null){
                    this.toaster.error("Please Select Anchor");
                    proposalFlag=false;
                    break;
                }
                if(proposals.product=="" || proposals.product==null){
                    this.toaster.error("Please Select Product");
                    proposalFlag=false;
                    break;
                }
//                 if(proposals.type=="" || proposals.type==null){
//                     this.toaster.error("Please Select Type");
//                     proposalFlag=false;
//                     break;
//                 }
                if(proposals.proposed=="" || proposals.proposed==null){
                    this.toaster.error("Please Select Proposed Amount");
                    proposalFlag=false;
                    break;
                }
            }
        }
//             this.gotoRiskOrCredit(id);
            if(this.proposalValidate==true && proposalFlag == true ){
                if(key == 'save'){
                    this.saveProposal();
                }else if(key == 'update'){
                    this.updateProposal();
                }
        }
    }


saveProposal(): void{
        this.showLoader=true;
        let response;
        let fileName="counterParty/proposedProductDetails/"+this.newAppId;
        let data={ proposedProductsDataList:this.proposalArray }
        response = this.requestapi.postData(fileName,JSON.stringify(data));
            response.subscribe((res: any) => {
            this.showLoader=false;
            this.cpProposed1 = false;
            this.cpProposed2 = true;
            this.toaster.success('Successfully Saved')
            this.getProposedProductDetails(true,this.newAppId)
//             this.getProProductDetails();
        },error=>{
        this.showLoader=false;
        if(error.status==401){
                            this.refresh_token=localStorage.getItem('refresh_token')
                                    this.authService.SignOut(this.refresh_token);
                           }
             	if(error.status==400)
             			{
             				if(error.error.message == null || error.error.message == ''){
             				this.toaster.error('Some Technical Error')
//              				this.cpProposed1 = true;
             				}else{
             				this.toaster.error(error.error.message);
//              				this.cpProposed1 = true;
             				}
             			}


        })
    }

    updateProposal(): void {
        this.showLoader=true;
        let response;
        let fileName="counterParty/proposedProductDetails/"+this.newAppId;
        console.log(this.proposalArray,'this.proposalArray!@#')
        let data={ proposedProductsDataList:this.proposalArray }
        response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
        this.getProposedProductDetails(true,this.newAppId)
        this.toaster.success('Successfully Updated');

//             this.getProProductDetails();
        },error=>{
        this.showLoader=false;
            if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        	if(error.status==400)
        			{
        				if(error.error.message == null || error.error.message == ''){
        				this.toaster.error('Some Technical Error')
        				}else{
        				this.toaster.error(error.error.message);
        				}
        			}

        })
    }

//Soft Policy

getSoftPolicyMaster(): void{

        let response;
        let fileName="counterParty/softPolicyMasterType";
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {

        this.SoftPolicyMasterList=res;
        },error=>{

        if(error.status==401){
               this.refresh_token=localStorage.getItem('refresh_token')
                       this.authService.SignOut(this.refresh_token);
                           }

        })
    }

storeSoftPolicy(id,item: any,dataType):void{
        this.creditNormDetails = [];
        this.softPolicyResult = [];
        this.softPolicyDealerResult = [];
        this.softPolicyVendorResult = [];
        this.saveSoftPolicyFlag=true;
        this.softPolicyValue=item.target.value;
        this.softPolicyId=id;
        this.enableSoftPolicy=1;
        if(this.softPolicyArray.length != 0){
            for(let itemId of this.softPolicyArray){
                if(itemId.softPolicyId == this.softPolicyId){
                    if(this.updateSoftPolicyFlag){
                        let index = this.softPolicyArray.indexOf(itemId);
                        let pId = itemId.id;
                        const a = { id:pId, appId: this.newAppId ,softPolicyId : this.softPolicyId,value : this.softPolicyValue}
                        this.softPolicyArray[index] = a;
                        this.enableSoftPolicy=1;
                    }else{
                        let index = this.softPolicyArray.indexOf(itemId);
                        const a = { appId: this.newAppId ,softPolicyId : this.softPolicyId,value : this.softPolicyValue}
                        this.softPolicyArray[index] = a;
                        this.enableSoftPolicy=1;
                    }
                    break;
                }else{
                    this.enableSoftPolicy=0;
                }
            }
        }else{
            const a = { appId: this.newAppId ,softPolicyId : this.softPolicyId,value : this.softPolicyValue}
            this.softPolicyArray.push(a);
            const b = { appId: this.newAppId ,softPolicyId : 164,value : this.cpState}
            this.softPolicyArray.push(b);
        }
        if(this.enableSoftPolicy==0){
            const a = { appId: this.newAppId ,softPolicyId : this.softPolicyId,value : this.softPolicyValue}
            this.softPolicyArray.push(a);
        }
    }

 validateSoftPolicy(): void{
        let response;
        let fileName="counterParty/validateSoftPolicy/";
        let data={ softPolicyDetailsDataList : this.softPolicyArray }
        response = this.requestapi.postData(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
            this.runSoftPolicy();
        },error=>{
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
            if(error.status==400)
            		{
            			if(error.error.message == null || error.error.message == ''){
            			this.toaster.error('Some Technical Error')
            			}else{
            			this.toaster.error(error.error.message);
            			}
            		}
        })
    }

runSoftPolicy(): void {

        this.showLoader=true;
        let response;
        let fileName="counterParty/runSoftPolicy/"+this.newAppId;
        let data={ softPolicyDetailsDataList : this.softPolicyArray }
        response = this.requestapi.postData(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
        console.log("res.softPolicyResult",res.softPolicyResult);
        if(res.flag){
            this.anchorType = true;
            this.softPolicyDealerResult = res.softPolicyResult[0].dealer;
            this.softPolicyVendorResult = res.softPolicyResult[1].vendor;
        }else{
            this.anchorType = false;
            this.softPolicyResult = res.softPolicyResult;
        }
        this.saveSoftPolicyFlag= false;
        },error=>{
        this.showLoader=false;
          if(error.status==401){
          this.refresh_token=localStorage.getItem('refresh_token')
          this.authService.SignOut(this.refresh_token);
        }
        	if(error.status==400)
        			{
        				if(error.error.message == null || error.error.message == ''){
        				this.toaster.error('Some Technical Error')
        				}else{
        				this.toaster.error(error.error.message);
        				}
        			}
        })
    }

getSoftPolicyDetails(id): void{

        let response;
        let fileName="counterParty/softPolicyDetails/"+id;
        response = this.requestapi.getData(fileName);
        response.subscribe((res:any) => {

            if(res.length == 0){
            this.getSoftPolicyDetails(this.oldAppId)
                }else{
                this.updateSoftPolicyFlag = true;
                this.softPolicyArray = [];
                this.softPolicyArray = res.softPolicyDetailsDataList;
                this.runSoftPolicy();
                }
                    },error=>{
                    if(error.status==401){
                    this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                    }
            })
}
getUpdateSoftPolicyDetails(id): void{
    this.showLoader=true;
//     id=12154
    let response;
    let fileName="counterParty/softPolicyDetails/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
    if(res.length != 0){
        this.showLoader=false;
        this.softPolicyArray = res.softPolicyDetailsDataList;
        this.softPolicyArray = [];
        for(var i=0;i<this.SoftPolicyMasterList.length;i++){
            for(let subItem of this.SoftPolicyMasterList[i].softPolicyMasterSubTypeEntities){
                for(let itemValue of res.softPolicyDetailsDataList){
                if(itemValue.appId == this.oldAppId){
                this.updateSoftPolicyFlag = false;
                this.saveSoftPolicyFlag = true;
                    if(subItem.id == itemValue.softPolicyId){
                        (<HTMLInputElement>document.getElementById(subItem.name)).value = itemValue.value;
                        const a1 = { id:itemValue.id, appId: this.newAppId ,softPolicyId : itemValue.softPolicyId,value : itemValue.value}
                        this.softPolicyArray.push(a1);
                    }
                }else{
                this.updateSoftPolicyFlag = true;
                if(subItem.id == itemValue.softPolicyId){
                            (<HTMLInputElement>document.getElementById(subItem.name)).value = itemValue.value;
                            const a1 = { id:itemValue.id, appId: this.newAppId ,softPolicyId : itemValue.softPolicyId,value : itemValue.value}
                            this.softPolicyArray.push(a1);
                        }
                }
            }
        }
        if(id == this.newAppId){
            this.runSoftPolicy();
        }
        }
    }else{
        this.getUpdateSoftPolicyDetails(this.oldAppId);
    }
    },error=>{
    if(error.error.text=="empty" && id == this.newAppId){
        this.getUpdateSoftPolicyDetails(this.oldAppId);
    }
    this.showLoader=false;
    if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
   }
   })
}
softPolicyDetailsSubmit(key){
        this.saveSoftPolicyFlag = true;
        if(key == 'save'){
            this.saveSoftPolicy();
        }else if(key == 'update'){
            this.updateSoftPolicy();
        }
}
creditPolicyDetailsSubmit(key){
    let flag = false;
    let flagvalues = true;
    for (let items of this.creditProposalList){
          if(items.creditPolicyCheck == true){
            flagvalues = false;
          }
    }
     console.log(flagvalues,'flagvalues1')
    for(let item of this.creditProposalList){
       this.creditPolicyCheckResults = item.creditPolicyCheck;
       console.log(this.creditPolicyCheckResults,'this.creditPolicyCheckResults')
       if(item.creditPolicyCheck != null && (!flagvalues)){
           flag = true;
        }else{
           flag = false;
            break;
        }
    }
    for (let items of this.creditPolicyResults){
                  if(items.status == true){
                    flag = true;
                  }
            }
    if(flag){
        if(key == 'save'){
            this.saveCreditPolicy();
            this.updateCreditProposal('NA');
        }else if(key == 'update'){
            this.updateCreditPolicy();
            this.updateCreditProposal('NA');
        }
    }else{
          this.toaster.error("Kindly take action for all result & minimum one should be approved");
         }
}

saveSoftPolicy(): void {
    this.showLoader=true;
    let response;
    let fileName="counterParty/softPolicyDetails";
    let data={ softPolicyDetailsDataList : this.softPolicyArray }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
    this.showLoader=false;
        this.toaster.success('Successfully Saved');
        this.getSoftPolicyDetails(this.newAppId);
         this.softPolicyNp = false;
         this.creditCpaRemarks = true;
         this.saveSoftPolicyFlag = false;
    },error=>{
    this.showLoader=false;
        this.saveSoftPolicyFlag = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        	if(error.status==400)
        			{
        				if(error.error.message == null || error.error.message == ''){
        				this.toaster.error('Some Technical Error')
        				}else{
        				this.toaster.error(error.error.message);
        				}
        			}

    })
}

updateSoftPolicy(): void {
    this.showLoader=true;
    let response;
    let fileName="counterParty/softPolicyDetails";
    let data={ softPolicyDetailsDataList : this.softPolicyArray }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
    this.showLoader=false;
        this.toaster.success('Successfully Updated');
        this.softPolicyNp = false;
        this.creditCpaRemarks = true;
        this.saveSoftPolicyFlag = false;
        this.getSoftPolicyDetails(this.newAppId);
    },error=>{
    this.showLoader=false;
        this.saveSoftPolicyFlag = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
            }else{
                this.toaster.error(error.error.message);
            }
        }

    })
}

 ///////////////////////////////////Credit Policy//////////////////////////////////////////

 getCreditPolicyFilter(): void{
                 this.creditNormDetails = [];
                 let response;
                 let fileName="counterParty/creditPolicyFilters?id="+this.newAppId;
                 response = this.requestapi.getData(fileName);
                 response.subscribe((res: any) => {
                 this.creditPolicyFilterList=res;
                 this.getUpdateCreditPolicyDetails();
                 if(this.creditPolicyFilterList == null){
                 this.toaster.error('Credit Policy Tab Not Applicable');
                 }else{
                 this.nextUWRCollateral();
                 }

                 },error=>{
                 if(error.status==401){
                     this.refresh_token=localStorage.getItem('refresh_token')
                             this.authService.SignOut(this.refresh_token);
                                    }
                     console.log("error");
                 })

             }


getCreditPolicy(id): void{
    this.creditNormDetails = [];
    let response;
    let fileName="counterParty/creditPolicyFilters?id="+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        console.log(res,'res!')
        if(res != null && res.length != 0){
            this.creditPolicyMasterList=res;
            console.log(this.creditPolicyMasterList,'this.creditPolicyMasterList')
            if(this.creditPolicyMasterList.length>0){
                this.creditPolicyView = true;
            }
        }else{
            if(this.oldAppId != id){
                this.getCreditPolicy(this.oldAppId);
            }
        }
    },error=>{
        console.log("Error -->",error);
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

getUpdateCreditPolicyDetails(): void{
    this.showLoader=true;
    let response;
    let fileName="counterParty/creditPolicyDetails/"+this.newAppId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
    this.showLoader=false;
    if(res.creditPolicyArray != 'empty'){
        this.creditPolicyArray = res.creditPolicyArray;
        this.updateCreditPolicyFlag = true;
        this.creditPolicyArray = [];
//         for(let master of this.creditPolicyFilterList){
                            for(let itemValue of res.creditPolicyArray){
//                                 if(master.creditPolicyMasterEntity.id == itemValue.creditPolicyId){
                                    (<HTMLInputElement>document.getElementById(itemValue.scpDisplayName)).value = itemValue.value;
                                    const a1 = { id:itemValue.id, appId: itemValue.appId ,cpMasterId : itemValue.creditPolicyId,value : itemValue.value}
                                    this.creditPolicyArray.push(a1);
//                                 }
                            }
                           this.runCreditPolicy();
//                         }
    }else{
    }
    },error=>{
    this.showLoader=false;
    if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
   }

   })
}

storeCreditPolicy(id,item: any,dataType):void{
        this.creditNormDetails = [];
        this.creditPolicyResults = [];
        this.saveCreditPolicyFlag=true;
        console.log(id,'id')
        if(Event.prototype.isPrototypeOf(item)){
            this.creditPolicyValue=item.target.value;
        }else{
            this.creditPolicyValue=item.toString();
        }
        this.creditPolicyId=id;
        this.enableCreditPolicy=1;
        if(this.creditPolicyArray.length != 0){
            for(let itemId of this.creditPolicyArray){
                if(itemId.cpMasterId == this.creditPolicyId){
                    if(this.updateCreditPolicyFlag){
                        let index = this.creditPolicyArray.indexOf(itemId);
                        let pId = itemId.id
                        const c = { id:pId, appId: this.newAppId ,cpMasterId : this.creditPolicyId,value : this.creditPolicyValue,}
                        this.creditPolicyArray[index] = c;
                        this.enableCreditPolicy=1;
                    }else{
                        let index = this.creditPolicyArray.indexOf(itemId);
                        const c = { appId: this.newAppId ,cpMasterId : this.creditPolicyId,value : this.creditPolicyValue,}
                        this.creditPolicyArray[index] = c;
                        this.enableCreditPolicy=1;
                    }

                    break;
                }else{
                    this.enableCreditPolicy=0;
                }
            }
        }else{
            const c = { appId: this.newAppId ,cpMasterId : this.creditPolicyId,value : this.creditPolicyValue,}
            this.creditPolicyArray.push(c);
        }
        if(this.enableCreditPolicy==0){
            const c = { appId: this.newAppId ,cpMasterId : this.creditPolicyId,value : this.creditPolicyValue,}
            this.creditPolicyArray.push(c);
        }
    }


validateCreditPolicy(): void{
//        this.decision=desc;
        this.saveProCreditPolicy('NA');
        let response;
        let fileName="counterParty/validateCreditPolicy/";
        let data={ creditPolicyDetailsData : this.creditPolicyArray }
        response = this.requestapi.postData(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
             setTimeout(()=>{
             this.runCreditPolicy();
             },500)
        },error=>{
        this.showLoader=false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
            if(error.status==400)
            		{
            			if(error.error.message == null || error.error.message == ''){
            			this.toaster.error('Some Technical Error')
            			}else{
            			this.toaster.error(error.error.message);
            			}
            		}

        })
    }

//Credit Policy
    saveProCreditPolicy(key): void{
        let proposalFlag = true;
        console.log(this.creditProposalList,'this.creditProposalList!!')
        for(let proposals of this.creditProposalList){
            if(proposals.vintageWithAnchor=="" || proposals.vintageWithAnchor==null ){
                proposalFlag=false;
            }
            if(proposals.minMonthlyAnchor=="" || proposals.minMonthlyAnchor==null){
                proposalFlag=false;
            }
            if(proposals.anchorRelationship=="" || proposals.anchorRelationship==null){
                proposalFlag=false;
            }
        }
        if(proposalFlag){
            this.updateCreditProposal(key);
        }else{
            this.toaster.error("Please enter all fields");
        }
    }

    storeCreditPolicyStatus(id,status){
        for (let item of this.creditProposalList){
            if(item.id == id){
                let index = this.creditProposalList.indexOf(item);
                item.creditPolicyCheck = status;
                this.creditProposalList[index] = item;
                if(status){
                    (<HTMLInputElement>document.getElementById(id+'A')).disabled = true;
                     this.toaster.success('Credit Policy Result has been Successfully Approved');
                    (<HTMLInputElement>document.getElementById(id+'R')).disabled = false;
                }else{
                    (<HTMLInputElement>document.getElementById(id+'A')).disabled = false;
                    this.toaster.error('Credit Policy Result has been Rejected');
                    (<HTMLInputElement>document.getElementById(id+'R')).disabled = true;
                }
            }
        }
        console.log("this.creditProposalList",this.creditProposalList)
    }

    updateCreditProposal(key): void {
            this.showLoader=true;
            let response;
            let fileName="counterParty/updateCreditProposedProduct";
            let data={ proposedProductsDataList:this.creditProposalList }
            console.log(data,'data')
            response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
            response.subscribe((res: any) => {
                this.showLoader=false;
                if(key == 'save'){
                    this.getCreditPolicy(this.newAppId);
                    this.toaster.success('Successfully saved');
                    this.creditCheck = true;
                }else if (key == 'update'){
//                     this.getCreditPolicy(this.newAppId);
                    this.toaster.success('Successfully Updated');
                    this.creditCheck = true;
                }else if(key == 'NA'){
                    this.creditCheck = true;
                }
            },error=>{
                this.showLoader=false;
                if(error.status==401){
                    this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                }
                if(error.status==400){
                    if(error.error.message == null || error.error.message == ''){
                    this.toaster.error('Some Technical Error')
                    }else{
                    this.toaster.error(error.error.message);
                    }
                }

            })
        }

  runCreditPolicy(): void {
      this.showLoader=true;
          if(this.proposalArray[0].product == "Dealer Invoice Finance" || this.proposalArray[0].product == "Dealer Purchase Order Finance" || this.proposalArray[0].product == "Anchor Sales Bill Discounting"){
              this.cpType = "Dealer";
          }else if(this.proposalArray[0].product == "Vendor Invoice Finance" || this.proposalArray[0].product == "Vendor Purchase Order Finance" || this.proposalArray[0].product == "Anchor Purchase Bill Discounting"){
              this.cpType = "Vendor";
          }

          if(this.creditPolicyArray.length==this.creditPolicyMasterList.length){
              let response;
              let fileName="counterParty/creditPolicyMaster?apppId="+this.newAppId;
              let data={ creditPolicyDetailsData : this.creditPolicyArray }
              response = this.requestapi.postData(fileName,JSON.stringify(data));
              response.subscribe((res: any) => {
              this.showLoader=false;
  //             this.creditPolicyDetailsSubmit(this.decision);
              this.creditPolicyResults = res.creditPolicyResults;
              for(let item of this.creditProposalList){
                     console.log(item,item.creditPolicyCheck,'item.creditPolicyCheck')
                     item.creditPolicyCheck = null;
                     console.log(item,item.creditPolicyCheck,'item.creditPolicyCheck')
                  }
              if(this.creditPolicyResults == null){
              this.toaster.error('Some Technical Error');
              this.saveCreditPolicyFlag= true;
              }else{
              this.saveCreditPolicyFlag= false;
              }
              },error=>{
              this.showLoader=false;
                if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
              }
              if(error.status==400)
              		{
              			if(error.error.message == null || error.error.message == ''){
              			this.toaster.error('Some Technical Error')
              			}else{
              			this.toaster.error(error.error.message);
              			}
              		}

              })
          }else{
          this.showLoader=false;
              this.toaster.error("Kindly fill all Field");
          }
      }

runCreditPolicyseven(): void {
    this.showLoader=true;
        if(this.proposalArray[0].product == "Dealer Invoice Finance" || this.proposalArray[0].product == "Dealer Purchase Order Finance" || this.proposalArray[0].product == "Anchor Sales Bill Discounting"){
            this.cpType = "Dealer";
        }else if(this.proposalArray[0].product == "Vendor Invoice Finance" || this.proposalArray[0].product == "Vendor Purchase Order Finance" || this.proposalArray[0].product == "Anchor Purchase Bill Discounting"){
            this.cpType = "Vendor";
        }

//         if(this.creditPolicyArray.length==this.creditPolicyMasterList.length){
            let response;
            let fileName="counterParty/creditPolicyMaster?apppId="+this.newAppId;
            this.creditPolicyArrayss = [];
            for(let itemValue of this.creditPolicyArray)
            {
            const a1 = { id:itemValue.id, appId: itemValue.appId ,cpMasterId : itemValue.creditPolicyId,value : itemValue.value}
            this.creditPolicyArrayss.push(a1);
            }

            let data={ creditPolicyDetailsData : this.creditPolicyArrayss }
            response = this.requestapi.postData(fileName,JSON.stringify(data));
            response.subscribe((res: any) => {
            this.showLoader=false;
//             this.creditPolicyDetailsSubmit(this.decision);
            this.creditPolicyResults = res.creditPolicyResults;
            if(this.creditPolicyResults == null){
            this.toaster.error('Some Technical Error');
            this.saveCreditPolicyFlag= true;
            }else{
            this.saveCreditPolicyFlag= false;
            }
            },error=>{
            this.showLoader=false;
              if(error.status==401){
              this.refresh_token=localStorage.getItem('refresh_token')
              this.authService.SignOut(this.refresh_token);
            }
            if(error.status==400)
            		{
            			if(error.error.message == null || error.error.message == ''){
            			this.toaster.error('Some Technical Error')
            			}else{
            			this.toaster.error(error.error.message);
            			}
            		}

            })
//         }else{
//         this.showLoader=false;
//         console.log("this.creditPolicyAr---",this.creditPolicyArray);
// //             this.toaster.error("Kindly fill all Field");
//         }
    }

getCreditPolicyDetails(id): void{

        let response;
        let fileName="counterParty/creditPolicyDetails/"+id;
        response = this.requestapi.getData(fileName);
        response.subscribe((res:any) => {

            this.creditPolicyArray = res.creditPolicyArray;
            setTimeout(() => {
                this.runCreditPolicyseven();
            }, 1000);
        },error=>{

            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }

        })
    }

      saveCreditPolicy(): void {
           this.showLoader=true;
              let response;
              let fileName="counterParty/creditPolicyDetails";
              let data={ creditPolicyDetailsData : this.creditPolicyArray }
              console.log(this.creditPolicyArray,'creditPolicyArray!!!')
              console.log(data,'data!')
              response = this.requestapi.postData(fileName,JSON.stringify(data));
              response.subscribe((res: any) => {
              this.showLoader=false;
                  this.toaster.success('Successfully Saved');
                  this.getCreditPolicyDetails(this.newAppId);
                  this.updateCreditPolicyFlag = true
                  this.uwrCreditPolicyNp = false;
                  this.uwrRemarks = true;window.scrollTo(0, 0);
              },error=>{
              this.showLoader=false;
              if(error.status==401){
                      this.refresh_token=localStorage.getItem('refresh_token')
                              this.authService.SignOut(this.refresh_token);
                                 }
              if(error.status==400)
              		{
              			if(error.error.message == null || error.error.message == ''){
              			this.toaster.error('Some Technical Error')
              			}else{
              			this.toaster.error(error.error.message);
              			}
              		}
              })
          }

      updateCreditPolicy(): void {
         this.showLoader=true;
          let response;
//           this.creditPolicyArray.push(this.approveCreditPolicyValue)
          let fileName="counterParty/creditPolicyDetails";
          for(let item of this.creditPolicyArray){
              if(typeof item.value == 'number'){
                  item.value = item.value.toString();
              }
          }
          let data={ creditPolicyDetailsData : this.creditPolicyArray }
          response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
          response.subscribe((res: any) => {
          this.showLoader=false;
              this.toaster.success('Successfully Updated');
              this.getCreditPolicyDetails(this.newAppId);
              this.uwrCreditPolicyNp = false;
              this.uwrRemarks = true;window.scrollTo(0, 0);
          },error=>{
          this.showLoader=false;
              if(error.status==401){
              this.refresh_token=localStorage.getItem('refresh_token')
              this.authService.SignOut(this.refresh_token);
          }
           if(error.status==400)
      		{
      			if(error.error.message == null || error.error.message == ''){
      			this.toaster.error('Some Technical Error')
      			}else{
      			this.toaster.error(error.error.message);
      			}
      		}
          })
      }

      //////////////////////////DebtProfile//////////////////////
      public debtProfileValidate = false;
      public DebtProfile2 = false;

          getDebtProfileById(id): void {

             this.showLoader=true;
              let response
              let fileName="counterParty/cpDebtProfile/"+id;
              response = this.requestapi.getData(fileName);
              response.subscribe((res: any) => {
                  this.showLoader=false;
                if(res.length != 0){
                    if(id == this.newAppId){
                        this.DebtProfile2=true;
                    }
                    this.debtProfileList=res;
                    if(res.length > 1 && this.debtProfileArray.length != res.length){
                        for(var j=1;j<res.length;j++){
                            this.addDebtProfileValue();
                        }
                    }
                    for(var i=0;i<res.length;i++){
                        this.debtProfileArray[i].id = res[i].id;
                        this.debtProfileArray[i].appId = res[i].applicationEntity.id;
                        this.debtProfileArray[i].bankFI = res[i].bankFI;
                        this.debtProfileArray[i].facilityType = res[i].facilityType;
                        this.debtProfileArray[i].tenure = res[i].tenure;
                        this.debtProfileArray[i].sanctionDate1 = this.datePipe.transform(res[i].sanctionDate, 'yyyy-MM-dd');
                        this.debtProfileArray[i].sanctionLimit = res[i].sanctionLimit;
                        this.debtProfileArray[i].outstandingOnDate = res[i].outstandingOnDate;
                        this.debtProfileArray[i].emi = res[i].emi;
                        this.debtProfileArray[i].interestRate = res[i].interestRate;
                        this.debtProfileArray[i].security = res[i].security;
                        this.debtProfileArray[i].specificLimit = res[i].specificLimit;
                        this.debtProfileArray[i].remarks = res[i].remarks;
                    }
                }else{
                if(id == this.newAppId){
                    this.getDebtProfileById(this.oldAppId);
                }
                }
              },error=>{
              this.showLoader=false;
                  if(error.status==401){
                      this.refresh_token=localStorage.getItem('refresh_token')
                      this.authService.SignOut(this.refresh_token);
                  }

              })
          }


          public debtProfileSubmit(key) {
              this.debtProfileValidate = false;
              this.debtProfileValidate = !this.debtProfileValidate;
              var debtProfileFlag = true;
              for(let debtProfileArr of this.debtProfileArray){
              if(debtProfileArr.bankFI=="" || debtProfileArr.bankFI==null){
                  debtProfileFlag=false;
              }
              if(debtProfileArr.facilityType=="" || debtProfileArr.facilityType==null){
                  debtProfileFlag=false;
              }
              if(debtProfileArr.tenure=="" || debtProfileArr.tenure==null){
                  debtProfileFlag=false;
              }
             if(debtProfileArr.sanctionLimit=="" || debtProfileArr.sanctionLimit==null){
                  debtProfileFlag=false;
              }
              if(debtProfileArr.outstandingOnDate=="" || debtProfileArr.outstandingOnDate==null){
                  debtProfileFlag=false;
              }
              if(debtProfileArr.emi=="" || debtProfileArr.emi==null){
                  debtProfileFlag=false;
              }
              if(debtProfileArr.interestRate=="" || debtProfileArr.interestRate==null){
                  debtProfileFlag=false;
              }
              if(debtProfileArr.security=="" || debtProfileArr.security==null){
                  debtProfileFlag=false;
              }
              if(debtProfileArr.specificLimit=="" || debtProfileArr.specificLimit==null){
                  debtProfileFlag=false;
              }
              if(debtProfileArr.remarks=="" || debtProfileArr.remarks==null){
                  debtProfileFlag=false;
              }
              if(debtProfileArr.sanctionDate1!='' || debtProfileArr.sanctionDate1!=null){
                  debtProfileArr.sanctionDate=this.datePipe.transform(debtProfileArr.sanctionDate1, 'MM-dd-yyyy');
              }
              if(debtProfileArr.sanctionDate1=='' || debtProfileArr.sanctionDate1==null){
                  debtProfileFlag = false;
              }
              }
              if(this.debtProfileValidate==true && debtProfileFlag == true ){
                  if(key == 'save'){
                      this.saveDebtProfile();
                  }else if(key == 'update'){
                      this.updateDebtProfile();
                  }

              }
          }

          getDebtProfile(id): void {
              this.showLoader=true;
              let response
              let fileName="counterParty/cpDebtProfile/"+id;
              response = this.requestapi.getData(fileName);
              response.subscribe((res: any) => {
                  this.showLoader=false;
                  if(res.length != 0){
                  if(id == this.newAppId){
                      this.DebtProfile2=true;
                  }
                  this.debtProfileList=res;
                      for(var i=0;i<res.length;i++){
                          this.debtProfileArray[i].id = res[i].id;
                          this.debtProfileArray[i].appId = res[i].applicationEntity.id;
                          this.debtProfileArray[i].bankFI = res[i].bankFI;
                          this.debtProfileArray[i].facilityType = res[i].facilityType;
                          this.debtProfileArray[i].tenure = res[i].tenure;
                          this.debtProfileArray[i].sanctionDate1 = this.datePipe.transform(res[i].sanctionDate, 'yyyy-MM-dd');
                          this.debtProfileArray[i].sanctionLimit = res[i].sanctionLimit;
                          this.debtProfileArray[i].outstandingOnDate = res[i].outstandingOnDate;
                          this.debtProfileArray[i].emi = res[i].emi;
                          this.debtProfileArray[i].interestRate = res[i].interestRate;
                          this.debtProfileArray[i].security = res[i].security;
                          this.debtProfileArray[i].specificLimit = res[i].specificLimit;
                          this.debtProfileArray[i].remarks = res[i].remarks;
                      }
                  }else{
                    this.getDebtProfile(this.oldAppId);
                  }
              },error=>{
                  this.showLoader=false;
                  if(error.status==401){
                      this.refresh_token=localStorage.getItem('refresh_token')
                      this.authService.SignOut(this.refresh_token);
                  }

              })
          }

      saveDebtProfile(): void {
          this.showLoader=true;
          let response;
          console.log(this.debtProfileArray,'this.debtProfileArray!')
          let fileName="counterParty/cpDebtProfile/"+this.newAppId;
          let data={ cpDebtProfileDataList:this.debtProfileArray }
          response = this.requestapi.postData(fileName,JSON.stringify(data));
          response.subscribe((res: any) => {
          this.showLoader=false;
      //         this.DebtProfile1 = false;
              this.DebtProfile2 = true;
              this.toaster.success('Successfully Saved')
              this.getDebtProfile(this.newAppId);
          },error=>{
          this.showLoader=false;
              if(error.status==401){
                  this.refresh_token=localStorage.getItem('refresh_token')
                  this.authService.SignOut(this.refresh_token);
              }
              	if(error.status==400)
              			{
              				if(error.error.message == null || error.error.message == ''){
              				this.toaster.error('Some Technical Error')
      //         				this.DebtProfile1 = true;
              				}else{
                                  var msg =error.error.message;
                                  for(let i=0 ; i<this.debtProfileArray.length;i++){
                                      // Use of String replace() Method
                                      msg = msg.replaceAll("cpDebtProfileDataList["+i+"]", i+1);
                                  }
                                  this.toaster.error(msg);
      //         				this.DebtProfile1 = true;
              				}
              			}
          })
      }

      updateDebtProfile(): void {
          this.showLoader=true;
          let response;
          let fileName="counterParty/cpDebtProfile/"+this.newAppId;
          let data={ cpDebtProfileDataList : this.debtProfileArray }
          response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
          response.subscribe((res: any) => {
              this.getDebtProfile(this.newAppId);
              this.showLoader=false;
              this.toaster.success('Successfully Updated');
          },error=>{
              this.showLoader=false;
              if(error.status==401){
                  this.refresh_token=localStorage.getItem('refresh_token')
                  this.authService.SignOut(this.refresh_token);
              }
              if(error.status==400){
                  if(error.error.message == null || error.error.message == ''){
                      this.toaster.error('Some Technical Error')
                  }else{
                      var msg =error.error.message;
                      for(let i=0 ; i<this.debtProfileArray.length;i++){
                          // Use of String replace() Method
                          msg = msg.replaceAll("cpDebtProfileDataList["+i+"]", i+1);
                      }
                      this.toaster.error(msg);
                  }
              }

          })
      }

      deleteDebtProfile(index,id,clearKey): void{
          this.showLoader=true;
          let response;
          let fileName="counterParty/cpDebtProfile/"+id;
          response = this.requestapi.deleteData(fileName);
          response.subscribe((res:any) => {
              if(clearKey){
                  this.debtProfileArray.splice(index, 1);
              }else{
                  this.debtProfileArray.splice(index, 1);
                  this.debtProfileValidate = false;
                  this.debtProfileArray.push(this.newDebtAttribute)
                  this.newDebtAttribute = {};
              }
              console.log("this.debtProfileArray.length",this.debtProfileArray.length);
              if(this.debtProfileArray.length == 1){
                  console.log("this.debtProfileArray== 1",this.debtProfileArray.length);
                  for(let debtProfileArr of this.debtProfileArray){
                      if(debtProfileArr.bankFI=="" || debtProfileArr.bankFI==null){
                          this.DebtProfile2=false;
                          break;
                      }
                      if(debtProfileArr.facilityType=="" || debtProfileArr.facilityType==null){
                          this.DebtProfile2=false;
                          break;
                      }
                      if(debtProfileArr.tenure=="" || debtProfileArr.tenure==null){
                          this.DebtProfile2=false;
                          break;
                      }
                      if(debtProfileArr.sanctionLimit=="" || debtProfileArr.sanctionLimit==null){
                          this.DebtProfile2=false;
                          break;
                      }
                      if(debtProfileArr.outstandingOnDate=="" || debtProfileArr.outstandingOnDate==null){
                         this.DebtProfile2=false;
                         break;
                      }
                      if(debtProfileArr.emi=="" || debtProfileArr.emi==null){
                         this.DebtProfile2=false;
                         break;
                      }
                      if(debtProfileArr.interestRate=="" || debtProfileArr.interestRate==null){
                         this.DebtProfile2=false;
                         break;
                      }
                      if(debtProfileArr.security=="" || debtProfileArr.security==null){
                         this.DebtProfile2=false;
                         break;
                      }
                      if(debtProfileArr.specificLimit=="" || debtProfileArr.specificLimit==null){
                         this.DebtProfile2=false;
                         break;
                      }
                      if(debtProfileArr.remarks=="" || debtProfileArr.remarks==null){
                         this.DebtProfile2=false;
                         break;
                      }
                      if(debtProfileArr.sanctionDate1=='' || debtProfileArr.sanctionDate1==null){
                         this.DebtProfile2=false;
                         break;
                      }
                  }
              }
              this.showLoader=false;
          },error=>{
              this.showLoader=false;
              if(error.status==401){
                  this.refresh_token=localStorage.getItem('refresh_token')
                  this.authService.SignOut(this.refresh_token);
              }
              if(error.status==400){
                  this.toaster.error(error.error.message);
              }
          })
      }

//Rm Cust Acceptance

checkboxProd(event): void{
    this.eve = event.target.checked;
}

checkboxAnchor(event): void{
    this.eveAnchor = event.target.checked;
}

checkboxRegular(event,i): void{
    if(event.target.checked){
        (<HTMLInputElement>document.getElementById("regularLimitRemarks"+i)).disabled = false;
    }else{
        (<HTMLInputElement>document.getElementById("regularLimitRemarks"+i)).disabled = true;
    }
    this.eveRegular = event.target.checked;
}

checkboxAdhoc(event,i): void{
    if(event.target.checked){
        (<HTMLInputElement>document.getElementById("adhocLimitRemarks"+i)).disabled = false;
    }else{
         (<HTMLInputElement>document.getElementById("adhocLimitRemarks"+i)).disabled = true;
     }
    this.eveAdhoc = event.target.checked;
}

checkboxCredit(event,i): void{
    if(event.target.checked){
        (<HTMLInputElement>document.getElementById("creditPeriodRemarks"+i)).disabled = false;
    }else{
         (<HTMLInputElement>document.getElementById("creditPeriodRemarks"+i)).disabled = true;
     }
    this.eveCredit = event.target.checked;
}

checkboxDoor(event,i): void{
    if(event.target.checked){
        (<HTMLInputElement>document.getElementById("doorRemarks"+i)).disabled = false;
    }else{
         (<HTMLInputElement>document.getElementById("doorRemarks"+i)).disabled = true;
     }
    this.eveDoor = event.target.checked;
}

checkboxInvoice(event,i): void{
if(event.target.checked){
        (<HTMLInputElement>document.getElementById("invoiceAgeingRemarks"+i)).disabled = false;
    }else{
         (<HTMLInputElement>document.getElementById("invoiceAgeingRemarks"+i)).disabled = true;
     }
    this.eveInvoice = event.target.checked;
}

checkboxMargin(event,i): void{
if(event.target.checked){
        (<HTMLInputElement>document.getElementById("marginRemarks"+i)).disabled = false;
    }else{
         (<HTMLInputElement>document.getElementById("marginRemarks"+i)).disabled = true;
     }
    this.eveMargin = event.target.checked;
}

checkboxInterest(event,i): void{

if(event.target.checked){
        (<HTMLInputElement>document.getElementById("interestRateRemarks"+i)).disabled = false;
    }else{
         (<HTMLInputElement>document.getElementById("interestRateRemarks"+i)).disabled = true;
     }
    this.eveInterest = event.target.checked;
}

checkboxPf(event,i): void{
if(event.target.checked){
        (<HTMLInputElement>document.getElementById("pfRemarks"+i)).disabled = false;
    }else{
         (<HTMLInputElement>document.getElementById("pfRemarks"+i)).disabled = true;
     }
    this.evePf = event.target.checked;
}


checkboxRenewal(event,i): void{
if(event.target.checked){
        (<HTMLInputElement>document.getElementById("renewalRemarks"+i)).disabled = false;
    }else{
         (<HTMLInputElement>document.getElementById("renewalRemarks"+i)).disabled = true;
     }
    this.eveRenewal = event.target.checked;
}

checkboxInterestType(event,i): void{
if(event.target.checked){
        (<HTMLInputElement>document.getElementById("interestTypeRemarks"+i)).disabled = false;
    }else{
         (<HTMLInputElement>document.getElementById("interestTypeRemarks"+i)).disabled = true;
     }
    this.eveInterestType = event.target.checked;
}
checkboxRenewalPeriod(event,i): void{
if(event.target.checked){
        (<HTMLInputElement>document.getElementById("renewalPeriodRemarks"+i)).disabled = false;
    }else{
         (<HTMLInputElement>document.getElementById("renewalPeriodRemarks"+i)).disabled = true;
     }
    this.eveRenewalPeriod = event.target.checked;
}

getCommercialCcGetById(id): void{
    this.showLoader=true;
    let response;
    let fileName="counterParty/commercialCc/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
    this.showLoader=false;
    console.log("1")
        this.commercialCcList=res;
//       var len = res.length-1
//       for(var j=0;j< len;j++){
//        this.addCommercialCcValue()
//       }
       console.log("Yes he is here")
        if(res.length != 0){
        console.log("Yes he is here")
//           this.commercial2 = true
            for(var i=0;i< res.length;i++){
                this.commercialArray[i].id = res[i].id;
                this.commercialArray[i].appId= res[i].applicationEntity.id;
                this.commercialArray[i].productRemarks = res[i].productRemarks;
                this.commercialArray[i].anchorNameRemarks = res[i].anchorNameRemarks;
                this.commercialArray[i].regularLimitRemarks = res[i].regularLimitRemarks;
                this.commercialArray[i].adhocLimitRemarks = res[i].adhocLimitRemarks;
                this.commercialArray[i].creditPeriodRemarks = res[i].creditPeriodRemarks;
                this.commercialArray[i].doorRemarks = res[i].doorRemarks;
                this.commercialArray[i].invoiceAgeingRemarks = res[i].invoiceAgeingRemarks;
                this.commercialArray[i].marginRemarks = res[i].marginRemarks;
                this.commercialArray[i].interestRateRemarks = res[i].interestRateRemarks;
                this.commercialArray[i].pfRemarks = res[i].pfRemarks;
                this.commercialArray[i].renewalRemarks = res[i].renewalRemarks;
                this.commercialArray[i].interestTypeRemarks = res[i].interestTypeRemarks;
                this.commercialArray[i].renewalPeriodRemarks = res[i].renewalPeriodRemarks;
            }
        }else{
        if(id == this.newAppId){
        this.getCommercialCcGetById(this.oldAppId)
        }
       }
       if(id ==this.oldAppId){
           this.commercial2=false;
//            this.fundRequirementControl = false;
           }else{
           this.commercial2=true;
//            this.fundRequirementControl = false;
           }
        console.log("this.commercialArray ::::",this.commercialArray)
    },error=>{
    this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

saveCommercialCc(): void {
       this.showLoader=true;
        let response;
        let fileName="counterParty/commercialCc/"+this.newAppId;
        let data={commercialDataList:this.commercialArray}
        response = this.requestapi.postData(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
           this.commercial1  = false;
            this.commercial2  = true;
            this.toaster.success('Successfully Saved')
            this.getCommercialCcGetById(this.newAppId);
        },error=>{
        this.showLoader=false;
        if(error.status==401){
                        this.refresh_token=localStorage.getItem('refresh_token')
                                this.authService.SignOut(this.refresh_token);
                           }
						   if(error.status==400)
        		{
        			if(error.error.message == null || error.error.message == ''){
        			this.toaster.error('Some Technical Error')
        			this.commercial1 = true;
        			}else{
                        var msg =error.error.message;
                        for(let i=0 ; i<this.commercialArray    .length;i++){
                            // Use of String replace() Method
                            msg = msg.replaceAll("commercialDataList["+i+"]", i+1);
                        }
                        this.toaster.error(msg);
                      this.commercial1  = true;
					  }
}
        })
    }

    updateCommercialCc():void{

           this.showLoader=true;
            let response;
            let fileName="counterParty/commercialCc";
           let data={commercialDataList:this.commercialArray}
            response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
            response.subscribe((res: any) => {
            this.showLoader=false;
               this.commercial1  = false;
                this.commercial2  = true;
                this.toaster.success('Successfully Updated')
            },error=>{
            this.showLoader=false;
            if(error.status==401){
                            this.refresh_token=localStorage.getItem('refresh_token')
                                    this.authService.SignOut(this.refresh_token);
                               }
                this.commercial1  = true;

            })

    }


}