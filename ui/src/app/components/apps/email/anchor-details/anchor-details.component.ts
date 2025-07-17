import { Component, OnInit, QueryList, ViewChildren} from '@angular/core';
import { Observable } from 'rxjs';
import { NgbdSortableHeader, SortEvent } from 'src/app/shared/directives/NgbdSortableHeader';
import { TableService } from 'src/app/shared/services/table.service';
import { ApiRequestService } from 'src/app/shared/services/api-request.service';
import { ORDERHISTORY, OrderHistory } from '../../../../shared/data/tables/order-history';
import { ToastrService } from 'ngx-toastr';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from '../../../../../environments/environment';
import { AuthService } from 'src/app/shared/services/firebase/auth.service';
import { Router } from '@angular/router';
declare var require
const Swal = require('sweetalert2')

import { CommonModule,DatePipe } from '@angular/common';

import { Pipe, PipeTransform } from '@angular/core';
import $ from "jquery";

@Component({
  selector: 'app-anchor-details',
  templateUrl: './anchor-details.component.html',
  styleUrls: ['./anchor-details.component.scss']
})
export class AnchorDetailsComponent implements OnInit {

    public deferralDocView = false;
    public otherDefDocView = false;
    public deferralHistory = false;

    public selected = [];
    anchorDetailsArr:object[]=[];
    fileUploadAnchorBasicList:[]=[];
    getAllAppId:[]=[];
    fileUploadAnchorAddressList:[]=[];
    fileUploadAnchorKeyList:[]=[];
    fileUploadAnchorProgramList:[]=[];
    fileUploadAnchorGstList:[]=[];
    fileUploadAnchorAuthoriseList:[]=[];
    searchAnchor:any;
    anchorBeneficiary:[]=[];
    creditNormsList:[]=[];
    docReports: Array<any> = [];

//Others Document
    otherDocumentArray: Array<any> = [];
    OtherDocReports: Array<any> = [];

    documentMaster:[]=[];
    activeAnchor:[]=[];
    inProgressAnchor:[]=[];
    rejectAnchor:[]=[];
    workFlowArray: Array<any> = [];
deferralWorkFlowArray: Array<any> = [];
    deferralDocReportArray: Array<any> = [];

showLoader: boolean = false;
    anchorList : Array<any> = [];
    anchorSearchList : Array<any> = [];
    public tableItem$: Observable<OrderHistory[]>;
    public searchText;
    total$: Observable<number>;

