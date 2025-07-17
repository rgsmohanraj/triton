import { Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { Observable } from 'rxjs';
import { NgbdSortableHeader, SortEvent } from 'src/app/shared/directives/NgbdSortableHeader';
import { TableService } from 'src/app/shared/services/table.service';
import { COMPANYDB, CompanyDB } from '../../../shared/data/tables/company';
import { ApiRequestService } from 'src/app/shared/services/api-request.service';
import { ToastrService } from 'ngx-toastr';
import { CommonModule,DatePipe } from '@angular/common';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { Router } from '@angular/router';
import $ from "jquery";
declare var require
const Swal = require('sweetalert2')

import { AuthService } from 'src/app/shared/services/firebase/auth.service';


@Component({
  selector: 'app-counter-party',
  templateUrl: './counter-party.component.html',
  styleUrls: ['./counter-party.component.scss']
})
export class CounterPartyComponent implements OnInit {
    refresh_token:any;
    public renewalEnhancement = false;
    public deferralDocView = false;
    public otherDefDocView = false;
    public deferralHistory = false;

    public selected = [];
    emailId:any;
    anchorId:any;
    custId:any;
    statusCheck = 'clear';
    anchorList : Array<any> = [];
    anchorMasterList : Array<any> = [];
    getAllAppId:[]=[];
    anchorDetailsArr:object[]=[];
    counterPartyList:[]=[];
    proposalList:[]=[];
    debtProfileList:[]=[];
    limitEligibilityList:[]=[];
    termSheetList:[]=[];
    anchorBeneficiary:[]=[];
    searchAnchor:any;
    searchValue:any;
    anchorSearchList:[]=[];
    docReports:[]=[];
    //Credit policy
    creditPolicyResults:Array<any> = [];
    creditPolicyArray: Array<any> = [];
    creditPolicyArrayss:Array<any> = [];
    //Soft policy Details
        softPolicyResult:Array<any> = [];
        softPolicyDealerResult:Array<any> = [];
        softPolicyVendorResult:Array<any> = [];
        softPolicyArray: Array<any> = [];
        anchorType:any;
//         public saveSoftPolicyFlag = true;
    //Others Document
        otherDocumentArray: Array<any> = [];
        OtherDocReports: Array<any> = [];

    documentMaster:[]=[];
    collateralList:[]=[];
    dueDiligenceList:[]=[];
    activeAnchor:[]=[];
    inProgressAnchor:[]=[];
    rejectAnchor:[]=[];
anchorName:any;
    workFlowArray: Array<any> = [];

    deferralDocReportArray: Array<any> = [];

    constructor(public authService: AuthService,private toaster: ToastrService,public service: TableService, private requestapi:ApiRequestService,private http: HttpClient,public router: Router,
    public datePipe:DatePipe) {
        const date = new Date();
        var currentDate = new Date(date.getFullYear(), date.getMonth(), date.getDate()+1);
        var date1 = currentDate.toISOString().split('T')[0]
        var date2 = this.datePipe.transform(date1, 'dd-MM-yyyy');
        this.currentDate = date2 ;
    }

    @ViewChildren(NgbdSortableHeader) headers: QueryList<NgbdSortableHeader>;

    onSort({ column, direction }: SortEvent) {
        this.headers.forEach((header) => {
            if (header.sortable !== column) {
                header.direction = '';
            }
        });
        this.service.sortColumn = column;
        this.service.sortDirection = direction;
    }

currentDate:any;
    public viewDocument = false;
    public cpViewDetails = false;
    public cpWorkFlowFlag: boolean = false;

    counterPartyId:any;

    public onSelect(selected) {
        this.service.deleteSingleData(selected);
    }


    ngOnInit() {
        this.requestapi.changeParam(null);
        this.getDocumentType();
        this.getRMNames();
        this.emailId=localStorage.getItem('email')
        let roles=localStorage.getItem('roles');
        for(let item of roles.split(",")){
        console.log("Roles",item)
            if(item == 'BUSINESS' || item == 'BUSINESS_LEAD' || item == 'CP_BUSINESS_LEAD'){
                this.renewalEnhancement = true;
                break;
            }
        }
        if(this.emailId==null){
            localStorage.clear();
            this.router.navigate(['/auth/login']);
        }
        this.getCustomerInfoDetails();
    }

    getCustomerInfoDetails(): void {
        this.showLoader=true;
        this.searchValue = '';
        let response;
        let fileName="wfApprovalStatus/existingAnchorInDesc";
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
            this.showLoader=false;
            this.anchorList=res;
            this.anchorMasterList = res;
        },error=>{
        this.showLoader=false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }

        })
    }

    searAnchor(event: any): void {
        let key = event.target.value;
        if(event.target.value!=''){
            key = key.toLowerCase();
            this.anchorList= this.anchorMasterList.filter(item => (item.appId.customerInfoEntity.customerName.toLowerCase().includes(key)) ||
            (item.appId.customerInfoEntity.pan.toLowerCase().includes(key)) || (item.appId.customerInfoEntity.cin ? item.appId.customerInfoEntity.cin.toLowerCase().includes(key):'')
            );
        }else{
            this.anchorList=this.anchorMasterList;
        }
    }

    statusTable(val){
        this.statusCheck = val;
    }

    public tableViewDetails: boolean = true;
    public viewDetails: boolean = false;
    public reCheck = true;
    public viewDetailsAllAppIds: boolean = false;
    rmNameArray: Array<any> = [];
    rmNames:any;

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

