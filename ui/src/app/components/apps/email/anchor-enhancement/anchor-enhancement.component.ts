import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiRequestService } from 'src/app/shared/services/api-request.service';

@Component({
  selector: 'app-anchor-enhancement',
  templateUrl: './anchor-enhancement.component.html',
  styleUrls: ['./anchor-enhancement.component.scss']
})
export class AnchorEnhancementComponent implements OnInit {

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
