import { Component, OnInit,ViewChild,ElementRef, Renderer2, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { ApiRequestService } from 'src/app/shared/services/api-request.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import {WizardComponent, ConfigurableNavigationMode} from 'angular-archwizard';
import { Observable } from 'rxjs/internal/Observable';
import { of } from 'rxjs/internal/observable/of';
import $ from "jquery";
import { Router } from '@angular/router';
    import { CommonModule,DatePipe } from '@angular/common';
import { AuthService } from 'src/app/shared/services/firebase/auth.service';
import { timer } from 'rxjs';

import {ActivatedRoute} from '@angular/router';
declare var require
const Swal = require('sweetalert2')
import { environment } from '../../../../../environments/environment';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-new-anchor-details',
  templateUrl: './new-anchor-details.component.html',
  styleUrls: ['./new-anchor-details.component.scss']
})

export class NewAnchorDetailsComponent implements OnInit,OnDestroy {
options$: Observable<string[]>;
historyData:any;
historyData1:any;
deferralStatus:any;
 viewData:[]=[];
 @ViewChild(WizardComponent)

public wizard: WizardComponent;
public loading = false;
public creditsubmitflag = true;
public beneficiaryflag = true;
public creditNorms1 = true;
public creditNorms2 = false;
public cnControl = false;
public beneficiary1 = true;
public beneficiary2 = false;
public editBeneficiaryIcon = false;
public editBeneficiary = false;
public DeferralType = true;

public opsMaker = false;
public docValidationCheck = false;
public deferralCheck = false;
public docProductCheck = false;
public deferralDocView = false;
public otherDocLabelView = false;
public otherDocDefLabelView = false;


public opsMakerDocEdit = false;
public anchorFileDisable = false;
public deferralBasicDetails = true;
//key Person Details
rmNameArray: Array<any> = [];
keyPersonArray: Array<any> = [];
interestOwnerShipArray :any = {};
anchorProgramNormsArray :Array<any> = [];
DeferralDocArray: Array<any> = [];
newOtherDeferralAttribute:any ={};
AuthorisedArray :Array<any> = [];
newKeyPersonAttribute: any = {};
newAuthorisedAttribute : any = {};
newAnchorProgramNorms :any = {};
newDeferralAttribute:any ={};
DocListEntity:any;
status:any;
documentName:any;
addDeferralDocArray(){
this.DeferralDocArray.push(this.newDeferralAttribute)
this.newDeferralAttribute = {};
}
addKeyPersonValue() {
  this.keyPersonArray.push(this.newKeyPersonAttribute)
  this.newKeyPersonAttribute = {};
}
deleteKeyPersonValue(index) {
  this.keyPersonArray.splice(index, 1);
}

addAnchorProgramNormsValue(){
  this.anchorProgramNormsArray.push(this.newAnchorProgramNorms)
  this.newAnchorProgramNorms = {};
}
deleteAnchorProgramNormsValue(index) {
  this.anchorProgramNormsArray.splice(index, 1);
}

addAuthorisedValue(){
  this.AuthorisedArray.push(this.newAuthorisedAttribute)
  this.newAuthorisedAttribute = {};
}
deleteAuthorisedValue(index) {
  this.AuthorisedArray.splice(index, 1);
}

//GST Details
gstDetailsArray: Array<any> = [];
newGstDetailsAttribute: any = {};
addGstDetailsValue() {
  this.gstDetailsArray.push(this.newGstDetailsAttribute)
  this.newGstDetailsAttribute = {};
}
deleteGstDetailsValue(index) {
  this.gstDetailsArray.splice(index, 1);
}

//Others Document
    otherDocumentArray: Array<any> = [];
    newOtherDocumentAttribute: any = {};
    addOtherDocument(docListId,deferral) {
      this.newOtherDocumentAttribute ={
        appId : this.uploadId,
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

    //Deferral
     approvalRemarks:any;

     addOtherDeferralDocArray(){
     this.OtherDeferralDocArray.push(this.newOtherDeferralAttribute)
     this.newOtherDeferralAttribute = {};
     }
     deleteOtherDeferralDocArray(index){
     this.OtherDeferralDocArray.splice(index,1);
     }

//Anchor Address Master
anchorAddressMaster: Array<any> = [];

//Anchor Basic Details
anchorBasicDetailsId:any;anchorName:any;industry:any;sector:any;cinNumber:any;panNumber:any;incorporateDateDate:any;address1:any;address2:any;city:any;state:any;country:'India';pinCode:any;addressLine1:any;addressLine2:any;addressDetailsId:any;incorpdate:any;activity:any;constitution:any;docConstitution:any;businessExpiry:any;

//Anchor Authorized Person
anchorAuthoriseId:any;authorisedName:any;authorisedType:any;authorisedMobile:any;authorisedEmail:any;

//Anchor GST Details
anchorGstDetailsId:any;gstDetailNumber:any;gstDetailAccountHolder:any;gstDetailAddress1:any;gstDetailAddress2:any;gstDetailCountry='India';gstDetailState:any;gstDetailCity:any;gstDetailPinCode:any;

//Anchor ProgramNorms Details
programDetailsId:any;programFundingType:any;programOverallLimit:any;programRegularLimit:any;programAdhocLimit:any;programOnboardingDate:any;programCpMinLimit:any;programCpMaxLimit:any;programPricingRoiMin:any;programPricingRoiMax:any;programInterestAppType:any;programInterestOwnership:any;programInterestPaymentFrequency:any;programTenure:any;programDoor1:any;programSecurityCoveragePrimary:any;programFundingPercent:any;programSecurityCoverageSecondary:any;programProcessingFeeMin:any;programProcessingFeeMax:any;programInterestCalculation:any;programGracePeriod:any;programMaxDrawDown:any;programTriggerDays:any;programRepaymentNature:any;programRepaymentType:any;programType1:any;programTransactionType:any;programReviewFrequency:any;programReviewDate:any;programCompoundInterest:any;programCompoundInterestOverdue:any;programPenalInterest:any;programInterestRate:any;programSubProduct:any;invoiceAgeing:any;anchorProgramNorms:any;

//Beneficiary Details
bankDetails:any;
bankBranchDetails:any;
benPkId:any;beneficiaryType:any;beneficiaryName:any;bankName:any;bankCode:any;bankAccountNumber:any;bankIfscCode:any;bankBranchName:any;bankBranchCode:any;

rmName:any;rmNames:any;

otherDocRes:any;otherDefDocRes:any;

storeProcedureFlag:any;
pan:any;vintage;vintageBusiness:any;corpGuarantee:any;annualPurchase:any;pat:any;anchorDependency:any;
currentRatio:any;leverage:any;bank:any;bureau:any;commercial:any;consumer:any;gst:any;watchout:any;
crimeChecks:any;dupeCheck:any;anchorReco:any;beneficiary:any;docUpload:any;cin:any;cinDetails:any;incorporateDate:any;  stateGst:any;cityGst:any;pinCodeGst:any;overallLimit:any;doorToDoor:any;regularLimit:any;funding:any;adhocLimit:any;securityCoverage:any;
cpMinLimit:any;cpMaxLimit:any;onboardingDate:any;processingFee:any;gracePeriod:any;interestAppTye:any;
penalInterest:any;tenure:any;doorToDoor1:any;fundingPercent:any;securityCovgPrimary:any;securityCovgSecondry:any;
processingFeesMin:any;processingFeesMax:any;interestCalculation:any;counterPartyGracePeriod:any;maxDrawdown:any;
repaymentType:any;repaymentNature:any;stopSupplyTriggerDays:any;message:any;index1:any;

anchorNameMaker:any;cusId:any;date:Date;flag:any;OnLMS:any;vintageName:any;credId:any;minVintageWithAnchor:any;
minBusinessVintage:any;grpOfVintageCorpGuarantee:any;minAnuPurchAnchor:any;adjustedLeverage:any;
bankStatement:any;deDupeChecks:any;watchOutInvestor:any;active:any;networth:any;netWorth:any;anchorReco1:any;
customerMob:any;customerEmail:any;uploadId:any;file:File;fileUpload:any;anchorBusVin:any;anchorgroupVin:any;
anchorMinAnu:any;anchorAdjLeverage:any;anchorDependency2:any;anchorBankstatement:any;anchorBureau:any;anchorCommercial:any;
anchorConsumer:any;anchorCurrentRation:any;anchorReco2:any;anchorPat1:any;anchorNetworth:any;anchorWatchOut:any;
anchorDeDupe2:any;creditNormsNext:any;beneficiaryNext:any;customerNext:any;description:any;programNormsRemark:any;
opsMakerRemark:any;description1:any;description2:any;description3:any;messageId:any;appId:any;
pan_Number:any;
searchAnchor:any;
typeVendor:any;
bankNameId:any;bankList:any;
branchList:any;
creditList:any;

remarkWf:any;
creditNormsId:any;creditNormsValue:any;creditNormsflag:any;enable:any;

document:File;
fileName:any; FileName:any;

docSubTypeName:any;docEnable:any;
bool:any;mandatoryFlag:any;

documentTypeUpload:any;documentTypeNameUpload:any;documentSubTypeUpload:any;documentSubTypeNameUpload:any;

file_name:any;

fileID:any;

prodCheck:any;
prodStatic=false;

//Deferral
public deferralApproval = false; public deferralRemarks = false; public workFlowRemarks=false;public defStatus = false; public OtherDoc = false; public deferrDoc = false;
public approveDisableOther = false; public waiveOffDisableOther = false; public approveDisable1Other = true; public waiveOffDisable1Other = false; public approveDisable3Other = false; public waiveOffDisable3Other = true;

public validate = false;
public creditValidate = false;
public beneficiaryValidate = false;
public creditNormValidate = false;
public programNormsUpdateFlag = false;

public updateAnchorBasicValidate = false;
public updateAnchorAddressValidate = false;
public updateAnchorKeyValidate = false;
public updateAnchorGstValidate = false;
public updateAnchorAuthValidate = false;
public updateAnchorProgramValidate = false;
public keyPersonUpdate = false;
public AuthorizedUpdate = false;
public GstUpdate = false;
public anchorBasicUpdate = false;
public anchorAddressUpdate = false;

///Next & Previous///
public deDupeNp = true;public fileUploadNp = false;
public pNBasicDetailsNp = true;public pNAnchorDetailsNp = false;public pNProgramNormsNp = false;public pNCreditNormsNp = false; public pNRemarksNp = false;
public cscBasicDetailsNp = true;public cscAnchorDetailsNp = false;public cscProgramNormsNp = false;public cscCreditNormsNp = false; public cscRemarksNp = false;
public opsMakerBasicDetailsNp = true;public opsMakerAnchorDetailsNp = false; opsMakerProgramNormsNp = false;public opsMakerCreditNormsNp = false;public opsMakerBeneficiaryNp = false;public opsMakerDocumentNp = false;public  opsMakerRemarksNp = false;
public opsCheckerBasicDetailsNp = true;public opsCheckerAnchorDetailsNp = false; opsCheckerProgramNormsNp = false;public opsCheckerCreditNormsNp = false;public opsCheckerBeneficiaryNp = false;public opsCheckerDocumentNp = false;public  opsCheckerRemarksNp = false;
public cscRemarks = false;
private subscription: Subscription;
public approveDisable1 = true;public waiveOffDisable1 = false;public approveDisable3 = false;public waiveOffDisable3 = true; public approveHide=false; public mandateApprove=true; public mandateWaiveOff=true;

  constructor(public authService: AuthService,private requestapi:ApiRequestService,
   private _formBuilder: FormBuilder,
    private toaster: ToastrService,private el: ElementRef,
    private renderer: Renderer2,public router: Router,public datePipe:DatePipe

  ) {

      const currentDate = new Date();
      var min = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate() + 2);
      var max = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate() + 32);
      var docCom = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate() + 1);
      this.minDate = min.toISOString().split('T')[0];
      this.maxDate = max.toISOString().split('T')[0];
      this.docComDate = docCom.toISOString().split('T')[0];


    this.subscription = this.requestapi.getData1().subscribe(data => {
        if(data){
          this.historyData = data.id;
          this.deferralStatus = data.status;
        }
    });
   this.options$=of(["Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhatisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttarakhand","Uttar Pradesh","West Bengal"]);

   }

anchorBasicList:string[]=[];
anchorAddressList:string[]=[];
anchorBeneficiaryList:[]=[];
anchorCreditNormsList:any;
anchorAuthorizedDetailsList:string[]=[];
anchorGstDetailsList:string[]=[];
anchorKeyDetailsList:string[]=[];
anchorProgramDetailList:string[]=[];
CreditNormsDetailsList:string[]=[];
anchorListCust:string[]=[];
anchorBasicDetails:[]=[];
anchorAddressDetails:[]=[];
anchorKeyDetails:[]=[];
AnchorProgramList:[]=[];
fileUploadAnchorGstList:[]=[];
anchorAuthoriseDetails:[]=[];
creditNormsList:[]=[];
documentType:[]=[];
docSubMainName:[]=[];
docExtension=["pdf", "xls", "xlsx", "doc", "docx", "txt", "png", "jpg", "jpeg","zip"];
docReports: Array<any> = [];
fileUploadAnchorCreditNormsList:[]=[];
fileUploadAnchorBeneficiaryList:[]=[];
anchorProgramNormsRemarkList:[]=[];
anchorCredSubRemarkList:[]=[];
anchorOpsCheckerRemarkList:[]=[];
anchorOpsMakerRemarkList:[]=[];
customerDeDupeList:[]=[];
creditNormsDetailsList:[]=[];
remarkArray:[]=[];

