import { Action } from '@ngrx/store';
import { Notification } from '../model/notification.model'; 

export const CREATE_NOTIFICATION = 'CREATE_NOTIFICATION';
export const REMOVE_NOTIFICATION = 'REMOVE_NOTIFICATION';

export class CreateNotification implements Action {
    readonly type = CREATE_NOTIFICATION;
  
    constructor(public payload: Notification) {}
}
  
export class RemoveNotification implements Action {
  readonly type = REMOVE_NOTIFICATION;

  constructor(public creationTimeInMilliseconds: number) {}
}

export type NotificationActions = CreateNotification | RemoveNotification;
