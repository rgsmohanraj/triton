import { Component, OnInit } from '@angular/core';
import { ApiRequestService } from 'src/app/shared/services/api-request.service';
import { AuthService } from 'src/app/shared/services/firebase/auth.service';
import { environment } from '../../../../../environments/environment';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { CommonModule,DatePipe } from '@angular/common';
import $ from "jquery";
import { Observable } from 'rxjs/internal/Observable';
import { of } from 'rxjs/internal/observable/of';

declare var require
const Swal = require('sweetalert2')

@Component({
  selector: 'app-workflow',
  templateUrl: './workflow.component.html',
  styleUrls: ['./workflow.component.scss']
})
export class WorkflowComponent implements OnInit {
options$: Observable<string[]>;


approvalRemarks:any;anchorCount:any;counterPartyCount:any;counterPartyList:any;anchorList:any;refresh_token:any;appId:any;approve:any;DocListEntity:any;status:any;documentName:any;displayName:any;rmNames:any;appType:any;deferralDocArr:any;deferralApproveStatus:any;index:any;lengthStore:any;
// anchorKeyDetails :[] =[];
docTypeId:any;docTypeName:any;docCategoryId:any;docCategoryName:any;document:any;otherDocRes:any;


docReports:[]=[];
remarkArray:[]=[];
DeferralDocArray: Array<any> = [];
OtherDeferralDocArray: Array<any> = [];
rmNameArray: Array<any> = [];
newDeferralAttribute:any ={};
newOtherDeferralAttribute:any ={};
currentStageLead:[]=[];
storeRes:[]=[];
fileUploadAnchorBasicList:[]=[];
fileUploadAnchorAddressList:[]=[];
fileUploadAnchorKeyList:[]=[];
fileUploadAnchorProgramList:[]=[];
fileUploadAnchorGstList:[]=[];
fileUploadAnchorAuthoriseList:[]=[];
documentMaster:Array<any> = [];
anchorBeneficiary:[]=[];
creditNormsList:[]=[];
counterPartyLists:[]=[];
proposalList:[]=[];
debtProfileList:[]=[];
limitEligibilityList:[]=[];
termSheetList:[]=[];
collateralList:[]=[];
dueDiligenceList:[]=[];
fundReqDetails:[]=[];
counterParty:any;
StageId:any;
remarkWf:any;
public defStatus = false;
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

showLoader: boolean = false;

 constructor(public authService: AuthService,private requestapi:ApiRequestService, private toaster: ToastrService,public router: Router,public datePipe:DatePipe)
{

 }


public deferralPending = true; public deferralBasicDetails = true; public deferralApproval = false; public deferralRemarks = false;public errorMsg = false;public anchorCounterParty = true;public anchorDetails = false;public counterPartyDetails = false;
public WorkFlowStage = false; public approveDisable = false; public waiveOffDisable = false;
public approveDisable1 = true;public waiveOffDisable1 = false;public approveDisable3 = false;public waiveOffDisable3 = true; public approveHide=false;
public approveDisableOther = false; public waiveOffDisableOther = false; public approveDisable1Other = true; public waiveOffDisable1Other = false; public approveDisable3Other = false; public waiveOffDisable3Other = true;
public OtherDoc = false; public DeferrDoc = false;
emailId:any;
ngOnInit(): void {
let roles=localStorage.getItem('roles');
this.emailId=localStorage.getItem('email')
         this.getCountDetails();
         this.getRMNames();
         this.roleBasedFun();

}
rbacArray: Array<any> = [];
a1Submit=false;
roleBasedFun(){
this.showLoader = true;
    let roles=localStorage.getItem('roles');
    let response;
    let fileName="permission/permission/";
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
    this.showLoader = false;
        for(let i=0;i<res.length;i++){
            if(res[i].stageId.clientType==1){
            this.rbacArray.push(res[i]);
            }
        }
        for(let j=0;j<this.rbacArray.length;j++){
        console.log(this.rbacArray[j].role,"this.rbacArray[j].role");
                if(this.rbacArray[j].role=='DEFERRAL COMMITTEE'){
                    this.a1Submit=true;
                }
        }
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

nextBasicDeferral(){
          this.deferralApproval = true;
          this.deferralBasicDetails = false;
}

  nextDeferral(){
          this.deferralRemarks = true;
          this.deferralApproval = false;
     }
  deferralPrevious(){
            this.deferralApproval = false;
            this.deferralBasicDetails = true;
  }
  prevDeferral(){
          this.deferralRemarks = false;
          this.deferralApproval = true;
  }

  prevList(){
        this.deferralBasicDetails = false;
        this.deferralApproval = false;
        this.deferralPending = true;
        this.WorkFlowStage = false;
    }
  previousDeferralApproval(){

  this.deferralRemarks = false;
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
        this.deferralRemarks= true;
        this.deferralApproval =false;
        }

  }

  getAnchors():void{
      this.anchorCounterParty = false;
      this.anchorDetails = true;
  }
  getCounterParty():void{
      this.anchorCounterParty = false;
      this.counterPartyDetails= true;
  }



