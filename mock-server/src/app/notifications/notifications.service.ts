import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';

import * as NotificationActions from './store/notification.actions';

@Injectable({
  providedIn: 'root'
})
export class NotificationsService {

  constructor(private store: Store<Notification[]>) { }

  removeNotificationAfterDelay(creationTimeInMilliseconds: number, delay: number){
    setTimeout(()=>{
      this.store.dispatch(new NotificationActions.RemoveNotification(creationTimeInMilliseconds));
    }, delay);
  }

}
