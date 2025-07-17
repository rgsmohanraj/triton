import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/shared/services/firebase/auth.service';

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.scss']
})
export class MyAccountComponent implements OnInit {
refresh_token:any;
  public userName: string;
  public profileImg: 'assets/images/dashboard/profile.jpg';

  constructor(public authService: AuthService) {
//     console.log("12 localstorage", JSON.parse(localStorage.getItem('user')))

    if (JSON.parse(localStorage.getItem('user'))) {
//       console.log("true");
    } else {
//       console.log("NO ");
    }

  }


  logoutFunc() {
  this.refresh_token=localStorage.getItem('refresh_token')
    this.authService.SignOut(this.refresh_token);
  }

  ngOnInit() {
   this.userName=localStorage.getItem('username')
  }

}
