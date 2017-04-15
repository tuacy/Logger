package com.tuacy.logger;

public class DebugLogger {

	private static LoggerImpl logger = new LoggerImpl(new LoggerBuild());

	public static void d(String tag, Object object) {
		logger.d(tag, object);
	}
}
