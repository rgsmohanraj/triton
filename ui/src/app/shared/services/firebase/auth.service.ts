import { Injectable, NgZone } from '@angular/core';
import * as auth from 'firebase/auth';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import {
  AngularFirestore,
  AngularFirestoreDocument,
} from '@angular/fire/compat/firestore';
import { Router } from '@angular/router';
import { ApiRequestService } from 'src/app/shared/services/api-request.service';
import { ToastrService } from 'ngx-toastr';
import { CookieService } from 'ngx-cookie-service';

export interface User {
  uid: string;
  email: string;
  displayName: string;
  photoURL: string;
  emailVerified: boolean;
}

@Injectable({
  providedIn: 'root',
})

export class AuthService {
  public showLoader: boolean = false;
  userData: any; // Save logged in user data
  constructor(
  private requestapi:ApiRequestService,
    public afs: AngularFirestore, // Inject Firestore service
    public afAuth: AngularFireAuth, // Inject Firebase auth service
    public router: Router,
    public ngZone: NgZone, // NgZone service to remove outside scope warning
    private toaster: ToastrService,
    private cookieService: CookieService
  ) {
    /* Saving user data in localstorage when 
    logged in and setting up null when logged out */
    this.afAuth.authState.subscribe((user) => {
      if (user) {
        this.userData = user;
        localStorage.setItem('user', JSON.stringify(this.userData));
        JSON.parse(localStorage.getItem('user')!);
      } else {
        localStorage.setItem('user', 'null');
        JSON.parse(localStorage.getItem('user')!);
      }
    });
  }
  // Sign in with email/password
  SignIn(email: string, password: string) {
    return this.afAuth
      .signInWithEmailAndPassword(email, password)
      .then((result) => {
        this.ngZone.run(() => {
          this.showLoader = true;
          this.router.navigate(['/dashboard/default']);
        });
        this.SetUserData(result.user);
      })
      .catch((error) => {
        this.showLoader = false;
        window.alert(error.message);
      });
  }
  // Sign up with email/password
  SignUp(email: string, password: string) {
    return this.afAuth
      .createUserWithEmailAndPassword(email, password)
      .then((result) => {
        /* Call the SendVerificaitonMail() function when new user sign 
        up and returns promise */
        this.SendVerificationMail();
        this.SetUserData(result.user);
      })
      .catch((error) => {
        window.alert(error.message);
      });
  }
  // Send email verfificaiton when new user sign up
  SendVerificationMail() {
    return this.afAuth.currentUser
      .then((u: any) => u.sendEmailVerification())
      .then(() => {
        this.router.navigate(['verify-email-address']);
      });
  }
  // Reset Forggot password
  ForgotPassword(passwordResetEmail: string) {
    return this.afAuth
      .sendPasswordResetEmail(passwordResetEmail)
      .then(() => {
        window.alert('Password reset email sent, check your inbox.');
      })
      .catch((error) => {
        window.alert(error);
      });
  }
  // Returns true when user is looged in and email is verified
  get isLoggedIn(): boolean {
    const user = JSON.parse(localStorage.getItem('user')!);
    return user !== null && user.emailVerified !== false ? true : false;
  }
  // Sign in with Google
  GoogleAuth() {
    console.log("res 100 --")
    return this.AuthLogin(new auth.GoogleAuthProvider()).then((res: any) => {
      if (res) {
        console.log("res 102", res)
        this.router.navigate(['/dashboard/default']);
      }
    });
  }

  signInFacebok() {
    return this.AuthLogin(new auth.FacebookAuthProvider()).then((res: any) => {
      if (res) {
        this.router.navigate(['/dashboard/default']);
      }
    });
  }

  signInTwitter() {
    return this.AuthLogin(new auth.TwitterAuthProvider()).then((res: any) => {
      if (res) {
        this.router.navigate(['/dashboard/default']);
      }
    });
  }
  // Auth logic to run auth providers
  AuthLogin(provider: any) {
    return this.afAuth
      .signInWithPopup(provider)
      .then((result) => {
        console.log("res 129", result.additionalUserInfo.profile)
        console.log("res 130", result.user)

        this.ngZone.run(() => {
          this.router.navigate(['/dashboard/default']);
        });
        this.SetUserData(result.user);
      })
      .catch((error) => {
        window.alert(error);
      });
  }
  /* Setting up user data when sign in with username/password, 
  sign up with username/password and sign in with social auth  
  provider in Firestore database using AngularFirestore + AngularFirestoreDocument service */
  SetUserData(user: any) {
    const userRef: AngularFirestoreDocument<any> = this.afs.doc(
      `users/${user.uid}`
    );
    const userData: User = {
      uid: user.uid,
      email: user.email,
      displayName: user.displayName,
      photoURL: user.photoURL,
      emailVerified: user.emailVerified,
    };
    return userRef.set(userData, {
      merge: true,
    });
  }
  // Sign out
  SignOut(refresh_token) {
    return this.afAuth.signOut().then(() => {
      let response;
                 let fileName="auth/logout";
                  let data={ token:refresh_token    }
                    response = this.requestapi.postData(fileName,JSON.stringify(data));
                  response.subscribe((res: any) => {
                  console.log(res,"res");

                  if(res.message=='Logged out successfully'){
                  localStorage.clear();
                        localStorage.removeItem('username');
                        localStorage.removeItem('email');
                        localStorage.removeItem('refresh_token');
                        localStorage.removeItem('access_token');
                        this.showLoader = false;
                                  this.router.navigate(['/auth/login']);
                              }
                              else{
                              this.showLoader = false;
                                  this.toaster.error(res.message)
                              }
                  },error=>{

                  })

    });
  }
}