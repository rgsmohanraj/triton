import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DefaultComponent } from './default/default.component';
import { EcommerceComponent } from './ecommerce/ecommerce.component';
import { InboxComponent } from './inbox/inbox.component';
import { VersionDetailsComponent } from './version-details/version-details.component';
import { AssignmentComponent } from './assignment/assignment.component';
import { ReportComponent } from './report/report.component';


const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'default',
        component: DefaultComponent
      },
      {
        path:'ecommerce',
        component:EcommerceComponent
      },
      {
              path:'inbox',
              component:InboxComponent
            },
      {
                    path:'report',
                    component:ReportComponent
                  },
                  {
                          path:'version',
                          component:VersionDetailsComponent
                        },
            {
                              path:'assignment',
                              component:AssignmentComponent
                            }
    ],
  }
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
