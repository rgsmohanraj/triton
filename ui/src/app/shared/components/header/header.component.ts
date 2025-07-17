import { Component, OnInit, Inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { NavService } from '../../services/nav.service';
import { LayoutService } from '../../services/layout.service';
import { ApiRequestService } from 'src/app/shared/services/api-request.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/shared/services/firebase/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
tableData:any;
emailId:any;
  public elem: any;
  public dark: boolean = this.layout.config.settings.layout_version == 'dark-only' ? true : false;
refresh_token:any;
  constructor(public authService: AuthService,public layout: LayoutService,
    public navServices: NavService, private requestapi:ApiRequestService,public router: Router,
    @Inject(DOCUMENT) private document: any
  ) {
  }
showLoader: boolean = false;

  ngOnInit() {
    this.elem = document.documentElement;
    this.emailId=localStorage.getItem('email')
    this.getPending();
  }

getPending():void {
this.showLoader = true;
  let response;
  let fileName="wfApprovalStatus/getFinalWFStatus/"+this.emailId;
  response = this.requestapi.getData(fileName);
  response.subscribe((res: any) => {
  this.showLoader = false;
  if(res.status==true){
  this.tableData=res.wfTableDataList.length;
}
  },error=>{
  this.showLoader = false;
  if(error.status==401){
                   this.refresh_token=localStorage.getItem('refresh_token')
                           this.authService.SignOut(this.refresh_token);
                     }

  })
}

  sidebarToggle() {
    this.navServices.collapseSidebar = !this.navServices.collapseSidebar;
    this.navServices.megaMenu  = false;
    this.navServices.levelMenu = false;
  }

  layoutToggle() {
    this.dark = !this.dark;
    this.layout.config.settings.layout_version = this.dark ? 'dark-only' : 'light';
  }

  searchToggle() {
    this.navServices.search = true;
  }

  languageToggle() {
    this.navServices.language = !this.navServices.language;
  }

  toggleFullScreen() {
    this.navServices.fullScreen = !this.navServices.fullScreen;
    if (this.navServices.fullScreen) {
      if (this.elem.requestFullscreen) {
        this.elem.requestFullscreen();
      } else if (this.elem.mozRequestFullScreen) {
        /* Firefox */
        this.elem.mozRequestFullScreen();
      } else if (this.elem.webkitRequestFullscreen) {
        /* Chrome, Safari and Opera */
        this.elem.webkitRequestFullscreen();
      } else if (this.elem.msRequestFullscreen) {
        /* IE/Edge */
        this.elem.msRequestFullscreen();
      }
    } else {
      if (!this.document.exitFullscreen) {
        this.document.exitFullscreen();
      } else if (this.document.mozCancelFullScreen) {
        /* Firefox */
        this.document.mozCancelFullScreen();
      } else if (this.document.webkitExitFullscreen) {
        /* Chrome, Safari and Opera */
        this.document.webkitExitFullscreen();
      } else if (this.document.msExitFullscreen) {
        /* IE/Edge */
        this.document.msExitFullscreen();
      }
    }
  }


}
