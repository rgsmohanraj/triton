import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { Ng5SliderModule } from 'ng5-slider';
import { NgxDropzoneModule } from 'ngx-dropzone';
import { DropzoneConfigInterface, DropzoneModule, DROPZONE_CONFIG } from 'ngx-dropzone-wrapper';
import { ImageCropperModule } from 'ngx-image-cropper';
import { JoyrideModule } from "ngx-joyride";
import { SwiperModule } from 'ngx-swiper-wrapper';
import { SWIPER_CONFIG } from 'ngx-swiper-wrapper';
import { SwiperConfigInterface } from 'ngx-swiper-wrapper';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { PerfectScrollbarConfigInterface, PerfectScrollbarModule, PERFECT_SCROLLBAR_CONFIG } from 'ngx-perfect-scrollbar';
import { SharedModule } from '../../../shared/shared.module';
import { AdvanceRoutingModule } from './advance-routing.module';

import { DropzoneComponent } from './dropzone/dropzone.component';
import { ImageCroperComponent } from './image-croper/image-croper.component';
import { OwlCarouselComponent } from './owl-carousel/owl-carousel.component';
import { RangeSliderComponent } from './range-slider/range-slider.component';
import { ScrollableComponent } from './scrollable/scrollable.component';
import { StickyComponent } from './sticky/sticky.component';
import { SweetAlert2Component } from './sweet-alert2/sweet-alert2.component';

const DEFAULT_SWIPER_CONFIG: SwiperConfigInterface = {
  direction: 'horizontal',
  slidesPerView: 'auto'
};

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: false,
};

const DEFAULT_DROPZONE_CONFIG: DropzoneConfigInterface = {
  // Change this to your upload POST address:
  url: 'https://httpbin.org/post',
  acceptedFiles: 'image/*',
  createImageThumbnails: true
};

@NgModule({
  declarations: [
    ScrollableComponent,
    DropzoneComponent,
    SweetAlert2Component,
    RangeSliderComponent,
    StickyComponent,
    OwlCarouselComponent,
    ImageCroperComponent,
  ],
  imports: [
    CommonModule,
    AdvanceRoutingModule,
    SwiperModule,
    NgxDropzoneModule,
    SweetAlert2Module,
    PerfectScrollbarModule,
    CarouselModule,
    Ng5SliderModule,
    FormsModule,
    ReactiveFormsModule,
    DropzoneModule,
    NgbModule,
    SharedModule,
    ImageCropperModule,
    JoyrideModule,
  ],
  providers: [
    {
      provide: SWIPER_CONFIG,
      useValue: DEFAULT_SWIPER_CONFIG
    },
    { provide: PERFECT_SCROLLBAR_CONFIG, useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG },
    { provide: DROPZONE_CONFIG, useValue: DEFAULT_DROPZONE_CONFIG },
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA]
})
export class AdvanceModule { }
