package com.pilot.myapplication.bean;


public class CustomerBean extends CustomerSuper {

	private String mNanme;
	private String mPasswrod;

	public CustomerBean() {
		super();
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
