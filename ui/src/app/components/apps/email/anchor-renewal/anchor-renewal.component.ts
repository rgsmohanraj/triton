import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import {WizardComponent, ConfigurableNavigationMode} from 'angular-archwizard';
import { ApiRequestService } from 'src/app/shared/services/api-request.service';
import { AuthService } from 'src/app/shared/services/firebase/auth.service';
import { Subscription } from 'rxjs';
import $ from "jquery";
import { CommonModule,DatePipe } from '@angular/common';
import { environment } from '../../../../../environments/environment';
declare var require
const Swal = require('sweetalert2')

@Component({
  selector: 'app-anchor-renewal',
  templateUrl: './anchor-renewal.component.html',
  styleUrls: ['./anchor-renewal.component.scss']
})
export class AnchorRenewalComponent implements OnInit {
@ViewChild(WizardComponent)
public wizard: WizardComponent;
public isDropdownDisabled: boolean = true;

anchorProgram = { subProduct: '' };
  dropdownOptions = [
    'Anchor Sales Bill Discounting',
    'Anchor Purchase Bill Discounting',
    'Dealer Purchase Order Finance',
    'Dealer Invoice Finance',
    'Vendor Purchase Order Finance',
    'Vendor Invoice Finance'
  ];

  selectedSecondDropdownOption: string = '';
  secondDropdownOptions:Array<any>=[];


private subscription: Subscription;

  constructor(public authService: AuthService, public router: Router,private toaster: ToastrService,public datePipe:DatePipe,
  private requestapi:ApiRequestService,private _formBuilder: FormBuilder
  ) {

    const currentDate = new Date();
    var min = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate() + 2);
    var max = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate() + 32);
    var docCom = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate() + 1);
    this.minDate = min.toISOString().split('T')[0];
    this.maxDate = max.toISOString().split('T')[0];
    this.docComDate = docCom.toISOString().split('T')[0];

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
   }

showLoader: boolean = false;
refresh_token:any;
firstId:any;
secondId:any;
thirdId:any;
fourthId:any;
fifthId:any;
sixId:any;
programCondition:any;

//RBAC Details
 viewData:[]=[];
rbacArray: Array<any> = [];
rmNameArray: Array<any> = [];
remarkArray:[]=[];

//Hide and show button
public basicUpdate = false
public keySave = true
public programNorms1 = false
public programNorms2 = true;


public nextKagDisable = true;
public nextBasicDisable = true;

public enableProd = true;
public enableProgType =true;
public enableRecourType =true;

anchorAddressMaster: Array<any> = [];

address:any;anchorAddressId:any;
action:any;


//Anchor Basic Details
anchorBasicDetails:[]=[];
basicAppId:any;
businessExpiry:any;
anchorBasicDetailsId:any;anchorName:any;industry:any;sector:any;cinNumber:any;panNumber:any;incorporateDateDate:any;address1:any;address2:any;city:any;state:any;country:'India';pinCode:any;addressLine1:any;addressLine2:any;addressDetailsId:any;incorpdate:any;activity:any;constitution:any;docConstitution:any;

//Anchor Address Details
anchorAddressDetails:[]=[];
gstDetailAddress1:any;gstDetailAddress2:any;gstDetailCountry='India';gstDetailState:any;gstDetailCity:any;gstDetailPinCode:any;

//AddressDetails
anchorAddrList:[]=[];
   addressArray: Array<any> = [];
   newAddressAttribute: any = {};
   addAddressValue() {
     this.addressArray.push(this.newAddressAttribute)
     this.newAddressAttribute = {};
   }
   deleteAddressValue(index) {
     this.addressArray.splice(index, 1);
   }

//Key Person Details
anchorKeyDetails:[]=[];
keyPersonArray: Array<any> = [];
newKeyPersonAttribute: any = {};
addKeyPersonValue() {
  this.keyPersonArray.push(this.newKeyPersonAttribute)
  this.newKeyPersonAttribute = {};

}
deleteKeyPersonValue(index) {
  this.keyPersonArray.splice(index, 1);
}

//Anchor Authorized Person
anchorAuthoriseDetails:[]=[];
AuthorisedArray :Array<any> = [];
newAuthorisedAttribute : any = {};
addAuthorisedValue(){
  this.AuthorisedArray.push(this.newAuthorisedAttribute)
  this.newAuthorisedAttribute = {};
}
deleteAuthorisedValue(index) {
  this.AuthorisedArray.splice(index, 1);
}
anchorAuthoriseId:any;authorisedName:any;authorisedType:any;authorisedMobile:any;authorisedEmail:any;

//Anchor GST Details
anchorGstList:[]=[];
gstDetailsArray: Array<any> = [];
newGstDetailsAttribute: any = {};
addGstDetailsValue() {
  this.gstDetailsArray.push(this.newGstDetailsAttribute)
  this.newGstDetailsAttribute = {};
}
deleteGstDetailsValue(index) {
  this.gstDetailsArray.splice(index, 1);
}
anchorGstDetailsId:any;gstDetailNumber:any;gstDetailAccountHolder:any;

//Anchor Program Norms Details
dropdownValue:any;
productName1:Array<any>=[];
AnchorProgramList:[]=[];
newAnchorProgramNorms :any = {};
anchorProgramNormsArray :Array<any> = [];
addAnchorProgramNormsValue(){
  this.anchorProgramNormsArray.push(this.newAnchorProgramNorms)
  console.log("anchorProgramNormsArray :::::::",this.anchorProgramNormsArray)
  this.newAnchorProgramNorms = { appId : this.newAppId ,dueDateCalculation : 'CALC_DUE_DT_FROM_FROM_INV_DATE',};
}
deleteAnchorProgramNormsValue(index,anchorProgram,product) {
    if(product != undefined){
        let response;
        let fileName="anchor/programCondition/"+this.custId;
//         let data = {}
        response = this.requestapi.postData(fileName,JSON.stringify(anchorProgram));
        response.subscribe((res: any) => {
            for(let item of res.result){
                if(item.message == 'success'){
                    this.toaster.success("Deleted Successfully");
                    this.anchorProgramNormsArray.splice(index, 1);
                }else if(item.message == 'fail'){
                    this.toaster.error("You are not allowed to delete the old product");
                }
            }
            this.programCondition = res;
            console.log("this.programCondition=",this.programCondition);
        },error=>{
        })
    }else{
        this.anchorProgramNormsArray.splice(index, 1);
    }
}

programDetailsId:any;programFundingType:any;programOverallLimit:any;programRegularLimit:any;programAdhocLimit:any;programOnboardingDate:any;programCpMinLimit:any;programCpMaxLimit:any;programPricingRoiMin:any;programPricingRoiMax:any;programInterestAppType:any;programInterestOwnership:any;programInterestPaymentFrequency:any;programTenure:any;programDoor1:any;programSecurityCoveragePrimary:any;programFundingPercent:any;programSecurityCoverageSecondary:any;programProcessingFeeMin:any;programProcessingFeeMax:any;programInterestCalculation:any;programGracePeriod:any;programMaxDrawDown:any;programTriggerDays:any;programRepaymentNature:any;programRepaymentType:any;programType1:any;programTransactionType:any;programReviewFrequency:any;programReviewDate:any;programCompoundInterest:any;programCompoundInterestOverdue:any;programPenalInterest:any;programInterestRate:any;programSubProduct:any;invoiceAgeing:any;anchorProgramNorms:any;
pnDetailsCount:any;
//Credit Norms Details
CreditNormsMasterList: Array<any> = [];
creditNormsArray: Array<any> = [];
creditNormsDetailsList:[]=[];
creditNormsValue:any;
creditDetailsId:any;

renewalEnhancement:any;
emailId:any;
custId:any;
oldAppId:any;
newAppId:any;
appStatus:any;
deferralStatus:any;
appId:any;
minDate:any; maxDate:any;docComDate:any;