    constructor(public authService: AuthService,private toaster: ToastrService,public router: Router,public service: TableService, private requestapi:ApiRequestService,private http: HttpClient,public datePipe:DatePipe) {
        this.tableItem$ = service.tableItem$;
        this.total$ = service.total$;
        this.service.setUserData(ORDERHISTORY)

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

    public renewalEnhancement = false;
    public onSelect(selected) {
        this.service.deleteSingleData(selected);
    }

    refresh_token:any;
    emailId:any;
    anchorId:any;
    custId:any
    searchValue:any;
    statusCheck:any;
    ngOnInit() {
        this.requestapi.changeParam(null);
        this.getDocumentType();
        this.getRMNames();
        this.emailId=localStorage.getItem('email')
        let roles=localStorage.getItem('roles');
            for(let item of roles.split(",")){
                if(item == 'CPA' || item == 'CPA_LEAD' || item == 'ANCHOR_CPA_LEAD'){
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
    errorMsg=false;
    getCustomerInfoDetails(): void {
        this.showLoader = true;
        this.searchValue = '';
        let response;
        let fileName="wfApprovalStatus/existingAnchorInDesc";
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
            this.showLoader = false;
            this.anchorList =[];
            this.anchorSearchList = [];
            this.searchAnchor = '';
            this.anchorList=res;
            this.statusCheck = 'clear';
        },error=>{
        this.showLoader = false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
        })

    }

    anchorName:any;currentDate:any;
    public tableViewDetails: boolean = true;
    public viewDetails: boolean = false;
    public viewDetailsAllAppIds: boolean = false;
    public viewDocument = false;
    public workFlowFlag: boolean =false;
    public reCheck = true;
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

    anchorDetails(id,name,custId){
    console.log(id,name,custId,'id,name,custId')
        this.anchorId = id;
        this.custId = custId;
//         this.tableViewDetails=false;
        this.viewDetailsAllAppIds = false;
        this.viewDetails = true;
        this.anchorName='Anchor Details: '+name;
        this.getAnchorBasicDetailsById(id);
        this.getAnchorAddressDetailsById(id);
        this.getAnchorKeyDetailsById(id);
        this.getAnchorProgramDetailsById(id);
        this.getAnchorGstById(id);
        this.getAnchorAuthoriseById(id);
        this.getAnchorBeneficiaryById(id);
        this.getAnchorCreditNormsId(id);
        window.scrollTo(0, 0);
    }

    items: any; searchText1: string

   searAnchor(event: any): void {

            console.log(event,'event')
            console.log(this.anchorSearchList,'this.anchorSearchList')
            this.anchorSearchList = this.anchorList;

            if(event.target.value!=''){
            this.searchAnchor=event.target.value;
            this.items=this.anchorList;
            this.searchText1=this.searchAnchor;
            this.searchText1 = this.searchText1.toLowerCase();
            this.anchorSearchList= this.items.filter(item => (item.appId.customerInfoEntity.customerName.toLowerCase().includes(this.searchText1)) ||
            (item.appId.customerInfoEntity.pan.toLowerCase().includes(this.searchText1)) || (item.appId.customerInfoEntity.cin ? item.appId.customerInfoEntity.cin.toLowerCase().includes(this.searchText1):'')
            );

            }else if(event.target.value == ''){
            this.anchorSearchList = this.anchorList;
            }
            }

    customerInfoSearch(event: any): void {
    this.showLoader=true;
        this.searchAnchor=event.target.value;
        if(this.searchAnchor==''||this.searchAnchor==null||this.searchAnchor.length==0){
            this.getCustomerInfoDetails();
            this.anchorSearchList=[];
        }else{
            this.anchorList=[];
            let access_token=localStorage.getItem('access_token')
            let headers= new HttpHeaders({
                'Content-Type': 'application/json',
                'SECRET-KEY':'7XkwJiMfgOg5oemHAzRaUf54BfLsmfK9',
                'Authorization':access_token
            })
            let params = new HttpParams().set("query",this.searchAnchor).set("type",1).set("stage","A5");
            let response;
            response=this.http.get(environment.baseUrl+'anchor/customerInfoSearch', { headers: headers,params: params });
            response.subscribe((res: any) => {
            this.showLoader=false;
                this.anchorSearchList=res.SearchResult;
            },error=>{
            this.showLoader=false;
                if(error.status==401){
                    this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                }
            })
        }
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
          this.router.navigate(['/email/AnchorRenewal']);
          this.requestapi.setRenewalEnhancementParams(this.anchorId,this.custId,val,true,false);
        }else{
            this.toaster.error("This Anchor is not applicable for Renewal/Enhancement...");
        }
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}
    getAnchorBasicDetailsById(uploadId):void {
    this.showLoader=true;
        let response;
        let fileName="anchor/anchorBasicFile/"+uploadId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
        this.showLoader=false;
            this.fileUploadAnchorBasicList=res;
        },error=>{
        this.showLoader=false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
        })
    }

    getAnchorAddressDetailsById(uploadId): void {
    this.showLoader=true;
        let response;
        let fileName="anchor/anchorAddressFile/"+uploadId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
        this.showLoader=false;
            this.fileUploadAnchorAddressList=res;
        },error=>{
        this.showLoader=false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
        })
    }

    getAnchorKeyDetailsById(uploadId): void {
    this.showLoader=true;
        let response
        let fileName="anchor/anchorKeyFile/"+uploadId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
        this.showLoader=false;
            this.fileUploadAnchorKeyList=res;
        },error=>{
        this.showLoader=false;
            if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
            }
        })
    }

    getAnchorProgramDetailsById(uploadId):void {
    this.showLoader = true;
        let response;
        let fileName="anchor/anchorProgramsFile/"+uploadId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
        this.showLoader = false;
            this.fileUploadAnchorProgramList=res;
        },error=>{
        this.showLoader = false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
        })
    }

    getAnchorGstById(uploadId):void {
    this.showLoader=true;
        let response;
        let fileName="anchor/anchorGstFile/"+uploadId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
        this.showLoader=false;
            this.fileUploadAnchorGstList=res;
        },error=>{
        this.showLoader=false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
        })
    }

    getAnchorAuthoriseById(uploadId):void {
    this.showLoader=true;
        let response;
        let fileName="anchor/anchorAuthorizedFile/"+uploadId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
        this.showLoader=false;
        this.fileUploadAnchorAuthoriseList=res;
        },error=>{
        this.showLoader=false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
        })
    }
    getAnchorBeneficiaryById(uploadId):void{
    this.showLoader=true;
        let response;
        let fileName="anchor/anchorBeneficiaryFile/"+uploadId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
        this.showLoader=false;
            this.anchorBeneficiary=res;
        },error=>{
        this.showLoader=false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
        })
    }

    getAnchorCreditNormsId(uploadId):void{
    this.showLoader=true;
        let response;
        let fileName="anchor/creditNormsDetailsByFId/"+uploadId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
        this.showLoader=false;
            this.creditNormsList=res;
        },error=>{
        this.showLoader=false;
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

    tableDetails():void{
        this.searchValue='';
//         this.getCustomerInfoDetails();
        this.tableViewDetails = true;
        this.viewDetailsAllAppIds = false;
        window.scrollTo(0, 0);
    }

    statusTable(val){
        this.statusCheck = val;
    }

    viewDocuments(){
        this.getAnchorDocument();
        this.getDeferralDocReports();
        this.getOtherDocMaster();
        this.getOtherDocDownload();
        this.viewDetails=false;
        this.viewDocument = true;
    }
//     statusTable(val):void{
//         this.showLoader=true;
//         let boolValue = (this.searchValue == ''  ||  this.searchValue == undefined) ? true : false;
//         console.log('Boolean Value ',boolValue);
//         this.searchValue = '';
//         let response;
//         let fileName="wfApprovalStatus/existingAnchor?stage=A5&status="+val;
//         response = this.requestapi.getData(fileName);
//         response.subscribe((res:any) => {
//         this.showLoader=false;
//             this.anchorList=[];
//             this.anchorList = res;
//             if(!boolValue){
//               this.searAnchor('');
//             }
//         },error=>{
//         this.showLoader=false;
//             if(error.status==401){
//                 this.refresh_token=localStorage.getItem('refresh_token')
//                 this.authService.SignOut(this.refresh_token);
//             }
//         })
//     }
deferralStatus:any;
getRemarks():void{
this.deferralStatus=false;
this.showLoader=true;
    let response;
    let fileName="wfApprovalStatus/getRemarks/"+this.anchorId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
    this.showLoader=false;
            this.workFlowArray=res;

            this.viewDetails = false;
            this.workFlowFlag = true;
            window.scrollTo(0, 0);
    for(let item of res){
    console.log(item.currentStageTeam);
       if(item.currentStageTeam=='ANCHOR_DEFERRAL_COMMITTEE_LEAD'){
       this.deferralStatus=true;
            let response;
                let fileName="deferralWorkflow/getHistoryOfWFStatus/"+this.anchorId;
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
                    this.errorMsg=true;
                })
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

    getAnchorDocument():void{
        this.showLoader=true;
        this.docReports = null;
        let response;
        let fileName="dms/documentReports?appId="+this.anchorId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res:any) => {
        this.showLoader=false;
            this.docReports = res.documentReports;
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
        let fileName="dms/deferralReports/"+this.anchorId;
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
        let url="dms/download/"+this.anchorId+"/"+docMainName+"/"+docSubMainName+"/"+fileName;
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
        console.log("Current Date",this.currentDate);
        this.viewDetails=false;
        let response;
        let fileName="dms/deferralReports/"+this.anchorId;
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

            }
            for(let item of res.deferralReports){
                if(item.rmName != null){
                    this.rmNames = item.rmName;
                }
//                 this.deferralDocView = true;
            }
            if(res.deferralReports.length != 0){
                this.deferralDocView = true;
                this.deferralHistory = true;
            }else{
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
        let fileName="dms/otherDocumentMaster/"+this.anchorId;
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
                    var date = this.datePipe.transform(dateString, 'dd-MM-yyy');
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
        this.OtherDocReports = null;
        let response;
        let fileName="dms/customerOtherDocReports?appId="+this.anchorId;
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
        let url="dms/otherDownload/"+this.anchorId+"/"+docMainName+"/"+docSubMainName+"/"+otherDocName+"/"+fileName;
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

    viewAnchorDetails(){
        this.tableViewDetails=false;
        this.viewDetails=true;
        this.viewDocument = false;
        this.deferralHistory = false;
        window.scrollTo(0, 0);
    }
    backToAnchorDetails(){
        this.viewDetails = true;
        this.workFlowFlag = false;
        window.scrollTo(0, 0);
    }

}

