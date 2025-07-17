import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiRequestService } from 'src/app/shared/services/api-request.service';

@Component({
  selector: 'app-disbursement-details',
  templateUrl: './disbursement-details.component.html',
  styleUrls: ['./disbursement-details.component.scss']
})
export class DisbursementDetailsComponent implements OnInit {

  constructor(public router: Router, private requestapi:ApiRequestService) { }
  emailId:any;
    ngOnInit(): void {
    this.requestapi.changeParam(null);
    this.emailId=localStorage.getItem('email')
       if(this.emailId==null){
       localStorage.clear();
       this.router.navigate(['/auth/login']);
       }
    }

}
