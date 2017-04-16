package com.tuacy.logger;

/**
 * 日志接口
 */
public interface ILogger {

	/**
	 * VERBOSE
	 *
	 * @param tag    log tag
	 * @param format log格式化格式
	 * @param args   log格式化内容
	 */
	void v(String tag, String format, Object... args);

	/**
	 * VERBOSE
	 *
	 * @param clazz  log class
	 * @param format log格式化格式
	 * @param args   log格式化内容
	 */
	void v(Class<?> clazz, String format, Object... args);

	/**
	 * VERBOSE
	 *
	 * @param format log格式化格式
	 * @param args   log格式化内容
	 */
	void v(String format, Object... args);

	/**
	 * VERBOSE
	 *
	 * @param tag    log tag
	 * @param object log内容
	 */
	void v(String tag, Object object);

	/**
	 * VERBOSE
	 *
	 * @param clazz  log class
	 * @param object log内容
	 */
	void v(Class<?> clazz, Object object);

	/**
	 * VERBOSE
	 *
	 * @param object log内容
	 */
	void v(Object object);

	/**
	 * DEBUG
	 *
	 * @param tag    log tag
	 * @param format log格式化格式
	 * @param args   log格式化内容
	 */
	void d(String tag, String format, Object... args);

	/**
	 * DEBUG
	 *
	 * @param clazz  log class
	 * @param format log格式化格式
	 * @param args   log格式化内容
	 */
	void d(Class<?> clazz, String format, Object... args);

	/**
	 * DEBUG
	 *
	 * @param format log格式化格式
	 * @param args   log格式化内容
	 */
	void d(String format, Object... args);

	/**
	 * DEBUG
	 *
	 * @param tag    log tag
	 * @param object log内容
	 */
	void d(String tag, Object object);

	/**
	 * DEBUG
	 *
	 * @param clazz  log class
	 * @param object log内容
	 */
	void d(Class<?> clazz, Object object);

	/**
	 * DEBUG
	 *
	 * @param object log内容
	 */
	void d(Object object);


	/**
	 * INFO
	 *
	 * @param tag    log tag
	 * @param format log格式化格式
	 * @param args   log格式化内容
	 */
	void i(String tag, String format, Object... args);

	/**
	 * INFO
	 *
	 * @param clazz  log class
	 * @param format log格式化格式
	 * @param args   log格式化内容
	 */
	void i(Class<?> clazz, String format, Object... args);

	/**
	 * INFO
	 *
	 * @param format log格式化格式
	 * @param args   log格式化内容
	 */
	void i(String format, Object... args);

	/**
	 * INFO
	 *
	 * @param tag    log tag
	 * @param object log内容
	 */
	void i(String tag, Object object);

	/**
	 * INFO
	 *
	 * @param clazz  log class
	 * @param object log内容
	 */
	void i(Class<?> clazz, Object object);

	/**
	 * INFO
	 *
	 * @param object log内容
	 */
	void i(Object object);


	/**
	 * WARN
	 *
	 * @param tag    log tag
	 * @param format log格式化格式
	 * @param args   log格式化内容
	 */
	void w(String tag, String format, Object... args);

	/**
	 * WARN
	 *
	 * @param clazz  log class
	 * @param format log格式化格式
	 * @param args   log格式化内容
	 */
	void w(Class<?> clazz, String format, Object... args);

	/**
	 * WARN
	 *
	 * @param format log格式化格式
	 * @param args   log格式化内容
	 */
	void w(String format, Object... args);

	/**
	 * WARN
	 *
	 * @param tag    log tag
	 * @param object log内容
	 */
	void w(String tag, Object object);

	/**
	 * WARN
	 *
	 * @param clazz  log class
	 * @param object log内容
	 */
	void w(Class<?> clazz, Object object);

	/**
	 * WARN
	 *
	 * @param object log内容
	 */
	void w(Object object);

	/**
	 * ERROR
	 *
	 * @param tag    log tag
	 * @param format log格式化格式
	 * @param args   log格式化内容
	 */
	void e(String tag, String format, Object... args);

	/**
	 * ERROR
	 *
	 * @param clazz  log class
	 * @param format log格式化格式
	 * @param args   log格式化内容
	 */
	void e(Class<?> clazz, String format, Object... args);

	/**
	 * ERROR
	 *
	 * @param format log格式化格式
	 * @param args   log格式化内容
	 */
	void e(String format, Object... args);

	/**
	 * ERROR
	 *
	 * @param tag    log tag
	 * @param object log内容
	 */
	void e(String tag, Object object);

	/**
	 * ERROR
	 *
	 * @param clazz  log class
	 * @param object log内容
	 */
	void e(Class<?> clazz, Object object);

	/**
	 * ERROR
	 *
	 * @param object log内容
	 */
	void e(Object object);

	/**
	 * ASSERT
	 *
	 * @param tag    log tag
	 * @param format log格式化格式
	 * @param args   log格式化内容
	 */
	void wtf(String tag, String format, Object... args);

	/**
	 * ASSERT
	 *
	 * @param clazz  log class
	 * @param format log格式化格式
	 * @param args   log格式化内容
	 */
	void wtf(Class<?> clazz, String format, Object... args);

	/**
	 * ASSERT
	 *
	 * @param format log格式化格式
	 * @param args   log格式化内容
	 */
	void wtf(String format, Object... args);

	/**
	 * ASSERT
	 *
	 * @param tag    log tag
	 * @param object log内容
	 */
	void wtf(String tag, Object object);

	/**
	 * ASSERT
	 *
	 * @param clazz  log class
	 * @param object log内容
	 */
	void wtf(Class<?> clazz, Object object);

	/**
	 * ASSERT
	 *
	 * @param object log内容
	 */
	void wtf(Object object);


	/**
	 * json格式输出
	 *
	 * @param tag  log tag
	 * @param json json 字符串
	 */
	void json(String tag, String json);

	/**
	 * json格式输出
	 *
	 * @param clazz log class
	 * @param json  json 字符串
	 */
	void json(Class<?> clazz, String json);

	/**
	 * json格式输出
	 *
	 * @param json json 字符串
	 */
	void json(String json);

	/**
	 * xml格式输出
	 *
	 * @param tag log tag
	 * @param xml json 字符串
	 */
	void xml(String tag, String xml);

	/**
	 * xml格式输出
	 *
	 * @param clazz log class
	 * @param xml   json 字符串
	 */
	void xml(Class<?> clazz, String xml);

	/**
	 * xml格式输出
	 *
	 * @param xml json 字符串
	 */
	void xml(String xml);

}
