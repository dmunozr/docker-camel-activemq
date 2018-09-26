import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';

import * as fromApp from '../store/app.reducers';
import { Notification } from '../notifications/model/notification.model';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {
  notifications: Observable<Notification[]>;

  constructor(private store: Store<fromApp.AppState>) {
  }

  ngOnInit() {
    this.notifications = this.store.select('notifications');
  }

}
