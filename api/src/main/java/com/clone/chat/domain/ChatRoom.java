package com.clone.chat.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.clone.chat.domain.base.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "chatroom")
public class ChatRoom extends BaseTimeEntity{
	
	@Id @Column(name = "chatroom_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String chatRoomName;
	private String adminId;

	@Builder
	public ChatRoom(Long id, String chatRoomName, String adminId) {
		this.id = id;
		this.chatRoomName = chatRoomName;
		this.adminId = adminId;
	}


}