getAnchorAllAppIds(appId,custId,status):void {
        if(status == -2 || status == 0){
            this.reCheck = false;
        }else{
            this.reCheck = true;
        }
        this.custId = custId;
        this.anchorId = appId;
        this.showLoader=true;
        this.tableViewDetails = false;
        this.viewDetailsAllAppIds = true;
        console.log(appId,custId,status,'params')
        let response;
        let fileName="anchor/getAllAppIds/"+custId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
            this.showLoader=false;
            this.getAllAppId=res.appDetails;
            console.log(this.getAllAppId,'this.getAllAppId')
        },error=>{
        this.showLoader=false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
        })
    }

    fundReqDetails:[]=[];
    anchorDetails(id,name,custId){
        this.counterPartyId = id;
        console.log(id,name,custId,'id,name,custId');
        this.custId = custId;
        this.viewDetailsAllAppIds = false;
        this.anchorName='Anchor Details: '+name;
        this.viewDetails = true;
        this.getCounterPartyById(id);
        this.getProposalById(id);
        this.getDebtProfileById(id);
        this.getFundRequirement(id);
        this.getLimitEligibilityById(id);
        this.getTermSheetById(id);
        this.getCollateralById(id);
        this.getDueDiligenceById(id);
        this.getCpBeneficiaryById(id);
        this.getUpdateCreditPolicyDetails();
        this.getSoftPolicyDetails();
        window.scrollTo(0, 0);
    }

    getCounterPartyById(uploadId):void {
        let response;
        let fileName="counterParty/cpBasicDetails/"+uploadId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
            this.counterPartyList=res;
        },error=>{
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
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
        })
    }



