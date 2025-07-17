import { Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { Observable } from 'rxjs';
import { NgbdSortableHeader, SortEvent } from 'src/app/shared/directives/NgbdSortableHeader';
import { ApiRequestService } from 'src/app/shared/services/api-request.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/shared/services/firebase/auth.service';

@Component({
  selector: 'app-version-details',
  templateUrl: './version-details.component.html',
  styleUrls: ['./version-details.component.scss']
})
export class VersionDetailsComponent implements OnInit {
@ViewChildren(NgbdSortableHeader) headers: QueryList<NgbdSortableHeader>;
emailId:any;
tableData:[]=[];
refresh_token:any;
showLoader: boolean = false;

onSort({ column, direction }: SortEvent) {

     // resetting other headers
     this.headers.forEach((header) => {
       if (header.sortable !== column) {
         header.direction = '';
       }
     });
   }
  constructor(public authService: AuthService,private requestapi:ApiRequestService,
                                private toaster: ToastrService,private router: Router) { }

  ngOnInit(): void {
  this.requestapi.changeParam(null);
   this.emailId=localStorage.getItem('email')
     if(this.emailId==null){
         localStorage.clear();
         this.router.navigate(['/auth/login']);
         }
         this.getVersionDetails();
  }

  getVersionDetails():void {
this.showLoader = true;
    let response;
    let fileName="anchor/tritonVersions";
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
    this.showLoader = false;
    if(res){
    this.tableData=res.content;
  }
    },error=>{
    this.showLoader = false;
    if(error.status==401){
                        this.refresh_token=localStorage.getItem('refresh_token')
                                this.authService.SignOut(this.refresh_token);
                        }

    })
  }

}
