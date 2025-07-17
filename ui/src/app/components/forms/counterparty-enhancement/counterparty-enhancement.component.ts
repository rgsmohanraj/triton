import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiRequestService } from 'src/app/shared/services/api-request.service';

@Component({
  selector: 'app-counterparty-enhancement',
  templateUrl: './counterparty-enhancement.component.html',
  styleUrls: ['./counterparty-enhancement.component.scss']
})
export class CounterpartyEnhancementComponent implements OnInit {

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
