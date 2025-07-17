import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { QuickViewComponent } from "../quick-view/quick-view.component";
import * as feather from 'feather-icons';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {

  @Input('icon') public icon;

  public openSidebar: boolean = false;
  public listView: boolean = false;
  public col: string = '3';
  
  @ViewChild("quickView") QuickView: QuickViewComponent;

  constructor() { }

  ngOnInit() {
    setTimeout(() => {
      feather.replace();
    });
  }

  sidebarToggle() {
    this.openSidebar = !this.openSidebar;
    this.col = '3';
  }

  toggleListView(val) {
    this.listView = val;
  }

  gridColumn(val) {
    this.col = val;
  }

}
