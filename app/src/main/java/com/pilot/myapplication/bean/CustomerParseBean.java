package com.pilot.myapplication.bean;


public class CustomerParseBean {

	private String mNanme;
	private String mPasswrod;

	public CustomerParseBean() {
		mNanme = "tuacy";
		mPasswrod = "123456";
	}

	public String getNanme() {
		return mNanme;
	}

	public void setNanme(String nanme) {
		mNanme = nanme;
	}

	public String getPasswrod() {
		return mPasswrod;
	}

	public void setPasswrod(String passwrod) {
		mPasswrod = passwrod;
	}
}
