import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbDateStruct, NgbCalendar } from '@ng-bootstrap/ng-bootstrap';
import { formatDate } from '@angular/common';

import {
  SwiperComponent, SwiperDirective, SwiperConfigInterface
} from 'ngx-swiper-wrapper';

@Component({
  selector: 'app-general',
  templateUrl: './general.component.html',
  styleUrls: ['./general.component.scss']
})
export class GeneralComponent implements OnInit {

  public show: boolean = true;

  public slides = [
    {
      id: 1,
      desc: "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia",
      img: "assets/images/dashboard/boy-2.png",
      name: "Poio klot",
      designation: "Developer"
    },
    {
      id: 1,
      desc: "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia",
      img: "assets/images/dashboard/boy-2.png",
      name: "Poio klot",
      designation: "Developer"
    }
  ];

  model: NgbDateStruct;
  date: { year: number, month: number };
  public intmin: any;
  public intsec: any;
  public inthour: any;
  public time: Date = new Date();
  public jstoday = '';
  today: number = Date.now();
  dates = new Date().getDate();
  month = new Date().getMonth();
  year = new Date().getFullYear();

  public type: string = 'component';

  public disabled: boolean = false;

  public config: SwiperConfigInterface = {
    slidesPerView: 'auto',
    keyboard: true,
    mousewheel: true,
    scrollbar: false,
    navigation: false,
    pagination: false
  };

  @ViewChild(SwiperComponent, { static: false }) componentRef?: SwiperComponent;
  @ViewChild(SwiperDirective, { static: false }) directiveRef?: SwiperDirective;

  constructor(private calender: NgbCalendar) {
    this.model = calender.getToday();
    this.jstoday = formatDate(this.time, 'dd-MM-yyyy hh:mm:ss a', 'en-US', '+0530');
  }

  public toggleSlidesPerView(): void {
    if (this.config.slidesPerView !== 1) {
      this.config.slidesPerView = 1;
    } else {
      this.config.slidesPerView = 2;
    }
  }

  // public onIndexChange(index: number): void {
  //   console.log('Swiper index: ', index);
  // }

  // public onSwiperEvent(event: string): void {
  //   console.log('Swiper event: ', event);
  // }

  // owlcarousel = [
  //   {
  //     id: 1,
  //     desc: "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia",
  //     img: "assets/images/dashboard/boy-2.png",
  //     name: "Poio klot",
  //     designation: "Developer"
  //   },
  //   {
  //     id: 1,
  //     desc: "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia",
  //     img: "assets/images/dashboard/boy-2.png",
  //     name: "Poio klot",
  //     designation: "Developer"
  //   }
  // ]

  // owlcarouselOptions = {
  //   loop: true,
  //   margin: 10,
  //   items: 1,
  //   nav: false,
  //   dots: false
  // };

  // slides = [
  //   {img: "http://placehold.it/350x150/000000"},
  //   {img: "http://placehold.it/350x150/111111"},
  //   {img: "http://placehold.it/350x150/333333"},
  //   {img: "http://placehold.it/350x150/666666"}
  // ];
  // slideConfig = {"slidesToShow": 4, "slidesToScroll": 4};

  // addSlide() {
  //   this.slides.push({img: "http://placehold.it/350x150/777777"})
  // }

  // removeSlide() {
  //   this.slides.length = this.slides.length - 1;
  // }

  // slickInit(e) {
  //   console.log('slick initialized');
  // }

  // breakpoint(e) {
  //   console.log('breakpoint');
  // }

  // afterChange(e) {
  //   console.log('afterChange');
  // }

  // beforeChange(e) {
  //   console.log('beforeChange');
  // }

  setTime() {
    this.intmin = setInterval(function () {
      var second = new Date().getSeconds();
      var sdegree = second * 6;
      var srotate = "rotate(" + sdegree + "deg)";
      var seconds = document.getElementById('sec').style.transform = srotate;
    }, 1000);
    this.intsec = setInterval(function () {
      var mins = new Date().getMinutes();
      var mdegree = mins * 6;
      var mrotate = "rotate(" + mdegree + "deg)";
      var minits = document.getElementById('min').style.transform = mrotate;
    }, 1000);
    this.inthour = setInterval(function () {
      var hour = new Date().getHours();
      var mints = new Date().getMinutes();
      var hdegree = hour * 30 + (mints / 2);
      var hrotate = "rotate(" + hdegree + "deg)";
      var hours = document.getElementById('hour').style.transform = hrotate;
    }, 1000);
  }

  ngOnInit() {
    const now: Date = new Date()
    this.setTime();
  }

  ngOnDestroy() {
    if (this.intmin) {
      clearInterval(this.intmin);
    }
    if (this.intsec) {
      clearInterval(this.intsec);
    }
    if (this.inthour) {
      clearInterval(this.inthour);
    }
  }
}	
