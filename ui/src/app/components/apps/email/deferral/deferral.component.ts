import { Component, OnInit,ViewChild } from '@angular/core';
import { ApiRequestService } from 'src/app/shared/services/api-request.service';
import { AuthService } from 'src/app/shared/services/firebase/auth.service';
import { environment } from '../../../../../environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { of } from 'rxjs/internal/observable/of';
import $ from "jquery";
import { CommonModule,DatePipe } from '@angular/common';
import { ToastrService } from 'ngx-toastr';
declare var require
const Swal = require('sweetalert2')
import {WizardComponent, ConfigurableNavigationMode} from 'angular-archwizard';
import { Router } from '@angular/router';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-deferral',
  templateUrl: './deferral.component.html',
  styleUrls: ['./deferral.component.scss']
})

export class DeferralComponent implements OnInit {
options$: Observable<string[]>;
@ViewChild(WizardComponent)

public wizard: WizardComponent;
viewData:[]=[];
public  deferralOpsMakerRemarks = false;
public deferralOpsCheckerRemarks = false;
public deferralOpsMaker = false;
public deferralPending = true;
public anchorCounterParty = true;
public anchorCounterPartys = true;
public anchorDetails = false;
public anchorDetailss = false;
public counterPartyDetails = false;
public counterPartyDetailss = false;
public opsCheckerDeferralNp = false;
public opsMakerDeferralNp = true;
public defBasicDetailsChecker = true;
public defDocumentsChecker = false;
public deferralDocuments = false;
public deferralBasicDetails = false;
public deferralRemarks = false;
public opsCheckerPendingPrevious = false;
public opsCheckerPending = true;
public deferralPendings = true;
public errorMsg = false;
public revisedTimeDis = false;
public anchorCountCheck = true;
public deferralOpsMakerStage = false;
public deferralOpsCheckerStage = false;
// public CersaiId = true;
public CersaiId = true;
public DateOfCompletion= true;
public DateOffCompletion = true;
public validate = false;
public NormalFile = true;
public CersaiFile = false;
public IdValidation = true;
public NewETA = false;
public DeferralStatus1 = false;
public DeferralStatus0 = true;
public revisedDate = false;
public DeferralStatus = false;
public NextEnable = true;
public otherDocs = false;
public OtherDoc = false;
public deferralDocs = false;

addDeferralDocArray(){
this.DeferralDocArray.push(this.newDeferralAttribute)
this.newDeferralAttribute = {};
}
deleteDeferralDocArray(index){
this.DeferralDocArray.splice(index,1);
}

addOtherDeferralDocArray(){
this.OtherDeferralDocArray.push(this.newOtherDeferralAttribute)
this.newOtherDeferralAttribute = {};
}
deleteOtherDeferralDocArray(index){
this.OtherDeferralDocArray.splice(index,1);
}


rbacArray: Array<any> = [];
remarkWf:any;
remarkArray:[]=[];
showLoader: boolean = false;
otherDocumentArray: Array<any> = [];
nextDeferralOpsMaker(){
        this.deferralOpsMakerRemarks = true;
        this.deferralOpsMaker = false;
        this.deferralOpsMakerStage = true;
}
previousDeferralOpsMaker(){
        this.deferralOpsMakerRemarks = false;
        this.deferralOpsMaker = true;
}

previousDeferralPending(){
    this.deferralOpsMaker = false;
    this.deferralPending = true;
    this.anchorCountCheck = true;
    this.deferralOpsMakerStage = false;
    this.deferralOpsCheckerStage = false;
}

getAnchors():void{
    this.anchorCounterParty = false;
    this.anchorDetails = true;
}
getAnchorss():void{
    this.anchorCounterPartys = false;
    this.anchorDetailss = true;
}
getCounterPartys():void{
    this.anchorCounterPartys = false;
    this.counterPartyDetailss= true;
}
getDeferralOpsMaker():void{
    this.deferralBasicDetails = false;
    this.deferralOpsMaker= true;
}
nextDeferralOpsChecker():void{
    this.defBasicDetailsChecker = false;
    this.defDocumentsChecker = true;
}
previousDeferralOpsChecker():void{
    this.defDocumentsChecker = false;
    this.defBasicDetailsChecker = true;
}

previousDeferralOpsCheckers():void{
    this.defDocumentsChecker = true;
    this.deferralOpsCheckerRemarks =false;
}

previousAnchorSelect():void{
    this.deferralOpsCheckerStage = false;
    this.deferralOpsMakerStage = false;
    this.deferralPending = true;
    this.anchorCountCheck = true;
    this.deferralBasicDetails = false;
}
previousBasicDetails():void{
    this.deferralBasicDetails = true;
    this.deferralOpsMaker= false;
}


getCounterParty():void{
    this.anchorCounterParty = false;
    this.counterPartyDetails= true;
    if(this.StageId == 33){
    this.getCustomerCount(2);
    }else if(this.StageId == 32){
    this.getCounterPartyDetails();
    }
}
viewOperationMaker(){
   this.deferralPending = false;
   this.deferralOpsMaker = true;
}
customerDashboard():void{
    this.anchorCounterParty = true;
    this.anchorDetails = false;
    this.counterPartyDetails = false;
    }
customerDashboards():void{
    this.anchorCounterPartys = true;
    this.anchorDetailss = false;
    this.counterPartyDetailss = false;
    }
docCompletionDate:any;
updateRes:any;
appType:any;
productRemarks:any;
nameRemarks:any;
regularLimitRemarks:any;
adhocRemarks:any;
creditPeriodRemarks:any;
DoorRemarks:any;
invoiceRemarks:any;
marginRemarks:any;
interestRemarks:any;
pfRemarks:any;
renewalRemarks:any;
interestTypeRemarks:any;
renewalPeriodRemarks:any;
approvalRemarks:any;
anchorCount:any;counterPartyCount:any;
anchorCount1:any;counterPartyCount1:any;
searchAnchor:any;searchCounterParty:any;
refresh_token:any;
revisedTime2:any;
initialTime2:any;
DocListEntity:any;
status:any;
ApprovalStatusReview:any;
documentcategoryId:any;
docTypeId:any;
docListId:any;
docTypeName:any;
docCategoryId:any;
docCategoryName:any;
document:any;
results:any;
minDate:any;
maximumDate:any;
maxDate:any;
emailId:any;
documentId:any;
counterParty:any;
StageId:any;
docComDate:any;
counts:any;
public countsother = 0;
index:any;
indexothers:any;
docId:any;
datess:any;
datesss:any;
otherDocRes:any;
deferralType:any;
completedValue:any;
extenddate:any;
extendotherdate:any;
OtherDeferralDocArray: Array<any> = [];

rmNameArray: Array<any> = [];
// newDeferralAttribute:any ={};
newOtherDeferralAttribute:any ={};
documentsId:any;rmNames:any;
deferralDocList:[]=[];
anchorList:string[]=[];
docReports:Array<any> = [];
currentStageLead:[]=[];
anchorBeneficiary:[]=[];
creditNormsList:[]=[];
counterPartyList:string[]=[];
anchorList1:string[]=[];
counterPartyLists:[]=[];
proposalList:[]=[];
debtProfileList:[]=[];
limitEligibilityList:[]=[];
termSheetList:[]=[];
collateralList:[]=[];
dueDiligenceList:[]=[];
fundReqDetails:[]=[];
// anchorBeneficiary:[]=[];
counterPartyList1:string[]=[];
DeferralDocArray: Array<any> = [];
documentMaster:Array<any> = [];
anchorSearchList : Array<any> = [];
fileUploadAnchorBasicList:[]=[];
fileUploadAnchorAddressList:[]=[];
fileUploadAnchorKeyList:[]=[];
fileUploadAnchorProgramList:[]=[];
fileUploadAnchorGstList:[]=[];
fileUploadAnchorAuthoriseList:[]=[];
newDeferralAttribute:any ={};
appId:any;
validateArray: Array<any>=[];
validateArrayother: Array<any>=[];

 constructor(public authService: AuthService,private requestapi:ApiRequestService, private toaster: ToastrService,public router: Router,public datePipe:DatePipe)
 {
       const currentDate = new Date();
       var min = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate() + 2);
       var max = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate() + 32);
       var maximum = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate() + 1);
       var docCom = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate() + 1);
       this.minDate = min.toISOString().split('T')[0];
       this.maxDate = max.toISOString().split('T')[0];
       this.maximumDate = maximum.toISOString().split('T')[0];
       this.docComDate = docCom.toISOString().split('T')[0];
//        console.log(this.docComDate,"docComDate");
 }
  StatusValue = 1;
nextDeferralOpsMakerProgramNorms(){
this.opsCheckerDeferralNp = true;
}

previousDeferralOpsCheckerDocument(){
this.opsCheckerDeferralNp = false;
this.opsMakerDeferralNp = true;

}

previousDeferralOpsMakers(){
this.deferralOpsMakerRemarks = false;
this.deferralOpsMaker = true;
}
nexDeferralRemarks(){

this.deferralOpsMakerRemarks = true;
this.deferralOpsMaker = false;
}

