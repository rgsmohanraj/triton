import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiRequestService } from 'src/app/shared/services/api-request.service';

@Component({
  selector: 'app-collection-details',
  templateUrl: './collection-details.component.html',
  styleUrls: ['./collection-details.component.scss']
})
export class CollectionDetailsComponent implements OnInit {

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