  customerDashboard():void{
      this.anchorCounterParty = true;
      this.anchorDetails = false;
      this.counterPartyDetails = false;
  }


  viewApproval(id,type){
     this.showLoader = true;
     this.appType=type;
     this.deferralBasicDetails =true;
     this.deferralApproval = false;
     this.deferralPending = false;
     this.WorkFlowStage = true;
     this.getAnchorBasicDetailsById(id);
               this.getAnchorAddressDetailsById(id);
               this.getAnchorKeyDetailsById(id);
               this.getAnchorProgramDetailsById(id);
               this.getAnchorGstById(id);
               this.getAnchorAuthoriseById(id);
               this.getAnchorBeneficiaryById(id);
               this.getAnchorCreditNormsId(id);

               this.getCounterPartyById(id);
               this.getProposalById(id);
               this.getDebtProfileById(id);
               this.getLimitEligibilityById(id);
               this.getTermSheetById(id);
               this.getCollateralById(id);
               this.getDueDiligenceById(id);
               this.getFundRequirement(id);
               this.getDeferralOtherDocuments(id);


     this.appId = id;
     let response;
               let fileName="dms/deferralReport/"+this.appId+"/0";
               response = this.requestapi.getData(fileName);
               response.subscribe((res: any) => {

                   console.log(res,"DeferralDocList");
                   this.storeRes = res;
                   console.log("this.storeRes",this.storeRes)
                   this.showLoader = false;

                   if(res.length>0){
                   this.DeferralDocArray = [];
                    this.DeferrDoc =  true;
                   }
                   for(var i=0;i<res.length;i++){
                   if(res[i].rmName != null){
                   this.rmNames = res[i].rmName;
                   }
                   this.addDeferralDocArray();
                   this.DeferralDocArray[i].id = res[i].id;
                   this.DeferralDocArray[i].appId = res[i].applicationEntity.id;
                   this.DeferralDocArray[i].docListId = res[i].documentListEntity.id;
                   this.DeferralDocArray[i].documentName = res[i].documentListEntity.displayName;
                   this.DeferralDocArray[i].initialTime = res[i].initialTime;
                   this.DeferralDocArray[i].revisedTime = res[i].revisedTime;
                   this.DeferralDocArray[i].status = res[i].status

                 }

               })
  }

  getDeferralOtherDocuments(id): void{

//       this.appId = id;
      let response;
      console.log("***************************************************************************")
                let fileName="dms/othersDeferralDocuments/"+id+"/0";
                response = this.requestapi.getData(fileName);
                response.subscribe((res: any) => {
                    console.log(res,"OtherDocuments");
                    this.otherDocRes = res;
                    console.log("this.otherDocRes::::::::::::::::::::::::::::::",this.otherDocRes)
                    this.showLoader = false;

                    if(res.length>0){
                    this.OtherDeferralDocArray = [];
                    this.OtherDoc = true
                    }
                    for(var i=0;i<res.length;i++){
//                     this.rmNames = res[i].rmName;
                    this.addOtherDeferralDocArray();
                    this.OtherDeferralDocArray[i].id = res[i].id;
                    this.OtherDeferralDocArray[i].appId = res[i].applicationEntity.id;
                    this.OtherDeferralDocArray[i].docListId = res[i].documentListEntity.id;
                    this.OtherDeferralDocArray[i].displayName = res[i].displayName;

                    var dateString1 = res[i].initialTime[0]+"-"+res[i].initialTime[1]+"-"+res[i].initialTime[2];
                    var date1 = this.datePipe.transform(dateString1, 'dd-MM-yyyy');
                    this.OtherDeferralDocArray[i].initialTime = date1;

                    if(res[i].revisedTime != null){
                        var dateString2 = res[i].revisedTime[0]+"-"+res[i].revisedTime[1]+"-"+res[i].revisedTime[2];
                        var date2 = this.datePipe.transform(dateString2, 'dd-MM-yyyy');
                        this.OtherDeferralDocArray[i].revisedTime = date2;
                    }
                   if(res[i].rmName != null){
                    this.rmNames = res[i].rmName;
                   }
                    this.OtherDeferralDocArray[i].status = res[i].status

  }
})
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
                         res[i].value =this.datePipe.transform(res[i].value, 'dd-MM-yyyy');
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
this.showLoader = true;
    let response;
    let fileName="dms/customerDocReports?appId="+this.appId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
    this.showLoader = false;
        this.docReports = res.documentReports;
    },error=>{
    this.showLoader = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        this.docReports = null;
    })
}

