import { Injectable } from '@angular/core';
import { Actions, Effect } from '@ngrx/effects';
import { tap } from 'rxjs/operators';

import * as NotificationActions from './notification.actions';
import { NotificationsService } from '../notifications.service';

const TIME_TO_SHOW_NOTIFICATION: number = 2000;

@Injectable()
export class NotificationEffects {

  constructor(private actions$: Actions,
              private notificationsService: NotificationsService) {
  }

  @Effect({dispatch: false})
  notificationCreation = this.actions$
    .ofType(NotificationActions.CREATE_NOTIFICATION)
    .pipe(
      tap((notification: NotificationActions.CreateNotification) => {
        this.notificationsService.removeNotificationAfterDelay(notification.payload.creationTimeInMilliseconds, TIME_TO_SHOW_NOTIFICATION);
      })
    );

}
