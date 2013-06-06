package com.unique.megasms;

public class Contact {
	private String nameString;
	private String telString;
    private String remarkString;
    public String getNameString() {
		return nameString;
	}
	public Contact(String nameString, String telString) {
		super();
		this.nameString = nameString;
		this.telString = telString;
	}
	public void setNameString(String nameString) {
		this.nameString = nameString;
	}
	public String getTelString() {
		return telString;
	}
	public void setTelString(String telString) {
		this.telString = telString;
	}
	public String getRemarkString() {
		return remarkString;
	}
	public void setRemarkString(String remarkString) {
		this.remarkString = remarkString;
	}
}
