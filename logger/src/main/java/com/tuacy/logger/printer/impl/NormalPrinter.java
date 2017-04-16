package com.tuacy.logger.printer.impl;


import android.util.Log;

import com.tuacy.logger.bean.LoggerInfo;
import com.tuacy.logger.printer.IPrinter;

/**
 * 正常打印
 */
public class NormalPrinter implements IPrinter {

	private static NormalPrinter sInstance;

	private NormalPrinter() {
	}

	public static NormalPrinter getInstance() {
		if (sInstance == null) {
			synchronized (NormalPrinter.class) {
				if (sInstance == null) {
					sInstance = new NormalPrinter();
				}
			}
		}
		return sInstance;
	}

	@Override
	public void print(LoggerInfo logInfo) {
		doPrint(logInfo.getLogLevel(), logInfo.getTag(), logInfo.getMessage());
	}

	private void doPrint(LoggerInfo.LogLevel logLevel, String tag, String message) {
		switch (logLevel) {
			case ERROR:
				Log.e(tag, message);
				break;
			case INFO:
				Log.i(tag, message);
				break;
			case VERBOSE:
				Log.v(tag, message);
				break;
			case WARN:
				Log.w(tag, message);
				break;
			case ASSERT:
				Log.wtf(tag, message);
				break;
			case DEBUG:
				// Fall through, log debug by default
			default:
				Log.d(tag, message);
				break;
		}
	}
}
