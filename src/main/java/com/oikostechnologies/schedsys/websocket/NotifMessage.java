package com.oikostechnologies.schedsys.websocket;

import java.util.List;

import com.oikostechnologies.schedsys.model.PeopleModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotifMessage {

	private String sender;
	private String content;
	private String to;
	private List<PeopleModel> tos;
}
