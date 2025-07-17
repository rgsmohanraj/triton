import { Component, OnInit, QueryList, ViewChildren  } from '@angular/core';
import { Observable } from 'rxjs';
import { NgbdSortableHeader, SortEvent } from 'src/app/shared/directives/NgbdSortableHeader';
import { ApiRequestService } from 'src/app/shared/services/api-request.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/shared/services/firebase/auth.service';
import * as $ from 'jquery';

@Component({
  selector: 'app-assignment',
  templateUrl: './assignment.component.html',
  styleUrls: ['./assignment.component.scss']
})
export class AssignmentComponent implements OnInit {
refresh_token:any;

  @ViewChildren(NgbdSortableHeader) headers: QueryList<NgbdSortableHeader>;
  emailId:any;
  leadData:any;
  tableData:[]=[];
  viewData:[]=[];
  grpListData:[]=[];
  currentStageLead:any;
     onSort({ column, direction }: SortEvent) {

       // resetting other headers
       this.headers.forEach((header) => {
         if (header.sortable !== column) {
           header.direction = '';
         }
       });
     }
showLoader: boolean = false;

    constructor(public authService: AuthService,private requestapi:ApiRequestService,
                    private toaster: ToastrService,private router: Router) {
                    }

    ngOnInit(): void {
    this.displayMain=false;
    this.emailId=localStorage.getItem('email')
     if(this.emailId==null){
         localStorage.clear();
         this.router.navigate(['/auth/login']);
         }
     this.getPending();

     this.leadData=localStorage.getItem('lead');
    }
  getPending():void {
this.showLoader = true;
    let response;
    let fileName="wfApprovalStatus/getFinalWFStatusByLead/"+this.emailId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
    this.showLoader = false;
    if(res.status==true){
    this.tableData=res.wfTableDataList;
  }
    },error=>{
    this.showLoader = false;
    if(error.status==401){
                          this.refresh_token=localStorage.getItem('refresh_token')
                                  this.authService.SignOut(this.refresh_token);
                        }

    })
  }

userGrpData:any;
getDropdown():void {
this.showLoader = true;
    let response;
    let fileName="group/groupOfUsers/"+this.currentStageLead;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
    this.showLoader = false;
    this.grpListData=res.userDetails[0];

    },error=>{
    this.showLoader = false;
    if(error.status==401){
                          this.refresh_token=localStorage.getItem('refresh_token')
                                  this.authService.SignOut(this.refresh_token);
                        }

    })
  }

  appId:any;
  customerType:any;
 currentStage:any;
 customerName:any;
 id:any;
 nextStageId:any;
 remarks:any;
 status:any;
displayMain=false;



leadDetails(data){
this.appId=data.appId
this.customerType=data.customerType
this.currentStage=data.currentStage
this.customerName=data.customerName
this.id=data.id
this.currentStageLead=data.currentStageLead;
this.nextStageId=data.nextStageId
this.remarks=data.remarks
this.status=data.status
this.displayMain=true;
this.getDropdown();
}

tableDetails(){
this.displayMain=false;
}

assignFun(){
this.showLoader = true;
let response;
    let fileName="wfApprovalStatus/changeAssigne/"+this.id+"/"+this.userGrpData;
    response = this.requestapi.putData(fileName);
    response.subscribe((res: any) => {
    this.showLoader = false;
    this.displayMain=false;
    this.getPending();
    window.location.reload();
    },error=>{
    this.showLoader = false;
    if(error.status==401){
                          this.refresh_token=localStorage.getItem('refresh_token')
                                  this.authService.SignOut(this.refresh_token);
                        }

    })
}
}
