package com.clone.chat.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.clone.chat.domain.base.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_in_chatroom")
public class UserInChatRoom extends BaseTimeEntity {

	@Id @Column(name = "user_in_chatroom_id", nullable = false)
	@GeneratedValue
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chatroom_id", nullable = false)
	private ChatRoom chatRoom;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;


	public UserInChatRoom(ChatRoom chatRoom, User user) {
		this.chatRoom = chatRoom;
		this.user = user;
	}
}
