package com.tuacy.logger.bean;


/**
 * 日志信息封装
 */
public class LoggerInfo {

	public enum LogLevel {
		VERBOSE,
		DEBUG,
		INFO,
		WARN,
		ERROR,
		ASSERT,
	}

	/**
	 * log的等级
	 */
	private LogLevel            mLogLevel;
	/**
	 * log的tag
	 */
	private String              mTag;
	/**
	 * log的具体内容
	 */
	private String              mMessage;
	/**
	 * log对应的线程名字
	 */
	private String              mThreadName;
	/**
	 * 堆栈信息
	 */
	private StackTraceElement[] mStackTrace;
	/**
	 * 打印mStackTrace后面多少级堆栈信息
	 */
	private int 				mStackCount;


	public void apply(Builder builder) {
		this.mLogLevel = builder.mLogLevel;
		this.mTag = builder.mTag;
		this.mMessage = builder.mMessage;
		this.mThreadName = builder.mThreadName;
		this.mStackTrace = builder.mStackTrace;
		this.mStackCount = builder.mStackCount;
	}

	public LogLevel getLogLevel() {
		return mLogLevel;
	}

	public String getTag() {
		return mTag;
	}

	public String getMessage() {
		return mMessage;
	}

	public String getThreadName() {
		return mThreadName;
	}

	public StackTraceElement[] getStackTrace() {
		return mStackTrace;
	}

	public int getStackCount() {
		return  mStackCount;
	}

	public static class Builder {

		/**
		 * log的等级
		 */
		private LogLevel            mLogLevel;
		/**
		 * log的tag
		 */
		private String              mTag;
		/**
		 * log的具体内容
		 */
		private String              mMessage;
		/**
		 * log对应的线程名字
		 */
		private String              mThreadName;
		/**
		 * log对应的堆栈信息
		 */
		private StackTraceElement[] mStackTrace;
		/**
		 * 打印mStackTrace后面多少级堆栈信息
		 */
		private int 				mStackCount;

		public Builder(LogLevel logLevel, String tag, String message) {
			this.mLogLevel = logLevel;
			this.mTag = tag;
			this.mMessage = message;
		}

		public Builder threadName(String threadName) {
			mThreadName = threadName;
			return this;
		}

		public Builder stackTrace(StackTraceElement[] traceElements, int stackCount) {
			mStackTrace = traceElements;
			mStackCount = stackCount;
			return this;
		}

		public LoggerInfo build() {
			LoggerInfo info = new LoggerInfo();
			info.apply(this);
			return info;
		}
	}
}
