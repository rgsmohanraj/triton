import { Component, OnInit, QueryList, ViewChildren  } from '@angular/core';
import { Observable } from 'rxjs';
import { NgbdSortableHeader, SortEvent } from 'src/app/shared/directives/NgbdSortableHeader';
import { ApiRequestService } from 'src/app/shared/services/api-request.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/shared/services/firebase/auth.service';
import {delay} from 'rxjs/operators'
import { environment } from 'src/environments/environment';
import $ from "jquery";

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit {
@ViewChildren(NgbdSortableHeader) headers: QueryList<NgbdSortableHeader>;
refresh_token:any;
emailId:any;
showLoader: boolean = false;
  constructor(public authService: AuthService,private requestapi:ApiRequestService,
                                private toaster: ToastrService,private router: Router,private http: HttpClient  ) { }

   ngOnInit(): void {
    this.emailId=localStorage.getItem('email')
     if(this.emailId==null){
         localStorage.clear();
         this.router.navigate(['/auth/login']);
         }
    }

//     getDailyReport():void{
//
//     this.showLoader = true;
//       let response;
//       let fileName="reports/fullReport";
//       response = this.http.get(environment.baseUrl+fileName, { responseType: 'blob' });
//       response.subscribe((data: any) => {
//       this.showLoader = false;
//       const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
//
//             const fileName = 'daily_report.xls';
//
//             const a = document.createElement('a');
//             a.href = window.URL.createObjectURL(blob);
//             a.download = fileName;
//
//             document.body.appendChild(a);
//             a.click();
//       },error=>{
//       this.showLoader = false;
//       if(error.status==401){
//                            this.refresh_token=localStorage.getItem('refresh_token')
//                                    this.authService.SignOut(this.refresh_token);
//                           }
//
//       })
//     }

}
