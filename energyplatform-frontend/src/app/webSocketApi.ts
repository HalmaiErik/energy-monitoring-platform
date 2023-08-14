import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { environment } from '../environments/environment';
import { ToastrService } from 'ngx-toastr';
import { LogAlert } from './model/logAlert';
import { Injectable } from '@angular/core';
import { UserService } from './service/user.service';

@Injectable({
    providedIn: 'root'
})
export class WebSocketApi {

    webSocketEndPoint: string = `${environment.webSocketUrl}`;
    topic: string = "/log-alert/get";
    stompClient: any;
    
    constructor(private toastr: ToastrService, private userService: UserService) {}

    _connect() {
        console.log("Listening on websocket");
        let ws = new SockJS(this.webSocketEndPoint);
        this.stompClient = Stomp.over(ws);
        const _this = this;
        _this.stompClient.connect({}, function (frame) {
            _this.stompClient.subscribe(_this.topic, function (msg) {
                let a = JSON.parse(msg.body);
                if (a.username == _this.userService.getUsername()) {
                    _this.toastr.warning(a.deviceName + " has an power peak of " + a.energyConsumption);
                }
            });
        }, this.errorCallBack);
    };

    _disconnect() {
        if (this.stompClient !== null) {
            this.stompClient.disconnect();
        }
        console.log("Disconnected");
    }

    errorCallBack(error) {
        console.log("errorCallBack -> " + error)
        setTimeout(() => {
            console.log("Error occured. Attempting reconnection!");
            this._connect();
        }, 5000);
    }
}