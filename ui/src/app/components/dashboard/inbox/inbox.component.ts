import { Component, OnInit, QueryList, ViewChildren  } from '@angular/core';
import { Observable } from 'rxjs';
import { NgbdSortableHeader, SortEvent } from 'src/app/shared/directives/NgbdSortableHeader';
import { ApiRequestService } from 'src/app/shared/services/api-request.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/shared/services/firebase/auth.service';
import {delay} from 'rxjs/operators'
@Component({
  selector: 'app-inbox',
  templateUrl: './inbox.component.html',
  styleUrls: ['./inbox.component.scss'],
})
export class InboxComponent implements OnInit {
@ViewChildren(NgbdSortableHeader) headers: QueryList<NgbdSortableHeader>;
emailId:any;
tableData:[]=[];
viewData:[]=[];
refresh_token:any;

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
  this.emailId=localStorage.getItem('email')
   if(this.emailId==null){
       localStorage.clear();
       this.router.navigate(['/auth/login']);
       }
   this.getPending();
  }
getPending():void {
this.showLoader = true;
  let response;
  let fileName="wfApprovalStatus/getFinalWFStatus/"+this.emailId;
  response = this.requestapi.getData(fileName).pipe(delay(2000));;
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

currentStepperFun(id,status){
  this.router.navigate(['/email/new-anchor']);
   this.requestapi.changeParam1(id, status);


}

currentStepperFunCp(id,status){
    this.router.navigate(['/form/new-counter']);
this.requestapi.changeParam1(id, status);
}

anchorRenewalEnhancementParams(appId,custId,reStatus,deferralStatus){
    this.router.navigate(['/email/AnchorRenewal']);
    this.requestapi.setRenewalEnhancementParams(appId,custId,reStatus,false,deferralStatus);
}

cpRenewalEnhancementParams(appId,custId,reStatus,deferralStatus){
    this.router.navigate(['/form/counter-party-renewal']);
    this.requestapi.setRenewalEnhancementParams(appId,custId,reStatus,false,deferralStatus);
}
}
