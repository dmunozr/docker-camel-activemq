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
    /*
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request)
            .pipe(
                catchError(
                    (error: any, caught: Observable<HttpEvent<any>>) => {
                        if (error.status === 401) {
                            this.handleAuthError();
                            // if you've caught / handled the error, you don't
                            // want to rethrow it unless you also want
                            // downstream consumers to have to handle it as
                            // well.
                            return of(error);
                        }
                        throw error;
                    }
                ),
            );
    }
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