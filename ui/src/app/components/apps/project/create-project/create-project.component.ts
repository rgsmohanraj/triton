import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.component.html',
  styleUrls: ['./create-project.component.scss']
})
export class CreateProjectComponent implements OnInit {

  files: File[] = [];

  onSelect(event) {
    this.files.push(...event.addedFiles);
  }

  constructor() { }

  ngOnInit() {
  }

}