popupForRE(index){
    Swal.fire({
        title: 'Renewal/Enhancement?',
        text: '',
        input: 'radio',
        inputOptions: {
            'renewal': 'Renewal',
            'enhancement': 'Enhancement'
        },
        inputValidator: (result) => {
            if (!result) {
                return 'You must select an option';
            }
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
        if (result.isConfirmed) {
            if (result.value === 'renewal') {
                this.navigateToRE(2);
                console.log("renewal");
            } else if (result.value === 'enhancement') {
                console.log("enhancement");
                this.navigateToRE(3);
            }
        }
    })
}

navigateToRE(val) {
//     localStorage.setItem('renewalEnhancement',val)
    let response;
    let fileName="wfApprovalStatus/getWFStatus/"+this.custId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        if(res.status){
          this.router.navigate(['/form/counter-party-renewal']);
          this.requestapi.setRenewalEnhancementParams(this.anchorId,this.custId,val,true,false);
        }else{
            this.toaster.error("This Counter Party is not applicable for Renewal/Enhancement...");
        }
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}
    getDueDiligenceById(uploadId): void {
       this.showLoader=true;
        let response
        let fileName="counterParty/dueDiligenceDetails/"+uploadId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
        this.showLoader=false;
            for(var i=0; i<res.length; i++){
                if(res[i].dueDiligenceMasterEntity.datatype == 'TimeStamp'){
                    res[i].value =this.datePipe.transform(res[i].value, 'dd-MM-yyyy');
                }
            }
            this.dueDiligenceList=res;
        },error=>{
        this.showLoader=false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }

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

        })
    }

    anchorFlowHistoryDetails():void{
        this.searchAnchor='';
        this.viewDetails = false;
        this.viewDetailsAllAppIds = true;
        window.scrollTo(0, 0);
    }
    tableDetails(){
        this.searchValue = '';
        this.statusCheck = 'clear';
        this.tableViewDetails= true;
        this.viewDetails=false;
        this.viewDetailsAllAppIds = false;
        this.anchorList=this.anchorMasterList;
        window.scrollTo(0, 0);
    }

    backToCpDetails(){
        this.viewDetails = true;
        this.cpWorkFlowFlag = false;
        window.scrollTo(0, 0);
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
        })
    }
