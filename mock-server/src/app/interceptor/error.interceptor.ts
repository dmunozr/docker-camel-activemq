import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { Observable } from 'rxjs';
import {tap, catchError} from 'rxjs/operators';
import {Store} from '@ngrx/store';
import { Notification } from '../notifications/model/notification.model';
//import { of } from 'rxjs/observable/of';

import * as NotificationActions from '../notifications/store/notification.actions';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private notificationStore: Store<Notification[]>) {}

    /**
     * @param HttpRequest<any> request - The intercepted request
     * @param HttpHandler next - The next interceptor in the pipeline
     * @return Observable<HttpEvent<any>>
     */
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req)
                .pipe(
                    tap(
                        event => {}, 
                        err => {
                            if (err instanceof HttpErrorResponse && (err.status === 0 || err.status === 404 || err.status === 500)) {
                                console.log('Unexpected HTTP error!! ' + err);
                                /*
                                const notification: Notification = new Notification('Unexpected errors interceptor!!', 'danger');
                                this.notificationStore.dispatch(new NotificationActions.CreateNotification(notification))
                                */
                            }
                        }
                    )
                );
    }
}
