import { Notification } from '../model/notification.model';
import * as NotificationActions from './notification.actions';

const initialState: Notification[] = [];

export function notificationsReducer(notificationsState = initialState, action: NotificationActions.NotificationActions): Notification[] {
  switch (action.type) {
    case (NotificationActions.CREATE_NOTIFICATION):
      return [...notificationsState, action.payload ];
    case (NotificationActions.REMOVE_NOTIFICATION):
      for(let i=0; i < notificationsState.length; i++){
        let notification: Notification  = notificationsState[i];
        if(notification.creationTimeInMilliseconds === action.creationTimeInMilliseconds){          
          notificationsState.splice(i,1);
          return notificationsState;
        }
      }
      return notificationsState;
    default:
      return notificationsState;
  }
}