///Next & Previous///
public deDupeNp = true;public fileUploadNp = false;
public pNBasicDetailsNp = true;public pNAnchorDetailsNp = false;public pNProgramNormsNp = false;public pNCreditNormsNp = false; public pNRemarksNp = false;
public cscBasicDetailsNp = true;public cscAnchorDetailsNp = false;public cscProgramNormsNp = false;public cscCreditNormsNp = false; public cscRemarksNp = false;
public opsMakerBasicDetailsNp = true;public opsMakerAnchorDetailsNp = false; opsMakerProgramNormsNp = false;public opsMakerCreditNormsNp = false;public opsMakerBeneficiaryNp = false;public opsMakerDocumentNp = false;public  opsMakerRemarksNp = false;
public opsCheckerBasicDetailsNp = true;public opsCheckerAnchorDetailsNp = false; opsCheckerProgramNormsNp = false;public opsCheckerCreditNormsNp = false;public opsCheckerBeneficiaryNp = false;public opsCheckerDocumentNp = false;public  opsCheckerRemarksNp = false;
public cscRemarks = false;
///Next & Previous for Deferral///
public deferralBasicDetailsNp = true;public deferralApprovalNp = false;public deferralRemarksNp = false;

public firstComplete='';
public firstWip='';
secondComplete:any;
public secondWip='allow';
thirdComplete:any;
thirdWip:any;
fourthComplete:any;
fourthWip:any;
fifthComplete:any;
fifthWip:any;
sixComplete:any;
sixWip:any;

public show = true;

a1View=false;
a1Submit=false;
a1Return= false;
a1Approve= false;
a1Reject= false;
a1Edit=false;

a2View=false;
a2Submit=true;
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
a4Submit=true;
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


//validation function

allowAlphabetNumbersWithSpace(e){
    let k;
    document.all ? k = e.keyCode : k = e.which;
    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32 || (k >= 48 && k <= 57));
}

allowAlphabetNumbersWithSpecialChar(e){
    let k;
    document.all ? k = e.keyCode : k = e.which;
    return ((k > 32 && k < 47) || (k > 58 && k < 64) || (k > 91 && k < 96) || (k > 123 && k < 126) || k == 8 || k == 32 || (k >= 48 && k <= 57));
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
    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 32);
}

blockSpecialChar(e){
    let k;
    document.all ? k = e.keyCode : k = e.which;
    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8  || (k >= 48 && k <= 57));
}

empty(e){
    let k;
    document.all ? k = e.keyCode : k = e.which;
    return
}



    ngOnInit(): void {
        this.getPending();
        this.getDocumentMaster();
        this.getRMNames();
        this.roleBasedFun();
        this.getCreditNormsLabels();
        if(this.oldAppId == undefined && this.newAppId == undefined){
            this.show = false;
        }else{
            console.log("oldAppId",this.oldAppId);
            console.log("newAppId",this.newAppId);
            console.log("customerId",this.custId);
            this.getApplicationIds(this.custId);
            this.show = true;
            this.appId = this.oldAppId;
            if(this.appStatus){
                this.createApplication();
            }

            this.currentStepperFun();
            this.getBasicDetailsById(this.newAppId);
            this.getAnchorKeyDetailsById(this.newAppId);
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
//New Application Create
createApplication():void{
    let response;
    let fileName="anchor/applicationDetails";
    let data={ type:1,appType:this.renewalEnhancement,custId:this.custId}
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.newAppId = res.entityId
        console.log("this.newAppId",this.newAppId);
        this.getApplicationIds(this.custId);
        this.workflowFunction(2,0,"Work in Progress","ANCHOR_CPA_LEAD");
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
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

        this.getBasicDetailsById(this.newAppId);
        this.getAnchorKeyDetailsById(this.newAppId);
        this.getCreditNormsDetails(this.newAppId);
        this.getProgramNormsDetails(this.newAppId);
      },error=>{
          if(error.status==401){
              this.refresh_token=localStorage.getItem('refresh_token')
              this.authService.SignOut(this.refresh_token);
          }
      })
  }

getPending():void {
    let response;
    let fileName="wfApprovalStatus/getFinalWFStatus/"+localStorage.getItem('email');
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
    if(res.status==true){
        for(let i=0;i<res.wfTableDataList.length;i++){
            if(res.wfTableDataList[i].currentStage=='A1' && res.wfTableDataList[i].appId==this.newAppId){
                this.firstId=res.wfTableDataList[i].nextStageId;
                console.log("this.firstId",this.firstId);
            }
            if(res.wfTableDataList[i].currentStage=='A2' && res.wfTableDataList[i].appId==this.newAppId){
                this.secondId=res.wfTableDataList[i].nextStageId;
                console.log("this.secondId",this.secondId);
            }
            if(res.wfTableDataList[i].currentStage=='A3' && res.wfTableDataList[i].appId==this.newAppId){
                this.thirdId=res.wfTableDataList[i].nextStageId;
                console.log("this.thirdId",this.thirdId);
            }
            if(res.wfTableDataList[i].currentStage=='A4' && res.wfTableDataList[i].appId==this.newAppId){
                this.fourthId=res.wfTableDataList[i].nextStageId;
                console.log("this.fourthId",this.fourthId);
            }
            if(res.wfTableDataList[i].currentStage=='A5' && res.wfTableDataList[i].appId==this.newAppId){
                this.fifthId=res.wfTableDataList[i].nextStageId;
                console.log("this.fifthId",this.fifthId);
            }
            if(res.wfTableDataList[i].currentStage=='A6' && res.wfTableDataList[i].appId==this.newAppId){
                this.sixId=res.wfTableDataList[i].nextStageId;
                console.log("this.sixId",this.sixId);
            }
        }
    }
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

//RM Names
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

//Stages Info
currentStepperFun(){
    let response;
    let fileName="wfApprovalStatus/getHistoryOfWFStatus/"+this.newAppId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.viewData=res;
        for(let i=0;i<res.length;i++){
            if(res[i].stage.stageId=='A1' && res[i].status==2){
                this.firstComplete='';
            }else if(res[i].stage.stageId=='A1' && res[i].status==0){
                this.firstWip='';
            }else if(res[i].stage.stageId=='A1' && res[i].status==null){
                this.firstComplete='';
                this.firstWip='';
            }else if(res[i].stage.stageId=='A2' && res[i].status==2){
                this.secondComplete='allow';
            }else if(res[i].stage.stageId=='A2' && res[i].status==0){
                this.secondWip='allow';
                this.getBasicDetailsById(this.newAppId);
                this.getProgramNormsDetails(this.newAppId);
                this.getCreditNormsDetails(this.newAppId);
                this.wizard.goToStep(0);
            }else if(res[i].stage.stageId=='A2' && res[i].status==null){
                this.secondComplete='';
                this.secondWip='';
            }else if(res[i].stage.stageId=='A3' && res[i].status==2){
                this.thirdComplete='allow';
            }else if(res[i].stage.stageId=='A3' && res[i].status==0){
                this.secondComplete='allow';
                this.thirdWip='allow';
                this.getProgramNormsDetails(this.newAppId);
                this.getCreditNormsDetails(this.newAppId);
                this.wizard.goToStep(0);
                this.wizard.goToStep(1);
            }else if(res[i].stage.stageId=='A3' && res[i].status==-1){
                this.secondComplete='';
                this.secondWip='allow';
            }else if(res[i].stage.stageId=='A3' && res[i].status==null){
                this.thirdComplete='';
                this.thirdWip='';
            }else if(res[i].stage.stageId=='A4' && res[i].status==2){
                this.fourthComplete='allow';
            }else if(res[i].stage.stageId=='A4' && res[i].status==0){
                this.fourthWip='allow';
                this.getProgramNormsDetails(this.newAppId);
                this.getCreditNormsDetails(this.newAppId);
                this.getBankDetails();
                this.wizard.goToStep(0);
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);

            }else if(res[i].stage.stageId=='A4' && res[i].status==-1){
                this.thirdComplete='';
                this.thirdWip='allow';
            }else if(res[i].stage.stageId=='A4' && res[i].status==null){
                this.fourthComplete='';
                this.fourthWip='';
            }else if(res[i].stage.stageId=='A5' && res[i].status==2){
                this.fifthComplete='allow';
            //         this.wizard.goToStep(5);
            }else if(res[i].stage.stageId=='A5' && res[i].status==0){
                this.fifthWip='allow';
                this.getDocumentsReports();
                this.getProgramNormsDetails(this.newAppId);
                this.getCreditNormsDetails(this.newAppId);
                this.wizard.goToStep(0);
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
                this.getAnchorBeneficiaryById(this.newAppId);
            }else if(res[i].stage.stageId=='A5' && res[i].status==-1){
                this.fourthComplete='';
                this.fourthWip='allow';
            }else if(res[i].stage.stageId=='A5' && res[i].status==null){
                this.fifthComplete='';
                this.fifthWip='';
            }else if(res[i].stage.stageId=='A6' && res[i].status==2){
                this.sixComplete='allow';
            //             this.wizard.goToStep(6);
            }else if(res[i].stage.stageId=='A6' && res[i].status==0){
                this.sixWip='allow';
                this.wizard.goToStep(0);
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
                this.wizard.goToStep(4);
                this.getProgramNormsDetails(this.newAppId);
                this.getCreditNormsDetails(this.newAppId);
                this.getDocDeferralReport();
                this.getOtherDocDeferralReport();
            }else if(res[i].stage.stageId=='A6' && res[i].status==-1){
                this.fifthComplete='';
                this.fifthWip='allow';
            }else if(res[i].stage.stageId=='A6' && res[i].status==null){
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
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error');
            }else{
                this.toaster.error(error.error.message);
            }
        }
    })
}

