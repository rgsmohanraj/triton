import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import {  HttpClient,  HttpHeaders , HttpErrorResponse,HttpParams} from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { BehaviorSubject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class ApiRequestService {
private paramSource1 = new BehaviorSubject<{ id: any, status: any }>({ id: null, status: null });
private paramSource = new BehaviorSubject(null);
private reParamSource = new BehaviorSubject(null);
  sharedParam = this.paramSource.asObservable();

     constructor(private http: HttpClient  ) { }

public loginPost(filename,param){
    let access_token=localStorage.getItem('access_token');
    const httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json',
            'SECRET-KEY':'7XkwJiMfgOg5oemHAzRaUf54BfLsmfK9',
        })
    };
    let baseUrl=environment.baseUrl+filename;
    return this.http.post(baseUrl, param, httpOptions);
}

public postData(filename,param){
    let access_token=localStorage.getItem('access_token');
    const httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json',
            'SECRET-KEY':'7XkwJiMfgOg5oemHAzRaUf54BfLsmfK9',
            'Authorization':access_token
        })
    };
    let baseUrl=environment.baseUrl+filename;
    return this.http.post(baseUrl, param, httpOptions);
}

  public getData(filename){
  let access_token=localStorage.getItem('access_token')
        const httpOptions = {
          headers: new HttpHeaders({
            'Content-Type': 'application/json',
             'SECRET-KEY':'7XkwJiMfgOg5oemHAzRaUf54BfLsmfK9',
             'Authorization':access_token
          })
        };
        let baseUrl=environment.baseUrl+filename;
        return this.http.get(baseUrl, httpOptions);
  }

  public getLoginData(filename){
          const httpOptions = {
            headers: new HttpHeaders({
              'Content-Type': 'application/json',
               'SECRET-KEY':'7XkwJiMfgOg5oemHAzRaUf54BfLsmfK9',
            })
          };
          let baseUrl=environment.baseUrl+filename;
          return this.http.get(baseUrl, httpOptions);
    }

  public postFileData(filename,param){
  let access_token=localStorage.getItem('access_token')
    const httpOptions = {
      headers: new HttpHeaders({
        // 'Content-Type': 'application/x-www-form-urlencoded'
        'SECRET-KEY':'7XkwJiMfgOg5oemHAzRaUf54BfLsmfK9',
        'Authorization':access_token
      })
    };
    let baseUrl=environment.baseUrl+filename;
    return this.http.post(baseUrl, param, httpOptions);
  }

  public putData(filename){
    let access_token=localStorage.getItem('access_token')
          const httpOptions = {
            headers: new HttpHeaders({
              'Content-Type': 'application/json',
               'SECRET-KEY':'7XkwJiMfgOg5oemHAzRaUf54BfLsmfK9',
               'Authorization':access_token
            })
          };
          let baseUrl=environment.baseUrl+filename;
          return this.http.put(baseUrl, httpOptions);
    }

    public putDataParam(filename,param){
    let access_token=localStorage.getItem('access_token')
          const httpOptions = {
            headers: new HttpHeaders({
              'Content-Type': 'application/json',
               'SECRET-KEY':'7XkwJiMfgOg5oemHAzRaUf54BfLsmfK9',
               'Authorization':access_token
            })
          };
          let baseUrl=environment.baseUrl+filename;
          return this.http.put(baseUrl, param, httpOptions);
    }

  public deleteData(filename){
  let access_token=localStorage.getItem('access_token')
        const httpOptions = {
          headers: new HttpHeaders({
            'Content-Type': 'application/json',
             'SECRET-KEY':'7XkwJiMfgOg5oemHAzRaUf54BfLsmfK9',
             'Authorization':access_token
          })
        };
        let baseUrl=environment.baseUrl+filename;
        return this.http.delete(baseUrl, httpOptions);
  }

//   public deleteData(filename){
//     let access_token=localStorage.getItem('access_token')
//       const httpOptions = {
//         headers: new HttpHeaders({
//            'Content-Type': 'application/json',
//           'SECRET-KEY':'7XkwJiMfgOg5oemHAzRaUf54BfLsmfK9',
//           'Authorization':access_token
//         })
//       };
//       let baseUrl=environment.baseUrl+filename;
//       return this.http.delete(baseUrl, httpOptions);
//     }

    changeParam1(id: any, status: any) {
        this.paramSource.next({ id, status });
    }
    getData1() {
        return this.paramSource.asObservable();
    }
    clearData(){
        this.paramSource.next(null);
    }
    changeParam(param: any) {
        this.paramSource.next(param)
    }

    //Renewal / Enhancement
    setRenewalEnhancementParams(appId: any, custId: any, renewalEnhancement: any, appStatus: any,deferralStatus: any) {
        this.reParamSource.next({ appId, custId, renewalEnhancement, appStatus, deferralStatus });
    }
    getRenewalEnhancementParams() {
        return this.reParamSource.asObservable();
    }
    clearRenewalEnhancementParams(){
        this.reParamSource.next(null);
    }
    changeRenewalEnhancementParam(param: any) {
        this.reParamSource.next(param)
    }
}
