package com.oikostechnologies.schedsys.websocket;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotifMessage {

	private String sender;
	private String content;
	private String to;

}
