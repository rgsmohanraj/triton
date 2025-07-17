import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { AuthService } from '../../shared/services/firebase/auth.service';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';
import {  HttpClient,  HttpHeaders , HttpErrorResponse,HttpParams} from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { CookieService } from 'ngx-cookie-service';
import { ApiRequestService } from 'src/app/shared/services/api-request.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public show: boolean = false;
  public loginForm: FormGroup;
  public errorMessage: any;
  browserId:any;
showLoader: boolean = false;
  constructor(private requestapi:ApiRequestService,private cookieService: CookieService,public router: Router,private fb: FormBuilder,public authService: AuthService,private http: HttpClient, private toaster: ToastrService) {
    this.loginForm = this.fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required]

    });
  }

  ngOnInit() {
  this.requestapi.changeParam(null);
  this.browserId = this.cookieService.get('browserId');
  if(this.browserId){
  this.userValidateFun();
  }
  }

  showPassword() {
    this.show = !this.show;
  }

  // Login With Google
  loginGoogle() {
    this.authService.GoogleAuth();
  }

  // // Login With Twitter
  loginTwitter(): void {
    this.authService.signInTwitter();
  }

  // Login With Facebook
  loginFacebook() {
    this.authService.signInFacebok();
  }

  // Simple Login
  login() {
this.showLoader = true;
let response;
let fileName="doLogin";
  let data={ username:this.loginForm.value.email,
              password:this.loginForm.value.password
            }
  response = this.requestapi.loginPost(fileName,JSON.stringify(data));
             response.subscribe((res: any) => {
             this.showLoader = false;
            if(res.status==true){
            localStorage.setItem('username',res.name)
            localStorage.setItem('email',res.email)
            this.cookieService.set('browserId', res.browserId,365);
            localStorage.setItem('refresh_token',res.refresh_token);
            localStorage.setItem('access_token',res.access_token);
            localStorage.setItem('roles',res.resource_access.ThinkIAM.roles);
            if(res.lead.length){
            localStorage.setItem('lead',res.lead);
            }
            let access_token=localStorage.getItem('access_token');
            let role=localStorage.getItem('roles');
                for(let item of role.split(",")){
                    if(item == 'CPA' || item == 'CPA_LEAD' || item == 'ANCHOR_CPA_LEAD'){
                        this.router.navigate(['/email/anchor-details']);
                        break;
                    }else if(item == 'BUSINESS' || item == 'BUSINESS_LEAD' || item == 'CP_BUSINESS_LEAD'){
                        this.router.navigate(['/form/counter-party-details']);
                        break;
                    }else{
                        this.router.navigate(['/email/anchor-details']);
                    }
                }
                this.toaster.success(res.message)
            }
            else{
                this.toaster.error(res.message)
            }
            },error=>{
            this.showLoader = false;
            this.toaster.error('Invalid Login Credentials')
            })
  }

userValidateFun(){
this.showLoader = true;
let response;
           let fileName="token/validatedUser/"+this.browserId;
            response = this.requestapi.getLoginData(fileName);
            response.subscribe((res: any) => {
this.showLoader = false;
            if(res.status==true){
                        localStorage.setItem('username',res.name)
                        localStorage.setItem('email',res.email)
                        localStorage.setItem('refresh_token',res.refresh_token)
                        localStorage.setItem('access_token',res.access_token)
                        let refresh_token=localStorage.getItem('refresh_token')
                            this.router.navigate(['/email/anchor-details']);
                        }
                        else{
                            this.toaster.error(res.message)
                        }
            },error=>{
            this.showLoader = false;
            })
}
}
