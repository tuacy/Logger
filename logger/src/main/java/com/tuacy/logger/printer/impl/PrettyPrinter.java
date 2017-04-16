package com.tuacy.logger.printer.impl;


import android.text.TextUtils;
import android.util.Log;

import com.tuacy.logger.bean.LoggerInfo;
import com.tuacy.logger.printer.IPrinter;

/**
 * 对打印格式做了一些处理
 */
public class PrettyPrinter implements IPrinter {

	/**
	 * Android's max limit for a log entry is ~4076 bytes, so 4000 bytes is used as chunk size since default charset is UTF-8
	 */
	private static final int CHUNK_SIZE = 4000;

	/**
	 * Drawing toolbox
	 */
	private static final char   TOP_LEFT_CORNER        = '╔';
	private static final char   BOTTOM_LEFT_CORNER     = '╚';
	private static final char   MIDDLE_CORNER          = '╟';
	private static final char   HORIZONTAL_DOUBLE_LINE = '║';
	private static final String DOUBLE_DIVIDER         = "════════════════════════════════════════════";
	private static final String SINGLE_DIVIDER         = "────────────────────────────────────────────";
	private static final String TOP_BORDER             = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
	private static final String BOTTOM_BORDER          = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
	private static final String MIDDLE_BORDER          = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

	private static PrettyPrinter sInstance;

	private PrettyPrinter() {
	}

	public static PrettyPrinter getInstance() {
		if (sInstance == null) {
			synchronized (PrettyPrinter.class) {
				if (sInstance == null) {
					sInstance = new PrettyPrinter();
				}
			}
		}
		return sInstance;
	}

	@Override
	public void print(LoggerInfo LoggerInfo) {
		doPrint(LoggerInfo);
	}

	/**
	 * This method is synchronized in order to avoid messy of logs' order.
	 */
	private synchronized void doPrint(LoggerInfo LoggerInfo) {

		LoggerInfo.LogLevel logLevel = LoggerInfo.getLogLevel();
		String tag = LoggerInfo.getTag();

		/**
		 * 打印头部格式
		 */
		printTopBorder(logLevel, tag);
		/**
		 * 打印线程名字
		 */
		printThreadName(logLevel, tag, LoggerInfo.getThreadName());
		/**
		 * 打印堆栈信息
		 */
		printStackTrace(logLevel, tag, LoggerInfo.getStackTrace(), LoggerInfo.getStackCount());
		/**
		 * 打印具体内容
		 */
		printMessage(logLevel, tag, LoggerInfo.getMessage());
		/**
		 * 打印底部格式
		 */
		printBottomBorder(logLevel, tag);
	}

	private void printTopBorder(LoggerInfo.LogLevel logLevel, String tag) {
		printChunk(logLevel, tag, TOP_BORDER);
	}

	private void printThreadName(LoggerInfo.LogLevel logLevel, String tag, String threadName) {
		if (!TextUtils.isEmpty(threadName)) {
			printChunk(logLevel, tag, HORIZONTAL_DOUBLE_LINE + " Thread: " + threadName);
			printDivider(logLevel, tag);
		}
	}

	private void printStackTrace(LoggerInfo.LogLevel logLevel, String tag, StackTraceElement[] trace, int methodCount) {

		if (trace == null || trace.length <= 0 || methodCount <= 0) {
			return;
		}

		//corresponding method count with the current stack may exceeds the stack trace. Trims the count
		if (methodCount > trace.length) {
			methodCount = trace.length;
		}

		String level = "";
		/**
		 * 从后面往前打印
		 */
		for (int i = methodCount - 1; i >= 0; i--) {

			StringBuilder builder = new StringBuilder();
			builder.append("║ ")
				   .append(level)
				   .append(getSimpleClassName(trace[i].getClassName()))
				   .append(".")
				   .append(trace[i].getMethodName())
				   .append(" ")
				   .append(" (")
				   .append(trace[i].getFileName())
				   .append(":")
				   .append(trace[i].getLineNumber())
				   .append(")");
			level += "   ";
			printChunk(logLevel, tag, builder.toString());
		}

		printDivider(logLevel, tag);
	}

	private String getSimpleClassName(String name) {
		int lastIndex = name.lastIndexOf(".");
		return name.substring(lastIndex + 1);
	}

	private void printMessage(LoggerInfo.LogLevel logLevel, String tag, String message) {
		if (!TextUtils.isEmpty(message)) {
			//get bytes of message with system's default charset (which is UTF-8 for Android)
			byte[] bytes = message.getBytes();
			int length = bytes.length;
			if (length <= CHUNK_SIZE) {
				printContent(logLevel, tag, message);
			} else {
				for (int i = 0; i < length; i += CHUNK_SIZE) {
					int count = Math.min(length - i, CHUNK_SIZE);
					//create a new String with system's default charset (which is UTF-8 for Android)
					printContent(logLevel, tag, new String(bytes, i, count));
				}
			}
		}
	}

	private void printBottomBorder(LoggerInfo.LogLevel logLevel, String tag) {
		printChunk(logLevel, tag, BOTTOM_BORDER);
	}

	private void printDivider(LoggerInfo.LogLevel logLevel, String tag) {
		printChunk(logLevel, tag, MIDDLE_BORDER);
	}

	private void printContent(LoggerInfo.LogLevel logLevel, String tag, String chunk) {
		String[] lines = chunk.split(System.getProperty("line.separator"));
		for (String line : lines) {
			printChunk(logLevel, tag, HORIZONTAL_DOUBLE_LINE + " " + line);
		}
	}

	private void printChunk(LoggerInfo.LogLevel logLevel, String tag, String chunk) {
		switch (logLevel) {
			case ERROR:
				Log.e(tag, chunk);
				break;
			case INFO:
				Log.i(tag, chunk);
				break;
			case VERBOSE:
				Log.v(tag, chunk);
				break;
			case WARN:
				Log.w(tag, chunk);
				break;
			case ASSERT:
				Log.wtf(tag, chunk);
				break;
			case DEBUG:
				Log.d(tag, chunk);
				break;
			default:
				Log.d(tag, chunk);
				break;
		}
	}
}
