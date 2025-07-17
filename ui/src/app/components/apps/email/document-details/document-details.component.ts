import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { NgbdSortableHeader, SortEvent } from 'src/app/shared/directives/NgbdSortableHeader';
import { TableService } from 'src/app/shared/services/table.service';
import { ApiRequestService } from 'src/app/shared/services/api-request.service';
import { ORDERHISTORY, OrderHistory } from '../../../../shared/data/tables/order-history';
import { CommonModule,DatePipe } from '@angular/common';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from '../../../../../environments/environment';
import { AuthService } from 'src/app/shared/services/firebase/auth.service';
import { Router } from '@angular/router';
import $ from "jquery";
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-document-details',
  templateUrl: './document-details.component.html',
  styleUrls: ['./document-details.component.scss']
})
export class DocumentDetailsComponent implements OnInit {

public anchorCounterParty = true;
public errorMsg = false;
searchText:any;
anchorCount:any;
counterPartyCount:any;

    showLoader: boolean = false;
    refresh_token:any;
    constructor(public authService: AuthService,private toaster: ToastrService,public router: Router,public service: TableService, private requestapi:ApiRequestService,private http: HttpClient,public datePipe:DatePipe) { }

    emailId:any;
    ngOnInit(): void {
        this.requestapi.changeParam(null);
        this.emailId=localStorage.getItem('email')
        if(this.emailId==null){
            localStorage.clear();
            this.router.navigate(['/auth/login']);
        }
        this.getDocumentType();
        this.getAnchorDetails();
        this.anchorCount = 0;
        this.counterPartyCount = 0;
    }

//DMS
documentMaster:Array<any> = [];
docReports:Array<any> = [];

getDocumentType(): void{
    let response;
    let fileName="dms/documentType";
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        console.log("documentMaster",res);
        this.documentMaster = res;
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

getDocumentReports(id,type): void{
    this.showLoader = true;
    let response;
    let fileName="dms/getRenewalEnhancementDocReports?custId="+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.showLoader = false;
        console.log("Report",res.documentReports);
        if(res.documentReports.length>0){
            if(type == 1){
                this.anchorReport = res.documentReports;
                this.anchorDetails = false;
                this.anchorViewDocument = true;
                console.log("this.anchorReport",this.anchorReport);
            }else if(type == 2){
              this.counterPartyReport = res.documentReports;
              this.counterPartyDetails = false;
              this.counterPartyViewDocument = true;
                console.log("this.counterPartyReport",this.counterPartyReport);
            }
        }
    },error=>{
        this.showLoader = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        this.anchorReport = null;
        this.counterPartyReport = null;
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
        }, error: function (error) {
        }
    });
}

downloadOtherDocument(appId,docMainName,docSubMainName,otherDocName,fileName){
    let response;
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
        }, error: function (error) {

        }
    });
}

//Anchor Documents
public anchorDetails = false;
public anchorViewDocument = false;

anchorList: Array<any> = [];
anchorReport:Array<any> = [];
anchorMasterList : Array<any> = [];

viewAnchorDocument(id){
    this.anchorCounterParty=false;
    this.anchorDetails = false;
    this.anchorReport = [];
    this.getDocumentReports(id,1);
}

getAnchorDetails(): void {
    this.showLoader = true;
    let response;
    let fileName="wfApprovalStatus/onBoardedCustomers/1";
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.getCounterPartyDetails();
        this.anchorList = res;
        this.anchorCount = res.length;
        this.anchorMasterList = res;
    },error=>{
        this.showLoader = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

anchorSearch(event: any): void {
    let key = event.target.value;
    if (event.target.value != '') {
        key = key.toLowerCase();
        this.anchorList = this.anchorMasterList.filter(item => (item.appId.customerInfoEntity.customerName.toLowerCase().includes(key)) ||
                          (item.appId.customerInfoEntity.pan.toLowerCase().includes(key)) || (item.appId.customerInfoEntity.cin ? item.appId.customerInfoEntity.cin.toLowerCase().includes(key):'')
                          );
    }else{
        this.anchorList=this.anchorMasterList;
    }
}

//Counter Party Documents
public counterPartyDetails = false;
public counterPartyViewDocument = false;
public counterPartyDefDocView = false;

counterPartyList:Array<any> = [];
counterPartyMasterList:Array<any> = [];
counterPartyReport:Array<any> = [];

viewCounterPartyDocument(id){
    this.counterPartyReport = [];
    this.getDocumentReports(id,2);
    this.counterPartyDetails = false;
    this.counterPartyViewDocument = true;
}

getCounterPartyDetails(): void {
    this.showLoader = true;
    let response;
    let fileName="wfApprovalStatus/onBoardedCustomers/2";
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.showLoader = false;
        this.counterPartyList=res;
        this.counterPartyCount = res.length;
        this.counterPartyMasterList = res;
    },error=>{
        this.showLoader = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

counterPartySearch(event: any): void {
    let key = event.target.value;
    if (event.target.value != '') {
        key = key.toLowerCase();
        this.counterPartyList = this.counterPartyMasterList.filter(item => (item.appId.customerInfoEntity.customerName.toLowerCase().includes(key)) ||
                          (item.appId.customerInfoEntity.pan.toLowerCase().includes(key)) || (item.appId.customerInfoEntity.cin ? item.appId.customerInfoEntity.cin.toLowerCase().includes(key):'')
                          );
    }else{
        this.counterPartyList=this.counterPartyMasterList;
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

viewAnchorDetails():void{
    this.anchorDetails = true;
    this.anchorViewDocument = false;
    this.anchorList=this.anchorMasterList;
}

viewCpDetails():void{
     this.counterPartyDetails = true;
     this.counterPartyViewDocument = false;
     this.counterPartyList=this.counterPartyMasterList;
 }

customerDashboard():void{
    this.anchorCounterParty = true;
    this.anchorDetails = false;
    this.counterPartyDetails = false;
    this.anchorViewDocument = false;

}

}