//RBAC
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
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
            }else{
                this.toaster.error(error.error.message);
            }
        }
    })
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
    this.setCreditNormsDetails(this.newAppId);
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
     this.setCreditNormsDetails(this.newAppId);
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
//Deferral Next Previous
nextBasicDeferral(){
    this.deferralBasicDetailsNp = false;
    this.deferralApprovalNp = true;
 window.scrollTo(0, 0);
}
previousDeferralApproval(){
    this.deferralApprovalNp = false;
    this.deferralBasicDetailsNp = true;
 window.scrollTo(0, 0);
}
// nextDeferralApproval(){
//     this.deferralApprovalNp = false;
//     this.deferralRemarksNp = true;
//  window.scrollTo(0, 0);
// }
previousDeferralRemarks(){
    this.deferralRemarksNp = false;
    this.deferralApprovalNp = true;
 window.scrollTo(0, 0);
}

//Workflow function
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
            if(this.deferralStatus && stageId==this.fifthId){
                this.workflowFunction(stageId,status,result.value,'ANCHOR_DEFERRAL_COMMITTEE_LEAD');
            }else if(stageId==this.sixId){
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
        if(status != 0 && stageId != this.sixId){
            this.router.navigate(['/dashboard/inbox']);
            setTimeout(() => {
                window.location.reload();
            }, 1000);
        }else if(stageId == this.sixId){
            this.deferralWorkFlow();
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

//Basic Details

public anchorBasic1 = true;
public anchorBasic2 = true;

getBasicDetailsById(id):void {
    let response;
    let fileName="anchor/anchorBasicFile/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.anchorBasicDetails=res;
        if(this.anchorBasicDetails.length != 0){
            this.getAnchorAddressDetailsById(this.newAppId);
//             if(this.newAppId == id){
//                 this.anchorBasic1 = false;
//                 this.anchorBasic2 = false;
//             }
            for(let basic of res){
                this.basicAppId = basic.applicationEntity.id
                this.anchorBasicDetailsId = basic.id;
                this.anchorName = basic.anchorName;
                this.industry = basic.industry;
                this.sector = basic.sector;
                this.cinNumber = basic.cin;
                this.panNumber = basic.pan;
                this.incorpdate = basic.incorpDate;
                this.activity=basic.activity;
                this.constitution=basic.constitution;
                this.docConstitution = basic.constitution.replace(/ /g, "");
                if(this.newAppId == id){
                    this.businessExpiry = basic.businessExpiry;
                }else{
                    this.businessExpiry = 12;
                }
            }
        }else{
            this.getBasicDetailsById(this.oldAppId);
        }
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}


//Anchor Address Details
getAnchorAddressDetailsById(id): void {
    let response;
    let fileName="anchor/anchorAddressFile/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.anchorAddrList=res;
        if(res.length != 0){
            if(this.newAppId == id){
                this.anchorBasic1 = false;
                this.anchorBasic2 = false;
            }
            for(let address of res){
                this.anchorAddressId= address.id
                this.address1 = address.addressLine1;
                this.address2 = address.addressLine2;
                this.city = address.city;
                this.state = address.state;
                this.country = address.country;
                this.pinCode = address.pinCode;
            }
        }else{
            this.getAnchorAddressDetailsById(this.oldAppId);
        }
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

public anchorBasicValidate = false;

public anchorBasicSubmit(key) {
    this.anchorBasicValidate = false;
    this.anchorBasicValidate = !this.anchorBasicValidate;

    const basicName =/^[A-Za-z\s ]*$/;

    const basicInd =/^[A-Za-z\s ]*$/;
    const basicSec =/^[A-Za-z\s ]*$/;
    const num = /[1-9][0-9]?$/
    const basicPan =/([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}$/;
    const basicCin =/([L|U]{1})([0-9]{5})([A-Za-z]{2})([0-9]{4})([A-Za-z]{3})([0-9]{6})$/;

    var flag = true;
    if(this.anchorName == null || this.anchorName == ''){
        flag = false;
        this.toaster.error("Please Enter valid Anchor Name");
    }else if(!basicInd.test(this.industry)){
        flag = false;
        this.toaster.error("Please Enter valid Industry");
    }else if(this.sector == null || this.sector == ''){
        flag = false;
        this.toaster.error("Please Enter valid Sub Industry");
    }else if(!num.test(this.businessExpiry)){
        flag = false;
        this.toaster.error("Please Enter valid Facility Tenure");
    }else if(!basicPan.test(this.panNumber)){
        flag = false;
        this.toaster.error("Please Enter valid PAN");
    }else if(!basicCin.test(this.cinNumber)){
        flag = false;
        this.toaster.error("Please Enter valid CIN");
    }

    const city =/^[a-zA-Z ]*$/;
    const stateAnchor =/^[a-zA-Z ]*$/;
    const pinCode =/[0-9]{6}$/;

    if(this.address1== "" || this.address1==null){
        flag = false;
        this.toaster.error("Please Enter valid Address 1");
    }else if(this.address2== "" || this.address2==null){
        flag = false;
        this.toaster.error("Please Enter valid Address 2");
    }else if(!pinCode.test(this.pinCode)){
        flag = false;
        this.toaster.error("Please Enter valid Pin code");
    }else if(!city.test(this.city) || this.city == ''){
        flag = false;
        this.toaster.error("Please Enter valid City/District");
    }

    if(this.anchorBasicValidate==true && flag){
        if(key=='save'){
            this.saveAnchorBasicDetails();
        }else if(key=='update'){
            this.updateAnchorBasicDetails();
        }
    }
}

saveAnchorBasicDetails():void{
    let response;
    let fileName="anchor/anchorBasic";
    let data={appId:this.newAppId, anchorName:this.anchorName, industry:this.industry, subIndustry:this.sector, cin:this.cinNumber, pan:this.panNumber, incorporationDate:this.incorpdate,activity:this.activity,constitution:this.constitution,businessExpiry:this.businessExpiry,}
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
//         this.anchorBasic1 = false;
        this.getBasicDetailsById(this.newAppId);
        this.saveAnchorAddressDetails();
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


saveAnchorAddressDetails():void{
    let response;
    let fileName="anchor/anchorAddress";
    let data={ appId : this.newAppId, addressLine1:this.address1, addressLine2:this.address2, city:this.city,
                state:this.state, country:this.country, pinCode:this.pinCode,
                }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.anchorBasic1 = false;
        this.getAnchorAddressDetailsById(this.newAppId);
        this.toaster.success('Successfully Saved');
        this.anchorBasic2=false;
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

updateAnchorBasicDetails(): void {
    this.showLoader=true;
    let response;
    let fileName="anchor/anchorBasic";
    let data={ id:this.anchorBasicDetailsId, appId : this.newAppId, anchorName:this.anchorName, industry:this.industry, subIndustry:this.sector,
    cin:this.cinNumber, pan:this.panNumber, incorporationDate:this.incorpdate,activity:this.activity,constitution:this.constitution,businessExpiry:this.businessExpiry,
    }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader=false;
        this.getBasicDetailsById(this.newAppId);
        this.updateAnchorAddressDetails();
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


updateAnchorAddressDetails(): void {
    let response;
    let fileName="anchor/anchorAddress";
    let data={ id : this.anchorAddressId, appId : this.newAppId, addressLine1:this.address1, addressLine2:this.address2, city:this.city,
                state:this.state, country:this.country, pinCode:this.pinCode,
                }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.getAnchorAddressDetailsById(this.newAppId);
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
                this.toaster.error(error.error.message);
            }
        }
    })
}

//Anchor Details Tab
public anchorDetails1 = true;
public anchorDetails2 = true;

getAnchorKeyDetailsById(id): void {
    let response
    let fileName="anchor/anchorKeyFile/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.anchorKeyDetails=res;
        if(res.length != 0){
            this.getAnchorAuthoriseById(this.newAppId);
//             if(this.newAppId == id){
//                 this.anchorDetails1 = false;
//                 this.anchorDetails2 = false;
//             }
            this.keyPersonArray = [];
            for(var i=0;i <res.length;i++){
                this.addKeyPersonValue();
                if(this.newAppId == id){
                    this.keyPersonArray[i].id = res[i].id;
                }
                this.keyPersonArray[i].appId = this.newAppId;
                this.keyPersonArray[i].type = res[i].type;
                this.keyPersonArray[i].name = res[i].name;
                this.keyPersonArray[i].mobile = res[i].mobile;
                this.keyPersonArray[i].emailId = res[i].emailId;
            }
        }else{
            this.getAnchorKeyDetailsById(this.oldAppId)
        }
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

getAnchorAuthoriseById(id):void {
    let response;
    let fileName="anchor/anchorAuthorizedFile/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.anchorAuthoriseDetails=res;
        if(res.length != 0){
            this.getAnchorGstById(this.newAppId);
//             if(this.newAppId == id){
//                 this.anchorDetails1 = false;
//                 this.anchorDetails2 = false;
//             }
            this.AuthorisedArray = [];
            for(var i=0;i <res.length;i++){
                this.addAuthorisedValue();
                if(this.newAppId == id){
                    this.AuthorisedArray[i].id = res[i].id;
                }
                this.AuthorisedArray[i].appId = this.newAppId;
                this.AuthorisedArray[i].type = res[i].type;
                this.AuthorisedArray[i].name = res[i].name;
                this.AuthorisedArray[i].mobile = res[i].mobile;
                this.AuthorisedArray[i].emailId = res[i].emailId;
                this.AuthorisedArray[i].indemnityDoc = res[i].indemnityDoc;
            }
        }else{
            this.getAnchorAuthoriseById(this.oldAppId)
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

getAnchorGstById(id):void {
    let response;
    let fileName="anchor/anchorGstFile/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.anchorGstList=res;
        if(res.length != 0){
            if(this.newAppId == id){
                this.anchorDetails1 = false;
                this.anchorDetails2 = false;
            }
            this.gstDetailsArray = [];
            for(var i=0;i <res.length;i++){
                this.addGstDetailsValue();
                if(this.newAppId == id){
                    this.gstDetailsArray[i].id = res[i].id;
                }
                this.gstDetailsArray[i].appId=this.newAppId;
                this.gstDetailsArray[i].gstNumber = res[i].gstNumber;
                this.gstDetailsArray[i].gstAccntHolderName = res[i].gstAcctHolderName;
                this.gstDetailsArray[i].addressLine1 = res[i].addressLine1;
                this.gstDetailsArray[i].addressLine2 = res[i].addressLine2;
                this.gstDetailsArray[i].country = res[i].country;
                this.gstDetailsArray[i].state = res[i].state;
                this.gstDetailsArray[i].city = res[i].city;
                this.gstDetailsArray[i].pinCode = res[i].pincode;
            }
        }else{
            this.getAnchorGstById(this.oldAppId);
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

public anchorDetailsValidate = false;

public anchorDetailsSubmit(key) {

    this.anchorDetailsValidate = false;
    this.anchorDetailsValidate = !this.anchorDetailsValidate;

    var flag = true;

    const keyName =/^[a-zA-Z ]*$/;
    const keyType =/^[a-zA-Z ]*$/;
    const keyMobile =/[0-9]{10}/;
    const keyEmail =/^([a-zA-Z0-9_.+-]+)@([a-zA-Z0-9_.+-]+\.(com|co.in|in)$)/;

    for(let keyPerson of this.keyPersonArray){
        if(keyPerson.name=="" || keyPerson.name==null || !keyName.test(keyPerson.name)){
            this.toaster.error('Please Enter valid keyPerson Name')
            flag = false;
        }else if(keyPerson.type=="" || keyPerson.type==null || !keyType.test(keyPerson.type)){
            this.toaster.error('Please Enter valid keyPerson Type')
            flag = false;
        }else if(keyPerson.mobile=="" || keyPerson.mobile==null || !keyMobile.test(keyPerson.mobile)){
            this.toaster.error('Please Enter valid keyPerson Mobile')
            flag = false;
        }else if(keyPerson.emailId=="" || keyPerson.emailId==null || !keyEmail.test(keyPerson.emailId)){
            this.toaster.error('Please Enter valid keyPerson Email Id')
            flag = false;
        }
    }

    const AuthName =/^[a-zA-Z ]*$/;
    const AuthType =/^[a-zA-Z ]*$/;
    const AuthMobile =/[0-9]{10}/;
    const AuthEmail =/^([a-zA-Z0-9_.+-]+)@([a-zA-Z0-9_.+-]+\.(com|co.in|in)$)/;

    for(let authorised of this.AuthorisedArray){
        if(authorised.name=="" || authorised.name==null || !AuthName.test(authorised.name)){
            this.toaster.error('Please Enter valid Authorised Name')
            flag = false;
        }
        if(authorised.type=="" || authorised.type==null || !AuthType.test(authorised.type)){
            this.toaster.error('Please Enter valid Authorised Type')
            flag = false;
        }
        if(authorised.mobile=="" || authorised.mobile==null || !AuthMobile.test(authorised.mobile)){
            this.toaster.error('Please Enter valid Authorised Mobile')
            flag = false;
        }
        if(authorised.email=="" || authorised.emailId==null || !AuthEmail.test(authorised.emailId)){
            this.toaster.error('Please Enter valid Authorised Email-Id')
            flag = false;
        }
    }

    const gstNumber =/([0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1})$/;
    const gstAcctHolderName =/^[a-zA-Z ]*$/;
    const city =/^[a-zA-Z ]*$/;
    const state = /^[a-zA-Z ]*$/;
    const pincode = /[0-9]{6}/;

    for(let gstDetails of this.gstDetailsArray){
        if(gstDetails.gstNumber=="" || gstDetails.gstNumber==null  || !gstNumber.test(gstDetails.gstNumber)){
            this.toaster.error('Please Enter valid GST Number')
            flag = false;
        }else if(gstDetails.gstAccntHolderName=="" || gstDetails.gstAccntHolderName==null || !gstAcctHolderName.test(gstDetails.gstAccntHolderName)){
            this.toaster.error('Please Enter valid GST Account Holder Name');
            flag = false;
        }else if(gstDetails.addressLine1=="" || gstDetails.addressLine1==null){
            this.toaster.error('Please Enter valid addressLine1');
            flag = false;
        }else if(gstDetails.addressLine2=="" || gstDetails.addressLine2==null){
            this.toaster.error('Please Enter valid addressLine2');
            flag = false;
        }else if(gstDetails.pinCode=="" || gstDetails.pinCode==null || !pincode.test(gstDetails.pinCode)){
            this.toaster.error('Please Enter valid pin code');
            flag = false;
        }else if(gstDetails.city=="" || gstDetails.city==null || !city.test(gstDetails.city)){
            this.toaster.error('Please Enter valid city');
            flag = false;
        }else if(gstDetails.state=="" || gstDetails.state==null || !state.test(gstDetails.state)){
            this.toaster.error('Please Enter valid state');
            flag = false;
        }
    }
    if(this.anchorDetailsValidate && flag){
        if(key == 'save'){
            this.saveKeyPersonDetails();
        }else if(key == 'update'){
            this.updateKeyPersonDetails();
        }
    }
}


//Save Key Person Details
saveKeyPersonDetails():void{
    let response;
    let fileName="anchor/anchorKey/"+this.newAppId;
    let data={anchorKeyDataList:this.keyPersonArray}
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.getAnchorKeyDetailsById(this.newAppId);
        this.saveAuthorisedPersonDetails();
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

//Save Authorise Details
saveAuthorisedPersonDetails():void{
    let response;
    let fileName="anchor/anchorAuthorized/"+this.newAppId;
    let data={anchorAuthDataList:this.AuthorisedArray}
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.getAnchorAuthoriseById(this.newAppId);
        this.saveGstDetails();
        this.toaster.success('Successfully Saved');
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

//Anchor GST Details
saveGstDetails():void{
    let response;
    let fileName="anchor/anchorGst/"+this.newAppId;
    let data={anchorGstDetailsDataList:this.gstDetailsArray}
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.getAnchorGstById(this.newAppId);
        this.anchorDetails1 = false;
        this.anchorDetails2 = false;
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
                var msg =error.error.message;
                for(let i=0 ; i<this.gstDetailsArray.length;i++){
                    msg = msg.replaceAll("anchorGstDetailsDataList["+i+"]", i+1);
                }
                this.toaster.error(msg);
            }
        }
    })
}

//Update Key Person Details
updateKeyPersonDetails(): void {
    this.showLoader=true;
    let response;
    let fileName="anchor/anchorKey";
    let data={ anchorKeyDataList : this.keyPersonArray }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader=false;
        this.getAnchorKeyDetailsById(this.newAppId);
        this.updateAuthorisedPersonDetails();
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

//Update Authorised Person Details
updateAuthorisedPersonDetails(): void  {
    let response;
    let fileName="anchor/anchorAuthorized";
    let data={ anchorAuthDataList:this.AuthorisedArray }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.getAnchorAuthoriseById(this.newAppId);
        this.updateGstDetails();
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

//Update Gst Details
updateGstDetails(): void {
    let response;
    let fileName="anchor/anchorGst";
    let data={ anchorGstDetailsDataList:this.gstDetailsArray }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.getAnchorGstById(this.newAppId);
        this.toaster.success('Successfully Updated');
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

//Program Norms Details
getProgramNormsDetails(id):void {
    let response;
    let fileName="anchor/anchorProgramsFile/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.pnDetailsCount = 0 ;
        this.AnchorProgramList=res;
        if(res.length != 0){
            if(id == this.newAppId){
                this.programNorms1=true;
                this.programNorms2=false;
            }
            this.anchorProgramNormsArray = [];
            for(var i=0;i <res.length;i++){
                this.pnDetailsCount = this.pnDetailsCount +1;
                this.addAnchorProgramNormsValue();
                if(id == this.newAppId){
                    this.anchorProgramNormsArray[i].id =  res[i].id;
                }
                this.anchorProgramNormsArray[i].appId = this.newAppId;
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
                this.anchorProgramNormsArray[i].productName = res[i].subProduct;
            this.anchorProgramNormsArray[i].productExpiry = res[i].productExpiry;


                this.productName1[i] = res[i].subProduct;
                console.log("productName1 :::",this.productName1)


                if(!this.docProductCheck){
                    if(res[i].subProduct == 'Anchor Purchase Bill Discounting' || res[i].subProduct == 'Anchor Sales Bill Discounting'){
                        this.docProductCheck = true;
                    }
                }
               const index = this.dropdownOptions.indexOf(this.anchorProgramNormsArray[i].subProduct);
               console.log("index **",index)
               if(index !==-1){
               this.dropdownOptions.splice(index,1)
               console.log("this.dropdownOptions ::",this.dropdownOptions)
               }
            }
        }else{
            this.getProgramNormsDetails(this.oldAppId)
        }
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

saveProgramNormsDetails():void{

 var saveProgramNameCondition =true;
for(var i = 0 ; i<this.anchorProgramNormsArray.length;i++){
            for(var j = i+1; j<this.anchorProgramNormsArray.length;j++){
                if(this.anchorProgramNormsArray[i].subProduct == this.anchorProgramNormsArray[j].subProduct){
                        this.toaster.error("Can't map same product twice",this.anchorProgramNormsArray[i].subProduct);
                        saveProgramNameCondition = false;
                    }
                }
            }
if(saveProgramNameCondition){
    let response;
    let fileName="anchor/anchorProgramsDetails";
    let data={ programNormsDataList : this.anchorProgramNormsArray }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.getProgramNormsDetails(this.newAppId);
        this.programNorms1 = true;
        this.programNorms2=false;
        this.toaster.success('Successfully Saved');
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
                var msg =error.error.message;
                for(let i=0 ; i<this.anchorProgramNormsArray.length;i++){
//                     Use of String replace() Method
                    msg = msg.replaceAll("programNormsDataList["+i+"]", i+1);
                }
                this.toaster.error(msg);
            }
        }
    })
 }
}
updateProgramNormsDetails(): void {

          var sameProgram = true;
          for(var i = 0 ; i<this.anchorProgramNormsArray.length;i++){
            for(var j = i+1; j<this.anchorProgramNormsArray.length;j++){
                if(this.anchorProgramNormsArray[i].subProduct == this.anchorProgramNormsArray[j].subProduct){
                        this.toaster.error("Can't map same product twice",this.anchorProgramNormsArray[i].subProduct);
                         sameProgram = false;
                    }
                }
           }
if(sameProgram){
    this.showLoader=true;
    let response;
    let fileName="anchor/anchorPrograms";
    let data = { programNormsDataList : this.anchorProgramNormsArray }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader=false;
        this.getProgramNormsDetails(this.newAppId);
        this.toaster.success('Updated Successfully');
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
                var msg =error.error.message;
                for(let i=0 ; i<this.anchorProgramNormsArray.length;i++){
//                     Use of String replace() Method
                    msg = msg.replaceAll("programNormsDataList["+i+"]", i+1);
                }
                this.toaster.error(msg);
            }
        }
    })
 }
}


//Credit Norms Details

getCreditNormsLabels(): void{
    this.showLoader=true;
    let response;
    let fileName="anchor/creditNormsMaster";
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.showLoader=false;
        this.CreditNormsMasterList = res;
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

public creditNormValidate = false;
public creditNorms1 = true;
public creditNorms2 = true;
// public cnControl = false;

setCreditNormsDetails(id): void{
    this.showLoader=true;
    let response;
    let fileName="anchor/creditNormsDetailsByFId/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.showLoader=false;
        this.creditNormsDetailsList = res;
        if(res.length != 0){
            if(this.newAppId == id){
                this.creditNorms1 = false;
                this.creditNorms2 = false;
            }
            this.creditNormsArray = [];
            for(let item of this.CreditNormsMasterList){
                for(var i=0; i<res.length; i++){
                    if(item.id == res[i].creditNormsMasterEntity.id){
                        (<HTMLInputElement>document.getElementById(item.name)).value = res[i].value;
                        const a1 = { id:res[i].id, appId: this.newAppId ,cnMasterId : res[i].creditNormsMasterEntity.id,value : res[i].value}
                        this.creditNormsArray.push(a1);
                    }
                }
            }
            console.log(this.creditNormsArray,'this.creditNormsArray');
        }else{
            this.setCreditNormsDetails(this.oldAppId);
        }
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

storeCreditNormsWorkFlow(id,item: any):void{
    var enable;
    if(Event.prototype.isPrototypeOf(item)){
        this.creditNormsValue=item.target.value;
    }else{
        this.creditNormsValue=item.toString();
    }
    if(this.creditNormsArray.length != 0){
        for(let itemId of this.creditNormsArray){
            if(itemId.cnMasterId == id){
                let index = this.creditNormsArray.indexOf(itemId);
                if(!this.creditNorms2){
                    var pId = itemId.id;
                    const b = { id: pId, appId: this.newAppId ,cnMasterId : id,value : this.creditNormsValue,}
                    this.creditNormsArray[index] = b;
                }else{
                    const b = { appId: this.newAppId ,cnMasterId : id,value : this.creditNormsValue,}
                    this.creditNormsArray[index] = b;
                }
               enable=1;
                break;
            }else{
                enable=0;
            }
        }
    }else{
        const b = { appId: this.newAppId ,cnMasterId : id,value : this.creditNormsValue,}
        this.creditNormsArray.push(b);
    }
    if(enable==0){
        const b = { appId: this.newAppId ,cnMasterId : id,value : this.creditNormsValue,}
        this.creditNormsArray.push(b);
    }
}

public creditNormSubmit(key) {
    this.showLoader=true;
//     this.cnControl = true;
    this.creditNormValidate = false;
    this.creditNormValidate = !this.creditNormValidate;
    let flag = false;
    if(this.creditNormsArray.length == this.CreditNormsMasterList.length){
        flag=true;
//         this.cnControl = false;
    }else{
        flag=false;
//         this.cnControl = false;
    }
    if(this.creditNormValidate && flag){
        this.showLoader=false;
        if(key == 'save'){
            this.saveCreditNorms();
        }else if(key == 'update'){
            this.updateCreditNorms();
        }
    }else{
        this.showLoader=false;
//         this.cnControl = false;
        this.toaster.error('Check once whether fill all field');
    }
}

saveCreditNorms(): void {
    this.showLoader=true;
//     this.cnControl = true;
    let response;
    let fileName="anchor/creditNormsDetails";
    let data={ creditNormsDetailsDataList : this.creditNormsArray };
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader=false;
        if(res.message === 'Validation Success'){
            this.toaster.success('Successfully Saved');
//             this.cnControl = false;
            this.creditNorms1 = false;
            this.creditNorms2 = false;
            this.setCreditNormsDetails(this.newAppId);
        }else{
            this.toaster.error(res.message);
//             this.cnControl = false;
        }
    },error=>{
        this.showLoader=false;
//         this.cnControl = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
                this.creditNorms1 = true;
            }else{
                this.toaster.error(error.error.message);
                this.creditNorms1 = true;
            }
        }
    })
}

