package com.vajra.vacs.pojo;

import java.io.Serializable;
import java.util.List;

public class MsgFromVajraAppWithMetadata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3441931813883776771L;

	private String messageId;
	private String conversationId;
	private String sourceAddress;
	private String destinationAddress;
	private List<String> messageType;
	private MqttInputMessage message;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public String getSourceAddress() {
		return sourceAddress;
	}

	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public List<String> getMessageType() {
		return messageType;
	}

	public void setMessageType(List<String> messageType) {
		this.messageType = messageType;
	}

	public MqttInputMessage getMessage() {
		return message;
	}

	public void setMessage(MqttInputMessage message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "MsgFromVajraAppWithMetadata [messageId=" + messageId + ", conversationId=" + conversationId
				+ ", sourceAddress=" + sourceAddress + ", destinationAddress=" + destinationAddress + ", messageType="
				+ messageType + ", message=" + message + "]";
	}

}
