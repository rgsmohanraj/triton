import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BasicComponent } from './bootstrap-tables/basic/basic.component';
import { SizingComponent } from './bootstrap-tables/sizing/sizing.component';
import { BorderComponent } from './bootstrap-tables/border/border.component';
import { StylingComponent } from './bootstrap-tables/styling/styling.component';
import { TableComponentsComponent } from './bootstrap-tables/table-components/table-components.component';
import { BasicDataTableComponent } from './data-tables/basic-data-table/basic-data-table.component'

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: "bootstrap-tables",
        children: [
          {
            path: 'basic',
            component: BasicComponent
          },
          {
            path: 'sizing',
            component: SizingComponent
          },
          {
            path: 'border',
            component: BorderComponent
          },
          {
            path: 'styling',
            component: StylingComponent
          },
          {
            path: 'table-components',
            component: TableComponentsComponent
          },
        ]
      },
      {
        path: "datatable",
        component: BasicDataTableComponent
      }
    ]
  }
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TableRoutingModule { }
