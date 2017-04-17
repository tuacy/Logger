package com.pilot.myapplication;

import android.app.Application;

import com.tuacy.logger.DebugLogger;


public class PhoneApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		DebugLogger.setShowThreadName(false);
		DebugLogger.setShowStackTrace(false);
	}
}
