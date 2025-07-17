import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { WidgetsRoutingModule } from './widgets-routing.module';
import { ChartComponent } from './chart/chart.component';
import { GeneralComponent } from './general/general.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// import { CarouselModule } from 'ngx-owl-carousel-o';
import { ChartistModule } from 'ng-chartist';
import { NgChartsModule } from 'ng2-charts';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { CountToModule } from 'angular-count-to';
import { NgApexchartsModule } from 'ng-apexcharts';
import { SwiperModule } from 'ngx-swiper-wrapper';
import { SWIPER_CONFIG } from 'ngx-swiper-wrapper';
import { SwiperConfigInterface } from 'ngx-swiper-wrapper';

const DEFAULT_SWIPER_CONFIG: SwiperConfigInterface = {
  direction: 'horizontal',
  slidesPerView: 'auto'
};
@NgModule({
  declarations: [ChartComponent, GeneralComponent],
  imports: [
    CommonModule,
    WidgetsRoutingModule,
    NgbModule,
    CountToModule,
    SwiperModule,
    ChartistModule,
    NgChartsModule,
    FormsModule,
    SharedModule,
    NgApexchartsModule
  ],
  providers: [
    {
      provide: SWIPER_CONFIG,
      useValue: DEFAULT_SWIPER_CONFIG
    }
  ]
})
export class WidgetsModule { }
