package com.unique.megasms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;

import net.tsz.afinal.exception.DbException;

import android.R.integer;

public class content_sms implements Serializable {

	/**
	 * 
	 */
	
	private String contentString;
	private String names;
	private String tels;
	private String statuses;
	

	private static final long serialVersionUID = 1L;
	public content_sms(String contentString, String names, String tels,
			String statuses) {
		super();
		this.contentString = contentString;
		this.names = names;
		this.tels = tels;
		this.statuses = statuses;
	
	}
	public content_sms()
	{
		
	}
	
	
	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getTels() {
		return tels;
	}

	public void setTels(String tels) {
		this.tels = tels;
	}

	public String getStatuses() {
		return statuses;
	}

	public void setStatuses(String statuses) {
		this.statuses = statuses;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getContentString() {
		return contentString;
	}

	public void setContentString(String contentString) {
		this.contentString = contentString;
	}
	
	public List<String> namesToList(String names){
		List<String> nameList= null;
		nameList = new ArrayList<String>();
	
		int len = names.length();
		String temp = "";
		for (int i = 0; i < len; i++) {
			char c = names.charAt(i);
			if (c != '#') {
				temp += c;
			} else {
				nameList.add(temp);
				temp = "";
			}
		}
		return nameList;
		
	}
	public String nameListtoString(List<String> nameList)
	{
		
			
			for (String name : nameList) {
				names += name + "#";
			}
			return names;
		
	}
	public List<String> telsToList(String names){
		List<String> telList= null;
		telList = new ArrayList<String>();
	
		int len = names.length();
		String temp = "";
		for (int i = 0; i < len; i++) {
			char c = names.charAt(i);
			if (c != '#') {
				temp += c;
			} else {
				telList.add(temp);
				temp = "";
			}
		}
		return telList;
		
	}
	public String telListtoString(List<String> nameList)
	{
		
			
			for (String name : nameList) {
				names += name + "#";
			}
			return names;
		
	}
	
	public List<Integer> statuesToList(String statuses)
	{
	
			List<Integer> statusList = new ArrayList<Integer>();
			int temp = 0;
			int len = statuses.length();
			for (int i = 0; i < len; i++) {
				char c = statuses.charAt(i);
				if (c != '#') {
					temp = temp * 10 + c - '0';
				} else {
					statusList.add(temp);
					temp = 0;
				}
			}
		
		
		return statusList;
		
	}
	public String statuesListToString(List<Integer> statusList)
	{
	for (Integer status : statusList ) {
		statuses += Integer.toString(status) + "#";
	}
	return statuses;
	}

}
