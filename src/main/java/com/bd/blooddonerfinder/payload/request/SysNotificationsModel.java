package com.bd.blooddonerfinder.payload.request;


import lombok.Data;

@Data
public class SysNotificationsModel {

	private String fromUser;

	private String toUser;

	private String eventType;

	private String actionType;

	private boolean isRead;

	private float latLangAlt;

	private String description;

}