nextDeferralRemarksChecker(){
this.deferralOpsCheckerRemarks = true;
this.defDocumentsChecker = false;
}

// addDeferralDocArray(){
// this.DeferralDocArray.push(this.newDeferralAttribute)
// this.newDeferralAttribute = {};
// }

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


fourthComplete:any;
fourthWip:any;
fifthComplete:any;
fifthWip:any;
fourthId:any;
fifthId:any;
  ngOnInit(): void {
  this.getRMNames();
  this.getDocumentType();


  this.emailId=localStorage.getItem('email')
  if(this.emailId==null){
          localStorage.clear();
          this.router.navigate(['/auth/login']);
      }
  this.getCountDetails();
this.roleBasedFun();
  }

opsChecker=false;
opsMaker=false;
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
        if(this.rbacArray[j].role=='OPERATION MAKER'){
            this.opsMaker=true;
//             console.log(this.opsMaker,"this.opsMaker");
        }
        if(this.rbacArray[j].role=='OPERATION CHECKER'){
            this.opsChecker=true;
            console.log(this.opsChecker,"this.opsChecker");
        }
            if((this.rbacArray[j].stageId.stageId=='DA1' || this.rbacArray[j].stageId.stageId=='DCP1') && this.fourthWip=='allow'){
                if(this.rbacArray[j].role=='OPERATION MAKER'){
                    this.a4Submit=true;
                    this.a4Return=true;
                }
            }
            if((this.rbacArray[j].stageId.stageId=='DA1' || this.rbacArray[j].stageId.stageId=='DCP1') && this.fourthComplete=='allow'){
                if(this.rbacArray[j].role=='OPERATION MAKER'){
                    this.a4View= true;
                    this.a4Submit=false;
                }
            }
            if((this.rbacArray[j].stageId.stageId=='DA2' || this.rbacArray[j].stageId.stageId=='DCP2') && this.fifthWip=='allow'){
                if(this.rbacArray[j].role=='OPERATION CHECKER'){
                    this.a5Submit=true;
                    this.a5Return=true;
                    this.a5Reject=true;
                    this.a4View= true;
                }
                if(this.rbacArray[j].role=='OPERATION MAKER'){
                    this.a5Return=true;
                    this.a4View= true;
                }
            }
             if((this.rbacArray[j].stageId.stageId=='DA2' || this.rbacArray[j].stageId.stageId=='DCP2') && this.fifthComplete=='allow'){
                if(this.rbacArray[j].role=='OPERATION CHECKER'){
                    this.a5View= true;
                    this.a5Submit=false;
                    this.a4View= true;
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

storeDocCompleteDate(event,docListId,deferralType):void{
    let flag = 1;
     this.docComDate = event.target.value;
     this.docListId = docListId;
    if(this.DeferralDocArray.length != 0){
        for(let itemId of this.DeferralDocArray){
           console.log("itemId.docListId *****************",itemId.docListId)
            if(itemId.docListId == docListId){
                this.index = this.DeferralDocArray.indexOf(itemId);
                itemId.docCompletionDate = this.docComDate;
//                 this.status = itemId.status;
                itemId.status = 2;
                this.DeferralDocArray[this.index] = itemId;
                itemId.deferralType = 2;
                this.docCompletionDate = this.docComDate;
                this.deferralType = itemId.deferralType;
                break;
            }else{
                flag=0;
            }
        }
    }else{
        const b = { appId: this.appId ,docListId : docListId,docCompletionDate : this.docComDate,deferralType : deferralType,}
        this.DeferralDocArray.push(b);
    }
//     if(flag==0){
//         const b = { appId: this.appId ,docListId : docListId,docCompletionDate : this.docComDate,deferralType : deferralType,}
//         this.DeferralDocArray.push(b);
//     }
    console.log("this.DeferralDocArray",this.DeferralDocArray);
}
storeDocumentId(event,docListId,deferralType):void{
    let flag = 1;
    this.docId = event.target.value;
    this.docListId = docListId;

        for(let itemId of this.DeferralDocArray){
            if(itemId.docListId == docListId){
                 this.index = this.DeferralDocArray.indexOf(itemId);
                itemId.documentId = this.docId;
//                 this.status = itemId.status;
                itemId.status = 2;
                itemId.deferralType = 2;
                this.DeferralDocArray[this.index] = itemId;
                this.deferralType = itemId.deferralType;
                flag=1;
                break;
            }else{
            flag=0;
            }
        }

        if(flag==0)
        {
        const b = { appId: this.appId ,docListId : docListId,documentId : this.docId,deferralType : deferralType,}
        this.DeferralDocArray.push(b);
        }

    console.log("this.DeferralDocArrayssssssss",this.DeferralDocArray);
}

storeDeferral(event,docListId,deferral,deferralType,DeferralId):void{
    deferralType = 2;
    console.log("deferralType***************",deferralType)
    let status = 0;
    let flag = 1;
//     this.DocListEntity = docListId;
        console.log("event.target.value",event.target.value);
        console.log("DeferralId***********",DeferralId);
        this.completedValue = event.target.value;
        if(event.target.value == 'No'){ //completed
            (<HTMLInputElement>document.getElementById(docListId+"B")).style.display = "block";
            (<HTMLInputElement>document.getElementById(docListId+"D")).style.display = "block";
//             (<HTMLInputElement>document.getElementById(docListId)).style.display = "block";
        }else if(event.target.value == 'Yes'){ //In-progress
             (<HTMLInputElement>document.getElementById(docListId+"B")).style.display = "none";
             (<HTMLInputElement>document.getElementById(docListId+"D")).style.display = "none";
//              (<HTMLInputElement>document.getElementById(docListId)).style.display = "none";
        }
    if(event.target.value == 'Yes'){
        status = 0;
        this.DateOfCompletion = true;
        for(let item of this.DeferralDocArray){
        if(docListId == item.docListId){
                item.documentId = null;
                item.dateOfCompletion = null;
                item.docCompletionDate = null;
                (<HTMLInputElement>document.getElementById(item.docListId+"B")).value = null;
                (<HTMLInputElement>document.getElementById(item.docListId+"D")).value = null;
                console.log(item.documentId,item.docCompletionDate,item,"inprogress case");
        }
       }
    }else if(event.target.value == 'No'){
        status = 2;

        this.DateOfCompletion = false;
//         this.revisedDate = true;
    }
       for(let itemId of this.DeferralDocArray){
           if(itemId.id == DeferralId){
               this.index = this.DeferralDocArray.indexOf(itemId);
               itemId.status = status;
               itemId.deferralType = deferralType;
               this.DeferralDocArray[this.index] = itemId;
               flag=1;
               break;
           }
       }
        if(flag==0){
               const b = { appId: this.appId ,docListId : DeferralId,status : status,deferralType : deferralType,}
//                console.log("condition failed********",b)
               this.DeferralDocArray.push(b);
           }
    }


currentStepperFun(id){

  let response;
  let fileName="deferralWorkflow/getHistoryOfWFStatus/"+id;
  response = this.requestapi.getData(fileName);
  response.subscribe((res: any) => {

  this.viewData=res;
  for(let i=0;i<res.length;i++){
     if((res[i].stage.stageId=='DA1' || res[i].stage.stageId=='DCP1') && res[i].status==2){
        this.fourthComplete='allow';
    }
    else if((res[i].stage.stageId=='DA1' || res[i].stage.stageId=='DCP1') && res[i].status==0){
        this.fourthWip='allow';;
        this.wizard.goToStep(0);
    }
    else if((res[i].stage.stageId=='DA2' || res[i].stage.stageId=='DCP2') && res[i].status==2){
        this.fifthComplete='allow';
        this.wizard.goToStep(1);
    }
    else if((res[i].stage.stageId=='DA2' || res[i].stage.stageId=='DCP2') && res[i].status==0){
        this.fifthWip='allow';
        this.wizard.goToStep(0);
        this.wizard.goToStep(1);
    }
    else if((res[i].stage.stageId=='DA2' || res[i].stage.stageId=='DCP2') && res[i].status==-1){
        this.fourthComplete='';
        this.fourthWip='allow';
    }
    else if((res[i].stage.stageId=='DA2' || res[i].stage.stageId=='DCP2') && res[i].status==null){
        this.fifthComplete='';
        this.fifthWip='';
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

getPending():void {
this.showLoader=true;
  let response;
  let fileName="deferralWorkflow/getFinalDeferralWFStatus/"+localStorage.getItem('email');
  response = this.requestapi.getData(fileName);
  response.subscribe((res: any) => {
  this.showLoader=false;
  if(res.status==true){
  for(let i=0;i<res.wfTableDataList.length;i++){
    if((res.wfTableDataList[i].currentStage=='DA1' || res.wfTableDataList[i].currentStage=='DCP1') && res.wfTableDataList[i].appId==this.appId){
            this.fourthId=res.wfTableDataList[i].nextStageId;

        }
    if((res.wfTableDataList[i].currentStage=='DA2' || res.wfTableDataList[i].currentStage=='DCP2') && res.wfTableDataList[i].appId==this.appId){
            this.fifthId=res.wfTableDataList[i].nextStageId;

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
  			if(error.error.message == null || error.error.message == ''){
  			this.toaster.error('Some Technical Error')
  			}else{
  			this.toaster.error(error.error.message);
  			}
  		}
  })
}


//   nextDeferralDoc(){
//   this.deferralDocuments = false;
//   this.deferralRemarks = true;
//   }

  previousDeferralDoc(){
    this.deferralDocuments = true;
    this.deferralRemarks = false;
    }

  previousPendingDefDoc(){
       this.deferralDocuments = false;
       this.deferralPendings = true;
  }

  nextStepper() {
    this.deferralPendings = true;
    this.deferralOpsMakerRemarks = false;
  }

  viewOperationChecker(){
        this.deferralPendings = false;
        this.deferralDocuments = true;
  }

  getRMNames():void{
      let response;
      let fileName="group/getKeycloakUserByGroupName/RELATIONSHIP_MANAGER";
      response = this.requestapi.getData(fileName);
      response.subscribe((res: any) => {
          this.rmNameArray=res;
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


  getDeferralReportsById(){

   for (let item of this.DeferralDocArray){
   console.log("this.deferralDocArr finals value ",item)
   if(item.docListId==20160 && item.docListId == 20161){
if(item.documentId !== null && item.status==2 && item.docCompletionDate !== null){
       console.log("this.deferralDocArr final 1234",item)
       this.showLoader=true;
       this.delayedExecutions();
     }
    }
   }
  }

getDefReports(id,appType):void{
    console.log("AppId:::",id);
    this.appType=appType;
    this.deferralOpsMaker = false;
    this.deferralPending = false;
    this.anchorCountCheck = false;
    this.deferralBasicDetails = true;
    this.getAnchorBasicDetailsById(id);
    this.getAnchorAddressDetailsById(id);
    this.getAnchorKeyDetailsById(id);
    this.getAnchorProgramDetailsById(id);
    this.getAnchorGstById(id);
    this.getAnchorAuthoriseById(id);
    this.getAnchorBeneficiaryById(id);
    this.getFundRequirement(id);
    this.getAnchorCreditNormsId(id);
    this.getCounterPartyById(id);
    this.getProposalById(id);
    this.getDebtProfileById(id);
    this.getLimitEligibilityById(id);
    this.getTermSheetById(id);
    this.getCollateralById(id);
    this.getDueDiligenceById(id);

    this.getPending();
    this.roleBasedFun();
    this.currentStepperFun(id);
    if(this.StageId == 30 || this.StageId == 33){
        this.getDeferralReports(id,appType);
        this.getDocDownloadType();
        this.getOtherDocMaster();
        this.getOtherDocDownload();
        this.deferralOpsCheckerStage = true;
    }else if(this.StageId == 29 || this.StageId == 32){
        this.deferralOpsMakerStage = true;
        this.appId=id;
        let response;
        let fileName="dms/deferralReport/"+this.appId+"/1";
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
            this.deferralDocList=res;
            console.log("RESPONSE::",res);
            if(res.length>0){
                this.deferralDocs = true;
                this.DeferralDocArray = [];
                for(var i=0;i<res.length;i++){
                    if(res[i].rmName != null) {
                        this.rmNames = res[i].rmName;
                    }
                    this.addDeferralDocArray();
                    this.DeferralDocArray[i].id = res[i].id;
                    this.DeferralDocArray[i].appId = res[i].applicationEntity.id;
                    this.DeferralDocArray[i].docListId = res[i].documentListEntity.id
                    this.DeferralDocArray[i].documentName = res[i].documentListEntity.displayName;
                    this.DeferralDocArray[i].documentId = res[i].documentId;
                    this.DeferralDocArray[i].deferral = res[i].documentListEntity.deferral;
                    if(res[i].initialTime != null){
                        var dateString = res[i].initialTime[0]+"-"+res[i].initialTime[1]+"-"+res[i].initialTime[2];
                        var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
                        res[i].initialTime = date;
                        console.log(res[i].initialTime.split('-').reverse().join('-'),'initialTime111')
                    }
                    if(res[i].newRevisedTime != null){
                        var dateString = res[i].newRevisedTime[0]+"-"+res[i].newRevisedTime[1]+"-"+res[i].newRevisedTime[2];
                        var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
                        res[i].newRevisedTime = date;
                    }
                    if(res[i].revisedTime != null){
                        var dateString = res[i].revisedTime[0]+"-"+res[i].revisedTime[1]+"-"+res[i].revisedTime[2];
                        var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
                        res[i].revisedTime = date;
                    }
                    if(res[i].docCompletionDate != null){
                        var dateString = res[i].docCompletionDate[0]+"-"+res[i].docCompletionDate[1]+"-"+res[i].docCompletionDate[2];
                        var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
                        this.docCompletionDate = date;
                        this.DeferralDocArray[i].docCompletionDate = this.docCompletionDate;
                        res[i].docCompletionDate = date;
                    }
                    if(res[i].docCompletionDate != null && res[i].documentId != null) {
                        this.DeferralStatus = true;
                    }
                    this.DeferralDocArray[i].initialTime = res[i].initialTime;
                    this.DeferralDocArray[i].revisedTime = res[i].revisedTime;
                    this.DeferralDocArray[i].newRevisedTime = res[i].newRevisedTime;
                    this.DeferralDocArray[i].docCompletionDate = res[i].docCompletionDate;
                    if(res[i].status !=2 && res[i].documentListEntity.deferral ==2){
                    }
                    this.DeferralDocArray[i].status = res[i].status;
                    this.DeferralDocArray[i].deferralType = res[i].documentListEntity.type;
                }
            }
            this.DeferralDocArray[this.index] = 0;
            this.OtherDeferralDocArray[this.indexothers] = 0;
            this.getDocDownloadType();
            this.getOtherDocDownload();
            this.getOtherDocMaster();
            this.getDeferralOtherDocuments();
            console.log(this.DeferralDocArray,"this.DeferralDocArray");
        },error=>{
            console.log("error from start");
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
}


//       setStatus():void{
//               console.log("setStatus1");
//               for (let item of this.DeferralDocArray){
//
//               console.log(item,'item11');
//                   if(item.deferral == 2){
//                   console.log("item.docCompletionDate ",item.docCompletionDate)
//                   console.log("item.documentId ",item.documentId)
//                   console.log("item.docCompletionDate !== null && item.documentId !== null :: ",item.docCompletionDate !== null && item.documentId !== null)
//                     if(item.docCompletionDate !== null && item.documentId !== null) {
//                       (<HTMLInputElement>document.getElementById(item.docListId+"C")).value = "No";
//                       (<HTMLInputElement>document.getElementById(item.docListId+"B")).value = item.documentId;
//                       (<HTMLInputElement>document.getElementById(item.docListId+"D")).value = item.docCompletionDate;
//                       console.log("item.docCompletionDate",item.docCompletionDate);
//                       (<HTMLInputElement>document.getElementById(item.docListId+"B")).style.display = "block";
//                       (<HTMLInputElement>document.getElementById(item.docListId+"D")).style.display = "block";
//
//                     }else{
//                       (<HTMLInputElement>document.getElementById(item.docListId+"C")).value = "Yes";
//                       (<HTMLInputElement>document.getElementById(item.docListId+"B")).style.display = "none";
//                       (<HTMLInputElement>document.getElementById(item.docListId+"D")).style.display = "none";
//
//                   }
//                 }
//               }
//             }

            setStatusUi():void{
                          console.log("setStatus12");
                          for (let item of this.DeferralDocArray){
                          console.log(item,'item11');
                              if(item.deferral == 2 ){
                              console.log("item.docCompletionDate ",item.docCompletionDate)
                              console.log("item.documentId ",item.documentId)
                              console.log("item.docCompletionDate !== null && item.documentId !== null :@@: ",item.docCompletionDate !== null && item.documentId !== null)
                                if(item.docCompletionDate !== null && item.documentId !== null) {
                                  (<HTMLInputElement>document.getElementById(item.docListId+"C")).value = "No";
                                  (<HTMLInputElement>document.getElementById(item.docListId+"B")).value = item.documentId;
                                  (<HTMLInputElement>document.getElementById(item.docListId+"D")).value = item.docCompletionDate;
                                  console.log("item.docCompletionDate",item.docCompletionDate);
                                  (<HTMLInputElement>document.getElementById(item.docListId+"B")).style.display = "block";
                                  (<HTMLInputElement>document.getElementById(item.docListId+"D")).style.display = "block";
                                  this.showLoader=false;

                                }
                                else{
                                if(item.status !== 3){
                                  (<HTMLInputElement>document.getElementById(item.docListId+"C")).value = "Yes";
                                  (<HTMLInputElement>document.getElementById(item.docListId+"B")).style.display = "none";
                                  (<HTMLInputElement>document.getElementById(item.docListId+"D")).style.display = "none";
                                  }else{
                                  this.showLoader=false;
                                  }


                              }
                            }
                          }
                        }

  compareDate(initialDate, revisedDate):boolean{
  var dateEnable;
  let date = new Date();
  console.log("datedate",date);
//    var g1 = new Date(date.getFullYear(), date.getMonth(), date.getDate());
  var g1 = date;
  console.log("g1g1g1",g1);
  // (YYYY, MM, DD)
  var g2;
  if(revisedDate){
  console.log("revisedDate",revisedDate);
      g2 = new Date(revisedDate);
      //g2 = new Date(revisedDate.getFullYear(), revisedDate.getMonth(), revisedDate.getDate());
  }else{
     g2 = new Date(initialDate);
     //g2 = new Date(initialDate.getFullYear(), initialDate.getMonth(), initialDate.getDate());
      console.log("initialDate",initialDate);
  }
  console.log(g2,'g222')
  if (g1.getTime() < g2.getTime()){
      dateEnable = false;
      }
  else if (g1.getTime() > g2.getTime()){
      dateEnable = true;
      }
  else{
      dateEnable = false;
      }
      console.log(dateEnable,"dateEnable");
      return dateEnable;
  }

// delayedExecution() {
// this.showLoader=true;
//    setTimeout(() => {
// //     this.setDatePicker();
//     this.setStatusUi();
//     this.showLoader=false;
//    }, 500); // Delayed execution after 1000 milliseconds (1 seconds)
// }

delayedExecutions() {
this.showLoader=true;
   setTimeout(() => {
//     this.setDatePicker();
let flag = false;
 for (let item of this.DeferralDocArray){
 console.log(item,'delayedExecutionitemvalue')
 console.log(item.status,'delayedExecutionstatus')
 if(item.status !== 3 && item.deferral == 2){
 flag = true;
 break;
 }
 }
 if(flag){
 console.log("!!!")
     this.setStatusUi();
     this.showLoader=false;
 }else{
 console.log("###")
 this.showLoader=false;
 }

   }, 500); // Delayed execution after 1000 milliseconds (1 seconds)
}

    getAnchorBasicDetailsById(uploadId):void {
            let response;
            console.log("response*********************")
            let fileName="anchor/anchorBasicFile/"+uploadId;
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
    getAnchorAddressDetailsById(uploadId): void {
            let response;
            let fileName="anchor/anchorAddressFile/"+uploadId;
            response = this.requestapi.getData(fileName);
            response.subscribe((res: any) => {
                this.fileUploadAnchorAddressList=res;
            },error=>{
                if(error.status==401){
                    this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                }
                console.log("error");
            })
        }
    getAnchorKeyDetailsById(uploadId): void {
        let response
        let fileName="anchor/anchorKeyFile/"+uploadId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
            this.fileUploadAnchorKeyList=res;
        },error=>{
            if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
            }
            console.log("error");
        })
    }
    getAnchorProgramDetailsById(uploadId):void {
            let response;
            let fileName="anchor/anchorProgramsFile/"+uploadId;
            response = this.requestapi.getData(fileName);
            response.subscribe((res: any) => {
                this.fileUploadAnchorProgramList=res;
            },error=>{
                if(error.status==401){
                    this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                }
                console.log("error");
            })
    }

    getAnchorGstById(uploadId):void {
            let response;
            let fileName="anchor/anchorGstFile/"+uploadId;
            response = this.requestapi.getData(fileName);
            response.subscribe((res: any) => {
                this.fileUploadAnchorGstList=res;
            },error=>{
                if(error.status==401){
                    this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                }
                console.log("error");
            })
        }
    getAnchorAuthoriseById(uploadId):void {
            let response;
            let fileName="anchor/anchorAuthorizedFile/"+uploadId;
            response = this.requestapi.getData(fileName);
            response.subscribe((res: any) => {
            this.fileUploadAnchorAuthoriseList=res;
            },error=>{
                if(error.status==401){
                    this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                }
                console.log("error");
            })
        }
    getAnchorBeneficiaryById(uploadId):void{
            let response;
            let fileName="anchor/anchorBeneficiaryFile/"+uploadId;
            response = this.requestapi.getData(fileName);
            response.subscribe((res: any) => {
                this.anchorBeneficiary=res;
            },error=>{
                if(error.status==401){
                    this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                }
                console.log("error");
            })
        }

        getFundRequirement(uploadId): void{
            let response;
            let fileName="counterParty/fundReqDetails/"+uploadId;
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

         getAnchorCreditNormsId(uploadId):void{
                let response;
                let fileName="anchor/creditNormsDetailsByFId/"+uploadId;
                response = this.requestapi.getData(fileName);
                response.subscribe((res: any) => {
                    this.creditNormsList=res;
                },error=>{
                    if(error.status==401){
                        this.refresh_token=localStorage.getItem('refresh_token')
                        this.authService.SignOut(this.refresh_token);
                    }
                    console.log("error");
                })
            }
     getCounterPartyById(uploadId):void {
            let response;
            let fileName="counterParty/cpBasicDetails/"+uploadId;
            response = this.requestapi.getData(fileName);
            response.subscribe((res: any) => {
                this.counterPartyLists=res;
            },error=>{
                if(error.status==401){
                    this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                }
                console.log("error");
            })
        }
     getProposalById(uploadId): void {
             let response;
             let fileName="counterParty/proposedProductsById/"+uploadId;
             response = this.requestapi.getData(fileName);
             response.subscribe((res: any) => {
                 this.proposalList=res;
             },error=>{
                 if(error.status==401){
                     this.refresh_token=localStorage.getItem('refresh_token')
                     this.authService.SignOut(this.refresh_token);
                 }
                 console.log("error");
             })
         }
     getDebtProfileById(uploadId): void {
             let response
             let fileName="counterParty/cpDebtProfile/"+uploadId;
             response = this.requestapi.getData(fileName);
             response.subscribe((res: any) => {
                 this.debtProfileList=res;
             },error=>{
                 if(error.status==401){
                     this.refresh_token=localStorage.getItem('refresh_token')
                     this.authService.SignOut(this.refresh_token);
                 }
                 console.log("error");
             })
         }
     getLimitEligibilityById(uploadId):void {
             let response;
             let fileName="counterParty/limitEligibilityById/"+uploadId;
             response = this.requestapi.getData(fileName);
             response.subscribe((res: any) => {
                 this.limitEligibilityList=res;
             },error=>{
                 if(error.status==401){
                     this.refresh_token=localStorage.getItem('refresh_token')
                     this.authService.SignOut(this.refresh_token);
                 }
                 console.log("error");
             })
         }
     getTermSheetById(uploadId):void {
             let response;
             let fileName="counterParty/termSheetGet/"+uploadId;
             response = this.requestapi.getData(fileName);
             response.subscribe((res: any) => {
                 this.termSheetList=res;
             },error=>{
                 if(error.status==401){
                     this.refresh_token=localStorage.getItem('refresh_token')
                     this.authService.SignOut(this.refresh_token);
                 }
                 console.log("error");
             })
         }
      getCollateralById(uploadId):void {
             let response;
             let fileName="counterParty/collateral/"+uploadId;
             response = this.requestapi.getData(fileName);
             response.subscribe((res: any) => {
                 this.collateralList=res;
             },error=>{
                 if(error.status==401){
                     this.refresh_token=localStorage.getItem('refresh_token')
                     this.authService.SignOut(this.refresh_token);
                 }
                 console.log("error");
             })
         }
     getDueDiligenceById(uploadId): void {
             let response
             let fileName="counterParty/dueDiligenceDetails/"+uploadId;
             response = this.requestapi.getData(fileName);
             response.subscribe((res: any) => {
                 for(var i=0; i<res.length; i++){
                     if(res[i].dueDiligenceMasterEntity.datatype == 'TimeStamp'){
                         res[i].value =this.datePipe.transform(res[i].value, 'yyyy-MM-dd');
                     }
                 }
                 this.dueDiligenceList=res;
             },error=>{
                 if(error.status==401){
                     this.refresh_token=localStorage.getItem('refresh_token')
                     this.authService.SignOut(this.refresh_token);
                 }
                 console.log("error");
             })
         }
      getCpBeneficiaryById(uploadId):void{
              let response;
              let fileName="anchor/anchorBeneficiaryFile/"+uploadId;
              response = this.requestapi.getData(fileName);
              response.subscribe((res: any) => {
                  this.anchorBeneficiary=res;
              },error=>{
                  if(error.status==401){
                      this.refresh_token=localStorage.getItem('refresh_token')
                      this.authService.SignOut(this.refresh_token);
                  }
                  console.log("error");
              })
          }


downloadDocument(docMainName,docSubMainName,fileName){
    let response;
    let customerId = this.appId;
    let url="dms/download/"+this.appId+"/"+docMainName+"/"+docSubMainName+"/"+fileName;
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

getDocumentType(): void{
this.showLoader=true;
    let response;
    let fileName="dms/documentType";
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
    this.showLoader=false;
        this.documentMaster = res;
        for(let docType of this.documentMaster)
        {
          if(docType.type == 1)
            {
              for(let docCategory of docType.documentCategoryEntities)
              {
              if(docCategory.sequenceNo == 3){
                    this.docTypeId = docCategory.documentTypeId;
                    this.docTypeName = docCategory.documentTypeName;
                    this.docCategoryId = docCategory.id;
                    this.docCategoryName = docCategory.name;
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

getDocDownloadType(): void{
    let response;
    let fileName="dms/customerDocReports?appId="+this.appId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.docReports = res.documentReports;
        this.counts = 0
        for(let item of this.DeferralDocArray){
            for (let doc of this.docReports){
                if(doc.docListId == item.docListId){
                    if(item.documentId == null && item.docCompletionDate == null){
                        const a = {id: item.id, docListId:item.docListId, date : 0, file : 0, count: this.counts++ }
                        this.validateArray.push(a);
                        console.log("this.validateArray1",this.validateArray);
                    }
                }
            }
        }
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        this.docReports = null;
    })
}

deleteUploadedFile(docTypeId,docTypeName,docCategoryId,docCategoryName,docListId,docListName,key,fileName,deferralId):void{
//
console.log(this.counts,'this.counts@@')
    for(let item of this.validateArray){

        if(item.docListId == docListId){
//             let flag = true;
//             console.log("this.docReports",this.docReports);
//             for(let report of this.docReports){
//             console.log(report,'report')
//                 if(report.docListId == docListId){
//                     flag = false;
                    let index = this.validateArray.indexOf(item);
                    item.count--;
                    item.file = 0;
                    this.validateArray[index] = item;
//                 }
//             }
//             if(flag){
//                 let index = this.validateArray.indexOf(item);
//                 item.file = 0;
//                 this.validateArray[index] = item;
//             }
        }
    }
    console.log("this.validateArray4",this.validateArray);
    let response;
    let url="dms/deleteFile";
    let data ={ appId:this.appId, docTypeId:docTypeId, docTypeName:docTypeName, docCategoryId:docCategoryId,
                docCategoryName:docCategoryName, docListId:docListId, docListName:docListName, fileName:fileName, key:key }
    response = this.requestapi.postData(url,JSON.stringify(data));
    response.subscribe((res: any) => {

        this.toaster.success('Successfully Deleted');
        if(docListId == 20161 || docListId ==20160){
//           this.storeDecision(deferralId,event,1);
//           this.storeDecision(deferralId,event,2);
          this.getDeferralReportsById();
          }else{
          this.storeDecision(deferralId,event,0);
          }
//         if(docListName == 'Charge Filling' || docListName =='CERSAI'){
//         this.storeDecision(deferralId,event,1);
//         }else{
//         this.storeDecision(deferralId,event,0);
//         }
        this.getDocDownloadType();
//         this.getOtherDocDownload();
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




uploadDocument(event,docTypeId,docTypeName,docCategoryId,docCategoryName,docListId,docListName,key,deferralId){

for(let item of this.validateArray){
                if(item.docListId == docListId){
                    let index = this.validateArray.indexOf(item);
                    item.count++;
                    item.file = 1;
                    this.validateArray[index] = item;
                }
            }
            console.log("this.validateArray3",this.validateArray);
            console.log(this.OtherDeferralDocArray[this.indexothers],'this.OtherDeferralDocArray[this.index]2')
        const file = event.target.files[0];
        console.log(file,'filename');
        console.log("docListId file upload ::",docListId)

        if(file.size <= 105906176){
            let response;
            let formData = new FormData();
            this.document=file;
            let url="dms/uploadFile";
            const data={ appId:this.appId, docTypeId:docTypeId, docTypeName:docTypeName,docCategoryId:docCategoryId,
                         docCategoryName:docCategoryName,docListId:docListId,docListName:docListName, key:key
                        }
            const documentReports = JSON.stringify(data);
            formData.append('file',this.document);
            formData.append('documentReportsData',documentReports);
            response = this.requestapi.postFileData(url,formData);
//             this.setDatePicker();
            response.subscribe((res: any) => {
              if(docListId == 20161 || docListId ==20160){
//               this.storeDecision(deferralId,event,2);

              this.getDeferralReportsById();
              }else{
              this.storeDecision(deferralId,event,1);
              console.log(this.OtherDeferralDocArray[this.indexothers],'this.OtherDeferralDocArray[this.index]123')
              }
//                 this.storeDecision(deferralId,event,1);
                this.getDocDownloadType();
//                 this.getOtherDocDownload();
(<HTMLInputElement>document.getElementById(docListId+"V")).value = null;
console.log(this.OtherDeferralDocArray[this.indexothers],'this.OtherDeferralDocArray[this.index]1234')
            },error=>{

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
            })
        }else{
            this.toaster.error('Supported File Size is 100MB');
        }
    }

public loading = false;
// public adds;
public add = 0;

//Other Deferral Document
OtherDocReports: Array<any> = [];

getOtherDocDownload(): void{
    let response;
    console.log("this.appId",this.appId);
    let fileName="dms/customerOtherDocReports?appId="+this.appId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.OtherDocReports = res.otherDocReports;
        console.log("OtherDocReports-->",res);
        this.countsother = 0;
        for(let item of this.OtherDeferralDocArray){
            for (let doc of this.OtherDocReports){
                if(doc.otherDocId == item.id){
                     const a = { otherMasterId : item.id, docListId:item.docListId, date : 0, file : 0, count: this.countsother++ }
                     this.validateArrayother.push(a);
                }
            }
        }
        console.log(this.countsother,'this.countsother')
    },error=>{

    console.log("error from getOtherDocDownload");
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        this.OtherDocReports = null;
    })
}

uploadOtherDocument(event,docTypeId,docTypeName,docCategoryId,docCategoryName,docListId,docListName,otherDocMasterId,otherDisplayName,key){
    console.log("otherDocMasterId--->",otherDocMasterId);
    console.log("otherDisplayName--->",otherDisplayName);


   console.log(this.add,'this.add')
    this.loading = true;
    for(let item of this.validateArrayother){
        if(item.otherMasterId == otherDocMasterId){
            item.count++;
            item.file = 1;

        }
    }
console.log(this.DeferralDocArray[this.index],"Deferrals0");
    const file = event.target.files[0];
    if(file != undefined){
        if(file.size <= 105906176){
            let response;
            let formData = new FormData();
            this.document=file;
            console.log(this.DeferralDocArray[this.index],"Deferrals1");
            let url="dms/otherUploadFile";
            const data={ appId:this.appId, otherDocumentDataList : this.otherDocumentArray,docTypeId:docTypeId, docTypeName:docTypeName,docCategoryId:docCategoryId,
                         docCategoryName:docCategoryName,docListId:docListId,docListName:docListName, otherDocMasterId:otherDocMasterId, otherDisplayName:otherDisplayName, key:key, rmName : this.rmNames
                        }
            const otherDocumentReports = JSON.stringify(data);
            formData.append('file',this.document);
            formData.append('otherDocumentReportsData',otherDocumentReports);
            response = this.requestapi.postFileData(url,formData);
            response.subscribe((res: any) => {

                this.loading = false;
                this.toaster.success('Successfully Uploaded');
                console.log(this.DeferralDocArray[this.index],"Deferrals12");
                for(let itemId of this.OtherDeferralDocArray){


                        if(itemId.id == otherDocMasterId){

//                             this.index = this.OtherDeferralDocArray.indexOf(itemId);
                            console.log(itemId.exRevisedTime,'itemId.exRevisedTime1');
                            itemId.exRevisedTime = null;
                            (<HTMLInputElement>document.getElementById(itemId.id+"S")).value = null;
                            console.log((<HTMLInputElement>document.getElementById(itemId.id+"S")).value,'datevalue')

//                             this.OtherDeferralDocArray[this.index] = itemId;
                            console.log(itemId.exRevisedTime,'itemId.exRevisedTime2')
                            break;

                        }

                    }
                    console.log(this.DeferralDocArray[this.index],"Deferrals123");
                this.getOtherDocDownload();
                this.add++;
                console.log(this.add,'this.add')
                console.log(this.DeferralDocArray[this.index],"Deferrals1234");
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

//                 (<HTMLInputElement>document.getElementById(docListId)).value = null;
            })
        }else{
            this.loading = false;
            this.toaster.error('Supported File Size is 100MB');
//             (<HTMLInputElement>document.getElementById(docListId)).value = null;
        }
    }else{
        this.loading = false;
    }
}

deleteOtherUploadedFile(docTypeId,docTypeName,docCategoryId,docCategoryName,docListId,docListName,otherDocMasterId,otherDisplayName,key,fileName):void{
    for(let item of this.validateArrayother){
        if(item.otherMasterId == otherDocMasterId){
            item.count--;
            item.file = 0;
        }
    }
    console.log("countsother1-->",this.countsother)
    if(this.countsother == 1){
    console.log("countsother2-->",this.countsother)
    this.countsother = 0;
    }
    console.log("countsother2-->",this.countsother)
    console.log("this.validateArray4",this.validateArrayother);
    let response;
    let url="dms/deleteOtherFile";
    const data={ appId:this.appId, otherDocumentDataList : this.otherDocumentArray,docTypeId:docTypeId, docTypeName:docTypeName,docCategoryId:docCategoryId,
                 docCategoryName:docCategoryName,docListId:docListId,docListName:docListName, otherDocMasterId:otherDocMasterId, otherDisplayName:otherDisplayName,fileName:fileName, key:key, rmName : this.rmNames
                }
    response = this.requestapi.postData(url,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.toaster.success('Successfully Deleted');
        this.getOtherDocDownload();
        this.add--;
        console.log(this.add,'this.adddelete');
        console.log(this.DeferralDocArray[this.index],"Deferrals");
        (<HTMLInputElement>document.getElementById(otherDocMasterId+"Y")).value = null;
//         (<HTMLInputElement>document.getElementById(docListId)).value = null;
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

downloadOtherDocument(docMainName,docSubMainName,otherDocName,fileName){
    let response;
    let customerId = this.appId;
    let url="dms/otherDownload/"+this.appId+"/"+docMainName+"/"+docSubMainName+"/"+otherDocName+"/"+fileName;
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



ApprovalStatus(key): void {
for(let deferralDocArr of this.DeferralDocArray){
if (key == 0)
{
deferralDocArr.status = 0;
}
else if(key == 2)
{
deferralDocArr.status = 2;
}
}

}

public updateDeferralValidation() {
//
this.showLoader=true;
    console.log(this.DeferralDocArray[this.index],"DeferralDocArray index*****");
    console.log(this.completedValue,'completedValuess');
    let flag = false;

    for(let item of this.DeferralDocArray){
        console.log(this.counts,'this.counts in cersai and charge filling')
        if((item.docListId == 16159 || item.docListId == 16160) || (item.docListId == 16116 || item.docListId == 16117)){
        console.log(item.docListId,'123')
            if((this.extenddate == undefined || this.extenddate == '') && ( this.counts == 0)){
            console.log('hi!!!')
                flag= true;
                this.showLoader=false;
            }
        }
        else{
        console.log('hello!!!')
                flag= false;
                break;
        }
    }

console.log(flag,'flag1');
console.log(this.add == 0,'add1');
console.log(this.extendotherdate,'otherdate');
    if((flag) && (this.countsother == 0 && (this.extendotherdate == undefined || this.extendotherdate == '')) ){
        this.toaster.error("Kindly take any decision on documents");
        this.showLoader=false;
//               window.location.reload();
    }else if((flag && this.DeferralDocArray[this.index]==0 )&& (this.OtherDeferralDocArray[this.indexothers] == undefined && this.countsother == 0))
    {
    this.toaster.error("Kindly take any decision on documents");
    this.showLoader=false;
    }
    else{
        if(this.DeferralDocArray[this.index] == 0){
            this.validation();

        }
        else{
            this.updateValidation();
        }
    }

}
validation(){
    if(this.countsother == 0 || this.countsother ==undefined){
        this.toaster.error("Make any Decision On Document");
        this.showLoader=false;
    }else{
        console.log("else condition")
        this.deferralOpsMakerRemarks = true;
        this.deferralOpsMaker = false;
        this.showLoader=false;
    }
}

updateValidation(){
let response;
        let fileName="dms/deferralOpsMakerValidate";
        let data={ appId:this.appId,deferralReportsDataList:this.DeferralDocArray, rmName : this.rmNames}
        response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
                response.subscribe((res: any) => {
                this.showLoader=false;
                this.updateRes = res;
                if(this.updateRes.message == 'Validation failed'){
                 this.toaster.error(this.updateRes.FileName);
                }else{
                this.deferralOpsMakerRemarks = true;
                this.deferralOpsMaker = false;
                }

})

}

public updateDeferralOpsMaker() {
//
this.showLoader=true;
  console.log(this.DeferralDocArray[this.index],"DeferralDocArray index*****");
  if(this.DeferralDocArray[this.index] ==0 && this.OtherDeferralDocArray[this.indexothers] ==0){
//   this.NextEnable=false;
  this.toaster.error("Make any Decision On Document");
  this.showLoader=false;
  }else{
        for(let itemId of this.DeferralDocArray){
        console.log("itemId********",itemId);
                this.index = this.DeferralDocArray.indexOf(itemId);
                console.log(itemId.initialTime,'itemIdinitialTime')
                console.log(itemId.revisedTime,'itemIdrevisedTime')
                itemId.initialTime = this.datePipe.transform(itemId.initialTime, 'yyyy-MM-dd');
                itemId.revisedTime = this.datePipe.transform(itemId.revisedTime, 'yyyy-MM-dd');
                this.DeferralDocArray[this.index] = itemId;
            }
        let response;
        let fileName="dms/deferralUpdate";
        let data={ appId:this.appId,deferralReportsDataList:this.DeferralDocArray, rmName : this.rmNames}
        console.log(this.DeferralDocArray,'this.DeferralDocArray')
        console.log(data,'this.DeferralDocArray')
        response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
        console.log("Update ETA Details",res)
        this.updateRes = res;
        console.log("this.updateRes",this.updateRes)
        if(this.updateRes.message == 'Validation failed'){
        console.log("************validationFaild*************")
          this.toaster.error(this.updateRes.FileName);
        }else{
        this.deferralOpsMakerRemarks = true;
        this.deferralOpsMaker = false;
        }
        this.getDocDownloadType();
        this.getOtherDocDownload();
        },error=>{
        this.showLoader=false;
        if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
        }
        console.log("error");
        })
        }
}

public updateDeferralOthers() {
//
this.showLoader=true;

  if(this.DeferralDocArray[this.index] ==0 && this.OtherDeferralDocArray[this.indexothers] ==0){
  this.NextEnable=false;
  this.toaster.error("Make any Decision On Document");
  this.showLoader=false;
  }else{

        for(let itemId of this.OtherDeferralDocArray){
        console.log("itemId********",itemId);
                this.index = this.OtherDeferralDocArray.indexOf(itemId);
                console.log(itemId.initialTime,'itemIdinitialTime')
                console.log(itemId.revisedTime,'itemIdrevisedTime')
                itemId.initialTime = this.datePipe.transform(itemId.initialTime, 'yyyy-MM-dd');
                itemId.revisedTime = this.datePipe.transform(itemId.revisedTime, 'yyyy-MM-dd');
                this.OtherDeferralDocArray[this.indexothers] = itemId;
            }
            console.log(this.OtherDeferralDocArray,'this.OtherDeferralDocArray!!!%%')
                    let response;
                    let fileName="dms/otherDeferralUpdate";
                    let data={ appId:this.appId,otherDocumentDataList:this.OtherDeferralDocArray, rmName : this.rmNames}
                    console.log(this.OtherDeferralDocArray,'this.DeferralDocArray')
                    console.log(data,'this.DeferralDocArray')
                    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));

                    response.subscribe((res: any) => {
                    this.showLoader=false;
                    console.log("Update ETA Details",res)
                    this.updateRes = res;
                    console.log("this.updateRes",this.updateRes)
//                     if(this.updateRes.message == 'Validation failed'){
//                     console.log("************validationFaild*************")
//                       this.toaster.error(this.updateRes.FileName);
//                     }else{
                    this.deferralOpsMakerRemarks = true;
                    this.deferralOpsMaker = false;
//                     }
                    this.getDocDownloadType();
                    },error=>{
                    this.showLoader=false;
                    if(error.status==401){
                    this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                    }
                    console.log("error");
                    })

}
}

  getDeferralOtherDocuments(): void{

//       this.appId = id;

      let response;
      console.log("***************************************************************************")
                let fileName="dms/othersDeferralDocuments/"+this.appId+"/1";
                response = this.requestapi.getData(fileName);

                response.subscribe((res: any) => {
                    console.log(res,"OtherDocuments12345");
                    this.otherDocRes = res;
                    console.log("this.otherDocRes::::::::::::::::::::::::::::::",this.otherDocRes)
                    this.showLoader = false;

                    if(res.length>0){
                    this.otherDocs = true;
                    this.OtherDeferralDocArray = [];
                    this.OtherDoc = true
                    }
                    for(var i=0;i<res.length;i++){
//                     this.rmNames = res[i].rmName;
                    if(res[i].rmName != null){
                       this.rmNames = res[i].rmName;
                       }
                    this.addOtherDeferralDocArray();
                    this.OtherDeferralDocArray[i].id = res[i].id;
                    this.OtherDeferralDocArray[i].appId = res[i].applicationEntity.id;
                    this.OtherDeferralDocArray[i].docListId = res[i].documentListEntity.id;
                    this.OtherDeferralDocArray[i].displayName = res[i].displayName;
                    console.log(this.OtherDeferralDocArray[i].displayName,"this.OtherDeferralDocArray[i].displayName");
                    if(res[i].initialTime != null){
                    var dateString = res[i].initialTime[0]+"-"+res[i].initialTime[1]+"-"+res[i].initialTime[2];
                    var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
                    res[i].initialTime = date;
                    }
                     if(res[i].revisedTime != null){
                     var dateString = res[i].revisedTime[0]+"-"+res[i].revisedTime[1]+"-"+res[i].revisedTime[2];
                     var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
                     res[i].revisedTime = date;
                     }
                     if(res[i].newRevisedTime != null){
                          var dateString = res[i].newRevisedTime[0]+"-"+res[i].newRevisedTime[1]+"-"+res[i].newRevisedTime[2];
                          var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
                          res[i].newRevisedTime = date;
                     }
                    this.OtherDeferralDocArray[i].initialTime = res[i].initialTime;
                    this.OtherDeferralDocArray[i].revisedTime = res[i].revisedTime;
                    this.OtherDeferralDocArray[i].newRevisedTime = res[i].newRevisedTime;
                    this.OtherDeferralDocArray[i].status = res[i].status

  }
  console.log(this.OtherDeferralDocArray,'this.OtherDeferralDocArray###')
})
  }

  getDeferralReports(id, appType):void {

               this.deferralDocuments = true;
               this.deferralPendings = false;
               this.appType = appType;
                 this.appId=id;
                 let response;
                 let fileName="dms/deferralReport/"+this.appId;
                 response = this.requestapi.getData(fileName);
                 response.subscribe((res: any) => {

                   for(var i=0;i<res.length;i++){
                   if(res[i].rmName != null){
                       this.rmNames = res[i].rmName;
                       }
                       if(res[i].initialTime != null){
                           var dateString = res[i].initialTime[0]+"-"+res[i].initialTime[1]+"-"+res[i].initialTime[2];
                           var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
                           res[i].initialTime = date;
                       }
                       if(res[i].newRevisedTime != null){
                             var dateString = res[i].newRevisedTime[0]+"-"+res[i].newRevisedTime[1]+"-"+res[i].newRevisedTime[2];
                             var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
                             res[i].newRevisedTime = date;
                         }
                         if(res[i].revisedTime != null){
                              var dateString = res[i].revisedTime[0]+"-"+res[i].revisedTime[1]+"-"+res[i].revisedTime[2];
                              var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
                              res[i].revisedTime = date;
                         }
                         if(res[i].docCompletionDate != null){
                           var dateString = res[i].docCompletionDate[0]+"-"+res[i].docCompletionDate[1]+"-"+res[i].docCompletionDate[2];
                           var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
                           res[i].docCompletionDate = date;
                       }
                   }
                   if(res.length>0){
                   this.deferralDocs = true;
                   }
                     this.deferralDocList=res;
                     this.getOtherDocMaster();
                 })
           }

decisionMaker=false;
updateDeferralStatus(remarks,type):void {
this.showLoader=true;
    let response;
    let fileName="dms/updateDeferralStatus/"+this.appId;
    response = this.requestapi.putData(fileName);
    response.subscribe((res: any) => {
    if(this.otherDocs){
        let response1;
        let fileName1="dms/otherDocsStatusUpdate/"+this.appId;
        response1 = this.requestapi.putData(fileName1);
        response1.subscribe((res: any) => {
        this.getDecisionMaker(remarks,type);
        },error=>{
            this.showLoader = false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
        })
    }
    else{
    this.getDecisionMaker(remarks,type);
    }
//     window.location.reload();
//     this.router.navigate(['/email/Deferral']);
//     this.showLoader = false;
    },error=>{
    this.showLoader = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

getDecisionMaker(remarks,type): void{
let response3;
            let fileName3="dms/workflowDecisionMake/"+this.appId;
            response3 = this.requestapi.getData(fileName3);
            response3.subscribe((res: any) => {
            this.decisionMaker = res;
            console.log("this.decisionMaker API : ", this.decisionMaker);
            if(type=='anchor'){
                console.log(this.decisionMaker,"anchor");
                this.wfAnchorCheckerStage(remarks,this.decisionMaker)
                }
                else{
                console.log(this.decisionMaker,"cp");
                console.log("cp");
                this.wfCpCheckerStage(remarks,this.decisionMaker)
                }
            },error=>{
                this.showLoader = false;
                if(error.status==401){
                    this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                }
            })
}

getCountDetails(): void {
    let response;
    this.anchorCount = 0;
    this.counterParty = 0;
    let fileName="deferralWorkflow/getFinalDeferralWFStatus/"+this.emailId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        if(res.length != 0){
            this.currentStageLead=res.wfTableDataList;
            for(let currentStage of res.wfTableDataList){
            this.StageId = currentStage.nextStageId;
            }
            this.anchorCount = res.anchorCount;
            this.counterParty = res.cpCount;
            this.errorMsg=false;
        }
    },error=>{
    if(error.status==401){
          this.refresh_token=localStorage.getItem('refresh_token')
                  this.authService.SignOut(this.refresh_token);
        }
        this.errorMsg=true;
    })
}

storeExtendedRevisedDate(event,docListId,deferralType):void{
    console.log("EVENT EVENT:::",event.target.value);
    console.log(docListId,deferralType,'docListId,deferralType');
    this.extenddate = '';
    this.extenddate = event.target.value;
    console.log(this.extenddate,'extenddate')
    if(event.target.value != ''){
        for(let item of this.validateArray){
            if(item.docListId == docListId){
                let index = this.validateArray.indexOf(item);
                item.date = 1;
                this.validateArray[index] = item;
            }
        }
        console.log("this.validateArray2",this.validateArray);
    }else{
        for(let item of this.validateArray){
            if(item.docListId == docListId){
                let index = this.validateArray.indexOf(item);
                item.date = 0;
                this.validateArray[index] = item;
            }
        }
        console.log("this.validateArray2222",this.validateArray);
    }
    let flag = 1;

    let date = event.target.value;

    if(this.DeferralDocArray.length != 0){

        for(let itemId of this.DeferralDocArray){
        console.log("itemId********",itemId);

            if(itemId.docListId == docListId){

                this.index = this.DeferralDocArray.indexOf(itemId);

                itemId.exRevisedTime = date;
                this.DeferralDocArray[this.index] = itemId;
                this.docCompletionDate = itemId.docCompletionDate;
                itemId.status = 0;
               flag=1;
                break;

            }else{

                flag=0;

            }

        }

    }else{

        const b = { appId: this.appId,docListId : docListId,exRevisedTime : date,deferralType : deferralType,}

        this.DeferralDocArray.push(b);

    }

    if(flag==0){

        const b = { appId: this.appId ,docListId : docListId,exRevisedTime : date,deferralType : deferralType,}

        this.DeferralDocArray.push(b);

    }

    console.log("this.DeferralDocArray",this.DeferralDocArray);

}

storeExtendedOtherRevisedDate(event,docListId,deferralType,id):void{
    console.log("EVENTs EVENTs:::",event.target.value);
    console.log(docListId,deferralType,'docListId,deferralType');
    this.extendotherdate = '';
        this.extendotherdate = event.target.value;

    let flag = 1;

    let date = event.target.value;
console.log(this.OtherDeferralDocArray,'this.OtherDeferralDocArrayssss@12345')

    if(this.OtherDeferralDocArray.length != 0){

        for(let itemId of this.OtherDeferralDocArray){
        console.log("itemIdvaluess",itemId);

            if(itemId.id == id){

                this.index = this.OtherDeferralDocArray.indexOf(itemId);

                itemId.exRevisedTime = date;
                this.OtherDeferralDocArray[this.indexothers] = itemId;

                itemId.status = 0;
               flag=1;
                break;

            }else{

                flag=0;

            }

        }

    }else{

        const b = { appId: this.appId,docListId : docListId,exRevisedTime : date,deferralType : deferralType,}

        this.OtherDeferralDocArray.push(b);

    }

    if(flag==0){

        const b = { appId: this.appId ,docListId : docListId,exRevisedTime : date,deferralType : deferralType,}

        this.OtherDeferralDocArray.push(b);

    }

    console.log("this.OtherDeferralDocArrays@@@123",this.OtherDeferralDocArray);

}


storeDecision(id,item:any,key){


  this.DocListEntity=id;

  console.log("StoreDecision:++this.DocListEntity",this.DocListEntity)


   if(Event.prototype.isPrototypeOf(item)){

              this.status=item.target.value;

              if(key == 0){
              this.status = 0;
              }else if(key == 2){
              this.status = 2;
              }else if(key == 1){
              this.status = 1;
             }
          }else{
              this.status=item.toString();
          }
          console.log(this.OtherDeferralDocArray[this.indexothers],'this.OtherDeferralDocArray[this.index]123456@@@')
    for(let deferralDoc of this.DeferralDocArray)
    {
    if(deferralDoc.id == this.DocListEntity)
      {
       this.index = this.DeferralDocArray.indexOf(deferralDoc);
       console.log(this.OtherDeferralDocArray[this.indexothers],'this.OtherDeferralDocArray[this.index]!!!')
       const c = {
                   id:deferralDoc.id,
                   appId: this.appId,
                   docListId : deferralDoc.docListId,
                   documentName : deferralDoc.documentName,
                   initialTime: deferralDoc.initialTime,
                   revisedTime: deferralDoc.revisedTime,
                   status: this.status,
                   deferralType :deferralDoc.deferralType,
                   documentId :deferralDoc.documentId,
                   docCompletionDate :deferralDoc.docCompletionDate,
                   deferral :deferralDoc.deferral,

                   }
                this.DeferralDocArray[this.index] = c;
//                 this.documentId = c.documentId;
                break;
    }
    }
this.delayedExecutions();
}


getCounterPartyDetails(): void {

    let response;
    this.counterPartyCount = 0;
    let fileName="dms/existingDeferralDetails?type=2&status=1";
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {

    this.counterPartyList=res;
    this.counterPartyCount = res.length;
    this.errorMsg=false;
    },error=>{

    if(error.status==401){
          this.refresh_token=localStorage.getItem('refresh_token')
                  this.authService.SignOut(this.refresh_token);
        }
    this.errorMsg=true;
    })
}

getCustomerCount(type): void {

    let response;
    let response1;
    this.anchorCount1 = 0;
    let fileName="dms/existingDeferralDetails/"+type;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {

        if(type == 1){
            this.anchorList = res;
            this.anchorCount1 = res.length;
        }else if(type == 2){
            this.counterPartyList = res;
            this.counterPartyCount1 = res.length;
        }
        this.errorMsg=false;
    },error=>{

    if(error.status==401){
          this.refresh_token=localStorage.getItem('refresh_token')
                  this.authService.SignOut(this.refresh_token);
        }
        this.errorMsg=true;
    })

}

getRemarks():void{

let response;
let fileName="deferralWorkflow/getRemarks/"+this.appId;
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

wfCpMakerStage(remarkWf){
this.showLoader=true;
this.updateDeferralOpsMaker();
this.updateDeferralOthers();
this.remarkWf = remarkWf;
    let response;
    let fileName="deferralWorkflow/saveDeferralWorkflow";
    let data={ stageId:this.fourthId,status:2,approverInfo:this.emailId,appId:this.appId,remarks:this.remarkWf
    ,nextApproverInfo:"CP_OPERATION_CHECKER_LEAD"}
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {

    window.location.reload();
            this.router.navigate(['/email/Deferral']);
this.showLoader=false;
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

wfAnchorMakerStage(remarkWf){
this.showLoader=true;
this.updateDeferralOpsMaker();
this.updateDeferralOthers();
    this.remarkWf = remarkWf;
    let response;
    let fileName="deferralWorkflow/saveDeferralWorkflow";
    let data={ stageId:this.fourthId,status:2,approverInfo:this.emailId,appId:this.appId,remarks:this.remarkWf
    ,nextApproverInfo:"ANCHOR_OPERATION_CHECKER_LEAD"}
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        window.location.reload();
        this.router.navigate(['/email/Deferral']);
        this.showLoader=false;
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

popupMakerStage(type,index){
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
    if(type==1){
        this.wfAnchorMakerStage(result.value);
                    this.wizard.goToStep(index);
    }else{
        this.wfCpMakerStage(result.value);
                    this.wizard.goToStep(index);
    }
        }
    })
}




wfCpCheckerStage(remarkWf,decision){
this.showLoader=true;
// this.updateDeferralStatus();
this.remarkWf = remarkWf;
if(!decision){
let response;
    let fileName="deferralWorkflow/saveDeferralWorkflow";
    let data={ stageId:this.fifthId,status:2,approverInfo:this.emailId,appId:this.appId,remarks:this.remarkWf
    ,nextApproverInfo:"CP_OPERATION_MAKER_LEAD"}
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
    window.location.reload();
        this.router.navigate(['/email/Deferral']);
        this.showLoader=false;
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
else{
let response;
    let fileName="deferralWorkflow/saveDeferralWorkflow";
    let data={ stageId:this.fifthId,status:2,approverInfo:this.emailId,appId:this.appId,remarks:this.remarkWf
    ,nextApproverInfo:"CP_DEFERRAL_COMMITTEE_LEAD"}
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
    window.location.reload();
        this.router.navigate(['/email/Deferral']);
        this.showLoader=false;
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

wfAnchorCheckerStage(remarkWf,decision){
this.showLoader=true;
// this.updateDeferralStatus();
    this.remarkWf = remarkWf;
    console.log(decision,"decision");
    if(!decision){
        let response;
            let fileName="deferralWorkflow/saveDeferralWorkflow";
            let data={ stageId:this.fifthId,status:2,approverInfo:this.emailId,appId:this.appId,remarks:this.remarkWf
            ,nextApproverInfo:"ANCHOR_OPERATION_MAKER_LEAD"}
            response = this.requestapi.postData(fileName,JSON.stringify(data));
            response.subscribe((res: any) => {
            window.location.reload();
            this.router.navigate(['/email/Deferral']);
            this.showLoader=false;
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
    else{
        let response;
            let fileName="deferralWorkflow/saveDeferralWorkflow";
            let data={ stageId:this.fifthId,status:2,approverInfo:this.emailId,appId:this.appId,remarks:this.remarkWf
            ,nextApproverInfo:"ANCHOR_DEFERRAL_COMMITTEE_LEAD"}
            response = this.requestapi.postData(fileName,JSON.stringify(data));
            response.subscribe((res: any) => {
            window.location.reload();
            this.router.navigate(['/email/Deferral']);
            this.showLoader=false;
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

popupCheckerStage(type,index){
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
    if(type==1){
//         this.wfAnchorCheckerStage(result.value);
this.updateDeferralStatus(result.value,'anchor');
                    this.wizard.goToStep(index);
    }else{
//         this.wfCpCheckerStage(result.value);
this.updateDeferralStatus(result.value,'cp');
                    this.wizard.goToStep(index);
    }
        }
    })
}
returnWf(StageId,remark,nextAppInfo){
this.showLoader=true;
    let response;
    let fileName="deferralWorkflow/saveDeferralWorkflow";
    let data={ stageId : StageId, status :-1, approverInfo : this.emailId, appId : this.appId, remarks : remark, nextApproverInfo : nextAppInfo }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {

        window.location.reload();
                this.router.navigate(['/email/Deferral']);
                 this.showLoader=false;

    },error=>{
    this.showLoader=false;
    if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
    }
    })
}

returnPopupFun(type){
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
        if(type==1){
            this.returnWf(this.fifthId,result.value,'ANCHOR_OPERATION_MAKER_LEAD');
        }else{
            this.returnWf(this.fifthId,result.value,'CP_OPERATION_MAKER_LEAD');
        }

        }
    })
}

rejectWf(StageId,remark,nextAppInfo){
this.showLoader = true;
    let response;
    let fileName="deferralWorkflow/saveDeferralWorkflow";
    let data={ stageId : StageId, status :-2, approverInfo : this.emailId, appId : this.appId, remarks : remark, nextApproverInfo : nextAppInfo }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {

        window.location.reload();
                this.router.navigate(['/email/Deferral']);
                this.showLoader = false;

    },error=>{
    this.showLoader = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

getOtherDocMaster(): void{
    this.showLoader = true;
    let response;
    let fileName="dms/otherDocsForOpsChecker/"+this.appId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.showLoader = false;
        this.otherDocumentArray = [];
        if(res.length>0){
            this.otherDocs = true;
        }
        for(let item of res){
            if(item.initialTime != null){
                var dateString = item.initialTime[0]+"-"+item.initialTime[1]+"-"+item.initialTime[2];
                var date = this.datePipe.transform(dateString, 'dd-MM-yyyy');
                item.initialTime = dateString;
            }
            if(item.revisedTime != null){
                var dateString1 = item.revisedTime[0]+"-"+item.revisedTime[1]+"-"+item.revisedTime[2];
                var date1 = this.datePipe.transform(dateString1, 'dd-MM-yyyy');
                item.revisedTime = dateString1;
            }
            if(item.newRevisedTime != null){
                var dateString1 = item.newRevisedTime[0]+"-"+item.newRevisedTime[1]+"-"+item.newRevisedTime[2];
                var date1 = this.datePipe.transform(dateString1, 'dd-MM-yyyy');
                item.newRevisedTime = dateString1;
            }
            const a = { id : item.id, appId: item.applicationEntity.id ,docListId : item.documentListEntity.id,displayName : item.displayName,initialTime : item.initialTime,revisedTime : item.revisedTime,newRevisedTime : item.newRevisedTime, status : item.status,deferralType : item.documentListEntity.type,deferral : item.documentListEntity.deferral,}
            this.otherDocumentArray.push(a);
        }
        console.log("otherDocumentArray",this.otherDocumentArray);
    },error=>{
        this.showLoader = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}


rejectPopupFun(type){
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
        if(type==1){
                    this.rejectWf(this.fifthId,result.value,'ANCHOR_OPERATION_CHECKER_LEAD');
                }else{
                    this.rejectWf(this.fifthId,result.value,'CP_OPERATION_CHECKER_LEAD');
                }
        }
    })
}



}