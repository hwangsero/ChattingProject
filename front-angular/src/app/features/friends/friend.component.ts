import {Component, OnInit, ViewChild} from '@angular/core';
import {AlertService} from '@app/services/ui/alert.service';
import {HomeListComponent} from '@app/features/home/modal/home-list.component';
import {JsonApiService} from '@app/services/json-api.service';

import {Router} from '@angular/router';
import {filter} from 'rxjs/operators';
import {HttpClient, HttpParams} from '@angular/common/http';
import {RxStompService} from '@stomp/ng2-stompjs';
import {UserService, WsService} from '@app/services';
import {UserTokenContain} from '@app/models/UserTokenContain';
import {com} from '@generate/models';
import User = com.clone.chat.domain.User;
import {BasicModalComponent, ButtonsClickType} from '@app/shareds/modals/basic-modal/basic-modal.component';
import Message = com.clone.chat.domain.Message;
import RequestMessage = com.clone.chat.controller.ws.messages.model.RequestMessage;
import {Observable} from "rxjs/index";

@Component({
    selector: 'app-home',
    templateUrl: './friend.component.html',
    styleUrls: ['./friend.component.css']
})
export class FriendComponent implements OnInit {

    @ViewChild('userModal') userModal: BasicModalComponent;
    @ViewChild('addFriendModal') addFriendModal: BasicModalComponent;

    private searchUserId: String;
    private choiceUser: User;
    private findUser: User;
    private friends: User[];
    private userMessage: Message[] = [];
    private userTokenContain: UserTokenContain;

    constructor(private wsService: WsService, private userService: UserService, private http: HttpClient, private alertService: AlertService, public router: Router, private api: JsonApiService) {
    }

    ngOnInit() {
        this.userService.user$.pipe(filter(it => it.authorities && it.authorities.length > 0)).subscribe((userTokenContain: UserTokenContain) => {
            this.userTokenContain = userTokenContain;
            this.wsService.watch<User[]>('/user/queue/friends').subscribe((wit) => {
                this.friends = wit;
            });

            this.wsService.watch<Message[]>('/user/queue/messages').subscribe((it) => {
                it.forEach(m => this.userMessage.push(m));
            });

            this.send();
        });
    }

    public send() {
        this.wsService.publish('/app/friends');
    }

    showUser(it: User) {
        this.userMessage = [];
        this.choiceUser = it;
        this.wsService.publish(`/app/messages/${this.choiceUser.id}`);
        this.userModal.show();
    }

    sendMessage(choiceUser: User, value: string) {
        const requestMsg = new RequestMessage();
        requestMsg.contents = value;
        this.wsService.publish(`/app/messages/${this.choiceUser.id}/send`, requestMsg);
    }

    public openAddFriendModal() {
        this.addFriendModal.show();
    }

    search(event) {
        if(event.key === 'Enter') {
            this.api.get<User>('/apis/friends/search?userId='+this.searchUserId).subscribe(it => {
                this.findUser = it
            });
        }
    }

    addFriend($event: ButtonsClickType) {
        let params = new HttpParams();
        console.log(22)
        console.log(this.findUser.id)
        params.append("friendId",this.findUser.id);
        if ($event.name === 'ok') {
            console.log(2333)
            this.api.post('/apis/friends/add',{params})
        }
    }
}
