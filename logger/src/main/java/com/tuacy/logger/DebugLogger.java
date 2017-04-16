package com.tuacy.logger;

public class DebugLogger {

	private static LoggerImpl logger = new LoggerImpl(new LoggerBuild());


	public static void v(String tag, String format, Object... args) {
		logger.v(tag, format, args);
	}


	public static void v(Class<?> clazz, String format, Object... args) {
		logger.v(clazz, format, args);
	}


	public static void v(String format, Object... args) {
		logger.v(format, args);
	}


	public static void v(String tag, Object object) {
		logger.v(tag, object);
	}


	public static void v(Class<?> clazz, Object object) {
		logger.v(clazz, object);
	}


	public static void v(Object object) {
		logger.v(object);
	}


	public static void d(String tag, String format, Object... args) {
		logger.d(tag, format, args);
	}


	public static void d(Class<?> clazz, String format, Object... args) {
		logger.d(clazz, format, args);
	}


	public static void d(String format, Object... args) {
		logger.d(format, args);
	}

	public static void d(String tag, Object object) {
		logger.d(tag, object);
	}


	public static void d(Class<?> clazz, Object object) {
		logger.d(clazz, object);
	}


	public static void d(Object object) {
		logger.d(object);
	}


	public static void i(String tag, String format, Object... args) {
		logger.i(tag, format, args);
	}


	public static void i(Class<?> clazz, String format, Object... args) {
		logger.i(clazz, format, args);
	}


	public static void i(String format, Object... args) {
		logger.i(format, args);
	}


	public static void i(String tag, Object object) {
		logger.i(tag, object);
	}


	public static void i(Class<?> clazz, Object object) {
		logger.i(clazz, object);
	}


	public static void i(Object object) {
		logger.i(object);
	}


	public static void w(String tag, String format, Object... args) {
		logger.w(tag, format, args);
	}


	public static void w(Class<?> clazz, String format, Object... args) {
		logger.w(clazz, format, args);
	}


	public static void w(String format, Object... args) {
		logger.w(format, args);
	}


	public static void w(String tag, Object object) {
		logger.w(tag, object);
	}


	public static void w(Class<?> clazz, Object object) {
		logger.w(clazz, object);
	}


	public static void w(Object object) {
		logger.w(object);
	}


	public static void e(String tag, String format, Object... args) {
		logger.e(tag, format, args);
	}


	public static void e(Class<?> clazz, String format, Object... args) {
		logger.e(clazz, format, args);
	}


	public static void e(String format, Object... args) {
		logger.e(format, args);
	}


	public static void e(String tag, Object object) {
		logger.e(tag, object);
	}


	public static void e(Class<?> clazz, Object object) {
		logger.e(clazz, object);
	}


	public static void e(Object object) {
		logger.e(object);
	}


	public static void wtf(String tag, String format, Object... args) {
		logger.wtf(tag, format, args);
	}


	public static void wtf(Class<?> clazz, String format, Object... args) {
		logger.wtf(clazz, format, args);
	}


	public static void wtf(String format, Object... args) {
		logger.wtf(format, args);
	}


	public static void wtf(String tag, Object object) {
		logger.wtf(tag, object);
	}


	public static void wtf(Class<?> clazz, Object object) {
		logger.wtf(clazz, object);
	}


	public static void wtf(Object object) {
		logger.wtf(object);
	}


	public static void json(String tag, String json) {
		logger.json(tag, json);
	}


	public static void json(Class<?> clazz, String json) {
		logger.json(clazz, json);
	}


	public static void json(String json) {
		logger.json(json);
	}


	public static void xml(String tag, String xml) {
		logger.xml(tag, xml);
	}


	public static void xml(Class<?> clazz, String xml) {
		logger.xml(clazz, xml);
	}


	public static void xml(String xml) {
		logger.xml(xml);
	}
}