//Other deferral
OtherDeferralDocArray: Array<any> = [];
OtherDocReports: Array<any> = [];


benType:any;
name:any;mobile:any;
emailId:any;gstNumber:any;gstAccntHolderName:any;gstAddress:any;gstAddress2:any;gstCountry='India';gstState:any;
gstCity:any;gstPincode:any;type:any;keyName:any;keyMobile:any;keyEmailId:any;anchorName2:any;panNumber2:any;
cinNumber2:any;fundingType:any;overallProgLimit:any;regularProgLimit:any;adhocProgLimit:any;anchorOnboardingDate:any;
cpMinLimit1:any;cpMaxLimit1:any;pricingRoiMin:any;pricingRoiMax:any;interestOwnerShip:any;interestPaymentFrequency:any;
anchorCrime:any;anchorDependencyPerMonth:any;

statutory:any;positiveATNW:any;salesGrowth:any;
churn:any;churnBounces:any;penalAndOtherCharges:any;
tolATNW:any;workingCapital:any;bureauScore:any;currentOverdues:any;
currentOverduesCredit:any;sma1:any;sma2:any;sma3:any;sub:any;wilful:any;
bureauScoreConsumer:any;currentOverduesConsumer:any;currentOverduesCreditConsumer:any;
wilfulSuit:any;restructured:any;sma0:any;epfoDelay:any;gstPaymentDelay:any;geoRestriction:any;
entityGst:any;entityPanNumber:any;entityUrc:any;entityEstablishment:any;individualPan:any;individualAadhaar:any;
firstComplete:any;
public firstWip='allow'
secondComplete:any;
secondWip:any;
thirdComplete:any;
thirdWip:any;
fourthComplete:any;
fourthWip:any;
fifthComplete:any;
fifthWip:any;
sixComplete:any;
sixWip:any;
minDate:any; maxDate:any;docComDate:any;

wilfulConsu:any;geoResti:any;gstDelay:any;curODCred:any;wilfulComer:any;sma3Comer:any;smaComer2:any;sma1Comer:any;
sma0Comer:any;gstCerti:any;busPan:any;promGuarOvd:any;promGuarPan:any;minimumTurnOver:any;satisfactoryReport:any;marketCheck:any;paymentHistory:any;pbt:any;

sta=false;
//Credit Norms Details
CreditNormsMasterList: Array<any> = [];
creditNormsArray: Array<any> = [];
//Document OPS Maker
documentMaster:Array<any> = [];
documentArray: Array<any> = [];

//Document mandatory
docValidationDataArray : Array<any> = [];
deferralDocumentArray: Array<any> = [];
deferralDocReportArray: Array<any> = [];

//unUploaded Document
anchorMissingDocument :Array<any> = [];

rbacArray: Array<any> = [];

//deferral
fileUploadAnchorBasicList:[]=[];

a1View=false;
a1Submit=false;
a1Return= false;
a1Approve= false;
a1Reject= false;
a1Edit=false;

a2View=false;
a2Submit=false;
a2Return= false;
a2Approve= false;
a2Reject= false;
a2Edit=false;


a3View=false;
a3Submit=false;
a3Return= false;
a3Approve= false;
a3Reject= false;
a3Edit=false;

a4View=false;
a4Submit=false;
a4Return= false;
a4Approve= false;
a4Reject= false;
a4Edit=false;

a5View=false;
a5Submit=false;
a5Return= false;
a5Approve= false;
a5Reject= false;
a5Edit=false;

a6View=false;
a6Submit=false;
a6Return= false;
a6Approve= false;
a6Reject= false;
a6Edit=false;



ngOnInit(): void {
    console.log("this.historyData",this.historyData);
    this.flag=0;
    this.getRMNames();
    this.getCreditNormsLabels();
    this.roleBasedFun();
    this.getCreditNorms();

    this.emailId=localStorage.getItem('email')
    if(this.emailId==null){
        localStorage.clear();
        this.router.navigate(['/auth/login']);
    }
    this.getDocumentType();
    if(this.historyData){
        this.currentStepperFun(this.historyData);
        this.uploadId=this.historyData;
    }
    this.getPending();
}

