package com.pilot.myapplication.bean;


public class CustomerString {

	private String mNanme;
	private String mPasswrod;

	public CustomerString() {
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

	@Override
	public String toString() {
		return "CustomerString{" +
			   "mNanme='" + mNanme + '\'' +
			   ", mPasswrod='" + mPasswrod + '\'' +
			   '}';
	}
}