deleteUploadedFile(docTypeId,docTypeName,docCategoryId,docCategoryName,docListId,docListName,key,fileName,deferralId):void{
   this.showLoader = true;
    let response;
    let url="dms/deleteFile";
    let data ={ appId:this.appId, docTypeId:docTypeId, docTypeName:docTypeName, docCategoryId:docCategoryId,
                docCategoryName:docCategoryName, docListId:docListId, docListName:docListName, fileName:fileName, key:key }
    response = this.requestapi.postData(url,JSON.stringify(data));
    response.subscribe((res: any) => {
    this.showLoader = false;
        this.toaster.success('Successfully Deleted');
        this.storeDecision(deferralId,event,1);
        this.getDocDownloadType();
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
        this.toaster.error('File Already Deleted');
    })
}

    uploadDocument(event,docTypeId,docTypeName,docCategoryId,docCategoryName,docListId,docListName,key,deferralId){
this.showLoader = true;
        const file = event.target.files[0];
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
            response.subscribe((res: any) => {
            this.showLoader = false;
                this.storeDecision(deferralId,event,2);
                this.getDocDownloadType();
            },error=>{
            this.showLoader = false;
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
              }else{
              this.status = 0;
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
              appId: this.appId,
              docListId : deferralDoc.docListId,
              documentName : deferralDoc.documentName,
              initialTime: deferralDoc.initialTime,
              revisedTime: deferralDoc.revisedTime,
              status: this.status
    }
    this.DeferralDocArray[index] = c;

    let lengthStore =this.DeferralDocArray.length-1
    console.log ("**lengthStore**",lengthStore);
    console.log ("**indexstore**",index);
    console.log("lengthStore == this.DeferralDocArray[index])",lengthStore == index);

//     if(lengthStore == index){
//
//     this.approveHide=false;
//
//     }
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
              appId: this.appId,
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

updateEta(){

//     this.showLoader = true;
    let response;
    let fileName="dms/saveNewDeferralDate";
    let data={ appId: this.appId,deferralReportsDataList:this.DeferralDocArray,rmName : this.rmNames}
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
    window.location.reload();
        this.router.navigate(['/email/worflow']);
        this.showLoader = false;
//     this.toaster.success("Successfully Submitted")
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

updateOtherDef(){

//     this.showLoader = true;
    let response;
    let fileName="dms/updateOtherDeferralStatus";
    let data={ appId: this.appId,otherDocumentDataList:this.OtherDeferralDocArray,rmName : this.rmNames}
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
//     window.location.reload();
//         this.router.navigate(['/email/worflow']);
        this.showLoader = false;
        console.log("Working ?????")
    this.toaster.success("Successfully Submitted")
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

getRemarks():void{
this.showLoader = true;
let response;
let fileName="deferralWorkflow/getRemarks/"+this.appId;
response = this.requestapi.getData(fileName);
response.subscribe((res: any) => {
this.showLoader = false;
this.remarkArray=res;

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

// disableApproveDecision(type): void{
//
//    for(let deferralApproveStatus of this.DeferralDocArray){
//     if(deferralApproveStatus.status == 0){
//        this.approveHide=true;
//        this.toaster.error("please select Decision ");
//     }
//   }
// }

  popupDeferralStage(type){

//          console.log("this.deferralDocArr.status" ,this.deferralDocArr.status);
//        for(let deferralApproveStatus of this.DeferralDocArray )
//        {
//          console.log("deferralApproveStatus.length",this.DeferralDocArray.length-1);
//          console.log("Index", this.DeferralDocArray.indexOf(deferralApproveStatus));
//
//
//          if(this.DeferralDocArray.length-1 == this.DeferralDocArray.indexOf(deferralApproveStatus)){

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
                this.wfAnchorDeferralStage(result.value);
            }else{
                this.wfCpDeferralStage(result.value);
            }
                }
            })
//          }
//        }
      }


  wfCpDeferralStage(remarkWf){
  this.showLoader = true;
  this.updateEta();
  this.updateOtherDef();
  this.remarkWf = remarkWf;
      let response;
      let fileName="deferralWorkflow/saveDeferralWorkflow";
      let data={ stageId:34,status:2,approverInfo:this.emailId,appId:this.appId,remarks:this.remarkWf
      ,nextApproverInfo:"CP_OPERATION_MAKER_LEAD"}
      response = this.requestapi.postData(fileName,JSON.stringify(data));
      response.subscribe((res: any) => {

//       window.location.reload();
//               this.router.navigate(['/email/worflow']);
//               this.showLoader = false;

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

  wfAnchorDeferralStage(remarkWf){
  this.showLoader = true;
  this.updateEta();
  this.updateOtherDef();
      this.remarkWf = remarkWf;
      let response;
      let fileName="deferralWorkflow/saveDeferralWorkflow";
      let data={ stageId:31,status:2,approverInfo:this.emailId,appId:this.appId,remarks:this.remarkWf
      ,nextApproverInfo:"ANCHOR_OPERATION_MAKER_LEAD"}
      response = this.requestapi.postData(fileName,JSON.stringify(data));
      response.subscribe((res: any) => {

//       window.location.reload();
//               this.router.navigate(['/email/worflow']);
//               this.showLoader = false;

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
                    this.rejectWf(31,result.value,'ANCHOR_DEFERRAL_COMMITTEE_LEAD');
                }else{
                    this.rejectWf(34,result.value,'CP_DEFERRAL_COMMITTEE_LEAD');
                }
        }
        else{
        window.location.reload();
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
                this.router.navigate(['/email/workflow']);
                this.showLoader = false;

    },error=>{
    this.showLoader = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}


}