ngOnDestroy() {
    this.requestapi.clearData();
}
secondId:any;
thirdId:any;
fourthId:any;
fifthId:any;
sixId:any;
refresh_token:any;
getPending():void {

  let response;
  let fileName="wfApprovalStatus/getFinalWFStatus/"+localStorage.getItem('email');
  response = this.requestapi.getData(fileName);
  response.subscribe((res: any) => {

  if(res.status==true){
  for(let i=0;i<res.wfTableDataList.length;i++){
    if(res.wfTableDataList[i].currentStage=='A2' && res.wfTableDataList[i].appId==this.uploadId){
        this.secondId=res.wfTableDataList[i].nextStageId;
    }
    if(res.wfTableDataList[i].currentStage=='A3' && res.wfTableDataList[i].appId==this.uploadId){
            this.thirdId=res.wfTableDataList[i].nextStageId;
        }
    if(res.wfTableDataList[i].currentStage=='A4' && res.wfTableDataList[i].appId==this.uploadId){
            this.fourthId=res.wfTableDataList[i].nextStageId;
        }
    if(res.wfTableDataList[i].currentStage=='A5' && res.wfTableDataList[i].appId==this.uploadId){
            this.fifthId=res.wfTableDataList[i].nextStageId;
        }
     if(res.wfTableDataList[i].currentStage=='A6' && res.wfTableDataList[i].appId==this.uploadId){
                this.sixId=res.wfTableDataList[i].nextStageId;
            }
  }
}
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

//Deferral

previousDeferralApproval(){

          this.workFlowRemarks = false;
          this.deferralApproval = true;
          }


 remarksNext() {


   for (let item of this.DeferralDocArray){
       if (item.status == 0)
          {
           console.log(item.status, "this.status***********************************************")
           this.defStatus = true;
          }
       }
   for(let doc of this.OtherDeferralDocArray){
       if (doc.status == 0)
             {
              console.log(doc.status, "this.status***********************************************")
              this.defStatus = true;
             }
       }

       if(this.defStatus){
       this.toaster.error("Please make Decision for all documents");
       this.defStatus = false;
       }
           else{
                this.workFlowRemarks= true;
                this.deferralApproval =false;
                }

  }
 nextDeferral(){
          this.deferralRemarks = true;
          this.deferralApproval = false;
     }
  prevDeferral(){
          this.deferralRemarks = false;
          this.deferralApproval = true;
  }
nextBasicDeferral(){
          this.deferralApproval = true;
          this.deferralBasicDetails = false;
}
deferralPrevious(){
            this.deferralApproval = false;
            this.deferralBasicDetails = true;
  }
//  prevList(){
//         this.deferralBasicDetails = false;
//         this.deferralApproval = false;
//         this.deferralPending = true;
//         this.WorkFlowStage = false;
//     }

previousFileUpload(){
    this.fileUploadNp = false;
    this.deDupeNp = true;
}
toggle(){

}
nextPnBasicDetails(){
    this.pNBasicDetailsNp = false;
    this.pNAnchorDetailsNp = true;
    window.scrollTo(0, 0);
}

previousPnAnchorDetails(){
    this.pNAnchorDetailsNp = false;
    this.pNBasicDetailsNp = true;
     window.scrollTo(0, 0);
}
nextPnAnchorDetails(){
    this.pNAnchorDetailsNp = false;
    this.pNProgramNormsNp = true;
    window.scrollTo(0, 0);
}
previousPnProgramNorms(){
    this.pNProgramNormsNp = false;
    this.pNAnchorDetailsNp = true;
     window.scrollTo(0, 0);
}
nextPnProgramNorms(){
    this.pNProgramNormsNp = false;
    this.pNCreditNormsNp = true;
    window.scrollTo(0, 0);
}
previousPnCreditNorms(){
    this.pNCreditNormsNp = false;
    this.pNProgramNormsNp = true;
    window.scrollTo(0, 0);
}
nextPnCreditNorms(){
    this.pNCreditNormsNp = false;
    this.pNRemarksNp = true;
     window.scrollTo(0, 0);
}
previousPnRemarks(){
    this.pNRemarksNp = false;
    this.pNCreditNormsNp = true;
     window.scrollTo(0, 0);
}

nextPnRemarks(){
     this.pNRemarksNp = true;
     this.pNCreditNormsNp = false;
     window.scrollTo(0, 0);
 }
nextCscBasicDetails(){
    this.cscBasicDetailsNp = false;
    this.cscAnchorDetailsNp = true;
     window.scrollTo(0, 0);
}
previousCscAnchorDetails(){
    this.cscAnchorDetailsNp = false;
    this.cscBasicDetailsNp = true;
    window.scrollTo(0, 0);
}
nextCscAnchorDetails(){
    this.cscAnchorDetailsNp = false;
    this.cscProgramNormsNp = true;
     window.scrollTo(0, 0);
}
previousCscProgramNorms(){
    this.cscProgramNormsNp = false;
    this.cscAnchorDetailsNp = true;
     window.scrollTo(0, 0);
}
nextCscProgramNorms(){
    this.cscProgramNormsNp = false;
    this.cscCreditNormsNp = true;
     window.scrollTo(0, 0);
}
previousCscCreditNorms(){
    this.cscCreditNormsNp = false;
    this.cscProgramNormsNp = true;
     window.scrollTo(0, 0);
}
nextCscCreditNorms(){
    this.cscCreditNormsNp = false;
    this.cscRemarksNp = true;
 window.scrollTo(0, 0);
}
previousCscRemarks(){
    this.cscRemarksNp = false;
    this.cscCreditNormsNp = true;
 window.scrollTo(0, 0);
}
nextCscRemarks(){
    this.cscRemarksNp = true;
    this.cscCreditNormsNp = false;
 window.scrollTo(0, 0);
}
nextOpsMakerBasicDetails(){
    this.opsMakerBasicDetailsNp = false;
    this.opsMakerAnchorDetailsNp = true;
 window.scrollTo(0, 0);
}
previousOpsMakerAnchorDetails(){
    this.opsMakerAnchorDetailsNp = false;
    this.opsMakerBasicDetailsNp = true;
 window.scrollTo(0, 0);
}
nextOpsMakerAnchorDetails(){
    this.opsMakerAnchorDetailsNp = false;
    this.opsMakerProgramNormsNp = true;
 window.scrollTo(0, 0);
}
previousOpsMakerProgramNorms(){
    this.opsMakerProgramNormsNp = false;
    this.opsMakerAnchorDetailsNp = true;
 window.scrollTo(0, 0);
}
nextOpsMakerProgramNorms(){
    this.opsMakerProgramNormsNp = false;
    this.opsMakerCreditNormsNp = true;
 window.scrollTo(0, 0);
}
previousOpsMakerCreditNorms(){
    this.opsMakerCreditNormsNp = false;
    this.opsMakerProgramNormsNp = true;
 window.scrollTo(0, 0);
}
nextOpsMakerCreditNorms(){
    this.opsMakerCreditNormsNp = false;
    this.opsMakerBeneficiaryNp = true;
 window.scrollTo(0, 0);
}
previousOpsMakerBeneficiary(){
    this.opsMakerBeneficiaryNp = false;
    this.opsMakerCreditNormsNp = true;
 window.scrollTo(0, 0);
}
nextOpsMakerBeneficiary(){
    this.opsMakerBeneficiaryNp = false;
    this.opsMakerDocumentNp = true;
 window.scrollTo(0, 0);
}
previousOpsMakerDocument(){
    this.opsMakerDocumentNp = false;
    this.opsMakerBeneficiaryNp = true;
 window.scrollTo(0, 0);
}
nextOpsMakerDocument(){
    this.opsMakerDocumentNp = false;
    this.opsMakerRemarksNp = true;
 window.scrollTo(0, 0);
}
previousOpsMakerRemarks(){
    this.opsMakerRemarksNp = false;
    this.opsMakerDocumentNp = true;
 window.scrollTo(0, 0);
}
nextOpsMakerRemarks(){
    this.opsMakerRemarksNp = true;
    this.opsMakerDocumentNp = false;
 window.scrollTo(0, 0);
}
nextOpsCheckerBasicDetails(){
    this.opsCheckerBasicDetailsNp = false;
    this.opsCheckerAnchorDetailsNp = true;
 window.scrollTo(0, 0);
}
previousOpsCheckerAnchorDetails(){
    this.opsCheckerAnchorDetailsNp = false;
    this.opsCheckerBasicDetailsNp = true;
 window.scrollTo(0, 0);
}
nextOpsCheckerAnchorDetails(){
    this.opsCheckerAnchorDetailsNp = false;
    this.opsCheckerProgramNormsNp = true;
 window.scrollTo(0, 0);
}
previousOpsCheckerProgramNorms(){
    this.opsCheckerProgramNormsNp = false;
    this.opsCheckerAnchorDetailsNp = true;
 window.scrollTo(0, 0);
}
nextOpsCheckerProgramNorms(){
    this.opsCheckerProgramNormsNp = false;
    this.opsCheckerCreditNormsNp = true;
 window.scrollTo(0, 0);
}
previousOpsCheckerCreditNorms(){
    this.opsCheckerCreditNormsNp = false;
    this.opsCheckerProgramNormsNp = true;
 window.scrollTo(0, 0);
}
nextOpsCheckerCreditNorms(){
    this.opsCheckerCreditNormsNp = false;
    this.opsCheckerBeneficiaryNp = true;
 window.scrollTo(0, 0);
}
previousOpsCheckerBeneficiary(){
    this.opsCheckerBeneficiaryNp = false;
    this.opsCheckerCreditNormsNp = true;
 window.scrollTo(0, 0);
}
nextOpsCheckerBeneficiary(){
    this.opsCheckerBeneficiaryNp = false;
    this.opsCheckerDocumentNp = true;
 window.scrollTo(0, 0);
}
previousOpsCheckerDocument(){
    this.opsCheckerDocumentNp = false;
    this.opsCheckerBeneficiaryNp = true;
 window.scrollTo(0, 0);
}
nextOpsCheckerDocument(){
    this.opsCheckerDocumentNp = false;
    this.opsCheckerRemarksNp = true;
 window.scrollTo(0, 0);
}
previousOpsCheckerRemarks(){
    this.opsCheckerRemarksNp = false;
    this.opsCheckerDocumentNp = true;
 window.scrollTo(0, 0);
}


getAnchorBasicDetailsById():void {
            let response;
            console.log("response*********************")
            let fileName="anchor/anchorBasicFile/"+this.uploadId;
            response = this.requestapi.getData(fileName);
            response.subscribe((res: any) => {
                this.fileUploadAnchorBasicList=res;
            },error=>{
                if(error.status==401){
                    this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                }
                console.log("error");
            })
        }

roleBasedFun(){

    let roles=localStorage.getItem('roles');
    let response;
    let fileName="permission/permission/";
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {

        for(let i=0;i<res.length;i++){
            if(res[i].stageId.clientType==1){
            this.rbacArray.push(res[i]);
            }
        }
        for(let j=0;j<this.rbacArray.length;j++){
            if(this.rbacArray[j].stageId.stageId=='A1' && this.firstWip=='allow'){
                if(this.rbacArray[j].role=='CPA' ){
                    this.a1Submit=true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='A1' && this.firstComplete=='allow'){
                if(this.rbacArray[j].role=='CPA' ){
                    this.a1View= true;
                    this.a1Submit=false;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='A2' && this.secondWip=='allow'){
                if(this.rbacArray[j].role=='CPA'){
                    this.a2Submit=true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='A2' && this.secondComplete=='allow'){
                if(this.rbacArray[j].role=='CPA'){
                    this.a2View= true;
                    this.a2Submit=false;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='A3' && this.thirdWip=='allow'){
                if(this.rbacArray[j].role=='CREDIT UNDERWRITER'){
                    this.a3Submit = true;
                    this.a3Return = true;
                    this.a2View=true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='A3' && this.thirdComplete=='allow'){
                if(this.rbacArray[j].role=='CREDIT UNDERWRITER'){
                    this.a3View= true;
                    this.a3Submit=false;
                    this.a2View=true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='A4' && this.fourthWip=='allow'){
                if(this.rbacArray[j].role=='OPERATION MAKER'){
                    this.a4Submit=true;
                    this.a4Return=true;
                    this.a2View=true;
                    this.a3View=true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='A4' && this.fourthComplete=='allow'){
                if(this.rbacArray[j].role=='OPERATION MAKER'){
                    this.a4View= true;
                    this.a4Submit=false;
                     this.a2View=true;
                     this.a3View=true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='A5' && this.fifthWip=='allow'){
                if(this.rbacArray[j].role=='OPERATION CHECKER'){
                    this.a5Submit=true;
                    this.a5Return=true;
                    this.a5Reject=true;
                    this.a4View= true;
                    this.a2View=true;
                    this.a3View=true;
                }
                if(this.rbacArray[j].role=='OPERATION MAKER'){
                    this.a5Return=true;
                    this.a2View=true;
                    this.a3View=true;
                    this.a4View= true;
                }
            }
             if(this.rbacArray[j].stageId.stageId=='A5' && this.fifthComplete=='allow'){
                if(this.rbacArray[j].role=='OPERATION CHECKER'){
                    this.a5View= true;
                    this.a5Submit=false;
                    this.a4View= true;
                    this.a2View=true;
                    this.a3View=true;

                }
            }
            if(this.rbacArray[j].stageId.stageId=='A6' && this.sixWip=='allow'){
                if(this.rbacArray[j].role=='DEFERRAL COMMITTEE'){
                    this.a6Submit=true;
                    this.a6Return=true;
                    this.a6Reject=true;
                    this.a5View= true;
                    this.a4View= true;
                    this.a2View=true;
                    this.a3View=true;
                }
                if(this.rbacArray[j].role=='OPERATION CHECKER'){
                    this.a6Return=true;
                    this.a5View=true;
                    this.a2View=true;
                    this.a3View=true;
                    this.a4View= true;
                }
            }
             if(this.rbacArray[j].stageId.stageId=='A6' && this.sixComplete=='allow'){
                if(this.rbacArray[j].role=='DEFERRAL COMMITTEE'){
                    this.a6View= true;
                    this.a5View= true;
                    this.a6Submit=false;
                    this.a4View= true;
                    this.a2View=true;
                    this.a3View=true;

                }
            }
        }
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

currentStepperFun(id){

  let response;
  let fileName="wfApprovalStatus/getHistoryOfWFStatus/"+id;
  response = this.requestapi.getData(fileName);
  response.subscribe((res: any) => {

  this.viewData=res;
  for(let i=0;i<res.length;i++){
    if(res[i].stage.stageId=='A1' && res[i].status==2){
        this.firstComplete='allow';
        this.getBasicDetailsById();
        this.getAnchorAddressDetailsById();
        this.getAnchorKeyDetailsById();
        this.getAnchorProgramDetailsById();
        this.getAnchorGstById();
        this.getAnchorAuthoriseById();
    }
    else if(res[i].stage.stageId=='A1' && res[i].status==0){
        this.firstWip='allow';
        this.wizard.goToStep(0);
    }
    else if(res[i].stage.stageId=='A1' && res[i].status==null){
        this.firstComplete='';
        this.firstWip='';
    }
    else if(res[i].stage.stageId=='A2' && res[i].status==2){
        this.secondComplete='allow';
        this.getCreditNormsDetails();
    }
    else if(res[i].stage.stageId=='A2' && res[i].status==0){
        this.secondWip='allow';
        this.getBasicDetailsById()
        this.getAnchorAddressDetailsById()
        this.getAnchorKeyDetailsById()
        this.getAnchorProgramDetailsById()
        this.getAnchorGstById()
        this.getAnchorAuthoriseById()
        this.wizard.goToStep(0);
        this.wizard.goToStep(1);
    }
    else if(res[i].stage.stageId=='A2' && res[i].status==null){
        this.secondComplete='';
        this.secondWip='';
    }
    else if(res[i].stage.stageId=='A3' && res[i].status==2){
        this.thirdComplete='allow';
        this.getCreditNormsDetails();
    }
    else if(res[i].stage.stageId=='A3' && res[i].status==0){
    this.secondComplete='allow';
        this.thirdWip='allow';
        this.wizard.goToStep(0);
        this.wizard.goToStep(1);
        this.wizard.goToStep(2);
    }
    else if(res[i].stage.stageId=='A3' && res[i].status==-1){
        this.secondComplete='';
        this.secondWip='allow';
    }
    else if(res[i].stage.stageId=='A3' && res[i].status==null){
        this.thirdComplete='';
        this.thirdWip='';
    }
    else if(res[i].stage.stageId=='A4' && res[i].status==2){
        this.fourthComplete='allow';
        this.getAnchorBeneficiaryById();
    }
    else if(res[i].stage.stageId=='A4' && res[i].status==0){
        this.fourthWip='allow';
        this.wizard.goToStep(0);
        this.wizard.goToStep(1);
        this.wizard.goToStep(2);
        this.wizard.goToStep(3);
        this.el.nativeElement.focus();

    }
    else if(res[i].stage.stageId=='A4' && res[i].status==-1){
        this.thirdComplete='';
        this.thirdWip='allow';
    }
    else if(res[i].stage.stageId=='A4' && res[i].status==null){
        this.fourthComplete='';
        this.fourthWip='';
    }
    else if(res[i].stage.stageId=='A5' && res[i].status==2){
        this.fifthComplete='allow';
        this.wizard.goToStep(5);
        this.getDocumentType();
    }
    else if(res[i].stage.stageId=='A5' && res[i].status==0){
        this.fifthWip='allow';
        this.wizard.goToStep(0);
        this.wizard.goToStep(1);
        this.wizard.goToStep(2);
        this.wizard.goToStep(3);
        this.wizard.goToStep(4);
        this.getDocumentsReports();
    }
    else if(res[i].stage.stageId=='A5' && res[i].status==-1){
        this.fourthComplete='';
        this.fourthWip='allow';
    }
    else if(res[i].stage.stageId=='A5' && res[i].status==null){
        this.fifthComplete='';
        this.fifthWip='';
    }
    else if(res[i].stage.stageId=='A6' && res[i].status==2){
            this.sixComplete='allow';
            this.wizard.goToStep(6);
            this.getDocumentType();
        }
        else if(res[i].stage.stageId=='A6' && res[i].status==0){
            this.sixWip='allow';
            this.wizard.goToStep(0);
            this.wizard.goToStep(1);
            this.wizard.goToStep(2);
            this.wizard.goToStep(3);
            this.wizard.goToStep(4);
            this.wizard.goToStep(5);
            this.getDocumentsReports();
            this. viewApproval();
            this.getDeferralOtherDocuments();
        }
        else if(res[i].stage.stageId=='A6' && res[i].status==-1){
            this.fifthComplete='';
            this.fifthWip='allow';
        }
        else if(res[i].stage.stageId=='A6' && res[i].status==null){
            this.sixComplete='';
            this.sixWip='';
        }

  }
  this.roleBasedFun();
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

public finish() {
    this.toaster.success('Successfully Registered')
}

getCustomerInfoDetails(): void {

  let response;
  let fileName="anchor/customerInfo";
  response = this.requestapi.getData(fileName);
  response.subscribe((res: any) => {

for(let y=0;y< res.length;y++){
this.anchorListCust.push(res[y].customerName.toLocaleLowerCase());
     }
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

getRemarks():void{

let response;
let fileName="wfApprovalStatus/getRemarks/"+this.uploadId;
response = this.requestapi.getData(fileName);
response.subscribe((res: any) => {

this.remarkArray=res;

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

allowAlphabetNumbersWithSpace(e){
        let k;
        document.all ? k = e.keyCode : k = e.which;
        return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32 || (k >= 48 && k <= 57));
        }

allowNumbersWithDotAndMinus(e){
        let k;
        document.all ? k = e.keyCode : k = e.which;
        return ((k >= 48 && k <= 57) || k == 46 || k==45)
        }
allowNumbersWithMinus(e){
        let k;
        document.all ? k = e.keyCode : k = e.which;
        return ((k >= 48 && k <= 57) || k==45)
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

allowAlphabetWithSpace(e){
        let k;
        document.all ? k = e.keyCode : k = e.which;
        return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32);
        }

allowAlphabetWithSpaceAndAmbisent(e){
                let k;
                document.all ? k = e.keyCode : k = e.which;
                return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32 || k == 38);
                }
blockSpecialChar(e){
        let k;
        document.all ? k = e.keyCode : k = e.which;
        return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8  || (k >= 48 && k <= 57));
        }

        allowAlphabetNumbersWithSpecialChar(e){
         let k;
         document.all ? k = e.keyCode : k = e.which;
         return ((k > 32 && k < 47) || (k > 58 && k < 64) || (k > 91 && k < 96) || (k > 97 && k < 122) || (k > 123 && k < 126) || (k > 65 && k < 90) ||  k == 8 || k == 32 || (k >= 48 && k <= 57));
         }
saveCustomerDetails(): void {
this.showLoader=true
  const cusName =/^[A-Za-z\s]*$/;
  const cusPan =/([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}$/;
  const cusCin =/([L|U]{1})([0-9]{5})([A-Za-z]{2})([0-9]{4})([A-Za-z]{3})([0-9]{6})$/;

if(this.anchorName2==""  || this.anchorName2==null ){
}
else if(this.panNumber2==""  || this.panNumber2==null){
  }

else if(!cusPan.test(this.panNumber2)){
   }



else if(this.cinNumber2==""  || this.cinNumber2==null){
}
else if(!cusCin.test(this.cinNumber2 )){
    }
else{

this.sta=false;
this.customerDeDupeList = [];
  let response;
  let fileName="anchor/customerInfoDetail";
  let data={ customerName:this.anchorName2,
              product:"SCF",cin:this.cinNumber2,
              pan:this.panNumber2,dedupeStatus:"Active",customerType:"Anchor"

            }
  response = this.requestapi.postData(fileName,JSON.stringify(data));
 response.subscribe((res: any) => {
this.showLoader=false;
        if(!res.customerInfoEntity){
             this.toaster.success('New Anchor')
             this.deDupeNp = false;
             this.fileUploadNp = true;
             }
             else{
                this.customerDeDupeList=res.customerInfoEntity;
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
}
}

wfThirdStage(remarkWf){
this.showLoader = true;
         this.remarkWf = remarkWf;
         let response;
         let fileName="wfApprovalStatus/saveWFApprovalSatge";
         let data={ stageId:this.thirdId,status:2,approverInfo:this.emailId,appId:this.uploadId,remarks:this.remarkWf
                           ,nextApproverInfo:"ANCHOR_OPERATION_MAKER_LEAD"}
               response = this.requestapi.postData(fileName,JSON.stringify(data));
               response.subscribe((res: any) => {
               window.location.reload();
               this.router.navigate(['/dashboard/inbox']);
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

returnWfThirdStage(remarkWf){
this.showLoader = true;
        this.remarkWf = remarkWf;
        let response;
        let fileName="wfApprovalStatus/saveWFApprovalSatge";
        let data={ stageId:2,status:-1,approverInfo:this.emailId,appId:this.uploadId,remarks:this.remarkWf
                           ,nextApproverInfo:"ANCHOR_CPA_LEAD"}
               response = this.requestapi.postData(fileName,JSON.stringify(data));
               response.subscribe((res: any) => {

               window.location.reload();
               this.router.navigate(['/dashboard/inbox']);
                this.showLoader = false;
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

getCreditNorms(): void{

        let response;
        let fileName="anchor/creditNormsMaster";

        response = this.requestapi.getData(fileName);

        response.subscribe((res:any) => {

        this.creditList = res;


        },error=>{
        this.showLoader = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                           }

        })
    }



wfFourthStage(remarkWf){
this.showLoader = true;
       this.remarkWf = remarkWf;
       let response;
       let fileName="wfApprovalStatus/saveWFApprovalSatge";
       let data={ stageId:this.fourthId,status:2,approverInfo:this.emailId,appId:this.uploadId,remarks:this.remarkWf
                                      ,nextApproverInfo:"ANCHOR_OPERATION_CHECKER_LEAD"}
                            response = this.requestapi.postData(fileName,JSON.stringify(data));
                            response.subscribe((res: any) => {

                            window.location.reload();
                            this.router.navigate(['/dashboard/inbox']);
                             this.showLoader = false;
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
returnWfFourthStage(remarkWf){
this.showLoader = true;
    this.remarkWf = remarkWf;
    let response;
    let fileName="wfApprovalStatus/saveWFApprovalSatge";
    let data={ stageId:3,status:-1,approverInfo:this.emailId,appId:this.uploadId,remarks:this.remarkWf
        ,nextApproverInfo:"ANCHOR_CREDIT_UNDERWRITER_LEAD"}
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {

        window.location.reload();
        this.router.navigate(['/dashboard/inbox']);
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


wfFifthStage(remarkWf){
this.showLoader = true;
    this.remarkWf = remarkWf;
    let response;
    let fileName="wfApprovalStatus/saveWFApprovalSatge";
   if(this.deferralStatus){
     let data={ stageId:this.fifthId,status:2,approverInfo:this.emailId,appId:this.uploadId,remarks:this.remarkWf
        ,nextApproverInfo:"ANCHOR_DEFERRAL_COMMITTEE_LEAD"}
        response = this.requestapi.postData(fileName,JSON.stringify(data));
   }else{
    let data={ stageId:this.fifthId,status:2,approverInfo:this.emailId,appId:this.uploadId,remarks:this.remarkWf
       ,nextApproverInfo:"ANCHOR_OPERATION_CHECKER_LEAD"}
    response = this.requestapi.postData(fileName,JSON.stringify(data));
   }

    response.subscribe((res: any) => {
    if(this.deferralStatus){
        window.location.reload();
        this.router.navigate(['/dashboard/inbox']);
    }else{
        this.storeProcedure();
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

wfSixStage(remarkWf){
this.showLoader = true;
this.updateEta();
this.updateOtherDef();
    this.remarkWf = remarkWf;
    let response;
    let fileName="wfApprovalStatus/saveWFApprovalSatge";
    let data={ stageId:this.sixId,status:2,approverInfo:this.emailId,appId:this.uploadId,remarks:this.remarkWf
    ,nextApproverInfo:"ANCHOR_DEFERRAL_COMMITTEE_LEAD"}
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
    this.storeProcedure();
    let saveDef=false;
    for(let item of this.DeferralDocArray){
        if(item.status == 1){
            saveDef = true;
        }
    }

    for(let item of this.OtherDeferralDocArray){
        if(item.status == 1){
            saveDef = true;
        }
    }

    if(!saveDef){
//    window.location.reload();
//                    this.router.navigate(['/dashboard/inbox']);
            this.showLoader = false;
    }
    if(saveDef){

    let response1;
        let fileName1="deferralWorkflow/saveDeferralWorkflow";
        let data1={ stageId:29,status:2,approverInfo:this.emailId,appId:this.uploadId,remarks:this.remarkWf
        ,nextApproverInfo:"ANCHOR_DEFERRAL_COMMITTEE_LEAD"}
        response1 = this.requestapi.postData(fileName1,JSON.stringify(data1));
        response1.subscribe((res: any) => {
//         window.location.reload();
//                 this.router.navigate(['/dashboard/inbox']);
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

returnWfFifthStage(remarkWf){
this.showLoader = true;
    this.remarkWf = remarkWf;
    let response;
    let fileName="wfApprovalStatus/saveWFApprovalSatge";
    let data={ stageId:4,status:-1,approverInfo:this.emailId,appId:this.uploadId,remarks:this.remarkWf
    ,nextApproverInfo:"ANCHOR_OPERATION_MAKER_LEAD"}
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {

        window.location.reload();
        this.router.navigate(['/dashboard/inbox']);
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


wfFirstStage(remarkWf){
this.showLoader = true;
    this.remarkWf = remarkWf;
    let response;
    let fileName="wfApprovalStatus/saveWFApprovalSatge";
    let data={ stageId:1,status:2,approverInfo:this.emailId,appId:this.uploadId[0],remarks:this.remarkWf
    ,nextApproverInfo:"ANCHOR_CPA_LEAD"}
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {

        window.location.reload();
        this.router.navigate(['/dashboard/inbox']);
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

wfSecStage(remarkWf){
this.showLoader = true;
    this.remarkWf = remarkWf;
    let response;
    let fileName="wfApprovalStatus/saveWFApprovalSatge";
    let data={ stageId:this.secondId,status:2,approverInfo:this.emailId,appId:this.uploadId,remarks:this.remarkWf
    ,nextApproverInfo:"ANCHOR_CREDIT_UNDERWRITER_LEAD"}
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {

        window.location.reload();
        this.router.navigate(['/dashboard/inbox']);
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

// ///////////////////Credit Norms/////////////////////////
getCreditNormsLabels(): void{

    let response;
    let fileName="anchor/creditNormsMaster";
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {

    this.CreditNormsMasterList = res;
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

storeCreditNormsWorkFlow(id,item: any):void{
    this.creditNormsId=id;
    if(Event.prototype.isPrototypeOf(item)){
        this.creditNormsValue=item.target.value;
    }else{
        this.creditNormsValue=item.toString();
    }
    this.creditNormsflag=1;
    if(this.creditNormsArray.length != 0){
        for(let itemId of this.creditNormsArray){
            if(itemId.cnMasterId == this.creditNormsId){
                let index = this.creditNormsArray.indexOf(itemId);
                if(this.creditNorms2){
                    var pId = itemId.id;
                    const b = { id: pId, appId: this.uploadId ,cnMasterId : this.creditNormsId,value : this.creditNormsValue,}
                    this.creditNormsArray[index] = b;
                }else{
                    const b = { appId: this.uploadId ,cnMasterId : this.creditNormsId,value : this.creditNormsValue,}
                    this.creditNormsArray[index] = b;
                }
               this.enable=1;
                break;
            }else{
                this.enable=0;
            }
        }
    }else{
        const b = { appId: this.uploadId ,cnMasterId : this.creditNormsId,value : this.creditNormsValue,}
        this.creditNormsArray.push(b);
    }
    if(this.enable==0){
        const b = { appId: this.uploadId ,cnMasterId : this.creditNormsId,value : this.creditNormsValue,}
        this.creditNormsArray.push(b);
    }
}


public creditNormSubmit(key) {
    this.cnControl = true;
    this.creditNormValidate = false;
    this.creditNormValidate = !this.creditNormValidate;
    var creditNormFlag = true;
    if(this.creditNormsArray.length == this.CreditNormsMasterList.length){
        creditNormFlag=true;
    }else{
        creditNormFlag=false;
        this.cnControl = false;
    }
    if(this.creditNormValidate==true && creditNormFlag == true ){
        if(key == 'save'){
            this.saveCreditNorm();
        }else if(key == 'update'){
            this.updateCreditNorm();
        }
    }else{
        this.cnControl = false;
        this.toaster.error('Check once whether fill all field');
    }
}

saveCreditNorm(): void {
this.showLoader=true;
    let response;
    let fileName="anchor/creditNormsDetails";
    let data={ creditNormsDetailsDataList : this.creditNormsArray };
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
this.showLoader=false;
        this.creditNorms1 = false;
        this.creditNorms2 = true;
        if(res.message === 'Validation Success'){
            this.toaster.success('Successfully Saved');
            this.cnControl = false;
            this.getCreditNormsDetails();
            this.updateGetCreditNormsDetails();
        }else{
            this.toaster.error(res.message)
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
    			this.creditNorms1 = true;
                this.cnControl = false;
    			}else{
    			this.toaster.error(error.error.message);
    			this.creditNorms1 = true;
                this.cnControl = false;
    			}
    		}

    })
}

updateCreditNorm(): void {
this.showLoader=true;
    let response;
    let fileName="anchor/creditNormsDetails";
    let data={ creditNormsDetailsDataList : this.creditNormsArray };
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
this.showLoader=false;
        this.toaster.success('Successfully Updated');
        this.cnControl = false;
        this.getCreditNormsDetails();
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
        			this.cnControl = false;
        			}else{
        			this.toaster.error(error.error.message);
        			this.cnControl = false;
        			}
        		}


    })
}

getCreditNormsDetails(): void{

    let response;
    let fileName="anchor/creditNormsDetailsByFId/"+this.uploadId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {

    this.creditNormsDetailsList = res;
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

updateGetCreditNormsDetails(): void{
this.showLoader=true;
    let response;
    let fileName="anchor/creditNormsDetailsByFId/"+this.uploadId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
this.showLoader=false;
    this.creditNormsDetailsList = res;
    if(res.length != 0){
        this.creditNorms2 = true;
        this.creditNormsArray = [];
        for(let item of this.CreditNormsMasterList){
            for(var i=0; i<res.length; i++){
                if(item.id == res[i].creditNormsMasterEntity.id){
                    (<HTMLInputElement>document.getElementById(item.name)).value = res[i].value;
                    const a1 = { id:res[i].id, appId: this.uploadId ,cnMasterId : res[i].creditNormsMasterEntity.id,value : res[i].value}
                    this.creditNormsArray.push(a1);
                }
            }
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

/////////////////////////Beneficiary//////////////////////////////
getBankDetails():void {
this.showLoader=true;
    let response;
    let fileName = "anchor/bankDetails";
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
this.showLoader=false;
        this.bankList = res.bankDetailsList;
    },error=>{
this.showLoader=false;

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


    })
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


    })
}

public beneficiarySubmit(key) {
    this.beneficiaryValidate = false;
    this.beneficiaryValidate = !this.beneficiaryValidate;
    const benefiType =/^[a-zA-Z ]*$/;
    const benefiBankName =/^[a-zA-Z ]*$/;
    const benefiBankAccnt =/^[a-zA-Z0-9]+$/;
    var flag =true;
    if(this.beneficiaryName==""  || this.beneficiaryName==null){
        flag=false;
    }
    if(this.bankName==""  || this.bankName==null){
        flag=false;
    }
    if(this.bankIfscCode==""  || this.bankIfscCode==null){
        flag=false;
    }
    if(this.bankBranchName==""  || this.bankBranchName==null){
//         this.toaster.error("Enter Valid IFSC Code");
        flag=false;
    }
    if(!benefiBankAccnt.test(this.bankAccountNumber)){
//                        this.cPBasicFlag1 = true;
                       this.toaster.error('Please Enter Valid bank Account Number');
                       flag = false;
                       this.beneficiaryValidate = false;
                       }
    if(this.beneficiaryValidate==true && flag == true){
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

getBankNameId(event):void{
    var values = event.split(",", 2);
    this.bankCode = values[0];
    this.bankName = values[1];
    this.getBranchDetails( this.bankCode );
}

saveBeneficiaryDetails(): void {
this.showLoader=true;
    let response;
    let fileName="anchor/anchorBeneficiary";
    let data={ appId:this.uploadId,benType:"Dealer",benName:this.beneficiaryName,bankCode : this.bankCode,bankName:this.bankName,
                   bankAcctNumber:this.bankAccountNumber,bankIfscCode:this.bankIfscCode,bankBranchCode : this.bankBranchCode,
                   bankBranchName:this.bankBranchName,
    }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
this.showLoader=false;
        this.beneficiary1 = false;
        this.beneficiary2 = true;
        this.toaster.success('Successfully Saved')
        this.editBeneficiaryIcon = true;
        this.getAnchorBeneficiaryById();
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
//             if(error.error.message == "Duplicate Bank Account No"){
                this.toaster.error("Some Technical Error");
//             }
        }

    })
}

updateBeneficiaryDetails(): void {
this.showLoader=true;
    let response;
    let fileName="anchor/anchorBeneficiary";
    let data={ id:this.benPkId,appId:this.uploadId,benType:"Dealer",benName:this.beneficiaryName,bankCode : this.bankCode,
                bankName:this.bankName,bankAcctNumber:this.bankAccountNumber,bankIfscCode:this.bankIfscCode,
                bankBranchCode : this.bankBranchCode,bankBranchName:this.bankBranchName,
    }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
this.showLoader=false;
        this.toaster.success('Successfully Updated')
        this.getAnchorBeneficiaryById();
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
//             if(error.error.message == "Duplicate Bank Account No"){
                this.toaster.error("Some Technical Error");
//             }
        }

    })
}

onChange(event) {
    this.file = event.target.files[0];
}

messageArr: Array<any> = [];
saveUploadFile(remarkWf):void{
this.showLoader=true;
         this.messageArr=[]
        this.anchorFileDisable = true;
        let response;
        let fileName="anchor/anchorFileUpload";
        let uploadData = new FormData();
        uploadData.append('file', this.file);
        let data={  anchorName:this.anchorName2, pan:this.panNumber2, cin:this.cinNumber2, approver:this.emailId,
                 }
        const dedupeData = JSON.stringify(data);
        uploadData.append('dedupeData', dedupeData);
        response = this.requestapi.postFileData(fileName,uploadData);
        response.subscribe((res: any) => {
this.showLoader=false;
            if(res.status[0]==true){
                this.uploadId=res.applicationEntity[0];
                this.flag=1;
                this.getBasicDetailsById()
                this.getAnchorAddressDetailsById()
                this.getAnchorKeyDetailsById()
                this.getAnchorProgramDetailsById()
                this.getAnchorGstById()
                this.getAnchorAuthoriseById()
                this.uploadExcelFile()
                this.wfFirstStage(remarkWf);
                this.toaster.success("Successfully Uploaded");
                this.anchorFileDisable = false;
            }
            if(res.status[0]==false){
                this.sta = !res.status[0];
                if(res.message[0].length==0 || res.message[0].length=='' || res.message[0].length==[] || res.message[0].length==undefined){
                    this.toaster.error('Some Technical Error,Please try Again')
                }
                else{
                    for(let i=0;i<res.message[0].length;i++){
                        this.messageArr.push(res.message[0][i]);
                        this.anchorFileDisable = false;
                    }
                }
            }
        },error=>{
this.showLoader=false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
            if(error.status==400)
            {
                if(error.error.message == null || error.error.message == " "){
                  this.toaster.error('Some Technical Error')
                  this.anchorFileDisable = false;
                }else if (error.error.message != null || error.error.message != " "){
                 this.toaster.error(error.error.message)
                 this.anchorFileDisable = false;
                }else{
                this.toaster.error('Some Technical Error,Please try Again')
                               this.anchorFileDisable = false;
                }
            }
            if(error.status==500){
               this.toaster.error('Some Technical Error,Please try Again')
               this.anchorFileDisable = false;
            }
            if(error.status==404){
               this.toaster.error('Some Technical Error,Please try Again')
               this.anchorFileDisable = false;
            }

        })

}

uploadExcelFile(){

    let response;
    let formData = new FormData();
    let url="dms/uploadFile";
    const data={ appId:this.uploadId[0], docTypeId:14533, docTypeName:"anchorExcel",docCategoryId:14536,
                         docCategoryName:"anchorExcel",docListId:14613,docListName:"anchorExcel", key:1
                        }
    const documentReports = JSON.stringify(data);
    formData.append('file', this.file);
    formData.append('documentReportsData',documentReports);
    response = this.requestapi.postFileData(url,formData);
    response.subscribe((res: any) => {

    },error=>{

    if(error.status==401){
          this.refresh_token=localStorage.getItem('refresh_token')
                  this.authService.SignOut(this.refresh_token);
        }

    })
}

getBasicDetailsById():void {

    let response;
    let fileName="anchor/anchorBasicFile/"+this.uploadId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {

        this.anchorBasicDetails=res;
        for(let basic of res){
            this.anchorBasicDetailsId = basic.id;
            this.anchorName = basic.anchorName;
            this.industry = basic.industry;
            this.sector = basic.sector;
            this.cinNumber = basic.cin;
            this.panNumber = basic.pan;
            this.incorpdate = basic.incorpDate;
            this.activity=basic.activity;
            this.constitution=basic.constitution;
            this.businessExpiry=basic.businessExpiry;
            this.docConstitution = basic.constitution.replace(/ /g, "");
        }
    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

public updateAnchorBasic() {


    this.updateAnchorBasicValidate = false;
    this.updateAnchorBasicValidate = !this.updateAnchorBasicValidate;


    const basicName =/^[A-Za-z\s ]*$/;
    const basicInd =/^[A-Za-z\s ]*$/;
    const basicSec =/^[A-Za-z\s ]*$/;
    const basicPan =/([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}$/;
    const basicCin =/([L|U]{1})([0-9]{5})([A-Za-z]{2})([0-9]{4})([A-Za-z]{3})([0-9]{6})$/;


    var flag = true;

    if(!basicName.test(this.anchorName)){
        flag = false;
    }
    if(!basicInd.test(this.industry)){
        flag = false;
    }
    if(!basicSec.test(this.sector)){
        flag = false;
    }
    if(!basicPan.test(this.panNumber)){
       flag = false;
    }
    if(!basicCin.test(this.cinNumber)){
        flag = false;
    }
        this.updateAnchorAddressValidate = false;
        this.updateAnchorAddressValidate = !this.updateAnchorAddressValidate;

        const cityAnchor =/^[a-zA-Z ]*$/;
        const stateAnchor =/^[a-zA-Z ]*$/;
        const pinCodeAnchor =/[0-9]{6}$/;

        var flag = true;

        if(this.address1== "" || this.address1==null){
        flag = false;
        }

        if(!cityAnchor.test(this.city)){
            flag = false;
        }
        if(!stateAnchor.test(this.state)){
                flag = false;
        }
        if(!pinCodeAnchor.test(this.pinCode))
        {
                 flag = false;
        }
        if(this.updateAnchorAddressValidate==true && flag == true ){
            this.updateAnchorBasicDetails();

        }
}

updateAnchorBasicDetails(): void {
this.showLoader=true;
    let response;
    let fileName="anchor/anchorBasic";
    let data={ id:this.anchorBasicDetailsId, appId : this.uploadId, anchorName:this.anchorName, industry:this.industry, subIndustry:this.sector,
            cin:this.cinNumber, pan:this.panNumber, incorporationDate:this.incorpdate,activity:this.activity,constitution:this.constitution,businessExpiry:this.businessExpiry,
            }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
this.showLoader=false;
        this.anchorBasicUpdate = true;
        this.getBasicDetailsById();
        this.updateAnchorAddressDetails();
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


getAnchorAddressDetailsById(): void {

    let response;
    let fileName="anchor/anchorAddressFile/"+this.uploadId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {

        this.anchorAddressDetails=res;
        for(let address of res){
            this.addressDetailsId= address.id
            this.address1 = address.addressLine1;
            this.address2 = address.addressLine2;
            this.city = address.city;
            this.state = address.state;
            this.country = address.country;
            this.pinCode = address.pinCode;
        }
    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

updateAnchorAddressDetails(): void {
this.showLoader=true;
    let response;
    let fileName="anchor/anchorAddress";
    let data={ id:this.addressDetailsId, appId : this.uploadId, addressLine1:this.address1, addressLine2:this.address2, city:this.city,
            state:this.state, country:this.country, pinCode:this.pinCode,
            }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
this.showLoader=false;
        this.anchorAddressUpdate = true;
        this.getAnchorAddressDetailsById();
        if(this.anchorBasicUpdate == true && this.anchorAddressUpdate == true){
           this.toaster.success('Successfully Updated')
        }
    },error=>{
this.showLoader=false;
        if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
    }

    })
}

getAnchorKeyDetailsById(): void {

    let response
    let fileName="anchor/anchorKeyFile/"+this.uploadId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {

        this.anchorKeyDetails=res;
        if(res.length > 0){
            this.keyPersonArray = [];
            for(var i=0;i <res.length;i++){
                this.addKeyPersonValue();
                this.keyPersonArray[i].appId = res[i].applicationEntity.id;
                this.keyPersonArray[i].id = res[i].id;
                this.keyPersonArray[i].type = res[i].type;
                this.keyPersonArray[i].name = res[i].name;
                this.keyPersonArray[i].mobile = res[i].mobile;
                this.keyPersonArray[i].emailId = res[i].emailId;
            }
        }
    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

    public updateAnchorKey() {

    this.updateAnchorKeyValidate = false;
    this.updateAnchorKeyValidate = !this.updateAnchorKeyValidate;

    const keyName =/^[a-zA-Z ]*$/;
    const keyType =/^[a-zA-Z ]*$/;
    const keyMobile =/[0-9]{10}/;
    const keyEmail =/^([a-zA-Z0-9_.+-]+)@([a-zA-Z0-9_.+-]+\.(com|co.in|in)$)/;
    var flag = true;
    var keyPersonUpdate = false;
    var AuthorizedUpdate = false;
    var GstUpdate = false;



    this.updateAnchorGstValidate = false;
    this.updateAnchorGstValidate = !this.updateAnchorGstValidate;

    const gstNumber =/([0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1})$/;
    const gstAcctHolderName =/^[a-zA-Z ]*$/;
    const city =/^[a-zA-Z ]*$/;
    const state = /^[a-zA-Z ]*$/;
    const pincode = /[0-9]{6}/;

    this.updateAnchorAuthValidate = false;
    this.updateAnchorAuthValidate = !this.updateAnchorAuthValidate;

    const AuthName =/^[a-zA-Z ]*$/;
    const AuthType =/^[a-zA-Z ]*$/;
    const AuthMobile =/[0-9]{10}/;
    const AuthEmail =/^([a-zA-Z0-9_.+-]+)@([a-zA-Z0-9_.+-]+\.(com|co.in|in)$)/;

    for(let keyPerson of this.keyPersonArray)
    {

            if(keyPerson.name=="" || keyPerson.name==null){
            this.toaster.error('Please Enter valid keyPerson Name')
            flag = false;
            }
            if(keyPerson.type=="" || keyPerson.type==null){
            this.toaster.error('Please Enter valid keyPerson Type')
            flag = false;
            }
            if(keyPerson.mobile=="" || keyPerson.mobile==null){
            this.toaster.error('Please Enter valid keyPerson Mobile')
            flag = false;
            }
            if(keyPerson.emailId=="" || keyPerson.emailId==null){
            this.toaster.error('Please Enter valid keyPerson Email Id')
            flag = false;
            }
            if(!keyName.test(keyPerson.name)){
            this.toaster.error('Please Enter valid key Person Name')
            flag = false;
            }
            if(!keyType.test(keyPerson.type)){
            this.toaster.error('Please Enter valid key Person Type')
            flag = false;
            }
            if(!keyMobile.test(keyPerson.mobile)){
            this.toaster.error('Please Enter valid key Person Mobile')
            flag = false;
            }
            if(!keyEmail.test(keyPerson.emailId)){
            this.toaster.error('Please Enter valid key Person Email')
            flag = false;
            }
    }

for(let gstDetails of this.gstDetailsArray){

    if(!gstNumber.test(gstDetails.gstNumber)){
    flag = false;
    }
    if(gstDetails.gstAccntHolderName=="" || gstDetails.gstAccntHolderName==null){
    this.toaster.error('Please Enter valid gstAccntHolderName')
    flag = false;
    }
    if(gstDetails.gstNumber=="" || gstDetails.gstNumber==null){
    this.toaster.error('Please Enter valid gstNumber')
    flag = false;
    }
    if(gstDetails.addressLine1=="" || gstDetails.addressLine1==null){
    this.toaster.error('Please Enter valid addressLine1')
    flag = false;
    }
    if(gstDetails.addressLine2=="" || gstDetails.addressLine2==null){
    this.toaster.error('Please Enter valid addressLine2')
    flag = false;
    }
    if(gstDetails.city=="" || gstDetails.city==null){
    this.toaster.error('Please Enter valid city')
    flag = false;
    }
    if(gstDetails.state=="" || gstDetails.state==null){
    this.toaster.error('Please Enter valid state')
    flag = false;
    }
    if(gstDetails.pinCode=="" || gstDetails.pinCode==null){
    this.toaster.error('Please Enter valid pinCode')
    flag = false;
    }
}


    for(let authorised of this.AuthorisedArray)
        {
                if(authorised.name=="" || authorised.name==null){
                 this.toaster.error('Please Enter valid AuthorisedName')
                flag = false;
                }
                if(authorised.type=="" || authorised.type==null){
                 this.toaster.error('Please Enter valid AuthorisedType')
                flag = false;
                }
                if(authorised.mobile=="" || authorised.mobile==null){
                this.toaster.error('Please Enter valid AuthorisedMobile')
                flag = false;
                }
                if(authorised.email=="" || authorised.emailId==null){
                this.toaster.error('Please Enter valid AuthorisedEmail-Id')
                flag = false;
                }
                if(!AuthName.test(authorised.name)){
                this.toaster.error('Please Enter valid AuthorisedName')
                flag = false;
                }
                if(!AuthType.test(authorised.type)){
                this.toaster.error('Please Enter valid AuthorisedType')
                flag = false;
                }
                if(!AuthMobile.test(authorised.mobile)){
                this.toaster.error('Please Enter valid AuthorisedMobile')
                flag = false;
                }
                if(!AuthEmail.test(authorised.emailId)){
                this.toaster.error('Please Enter valid AuthorisedEmail-Id')
                flag = false;
                  }
        }
    if(this.updateAnchorGstValidate==true && flag == true && this.updateAnchorKeyValidate==true && this.updateAnchorAuthValidate==true)
             {
               this.updateAnchorKeyDetails();
             }
}


updateAnchorKeyDetails(): void {
this.showLoader=true;
    let response;
    let fileName="anchor/anchorKey";
    let data={ anchorKeyDataList:this.keyPersonArray }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
this.showLoader=false;
         this.keyPersonUpdate = true;
        this.getAnchorKeyDetailsById();
        this.updateAnchorAuthoriseDetails();
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

getAnchorProgramDetailsById():void {

    let response;
    let fileName="anchor/anchorProgramsFile/"+this.uploadId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {

        this.AnchorProgramList=res;
         if(res.length > 0){
             this.anchorProgramNormsArray = [];
             for(var i=0;i <res.length;i++){
             this.addAnchorProgramNormsValue();
           this.anchorProgramNormsArray[i].id =  res[i].id;
           this.anchorProgramNormsArray[i].appId = res[i].applicationEntity.id;
           this.anchorProgramNormsArray[i].fundingType = res[i].fundingType;
           this.anchorProgramNormsArray[i].overallProgLimit = res[i].overallProgLimit;
           this.anchorProgramNormsArray[i].regularProgLimit = res[i].regularProgLimit;
           this.anchorProgramNormsArray[i].adhocProgLimit = res[i].adhocProgLimit;
           this.anchorProgramNormsArray[i].anchorOnboardingDate = res[i].anchorOnboardingDate;
           this.anchorProgramNormsArray[i].cpMinLimit = res[i].cpMinLimit;
           this.anchorProgramNormsArray[i].cpMaxLimit = res[i].cpMaxLimit;
           this.anchorProgramNormsArray[i].pricingRoiMin = res[i].pricingRoiMin;
           this.anchorProgramNormsArray[i].pricingRoiMax = res[i].pricingRoiMax;
           this.anchorProgramNormsArray[i].interestAppTye = res[i].interestAppTye;
           this.anchorProgramNormsArray[i].interestPaymentFrequency = res[i].interestPaymentFrequency;
           this.anchorProgramNormsArray[i].tenure = res[i].tenure;
           this.anchorProgramNormsArray[i].invoiceAgeing = res[i].invoiceAgeing;
           this.anchorProgramNormsArray[i].doorToDoor = res[i].doorToDoor;
           this.anchorProgramNormsArray[i].fundingPercent = res[i].fundingPercent;
           this.anchorProgramNormsArray[i].penalInterest = res[i].penalInterest;
           this.anchorProgramNormsArray[i].securityCovgPrimary = res[i].securityCovgPrimary;
           this.anchorProgramNormsArray[i].securityCovgSecondry = res[i].securityCovgSecondry;
           this.anchorProgramNormsArray[i].processingFeesMax = res[i].processingFeesMax;
           this.anchorProgramNormsArray[i].processingFeesMin = res[i].processingFeesMin;
           this.anchorProgramNormsArray[i].interestCalculation = res[i].interestCalculation;
           this.anchorProgramNormsArray[i].counterPartyGracePeriod = res[i].counterPartyGracePeriod;
           this.anchorProgramNormsArray[i].maxDrawdown = res[i].maxDrawdown;
           this.anchorProgramNormsArray[i].stopSupplyTriggerDays = res[i].stopSupplyTriggerDays;
           this.anchorProgramNormsArray[i].repaymentNature = res[i].repaymentNature;
           this.anchorProgramNormsArray[i].repaymentType = res[i].repaymentType;
           this.anchorProgramNormsArray[i].programType = res[i].programType;
           this.anchorProgramNormsArray[i].transactionType = res[i].transactionType;
           this.anchorProgramNormsArray[i].interimReviewFrequency = res[i].interimReviewFrequency;
           this.anchorProgramNormsArray[i].nextInterimReviewOn = res[i].nextInterimReviewOn;
           this.anchorProgramNormsArray[i].cmpdInt = res[i].cmpdInt;
           this.anchorProgramNormsArray[i].cmpOvdInt = res[i].cmpOvdInt;
           this.anchorProgramNormsArray[i].penalInterestOwnerShip = res[i].penalInterestOwnerShip;
           this.anchorProgramNormsArray[i].penalInterest = res[i].penalInterest;
           this.anchorProgramNormsArray[i].interestRate = res[i].interestRate;
           this.anchorProgramNormsArray[i].subProduct = res[i].subProduct;
           this.anchorProgramNormsArray[i].dueDateCalculation = res[i].dueDateCalculation;
            this.anchorProgramNormsArray[i].interestOwnerShip = res[i].interestOwnerShip;
            this.anchorProgramNormsArray[i].productExpiry = res[i].productExpiry;

            if(!this.docProductCheck){
                if(res[i].subProduct == 'Anchor Purchase Bill Discounting' || res[i].subProduct == 'Anchor Sales Bill Discounting'){
                    this.docProductCheck = true;
                }
            }


//               if((this.anchorProgramNormsArray[i].overallProgLimit < (this.anchorProgramNormsArray[i].adhocProgLimit + this.anchorProgramNormsArray[i].regularProgLimit))){
//                  this.programNormsUpdateFlag = false;
//                  this.toaster.error("Please Enter valid adhocProgLimit and regularProgLimit")
//              }
//              else if((this.anchorProgramNormsArray[i].doorToDoor > (this.anchorProgramNormsArray[i].tenure + this.anchorProgramNormsArray[i].invoiceAgeing)))
//              {
//                 this.programNormsUpdateFlag = false;
//                 this.toaster.error("Please Enter Valid Tenure and InvoiceAgeing")
//
//              }
         }
//           if(this.programNormsUpdateFlag == true)
//         {
//            this.toaster.success('Successfully Updated ProgramNorms')
//         }
      }

    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

public updateAnchorProgramNorms() {
    this.updateAnchorProgramValidate = false;
    this.updateAnchorProgramValidate = !this.updateAnchorProgramValidate;


    var flag = true;
    var progFlag = false;
    var progOvrLmtFlag = false;
    for(let anchorProgramNorms of this.anchorProgramNormsArray)
    {
    if((parseFloat(anchorProgramNorms.overallProgLimit) < (parseFloat(anchorProgramNorms.adhocProgLimit) + parseFloat(anchorProgramNorms.regularProgLimit)))){
             this.programNormsUpdateFlag = false;
              console.log("this.programNormsUpdateFlag", this.programNormsUpdateFlag);
             flag = false;
             this.toaster.error("Please Enter valid adhocProgLimit and regularProgLimit")
         }
         else if((parseFloat(anchorProgramNorms.doorToDoor) > (parseFloat(anchorProgramNorms.tenure) + parseFloat(anchorProgramNorms.invoiceAgeing))))
         {
            this.programNormsUpdateFlag = false;
            flag = false;
            this.toaster.error("Please Enter Valid Tenure and InvoiceAgeing")

         }
    }

    if(this.updateAnchorProgramValidate==true && flag == true){
        this.updateAnchorProgramDetails();
    }
  }


updateAnchorProgramDetails(): void {
this.showLoader=true;
    let response;
    let fileName="anchor/anchorPrograms";
    let data = { programNormsDataList : this.anchorProgramNormsArray }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
    this.showLoader=false;
        this.programNormsUpdateFlag = true;
        this.getAnchorProgramDetailsById();
        this.toaster.success("Successfully updated")
    },error=>{
this.showLoader=false;
        if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
    }
    if(error.status==400){
           if(error.error.message == null || error.error.message == " "){
             this.toaster.error('Some Technical Error')
           }else{
            this.toaster.error(error.error.message)
           }
        }

    })
}


getAnchorGstById():void {

  let response;
  let fileName="anchor/anchorGstFile/"+this.uploadId;
  response = this.requestapi.getData(fileName);
  response.subscribe((res: any) => {

  this.fileUploadAnchorGstList=res;
     if(res.length > 0){
         this.gstDetailsArray = [];
         for(var i=0;i <res.length;i++){
             this.addGstDetailsValue();
             this.gstDetailsArray[i].id = res[i].id;
             this.gstDetailsArray[i].appId=res[i].applicationEntity.id;
             this.gstDetailsArray[i].gstNumber = res[i].gstNumber;
             this.gstDetailsArray[i].gstAccntHolderName = res[i].gstAcctHolderName;
             this.gstDetailsArray[i].addressLine1 = res[i].addressLine1;
             this.gstDetailsArray[i].addressLine2 = res[i].addressLine2;
             this.gstDetailsArray[i].country = res[i].country;
             this.gstDetailsArray[i].state = res[i].state;
             this.gstDetailsArray[i].city = res[i].city;
             this.gstDetailsArray[i].pinCode = res[i].pincode;
         }
     }
  },error=>{

  if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
      }
  if(error.status==400){
         if(error.error.message == null || error.error.message == " "){
           this.toaster.error('Some Technical Error')
         }else{
          this.toaster.error(error.error.message)
         }
      }

  })
}

updateAnchorGstDetails(): void {

    let response;
    let fileName="anchor/anchorGst";
    let data={ anchorGstDetailsDataList:this.gstDetailsArray }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {

        this.GstUpdate = true;
        this.getAnchorGstById();
        if(this.keyPersonUpdate == true && this.AuthorizedUpdate == true && this.GstUpdate == true){
                  this.toaster.success('Successfully Updated');
            }
    },error=>{

        if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
    }
    if(error.status==400){
       if(error.error.message == null || error.error.message == " "){
         this.toaster.error('Some Technical Error')
       }else{
        this.toaster.error(error.error.message)
       }
    }

    })
}



getAnchorAuthoriseById():void {

    let response;
    let fileName="anchor/anchorAuthorizedFile/"+this.uploadId;

    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {

        this.anchorAuthoriseDetails=res;
        if(res.length > 0){
            this.AuthorisedArray = [];
            for(var i=0;i <res.length;i++){
                this.addAuthorisedValue();
                this.AuthorisedArray[i].appId = res[i].applicationEntity.id;
                this.AuthorisedArray[i].id = res[i].id;
                this.AuthorisedArray[i].type = res[i].type;
                this.AuthorisedArray[i].name = res[i].name;
                this.AuthorisedArray[i].mobile = res[i].mobile;
                this.AuthorisedArray[i].emailId = res[i].emailId;
                this.AuthorisedArray[i].indemnityDoc = res[i].indemnityDoc;
            }
        }
    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }if(error.status==400){
                if(error.error.message == null || error.error.message == " "){
                  this.toaster.error('Some Technical Error')
                }else{
                 this.toaster.error(error.error.message)
                }
             }

    })
}


updateAnchorAuthoriseDetails(): void  {

    let response;
    let fileName="anchor/anchorAuthorized";
    let data={ anchorAuthDataList:this.AuthorisedArray }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {

        this.AuthorizedUpdate = true;
        this.getAnchorAuthoriseById();
        this.updateAnchorGstDetails();
    },error=>{

        if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
         }
        if(error.status == 400){
            if(error.error.message == null || error.error.message == " "){
            this.toaster.error('Some Technical Error');
            }else{
              this.toaster.error(error.error.message)
            }
        }

    })
}

showLoader: boolean = false;

getAnchorBeneficiaryById():void {

    this.fileUploadAnchorBeneficiaryList=[];
    let response;
    let fileName="anchor/anchorBeneficiaryFile/"+this.uploadId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {

        this.fileUploadAnchorBeneficiaryList=res;
        if(this.fileUploadAnchorBeneficiaryList.length != 0){
            this.beneficiary2 = true;
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
        }
    },error=>{

        if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
    }

    })
}

saveProgramNormsRemarksDetails():void{

    let response;
    let fileName="anchor/remarks";
    let data={ description:this.description,stepperTab:"programNormsRemark",custId:this.uploadId[0]}
        response = this.requestapi.postData(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {

        this.toaster.success('Successfully Submitted')
        this.wizard.goToStep(3);
    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}



saveCreditSubRemarksDetails():void{

    let response;
    let fileName="anchor/remarks";
    let data={ description:this.description1,stepperTab:"Credit Sub Remark",custId:this.uploadId[0]}
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {

        this.toaster.success('Successfully Submitted');
    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

saveOpsMakerRemarksDetails():void{

    let response;
    let fileName="anchor/remarks";
    let data={ description:this.description2,stepperTab:"Ops Maker Remark",custId:this.uploadId[0]
    }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {

        this.toaster.success('Successfully Submitted');
    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}


public submit() {

    this.validate = false;
    this.validate = !this.validate;
    const cusName =/^[A-Za-z\s]*$/;
    const cusPan =/([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}$/;
    const cusCin =/([L|U]{1})([0-9]{5})([A-Za-z]{2})([0-9]{4})([A-Za-z]{3})([0-9]{6})$/;
    var flag = true;
    if(!cusPan.test(this.panNumber2)){
    this.toaster.error('Please Enter Valid PAN Number');
        flag = false;
    }
    if(!cusCin.test(this.cinNumber2)){
    this.toaster.error('Please Enter Valid CIN Number');
        flag = false;
    }
    if(this.validate==true && flag == true ){
        this.saveCustomerDetails();
    }
}

//////////////DMS//////////////////
getDocumentType(): void{
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

setDocDate(): void{
    this.deferralDocumentArray = [];
    for(let docType of this.documentMaster){
        if(docType.type == 1){
            for(let docCategory of docType.documentCategoryEntities){
                for(let docList of docCategory.documentListEntities){
                    console.log("this.docConstitution",this.docConstitution);
                    if(docList.deferral == 2 && docList.id != 20161){
                        var date = new Date();
                        var days = docList.deferralTime+1;
                        date.setDate(date.getDate() + days);
                        var date1 = (<HTMLInputElement>document.getElementById(docList.id+'C'));
                        var dateString = date.toISOString().split('T')[0];
                        date1.value = dateString;
                        (<HTMLInputElement>document.getElementById(docList.id+"E")).style.display = "none";
                        (<HTMLInputElement>document.getElementById(docList.id+"F")).style.display = "none";
                        (<HTMLInputElement>document.getElementById(docList.id)).style.display = "none";
                        const b = { appId: this.uploadId ,docListId : docList.id,initialTime : dateString,status : 0,deferralType : docList.type}
                        this.deferralDocumentArray.push(b);
                    }else if(this.docConstitution == 'PrivateCompany' || this.docConstitution == 'PublicCompany-Listed' || this.docConstitution == 'PublicCompany-Unlisted'){
                        if(docList.id == 20161){
                            var date = new Date();
                            var days = docList.deferralTime+1;
                            date.setDate(date.getDate() + days);
                            var date1 = (<HTMLInputElement>document.getElementById(docList.id+'C'));
                            var dateString = date.toISOString().split('T')[0];
                            date1.value = dateString;
                            (<HTMLInputElement>document.getElementById(docList.id+"E")).style.display = "none";
                            (<HTMLInputElement>document.getElementById(docList.id+"F")).style.display = "none";
                            (<HTMLInputElement>document.getElementById(docList.id)).style.display = "none";
                            const b = { appId: this.uploadId ,docListId : docList.id,initialTime : dateString,status : 0,deferralType : docList.type}
                            this.deferralDocumentArray.push(b);
                        }
                    }
                }
            }
        }
    }
    console.log("this.deferralDocumentArray",this.deferralDocumentArray);
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
                for(let item of this.deferralDocumentArray){
                    if(item.docListId == docListId){
                       let index = this.deferralDocumentArray.indexOf(item);
                       item.documentId = null;
                       (<HTMLInputElement>document.getElementById(docListId+"E")).value = null;
                       item.docCompletionDate = null;
                       (<HTMLInputElement>document.getElementById(docListId+"F")).value = null;
                       this.deferralDocumentArray[index] = item;
                    }
                }
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
        const b = { appId: this.uploadId ,docListId : docListId,status : status,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
    if(flag==0){
        const b = { appId: this.uploadId ,docListId : docListId,status : status,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
    console.log("this.deferralDocumentArray",this.deferralDocumentArray);
}
onKeyPressEvent(event: any){
event.preventDefault();
   console.log(event.target.value);
}
storeDeferralDate(event: any,docListId,deferralType):void{
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
        const b = { appId: this.uploadId ,docListId : docListId,initialTime : date,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
    if(flag==0){
        const b = { appId: this.uploadId ,docListId : docListId,initialTime : date,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
    console.log("this.deferralDocumentArray",this.deferralDocumentArray);
}

storeDocumentId(event,docListId,deferralType):void{
    let flag = 1;
    let docId = event.target.value;
    if(this.deferralDocumentArray.length != 0){
        for(let itemId of this.deferralDocumentArray){
            if(itemId.docListId == docListId){
                let index = this.deferralDocumentArray.indexOf(itemId);
                itemId.documentId = docId;
                this.deferralDocumentArray[index] = itemId;
               flag=1;
                break;
            }else{
                flag=0;
            }
        }
    }else{
        const b = { appId: this.uploadId ,docListId : docListId,documentId : docId,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
    if(flag==0){
        const b = { appId: this.uploadId ,docListId : docListId,documentId : docId,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
    console.log("this.deferralDocumentArray",this.deferralDocumentArray);
}

storeDocCompleteDate(event,docListId,deferralType):void{
    let flag = 1;
    let docComDate = event.target.value;
    if(this.deferralDocumentArray.length != 0){
        for(let itemId of this.deferralDocumentArray){
            if(itemId.docListId == docListId){
                let index = this.deferralDocumentArray.indexOf(itemId);
                itemId.docCompletionDate = docComDate;
                this.deferralDocumentArray[index] = itemId;
               flag=1;
                break;
            }else{
                flag=0;
            }
        }
    }else{
        const b = { appId: this.uploadId ,docListId : docListId,docCompletionDate : docComDate,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
    if(flag==0){
        const b = { appId: this.uploadId ,docListId : docListId,docCompletionDate : docComDate,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
    console.log("this.deferralDocumentArray",this.deferralDocumentArray);
}

documentValidation(key): void{

    this.docValidationCheck = true;
    let response;
    let fileName="dms/docValidation";
    let data={ appId : this.uploadId, customerType :1, docValidationData:this.docValidationDataArray,constitution : "Proprietorship", anchorType:"Vendor Type", assessmentType:"KYC" }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res:any) => {

        if(key == 'save'){
            this.saveDeferralDocumentReports();
        }else if(key == 'update'){
            this.updateDeferralDocumentReports();
        }
    },error=>{

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

saveDeferralDocumentReports(): void {
    this.showLoader=true;
    let response;
    let fileName="dms/deferralReport";
//     if(this.rmName != undefined){
        let data={ appId : this.uploadId, deferralReportsDataList : this.deferralDocumentArray, customerType : 1, rmName : this.rmName, docProductCheck : this.docProductCheck, constitution : this.constitution};
        response = this.requestapi.postData(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
            this.showLoader=false;
            this.getDeferralDocReports();
            this.updateOtherDocuments();
//             this.opsMakerDocumentNp=false;
//             this.opsMakerRemarksNp = true;
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
            if(error.status==500){
                this.toaster.error("Some Technical Error Contact admin.");
            }

        })
//     }else{
//         this.showLoader=false;
//         this.docValidationCheck = false;
//         this.toaster.error("Kindly select the RM Name");
//     }
    console.log("this.deferralDocumentArray",this.deferralDocumentArray);
}

updateDeferralDocumentReports(): void {
this.showLoader=true;
    let response;
    let fileName="dms/deferralReport";
//     if(this.rmName != undefined){
        let data={ appId : this.uploadId, deferralReportsDataList : this.deferralDocumentArray, customerType : 1, rmName : this.rmName, docProductCheck : this.docProductCheck, constitution : this.constitution};
        response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
            this.getDeferralDocReports();
            this.updateOtherDocuments();
//             this.opsMakerDocumentNp=false;
//             this.opsMakerRemarksNp = true;
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
            if(error.status==500){
                this.toaster.error("Some Technical Error Contact admin.");
            }

        })
//     }else{
//         this.showLoader=false;
//         this.docValidationCheck = false;
//         this.toaster.error("Kindly select the RM Name");
//     }
    console.log("this.deferralDocumentArray",this.deferralDocumentArray);
}

getDeferralDocumentReports(): void{
    if(this.deferralDocumentArray.length == 0){
        let response;
        let fileName="dms/deferralReport/"+this.uploadId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res:any) => {
        console.log("Response : ",res);
            this.deferralDocumentArray = [];
            if(res.length==0 && this.docProductCheck){
                this.setDocDate();
            }else if(res.length != 0){
                this.deferralCheck = true;
            }
            for(var item of res){
                this.rmName = item.rmName;
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
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id)).style.display = "none";
                    }else if(item.status == 2){
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"D")).value = "No";
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"E")).value = item.documentId;
                        if(item.docCompletionDate != null){
                            var dateString = item.docCompletionDate[0]+"-"+item.docCompletionDate[1]+"-"+item.docCompletionDate[2];
                            var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
                            (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"F")).value = date;
                        }
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"E")).style.display = "block";
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"F")).style.display = "block";
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id)).style.display = "block";
                    }
                }
                if(item.initialTime != null){
                    var dateString = item.initialTime[0]+"-"+item.initialTime[1]+"-"+item.initialTime[2];
                    var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
                    (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"C")).value = date;
                }else{
                    if(item.documentListEntity.deferral == 2){
                        var date1 = new Date();
                        var days = item.documentListEntity.deferralTime+1;
                        date1.setDate(date1.getDate() + days);
                        var key = (<HTMLInputElement>document.getElementById(item.documentListEntity.id+'C'));
                        var dateString = date1.toISOString().split('T')[0];
                        key.value = dateString;
                        item.initialTime = dateString;
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"C")).style.display = "none";
                    }
                }
                const a = { id : item.id, appId: item.applicationEntity.id ,docListId : item.documentListEntity.id,initialTime : item.initialTime,status : item.status, documentId : item.documentId, docCompletionDate : item.docCompletionDate,deferralType : item.documentListEntity.type,}
                this.deferralDocumentArray.push(a);
            }
        },error=>{
            if(this.docProductCheck){
                this.setDocDate();
            }
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }

        })
        console.log("this.deferralDocumentArray1",this.deferralDocumentArray);
    }
}

getDeferralDocReports(): void{

    let response;
    let fileName="dms/deferralReport/"+this.uploadId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {

        this.deferralDocumentArray = [];
        if(res.length==0 && this.docProductCheck){
            this.setDocDate();
        }else if(res.length != 0){
            this.deferralCheck = true;
        }
        for(var item of res){
            const a = { id : item.id, appId: item.applicationEntity.id ,docListId : item.documentListEntity.id,initialTime : item.initialTime,status : item.status, documentId : item.documentId, docCompletionDate : item.docCompletionDate,deferralType : item.documentListEntity.type,deferral : item.documentListEntity.deferral,}
            this.deferralDocumentArray.push(a);
        }
    },error=>{

        if(this.docProductCheck){
            this.setDocDate();
        }
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}
// deferralDocumentArray

uploadDocument(event,docTypeId,docTypeName,docCategoryId,docCategoryName,docListId,docListName,key){
    this.loading = true;
    const file = event.target.files[0];
    if(file != undefined){
        if(file.size <= 105906176){
            let response;
            let formData = new FormData();
            this.document=file;
            let url="dms/uploadFile";
            const data={ appId:this.uploadId, docTypeId:docTypeId, docTypeName:docTypeName,docCategoryId:docCategoryId,
                         docCategoryName:docCategoryName,docListId:docListId,docListName:docListName, key:key
                        }
            const documentReports = JSON.stringify(data);
            formData.append('file',this.document);
            formData.append('documentReportsData',documentReports);
            response = this.requestapi.postFileData(url,formData);
            response.subscribe((res: any) => {

                this.loading = false;
                this.toaster.success('Successfully Uploaded');
                this.getDocumentsReports();
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
    let data ={ appId:this.uploadId, docTypeId:docTypeId, docTypeName:docTypeName, docCategoryId:docCategoryId,
                docCategoryName:docCategoryName, docListId:docListId, docListName:docListName, fileName:fileName, key:key }
    response = this.requestapi.postData(url,JSON.stringify(data));
    response.subscribe((res: any) => {

        this.toaster.success('Successfully Deleted');
        this.getDocumentsReports();
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
        this.toaster.error('File Already Deleted');

    })
}

docCheckbox(event,docTypeId,docCategoryId,docListId): void{
    let mandatory = event.target.checked;
    let flag = 1;
    if(this.docValidationDataArray.length != 0){
        for(let item of this.docValidationDataArray){
            if(item.docTypeId == docTypeId && item.docCategoryId == docCategoryId && item.docListId == docListId){
                let index = this.docValidationDataArray.indexOf(item);
                const a = { appId:this.uploadId, mandatory:mandatory, docTypeId:docTypeId, docCategoryId:docCategoryId , docListId:docListId }
                this.docValidationDataArray[index] = a;
                flag=1;
                break;
            }else{
                flag=0;
            }
        }
    }else{
        const a = { appId:this.uploadId, mandatory:mandatory, docTypeId:docTypeId, docCategoryId:docCategoryId , docListId:docListId }
        this.docValidationDataArray.push(a);
    }
    if(flag==0){
        const a = { appId:this.uploadId, mandatory:mandatory, docTypeId:docTypeId, docCategoryId:docCategoryId , docListId:docListId }
        this.docValidationDataArray.push(a);
    }
}

getDeferralReport(): void{

    let response;
    let fileName="dms/deferralReports/"+this.uploadId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {

        if(res.deferralReports.length != 0){
            this.deferralDocReportArray = res.deferralReports;
            for(let item of res.deferralReports){
                if(item.initialTime != null){
                    let dateString = item.initialTime;
//                     var dateString = item.initialTime[0]+"-"+item.initialTime[1]+"-"+item.initialTime[2];
                        let date = this.datePipe.transform(dateString, 'dd-MM-yyyy');
                    item.initialTime = date;
                }
                if(item.docCompletionDate != null){
                    let dateString = item.docCompletionDate ;
//                     var dateString = item.docCompletionDate[0]+"-"+item.docCompletionDate[1]+"-"+item.docCompletionDate[2];
                    let date = this.datePipe.transform(dateString, 'dd-MM-yyyy');
                    item.docCompletionDate = date;
                }
            }
            for(let item of res.deferralReports){
                if(item.rmName != null){
                    this.rmNames = item.rmName;
                }
                this.deferralDocView = true;
            }
        }else{

        }
    },error=>{

    if(error.status==401){
          this.refresh_token=localStorage.getItem('refresh_token')
                  this.authService.SignOut(this.refresh_token);
        }

    })
}

// DMS for Other Document

saveOtherDocuments(){
    this.showLoader = true;
    let response;
    let fileName="dms/otherDocumentMaster";
    let data={ appId : this.uploadId, otherDocumentDataList : this.otherDocumentArray, customerType : 1, rmName : this.rmName, constitution : this.constitution }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader = false;
        this.opsMakerDocumentNp=false;
        this.opsMakerRemarksNp = true;
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
    let data={ appId : this.uploadId, otherDocumentDataList : this.otherDocumentArray, customerType : 1, rmName : this.rmName, constitution : this.constitution }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader = false;
        this.opsMakerDocumentNp=false;
        this.opsMakerRemarksNp = true;
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
    let fileName="dms/otherDocumentMaster/"+this.uploadId;
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
            this.document=file;
            let url="dms/otherUploadFile";
            const data={ appId:this.uploadId, otherDocumentDataList : this.otherDocumentArray,docTypeId:docTypeId, docTypeName:docTypeName,docCategoryId:docCategoryId,
                         docCategoryName:docCategoryName,docListId:docListId,docListName:docListName, otherDocMasterId:otherDocMasterId, otherDisplayName:otherDisplayName, key:key, rmName : this.rmName
                        }
            const otherDocumentReports = JSON.stringify(data);
            formData.append('file',this.document);
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
    const data={ appId:this.uploadId, otherDocumentDataList : this.otherDocumentArray,docTypeId:docTypeId, docTypeName:docTypeName,docCategoryId:docCategoryId,
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
//         this.toaster.error('File Already Deleted');

    })
}

deleteOtherDocRecord(index,id): void{
    this.showLoader=true;
    let response;
    let fileName="dms/deleteOtherDocRecord/"+this.uploadId+"/"+id;
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
// this.showLoader=true;
    let response;
    let fileName="dms/customerOtherDocReports?appId="+this.uploadId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
//         this.showLoader=false;
        this.OtherDocReports = res.otherDocReports;
        console.log("this.OtherDocReports",this.OtherDocReports);
    },error=>{
//         this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        this.OtherDocReports = null;
    })
}

downloadOtherDocument(docMainName,docSubMainName,otherDocName,fileName){
    let response;
    let customerId = this.uploadId;
    let url="dms/otherDownload/"+this.uploadId+"/"+docMainName+"/"+docSubMainName+"/"+otherDocName+"/"+fileName;
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

  viewApproval(){


     let response;
               let fileName="dms/deferralReport/"+this.uploadId+"/0";
               response = this.requestapi.getData(fileName);
               response.subscribe((res: any) => {
                    console.log("Response::",res);
                   if(res.length>0){
//                    this.DeferralDocArray = [];
                   this.deferrDoc=true
                   }
                   for(var i=0;i<res.length;i++){
                   this.addDeferralDocArray();
                   this.DeferralDocArray[i].id = res[i].id;
                   this.DeferralDocArray[i].appId = res[i].applicationEntity.id;
                   this.DeferralDocArray[i].docListId = res[i].documentListEntity.id;
                   this.DeferralDocArray[i].documentName = res[i].documentListEntity.displayName;
                   this.DeferralDocArray[i].initialTime = res[i].initialTime;
                   this.DeferralDocArray[i].revisedTime = res[i].revisedTime;
                   this.DeferralDocArray[i].deferralType = res[i].documentListEntity.deferralType;
                   this.DeferralDocArray[i].deferral = res[i].documentListEntity.deferral;
                   this.DeferralDocArray[i].status = res[i].status
                   if(res[i].rmName != null){
                   this.rmName = res[i].rmName;
                   this.rmNames = res[i].rmName;
                    }
           }

     })
  }

getDeferralOtherDocuments(): void{

//       this.appId = id;
      let response;
      console.log("***************************************************************************")
                let fileName="dms/othersDeferralDocuments/"+this.uploadId+"/0";
                response = this.requestapi.getData(fileName);
                response.subscribe((res: any) => {
                    console.log(res,"OtherDocuments");
                    this.otherDefDocRes = res;
                    console.log("this.otherDefDocRes",this.otherDefDocRes)
                    this.showLoader = false;

                    if(res.length>0){
//                     this.OtherDeferralDocArray = [];
                    this.OtherDoc = true
                    }
                    for(var i=0;i<res.length;i++){
//                     this.rmNames = res[i].rmName;
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
                       this.rmNames = res[i].rmName;
                    }

      }
    })
  }

updateOtherDef(){

//     this.showLoader = true;
    let response;
    let fileName="dms/updateOtherDeferralStatus";
    let data={ appId: this.uploadId,otherDocumentDataList:this.OtherDeferralDocArray,rmName : this.rmNames}
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
//     window.location.reload();
//         this.router.navigate(['/email/worflow']);
        this.showLoader = false;
        console.log("Working ?????")
    this.toaster.success("Successfully Submitted");
    },error=>{
    this.showLoader = false;
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

  ApprovalStatus(key): void {
      for(let deferralDocArr of this.DeferralDocArray){
               if (key == 2)
               {
               deferralDocArr.status = 2;
               }
               else if(key == 1)
               {
               deferralDocArr.status = 1;
               }
      }

  }


ApprovalOtherDeferralStatus(key): void {
    for(let otherDeferralDocArr of this.OtherDeferralDocArray){
             if (key == 2)
             {
             otherDeferralDocArr.status = 2;
             }
             else if(key == 1)
             {
             otherDeferralDocArr.status = 1;
             }
    }

}

updateEta(){
this.showLoader=true;
    let response;
    let fileName="dms/saveNewDeferralDate";
    let data={ appId: this.uploadId,deferralReportsDataList:this.DeferralDocArray,rmName : this.rmName}
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
this.showLoader=false;
//     for(let DeferralDoc of this.DeferralDocArray)
//     this.toaster.success("Successfully Submitted");
//     this.popupSixthStage(6);
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

storeDecision(id,item:any,key){

  this.DocListEntity=id;
   if(Event.prototype.isPrototypeOf(item)){
              this.status=item.target.value;
              if(key == 1){
              this.status = 1;
              this.toaster.success("Decision made as Approve")
              }else if(key == 3){
              this.status = 3;
              this.toaster.success("Decision made as WaiveOff")
              }
          }else{
              this.status=item.toString();
          }
  for(let deferralDoc of this.DeferralDocArray){
  if(deferralDoc.id == this.DocListEntity)
    {
    let index = this.DeferralDocArray.indexOf(deferralDoc);
    console.log("index*************",index);
    const c = {
              id:deferralDoc.id,
              appId: this.uploadId,
              docListId : deferralDoc.docListId,
              documentName : deferralDoc.documentName,
              initialTime: deferralDoc.initialTime,
              revisedTime: deferralDoc.revisedTime,
              status: this.status,
              deferral: deferralDoc.deferral
    }
    this.DeferralDocArray[index] = c;

    let lengthStore =this.DeferralDocArray.length-1
    console.log ("**lengthStore**",lengthStore);
    console.log ("**indexstore**",index);
    console.log("lengthStore == this.DeferralDocArray[index])",lengthStore == index);

    if(lengthStore == index){

    this.approveHide=false;

    }
    console.log(this.documentName,"this.documentName");
    console.log("const c",c);
    console.log(this.status,"this.status");
    break;
    }
   }
  }

otherDeferralStoreDecision(id,item:any,key){

   this.DocListEntity=id;
   if(Event.prototype.isPrototypeOf(item)){
              this.status=item.target.value;
              if(key == 1){
              this.status = 1;
              this.toaster.success("Decision made as Approve")
              }else if(key == 3){
              this.status = 3;
              this.toaster.success("Decision made as WaiveOff")
              }else{
              this.status = 0;
              }
          }else{
              this.status=item.toString();
          }
  for(let otherDefDoc of this.OtherDeferralDocArray){
  if(otherDefDoc.id == this.DocListEntity)
    {
    let index = this.OtherDeferralDocArray.indexOf(otherDefDoc);
    console.log("index*************",index);
    const c = {
              id:otherDefDoc.id,
              appId: this.uploadId,
              docListId : otherDefDoc.docListId,
              displayName : otherDefDoc.displayName,
              initialTime: otherDefDoc.initialTime,
              revisedTime: otherDefDoc.revisedTime,
              status: this.status
    }
    this.OtherDeferralDocArray[index] = c;
    console.log("this.OtherDeferralDocArray[index]",this.OtherDeferralDocArray[index])
    console.log("this.status :::",this.status)

    let lengthStore =this.OtherDeferralDocArray.length-1
    console.log ("**lengthStore**",lengthStore);
    console.log ("**indexstore**",index);
    console.log("lengthStore == this.DeferralDocArray[index])",lengthStore == index);

    if(lengthStore == index){

    this.approveHide=false;

    }
    console.log(this.documentName,"this.documentName");
    console.log("const c",c);
    console.log(this.status,"this.status");
    break;
    }
   }
  }


//
// settingDocReport(): void{
//
//     let response;
//     let fileName="dms/customerDocReports?appId="+this.uploadId;
//     response = this.requestapi.getData(fileName);
//     response.subscribe((res:any) => {
//
//         if(res.documentReports.length>0){
//             this.getDocumentsReports();
//             for(let doc of res.documentReports){
//                 if(doc.customerType == '1'){
//                     document.getElementById(doc.docSubTypeName).innerHTML = doc.fileName;
//                 }
//             }
//         }
//     },error=>{
//
//     if(error.status==401){
//           this.refresh_token=localStorage.getItem('refresh_token')
//                   this.authService.SignOut(this.refresh_token);
//         }
//
//         this.docReports = null;
//     })
// }

// getUpdateDocReports(): void{
//     let response;
//     let fileName="dms/customerDocReports?appId="+this.uploadId;
//     response = this.requestapi.getData(fileName);
//     response.subscribe((res:any) => {
//         if(res.documentReports.length>0){
//             this.docReports = res.documentReports;
// //             this.getDocDownloadType();
//         }
//     },error=>{
//     if(error.status==401){
//           this.refresh_token=localStorage.getItem('refresh_token')
//                   this.authService.SignOut(this.refresh_token);
//         }
//         this.docReports = null;
//     })
// }

getDocumentsReports(): void{
// this.showLoader=true;
    let response;
    let fileName="dms/documentReports?appId="+this.uploadId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.showLoader=false;
        if(res.documentReports.length > 0){
            this.docReports = res.documentReports;
        }else{
            this.docReports = null;
        }
    },error=>{
this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        this.docReports = null;
    })
}

downloadDocument(docMainName,docSubMainName,fileName){
    let response;
    let customerId = this.uploadId;
    let url="dms/download/"+this.uploadId+"/"+docMainName+"/"+docSubMainName+"/"+fileName;
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

getPincodeDetails(event,key,i):void {

    let pinCode = event.target.value;
    let response;
    let fileName="anchor/getPincodeDetails?pinCode="+pinCode;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any)=>{

        this.anchorAddressMaster = res.addressDetails;
        if(key=='basicDetails'){
            for(let address of this.anchorAddressMaster){
                if(address.status == "Success"){
                    this.state=address.state;
                    this.city=address.city;
                }else if(address.status == "Fail"){
                    this.state=null;
                    this.city=null;
                    this.toaster.error("Enter Valid PIN Code!!!");
                }
            }
        }else if(key=='gst'){
            for(let address of this.anchorAddressMaster){
                if(address.status == "Success"){
                    this.gstDetailsArray[i].state=address.state;
                    this.gstDetailsArray[i].city=address.city;
                }else if(address.status == "Fail"){
                    this.gstDetailsArray[i].state= null;
                    this.gstDetailsArray[i].city=null;
                    this.toaster.error("Enter Valid PIN Code!!!");
                }
            }
        }
    },error=>{


    })
}

storeProcedure():void{


    let response;
    let fileName="masterStoreProcedure/"+this.uploadId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any)=>{

        if(res.P_OUT_MSG == "SUCCESS"){
            this.storeProcedureFlag= true;
            this.showLoader=false;
            window.location.reload();
        }
    },error=>{
        this.showLoader=false;

    })
}

popupFirstStage(index)
{
    if(this.file !=  null)
    {
        Swal.fire(
        {
            title: 'Are you sure to submit?',
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
        this.saveUploadFile(result.value)
        }
       })
    }
    else
    {
       this.toaster.error("Kindly Upload File");
    }
    }

popupSecStage(index){
    Swal.fire({
        title: 'Are you sure to submit?',
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
            this.creditsubmitflag = false;
            this.wfSecStage(result.value);
        }
    })
}

popupThirdStage(index){
    Swal.fire({
        title: 'Are you sure to submit?',
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
            this.wfThirdStage(result.value);
        }
    })
}

popupFourthStage(index){
Swal.fire({
  title: 'Are you sure to submit?',
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
          this.wfFourthStage(result.value);
        }
      })
    }



popupFifthStage(index){
    Swal.fire({
        title: 'Are you sure to submit?',
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
                this.wfFifthStage(result.value);
                          this.wizard.goToStep(index);

        }
    })
}




popupSixthStage(index){
    Swal.fire({
        title: 'Are you sure to submit?',
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
            this.wfSixStage(result.value);
            this.wizard.goToStep(index);

        }
    })
}
returnWf(StageId,remark,nextAppInfo){
    this.showLoader = true;
    let response;
    let fileName="wfApprovalStatus/saveWFApprovalSatge";
    let data={ stageId : StageId, status :-1, approverInfo : this.emailId, appId : this.uploadId, remarks : remark, nextApproverInfo : nextAppInfo }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {

        window.location.reload();
        this.router.navigate(['/dashboard/inbox']);
        this.showLoader = false;
    },error=>{
    this.showLoader = false;
    if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
    }

    })
}

returnPopupFun(stageId,nextAppInfo){
    Swal.fire({
        title: 'Are you sure to return?',
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
            this.returnWf(stageId,result.value,nextAppInfo);
        }
    })
}

rejectWf(StageId,remark,nextAppInfo){
    this.showLoader = true;
    let response;
    let fileName="wfApprovalStatus/saveWFApprovalSatge";
    let data={ stageId : StageId, status :-2, approverInfo : this.emailId, appId : this.uploadId, remarks : remark, nextApproverInfo : nextAppInfo }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {

        window.location.reload();
        this.router.navigate(['/dashboard/inbox']);
        this.showLoader = false;
    },error=>{
    this.showLoader = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

rejectPopupFun(stageId,nextAppInfo){
    Swal.fire({
        title: 'Are you sure to reject?',
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
            this.rejectWf(stageId,result.value,nextAppInfo);
        }
    })
}


}