showLoader: boolean = false;

    getDocumentType(): void{
        this.showLoader=true;
        let response;
        let fileName="dms/documentType";
        response = this.requestapi.getData(fileName);
        response.subscribe((res:any) => {
        this.showLoader=false;
            this.documentMaster = res;
        },error=>{
            this.showLoader=false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
        })
    }

    getCounterPartyDocument():void{
       this.showLoader=true;
        this.tableViewDetails=false;
        this.viewDetails=false;
        let response;
        let fileName="dms/documentReports?appId="+this.counterPartyId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res:any) => {
            this.showLoader=false;
            if(res.documentReports.length > 0){
                this.docReports = res.documentReports;
                this.viewDocument = true;
            }else{
                this.docReports = null;
                this.viewDetails=true;
                this.toaster.error("Document Unavailable");
            }
        },error=>{
        this.showLoader=false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
            this.viewDetails=true;
            this.toaster.error("Document Unavailable");
        })
    }

    getDeferralDocReports(): void{
        let response;
        let fileName="dms/deferralReports/"+this.counterPartyId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res:any) => {
            this.deferralDocReportArray = res.deferralReports;
            for(let item of res.deferralReports){
                if(item.initialTime != null){
                    let dateString = item.initialTime;
//                     var dateString = item.initialTime[0]+"-"+item.initialTime[1]+"-"+item.initialTime[2];
                    var date = this.datePipe.transform(dateString, 'dd-MM-yyyy');
                    item.initialTime = date;
                }
                if(item.docCompletionDate != null){
                    let dateString = item.docCompletionDate;
//                     var dateString = item.docCompletionDate[0]+"-"+item.docCompletionDate[1]+"-"+item.docCompletionDate[2];
                    var date = this.datePipe.transform(dateString, 'dd-MM-yyyy');
                    item.docCompletionDate = date;
                }
            }
            for(let item of res.deferralReports){
                if(item.rmName != null){
                    this.rmNames = item.rmName;
                }
//                 this.deferralDocView = true;
            }
            if(res.deferralReports.length != 0){
                this.deferralDocView = true;
            }else{
                this.deferralDocView = false;
            }
        },error=>{
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }

        })
    }

    downloadDocument(docMainName,docSubMainName,fileName){
        let response;
        let url="dms/download/"+this.counterPartyId+"/"+docMainName+"/"+docSubMainName+"/"+fileName;
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


    getDefHistoryDocReports(): void{
        this.viewDetails=false;
        let response;
        let fileName="dms/deferralReports/"+this.counterPartyId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res:any) => {
            this.deferralDocReportArray = res.deferralReports;
            for(let item of res.deferralReports){
                if(item.initialTime != null){
                    let dateString = item.initialTime;
//                     var dateString = item.initialTime[0]+"-"+item.initialTime[1]+"-"+item.initialTime[2];
                    var date = this.datePipe.transform(dateString, 'dd-MM-yyyy');
                    item.initialTime = date;
                }
                if(item.revisedTime != null){
                    let dateString = item.revisedTime;
//                     var dateString = item.revisedTime[0]+"-"+item.revisedTime[1]+"-"+item.revisedTime[2];
                    var date = this.datePipe.transform(dateString, 'dd-MM-yyyy');
                    item.revisedTime = date;
                }

                if(item.processedDate != null){
                    var date = this.datePipe.transform(item.processedDate, 'dd-MM-yyyy');
                    item.processedDate = date;
                }

                if(item.docCompletionDate != null){
                    let dateString = item.docCompletionDate;
//                     var dateString = item.docCompletionDate[0]+"-"+item.docCompletionDate[1]+"-"+item.docCompletionDate[2];
                    var date = this.datePipe.transform(dateString, 'dd-MM-yyyy');
                    item.docCompletionDate = date;
                }
                if(item.updatedAt != null){

                }

            }
            for(let item of res.deferralReports){
                if(item.rmName != null){
                    this.rmNames = item.rmName;
                }
            }
            if(res.deferralReports.length != 0){
                this.deferralDocView = true;
                this.deferralHistory = true;
            }
            else{
            this.viewDetails=true;
            this.toaster.error("Deferral history not available!!!")
            }
        },error=>{
        this.viewDetails=true;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }

        })
    }

    getOtherDocMaster(): void{
        this.showLoader = true;
        let response;
        let fileName="dms/otherDocumentMaster/"+this.counterPartyId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res:any) => {
            this.showLoader = false;
            this.otherDocumentArray = [];
            if(res.length != 0){
                this.otherDefDocView = true;
            }
            for(let item of res){
                if(item.initialTime != null){
                    var dateString = item.initialTime[0]+"-"+item.initialTime[1]+"-"+item.initialTime[2];
                    var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
                    item.initialTime = date;
                }
                if(item.revisedTime != null){
                    var dateString = item.revisedTime[0]+"-"+item.revisedTime[1]+"-"+item.revisedTime[2];
                    var date = this.datePipe.transform(dateString, 'dd-MM-yyy');
                    item.revisedTime = date;
                }

                if(item.createdAt != null){
                    var date = this.datePipe.transform(item.createdAt, 'dd-MM-yyyy');
                    item.createdAt = date;
                }
                if(item.updatedAt != null){
                    var date = this.datePipe.transform(item.updatedAt, 'dd-MM-yyyy');
                    item.updatedAt = date;
                }
                const a = { id : item.id, appId: item.applicationEntity.id ,docListId : item.documentListEntity.id,displayName : item.displayName,initialTime : item.initialTime,revisedTime:item.revisedTime,status : item.status.toString(),deferralType : item.deferralType,deferral : item.documentListEntity.deferral,createdAt : item.createdAt,updatedAt : item.updatedAt,}
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

    getOtherDocDownload(): void{
    // this.showLoader=true;
        let response;
        let fileName="dms/customerOtherDocReports?appId="+this.counterPartyId;
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
        let url="dms/otherDownload/"+this.counterPartyId+"/"+docMainName+"/"+docSubMainName+"/"+otherDocName+"/"+fileName;
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

    viewCpDetails(){
        this.tableViewDetails=false;
        this.viewDetails=true;
        this.deferralHistory = false;
        this.viewDocument = false;
        window.scrollTo(0, 0);
    }
deferralWorkFlowArray: Array<any> = [];
    cpWorkflowPage(){
           this.showLoader=true;
            let response;
            let fileName="wfApprovalStatus/getRemarks/"+this.counterPartyId;
            response = this.requestapi.getData(fileName);
            response.subscribe((res: any) => {
            this.showLoader=false;
                this.workFlowArray=res;
                this.viewDetails = false;
                this.cpWorkFlowFlag = true;
                window.scrollTo(0, 0);
                for(let item of res){
                    console.log(item.currentStageTeam);
                       if(item.currentStageTeam=='CP_DEFERRAL_COMMITTEE_LEAD'){
                            let response;
                                let fileName="deferralWorkflow/getHistoryOfWFStatus/"+this.counterPartyId;
                                response = this.requestapi.getData(fileName);
                                response.subscribe((res: any) => {

                                    if(res.length != 0){
                                    this.deferralWorkFlowArray=res;
                                               console.log(res,"res");
                                       }
                                },error=>{
                                if(error.status==401){
                                      this.refresh_token=localStorage.getItem('refresh_token')
                                      this.authService.SignOut(this.refresh_token);
                                    }
                                })
                          }
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

    getUpdateCreditPolicyDetails(): void{
        this.showLoader=true;
        let response;
        let fileName="counterParty/creditPolicyDetails/"+this.counterPartyId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res:any) => {
        this.showLoader=false;
        if(res.creditPolicyArray != 'empty'){
            this.creditPolicyArray = res.creditPolicyArray;
            console.log(res.creditPolicyArray,'res.creditPolicyArray')
//             this.updateCreditPolicyFlag = true;
//             this.creditPolicyArray = [];
    //         for(let master of this.creditPolicyFilterList){
//                                 for(let itemValue of res.creditPolicyArray){
//     //                                 if(master.creditPolicyMasterEntity.id == itemValue.creditPolicyId){
//                                         (<HTMLInputElement>document.getElementById(itemValue.scpDisplayName)).value = itemValue.value;
//                                         const a1 = { id:itemValue.id, appId: itemValue.appId ,cpMasterId : itemValue.creditPolicyId,value : itemValue.value}
//                                         this.creditPolicyArray.push(a1);
//     //                                 }
//                                 }
                               this.runCreditPolicyseven();
    //                         }
    console.log(this.creditPolicyArray,'this.creditPolicyArray')
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

    runCreditPolicyseven(): void {
        this.showLoader=true;
//             if(this.proposalArray[0].product == "Dealer Invoice Finance" || this.proposalArray[0].product == "Dealer Purchase Order Finance" || this.proposalArray[0].product == "Anchor Sales Bill Discounting"){
//                 this.cpType = "Dealer";
//             }else if(this.proposalArray[0].product == "Vendor Invoice Finance" || this.proposalArray[0].product == "Vendor Purchase Order Finance" || this.proposalArray[0].product == "Anchor Purchase Bill Discounting"){
//                 this.cpType = "Vendor";
//             }

    //         if(this.creditPolicyArray.length==this.creditPolicyMasterList.length){
                let response;
                let fileName="counterParty/creditPolicyMaster?apppId="+this.counterPartyId;
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
                console.log(res.creditPolicyResults,this.creditPolicyResults,'this.creditPolicyResults123')
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

        runSoftPolicy(): void {
                this.showLoader=true;
                let response;
                let fileName="counterParty/runSoftPolicy/"+this.counterPartyId;
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
//                 this.saveSoftPolicyFlag= false;
                console.log(this.softPolicyResult,'this.softPolicyResult123')
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

        getFundRequirement(id): void{

            let response;
            let fileName="counterParty/fundReqDetails/"+id;
            response = this.requestapi.getData(fileName);
            response.subscribe((res:any) => {

                if(res.length !=0){
                    this.fundReqDetails = res;
                }
            },error=>{

            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                        this.authService.SignOut(this.refresh_token);
                               }

            })
        }

    getSoftPolicyDetails(): void{
                let response;
                let fileName="counterParty/softPolicyDetails/"+this.counterPartyId;
                response = this.requestapi.getData(fileName);
                response.subscribe((res:any) => {
//                     this.updateSoftPolicyFlag = true;
                    this.softPolicyArray = [];
                    this.softPolicyArray = res.softPolicyDetailsDataList;
                    console.log(this.softPolicyArray,'this.softPolicyArray')
                    this.runSoftPolicy();
                },error=>{

                    if(error.status==401){
                        this.refresh_token=localStorage.getItem('refresh_token')
                        this.authService.SignOut(this.refresh_token);
                    }

                })
             }
         }