updateCreditNorms(): void {
    this.showLoader=true;
//     this.cnControl = true;
    let response;
    let fileName="anchor/creditNormsDetails";
    let data={ creditNormsDetailsDataList : this.creditNormsArray };
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader=false;
        this.toaster.success('Successfully Updated');
//         this.cnControl = false;
        this.creditNorms1 = false;
        this.setCreditNormsDetails(this.newAppId);
    },error=>{
        this.showLoader=false;
//         this.cnControl = false;
        if(error.status==401){
             this.refresh_token=localStorage.getItem('refresh_token')
             this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error');
            }else{
                this.toaster.error(error.error.message);
            }
        }
    })
}

getCreditNormsDetails(appId): void{
    let response;
    let fileName="anchor/creditNormsDetailsByFId/"+appId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.creditNormsDetailsList = res;
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

getRemarks():void{
    let response;
    let fileName="wfApprovalStatus/getRemarks/"+this.newAppId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.remarkArray=res;
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

//Beneficiary Details
BeneficiaryDetailsList:[]=[];
bankList:any;branchList:any;bankDetails:any;bankBranchDetails:any;benPkId:any;beneficiaryType:any;
beneficiaryName:any;bankName:any;bankCode:any;bankAccountNumber:any;bankIfscCode:any;bankBranchName:any;bankBranchCode:any;

public beneficiaryValidate = false;
public beneficiary1 = true;
public beneficiary2 = true;

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

getAnchorBeneficiaryById(id):void {
    this.BeneficiaryDetailsList=[];
    let response;
    let fileName="anchor/anchorBeneficiaryFile/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.BeneficiaryDetailsList=res;
        if(this.BeneficiaryDetailsList.length != 0){
            if(this.newAppId == id){
                this.beneficiary1 = false;
                this.beneficiary2 = false;
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
            this.getAnchorBeneficiaryById(this.oldAppId);
        }
    },error=>{
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
    const benefiBankName =/^[a-zA-Z ]*$/;
    const benefiBankAccnt =/^[a-zA-Z0-9]+$/;
    var flag =true;
    if(this.beneficiaryName==""  || this.beneficiaryName==null){
        this.toaster.error("Please enter valid beneficiary name");
        flag=false;
    }else if(this.bankName==""  || this.bankName==null){
        this.toaster.error("Please enter valid bank name");
        flag=false;
    }else if(this.bankIfscCode==""  || this.bankIfscCode==null){
        this.toaster.error("Please enter valid bank IFSC");
        flag=false;
    }else if(this.bankBranchName==""  || this.bankBranchName==null){
        this.toaster.error("Please enter valid bank branch name");
        flag=false;
    }else if(this.bankAccountNumber==""  || this.bankAccountNumber==null || !benefiBankAccnt.test(this.bankAccountNumber)){
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
        this.beneficiary2 = false;
        this.toaster.success('Successfully Saved');
        this.getAnchorBeneficiaryById(this.newAppId);
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
                this.beneficiary2 = true;
            }else{
                this.toaster.error(error.error.message);
                this.beneficiary1 = true;
                this.beneficiary2 = true;
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
        this.toaster.success('Successfully Updated')
        this.getAnchorBeneficiaryById(this.newAppId);
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

/////////////////////DMS Module///////////////////////
docReports:Array<any> = [];
documentMaster:Array<any> = [];
renewalEnhancementReport:Array<any> = [];
docValidationDataArray : Array<any> = [];
deferralDocumentArray: Array<any> = [];
deferralDocReportArray: Array<any> = [];
//Other deferral
OtherDocReports: Array<any> = [];

rmName:any;rmNames:any;

public loading = false;
public docValidationCheck = false;
public deferralCheck = false;
public docProductCheck = false;
public deferralDocView = false;
public otherDocLabelView = false;
public otherDocDefLabelView = false;

//Others Document
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

// getUpdateDocReport(): void{
//     let response;
//     let fileName="dms/customerDocReports?appId="+this.newAppId;
//     response = this.requestapi.getData(fileName);
//     response.subscribe((res:any) => {
//         if(res.documentReports.length>0){
//             this.getDocumentsReports();
//         }
//     },error=>{
//         if(error.status==401){
//             this.refresh_token=localStorage.getItem('refresh_token')
//             this.authService.SignOut(this.refresh_token);
//         }
//         this.docReports = null;
//     })
// }

// getUpdateDocReports(): void{
//     let response;
//     let fileName="dms/customerDocReports?appId="+this.newAppId;
//     response = this.requestapi.getData(fileName);
//     response.subscribe((res:any) => {
//         if(res.documentReports.length>0){
//             this.docReports = res.documentReports;
// //             this.getDocumentsReports();
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
    let response;
    let fileName="dms/documentReports?appId="+this.newAppId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        if(res.documentReports.length>0){
            this.docReports = res.documentReports;
        }else{
           this.docReports = null;
        }
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        this.docReports = null;
    })
}

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

uploadDocument(event,docTypeId,docTypeName,docCategoryId,docCategoryName,docListId,docListName,key){
    this.loading = true;
    let file = event.target.files[0];
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
    let data ={ appId:this.newAppId, docTypeId:docTypeId, docTypeName:docTypeName, docCategoryId:docCategoryId,
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

documentValidation(key): void{
    this.docValidationCheck = true;
    let response;
    let fileName="dms/docValidation";
    let data={ appId : this.newAppId, customerType :1, docValidationData:this.docValidationDataArray,constitution : "Proprietorship", anchorType:"Vendor Type", assessmentType:"KYC" }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res:any) => {
        this.docValidationCheck = false;
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

opsMakerNext(key){
    if(key == 'save'){
        this.saveDeferralDocumentReports();
    }else if(key == 'update'){
        this.updateDeferralDocumentReports();
    }
}

//Deferral Module
getDeferralReport(): void{
    let response;
    let fileName="dms/deferralReports/"+this.newAppId;
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
            console.log("Response null");
        }
    },error=>{

    if(error.status==401){
          this.refresh_token=localStorage.getItem('refresh_token')
                  this.authService.SignOut(this.refresh_token);
        }

    })
}

setDocDate(): void{
    this.deferralDocumentArray = [];
    for(let docType of this.documentMaster){
        if(docType.type == 1){
            for(let docCategory of docType.documentCategoryEntities){
                for(let docList of docCategory.documentListEntities){
                    if(docList.deferral == 1 && docList.status == 1 && docList.type == 0 && docList.mandatory == 1){
                        (<HTMLInputElement>document.getElementById(docList.id+"B")).checked = true;
                        const b = { appId: this.newAppId ,docListId : docList.id,status : 2,deferralType : docList.type}
                        this.deferralDocumentArray.push(b);
                    }
                    if(docList.deferral == 2 && docList.status == 1 && docList.type == 0 && docList.id != 20161 && this.docProductCheck){
                        var date = new Date();
                        var days = docList.deferralTime+1;
                        date.setDate(date.getDate() + days);
                        var date1 = (<HTMLInputElement>document.getElementById(docList.id+'C'));
                        var dateString = date.toISOString().split('T')[0];
                        date1.value = dateString;
                        (<HTMLInputElement>document.getElementById(docList.id+"E")).style.display = "none";
                        (<HTMLInputElement>document.getElementById(docList.id+"F")).style.display = "none";
                        (<HTMLInputElement>document.getElementById(docList.id)).style.display = "none";
                        const b = { appId: this.newAppId ,docListId : docList.id,initialTime : dateString,status : 0,deferralType : docList.type}
                        this.deferralDocumentArray.push(b);
                    }else if(this.docConstitution == 'PrivateCompany' || this.docConstitution == 'PublicCompany-Listed' || this.docConstitution == 'PublicCompany-Unlisted'){
                        if(docList.id == 20161 && docList.status == 1 && docList.type == 0 && this.docProductCheck){
                            var date = new Date();
                            var days = docList.deferralTime+1;
                            date.setDate(date.getDate() + days);
                            var date1 = (<HTMLInputElement>document.getElementById(docList.id+'C'));
                            var dateString = date.toISOString().split('T')[0];
                            date1.value = dateString;
                            (<HTMLInputElement>document.getElementById(docList.id+"E")).style.display = "none";
                            (<HTMLInputElement>document.getElementById(docList.id+"F")).style.display = "none";
                            (<HTMLInputElement>document.getElementById(docList.id)).style.display = "none";
                            const b = { appId: this.newAppId ,docListId : docList.id,initialTime : dateString,status : 0,deferralType : docList.type}
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
        const b = { appId: this.newAppId ,docListId : docListId,status : status,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
    if(flag==0){
        const b = { appId: this.newAppId ,docListId : docListId,status : status,deferralType : deferralType,}
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
        const b = { appId: this.newAppId ,docListId : docListId,initialTime : date,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
    if(flag==0){
        const b = { appId: this.newAppId ,docListId : docListId,initialTime : date,deferralType : deferralType,}
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
        const b = { appId: this.newAppId ,docListId : docListId,documentId : docId,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
    if(flag==0){
        const b = { appId: this.newAppId ,docListId : docListId,documentId : docId,deferralType : deferralType,}
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
        const b = { appId: this.newAppId ,docListId : docListId,docCompletionDate : docComDate,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
    if(flag==0){
        const b = { appId: this.newAppId ,docListId : docListId,docCompletionDate : docComDate,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
    console.log("this.deferralDocumentArray",this.deferralDocumentArray);
}


saveDeferralDocumentReports(): void {
    this.showLoader=true;
    let response;
    let fileName="dms/deferralReport";
    let data={ appId : this.newAppId, deferralReportsDataList : this.deferralDocumentArray, customerType : 1, rmName : this.rmName, docProductCheck : this.docProductCheck, constitution : this.constitution};
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader=false;
        this.getDeferralDocReports();
        this.updateOtherDocuments();
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
}

updateDeferralDocumentReports(): void {
    this.showLoader=true;
    let response;
    let fileName="dms/deferralReport";
//     if(this.rmName != undefined){
        let data={ appId : this.newAppId, deferralReportsDataList : this.deferralDocumentArray, customerType : 1, rmName : this.rmName, docProductCheck : this.docProductCheck, constitution : this.constitution};
        response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
            this.getDeferralDocReports();
            this.updateOtherDocuments();
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
}

getDeferralDocumentReports(id): void{
    let chargeFileCheck = true;
    let cersaiCheck = true;
    let response;
    let fileName="dms/deferralReport/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.deferralDocumentArray = [];
        if(res.length==0 && id == this.oldAppId){
            this.setDocDate();
        }else if(res.length != 0 && id == this.newAppId){
            this.deferralCheck = true;
        }
        if(res.length != 0){
            for(var item of res){
                this.rmName = item.rmName;
                if(item.documentListEntity.deferral == 1 && id == this.newAppId){
                    if(item.status == 0){
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"A")).checked = true;
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"C")).style.display = "block";
                    }else if(item.status == 2 || item.status == 3){
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"B")).checked = true;
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"C")).style.display = "none";
                    }
                }
                if(item.documentListEntity.deferral == 2){
                    if(item.status == 0 || item.status == 3){
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
                if(item.initialTime != null && item.documentListEntity.deferral == 1 && id != this.oldAppId){
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
                        if(item.status == 0 || item.status == 3){
                            (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"C")).style.display = "block";
                        }else{
                            (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"C")).style.display = "none";
                        }
                    }
                }
                
                if(id == this.newAppId){
                    if(item.documentListEntity.deferral != 2){
                        if(item.status == 2){
                            const a = { id : item.id, appId: this.newAppId ,docListId : item.documentListEntity.id,initialTime : null,status : item.status, documentId : item.documentId, docCompletionDate : item.docCompletionDate,deferralType : item.documentListEntity.type,}
                            this.deferralDocumentArray.push(a);
                        }else if(item.status == 3){
                            const a = { id : item.id, appId: this.newAppId ,docListId : item.documentListEntity.id,initialTime : null,status : 2, documentId : item.documentId, docCompletionDate : item.docCompletionDate,deferralType : item.documentListEntity.type,}
                            this.deferralDocumentArray.push(a);
                        }else{
                            const a = { id : item.id, appId: this.newAppId ,docListId : item.documentListEntity.id,initialTime : item.initialTime,status : item.status, documentId : item.documentId, docCompletionDate : item.docCompletionDate,deferralType : item.documentListEntity.type,}
                            this.deferralDocumentArray.push(a);
                        }
                    }else{
                        if(item.status == 2){
                            const a = { id : item.id, appId: this.newAppId ,docListId : item.documentListEntity.id,initialTime : item.initialTime,status : item.status, documentId : item.documentId, docCompletionDate : item.docCompletionDate,deferralType : item.documentListEntity.type,}
                            this.deferralDocumentArray.push(a);
                        }else if(item.status == 3){
                            const a = { id : item.id, appId: this.newAppId ,docListId : item.documentListEntity.id,initialTime : item.initialTime,status : 2, documentId : item.documentId, docCompletionDate : item.docCompletionDate,deferralType : item.documentListEntity.type,}
                            this.deferralDocumentArray.push(a);
                        }else{
                            const a = { id : item.id, appId: this.newAppId ,docListId : item.documentListEntity.id,initialTime : item.initialTime,status : item.status, documentId : item.documentId, docCompletionDate : item.docCompletionDate,deferralType : item.documentListEntity.type,}
                            this.deferralDocumentArray.push(a);
                        }
                    }
                }else{
                    if(item.documentListEntity.deferral == 2){
                        if(item.status == 2){
                            const a = { appId: this.newAppId ,docListId : item.documentListEntity.id,initialTime : item.initialTime,status : item.status, documentId : item.documentId, docCompletionDate : item.docCompletionDate,deferralType : item.documentListEntity.type,}
                            this.deferralDocumentArray.push(a);
                        }else if(item.status == 3){
                            const a = { appId: this.newAppId ,docListId : item.documentListEntity.id,initialTime : item.initialTime,status : 0, documentId : item.documentId, docCompletionDate : item.docCompletionDate,deferralType : item.documentListEntity.type,}
                            this.deferralDocumentArray.push(a);
                        }else{
                            const a = { appId: this.newAppId ,docListId : item.documentListEntity.id,initialTime : item.initialTime,status : item.status, documentId : item.documentId, docCompletionDate : item.docCompletionDate,deferralType : item.documentListEntity.type,}
                            this.deferralDocumentArray.push(a);
                        }
                    }
                }

                if(item.documentListEntity.id == 20161){
                    chargeFileCheck = false;
                }
                if(item.documentListEntity.id == 20160){
                    cersaiCheck = false;
                }
            }
        }else if(id == this.newAppId){
            this.getDeferralDocumentReports(this.oldAppId);
        }
        if(cersaiCheck || chargeFileCheck){
            for(let docType of this.documentMaster){
                if(docType.type == 1){
                    for(let docCategory of docType.documentCategoryEntities){
                        for(let docList of docCategory.documentListEntities){
                            if(cersaiCheck && res.length != 0 && docList.deferral == 2 && docList.status == 1 && docList.type == 0 && docList.id != 20161 && this.docProductCheck){
                                let date = new Date();
                                let days = docList.deferralTime+1;
                                date.setDate(date.getDate() + days);
                                let date1 = (<HTMLInputElement>document.getElementById(docList.id+'C'));
                                var dateString = date.toISOString().split('T')[0];
                                date1.value = dateString;
                                (<HTMLInputElement>document.getElementById(docList.id+"E")).style.display = "none";
                                (<HTMLInputElement>document.getElementById(docList.id+"F")).style.display = "none";
                                (<HTMLInputElement>document.getElementById(docList.id)).style.display = "none";
                                const b = { appId: this.newAppId ,docListId : docList.id,initialTime : dateString,status : 0,deferralType : docList.type}
                                this.deferralDocumentArray.push(b);
                            }else if(chargeFileCheck && res.length != 0 && this.docConstitution == 'PrivateCompany' || this.docConstitution == 'PublicCompany-Listed' || this.docConstitution == 'PublicCompany-Unlisted'){
                                if(docList.id == 20161 && docList.status == 1 && docList.type == 0 && this.docProductCheck){
                                    let date = new Date();
                                    let days = docList.deferralTime+1;
                                    date.setDate(date.getDate() + days);
                                    let date1 = (<HTMLInputElement>document.getElementById(docList.id+'C'));
                                    var dateString = date.toISOString().split('T')[0];
                                    date1.value = dateString;
                                    (<HTMLInputElement>document.getElementById(docList.id+"E")).style.display = "none";
                                    (<HTMLInputElement>document.getElementById(docList.id+"F")).style.display = "none";
                                    (<HTMLInputElement>document.getElementById(docList.id)).style.display = "none";
                                    const b = { appId: this.newAppId ,docListId : docList.id,initialTime : dateString,status : 0,deferralType : docList.type}
                                    this.deferralDocumentArray.push(b);
                                }
                            }
                        }
                    }
                }
            }
        }
        console.log("this.deferralDocumentArray1",this.deferralDocumentArray);
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
    console.log("this.deferralDocumentArray",this.deferralDocumentArray);
}

getDeferralDocReports(): void{
    let response;
    let fileName="dms/deferralReport/"+this.newAppId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.deferralDocumentArray = [];
        if(res.length != 0){
            this.deferralCheck = true;
        }
        for(var item of res){
            const a = { id : item.id, appId: this.newAppId ,docListId : item.documentListEntity.id,initialTime : item.initialTime,status : item.status, documentId : item.documentId, docCompletionDate : item.docCompletionDate,deferralType : item.documentListEntity.type,deferral : item.documentListEntity.deferral,}
            this.deferralDocumentArray.push(a);
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
    let data={ appId : this.newAppId, otherDocumentDataList : this.otherDocumentArray, customerType : 1, rmName : this.rmName, constitution : this.constitution }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader = false;
        this.opsMakerDocumentNp=false;
        this.opsMakerRemarksNp = true;
        this.getOtherDocMaster();
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
    let data={ appId : this.newAppId, otherDocumentDataList : this.otherDocumentArray, customerType : 1, rmName : this.rmName, constitution : this.constitution }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader = false;
        this.opsMakerDocumentNp=false;
        this.opsMakerRemarksNp = true;
        this.getOtherDocMaster();
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
//         this.toaster.error('File Already Deleted');

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
// this.showLoader=true;
    let response;
    let fileName="dms/customerOtherDocReports?appId="+this.newAppId;
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
                this.rmNames = res[i].rmName;
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
                this.rmNames = res[i].rmName;
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
        let data1={ stageId:29,status:2,approverInfo:this.emailId,appId:this.newAppId,remarks:"Deferral Workflow Started"
        ,nextApproverInfo:"ANCHOR_DEFERRAL_COMMITTEE_LEAD"}
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
    let data={ appId: this.newAppId,otherDocumentDataList:this.OtherDeferralDocArray,rmName : this.rmNames}
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

//Pin Code

getPincodeDetails(event,key,i):void {
    let pinCode = event.target.value;
    let response;
    let fileName="anchor/getPincodeDetails?pinCode="+pinCode;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any)=>{
        for(let item of res.addressDetails){
            if(item.status == "Success"){
                this.state=item.state;
                this.city=item.city;
            }else if(item.status == "Fail"){
                this.state=null;
                this.city=null;
                this.toaster.error("Enter Valid PIN Code!!!");
            }
        }
        this.anchorAddressMaster = res.addressDetails;
        if(key=='gst'){
            for(let address of res.addressDetails){
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

}
