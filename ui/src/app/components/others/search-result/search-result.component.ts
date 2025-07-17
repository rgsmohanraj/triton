import { Component, OnInit, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class SearchResultComponent implements OnInit {

  public active1 = 1;
  
  constructor() { }

  ngOnInit() { }

}
