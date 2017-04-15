package com.tuacy.logger;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.tuacy.logger.bean.LoggerInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * 日志的具体实现
 */
public class LoggerImpl implements ILogger {

	private final LoggerBuild mConfig;


	public LoggerImpl(LoggerBuild config) {
		mConfig = config;
	}

	private void log(@NonNull LoggerInfo.LogLevel logLevel, @NonNull String tag, String format, Object... args) {
		log(logLevel, tag, String.format(format, args));
	}

	private void log(@NonNull LoggerInfo.LogLevel logLevel, @NonNull String tag, Object object) {
		log(logLevel, tag, LoggerConvert.objectToString(object, mConfig.getParseList()));
	}

	private void log(@NonNull LoggerInfo.LogLevel logLevel, @NonNull String tag, @NonNull String message) {
		/**
		 * 是否显示日志
		 */
		if (!mConfig.isLogEnable()) {
			return;
		}
		//TODO:日志等级判断
		LoggerInfo.Builder builder = new LoggerInfo.Builder(logLevel, tag, message);
		if (mConfig.isShowThreadName()) {
			builder.threadName(Thread.currentThread().getName());
		}
		if (mConfig.isShowStackTrace()) {
			builder.stackTrace(getStackTrace(), mConfig.getStackCount());
		}

		mConfig.getPrinter().print(builder.build());
	}


	private StackTraceElement[] getStackTrace() {
		StackTraceElement[] allTraces = Thread.currentThread().getStackTrace();
		int unusedCount = getUnusedStackCount(allTraces);
		StackTraceElement[] trace = new StackTraceElement[allTraces.length - unusedCount];
		System.arraycopy(allTraces, unusedCount, trace, 0, allTraces.length - unusedCount);
		return trace;
	}

	/**
	 * Determines the starting index of the stack trace
	 *
	 * @param trace the stack trace
	 * @return the start index
	 */
	private int getUnusedStackCount(@NonNull StackTraceElement[] trace) {
		/**
		 * The minimum stack trace index, starts at this class after two native calls.
		 */
		final int MIN_STACK_OFFSET = 2;
		int count = MIN_STACK_OFFSET;
		for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
			if (DebugLogger.class.getName().equals(trace[i].getClassName()) || LoggerImpl.class.getName().equals(trace[i].getClassName())) {
				count++;
			} else {
				return count;
			}
		}
		return -1;
	}

	@Override
	public void v(@NonNull String tag, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.VERBOSE, tag, args);
		}
	}

	@Override
	public void v(@NonNull Class<?> clazz, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.VERBOSE, clazz.getSimpleName(), args);
		}
	}

	@Override
	public void v(String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.VERBOSE, mConfig.getTag(), args);
		}
	}

	@Override
	public void v(@NonNull String tag, Object object) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.VERBOSE, tag, object);
		}
	}

	@Override
	public void v(@NonNull Class<?> clazz, Object object) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.VERBOSE, clazz.getSimpleName(), object);
		}
	}

	@Override
	public void v(Object object) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.VERBOSE, mConfig.getTag(), object);
		}
	}

	@Override
	public void d(@NonNull String tag, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.DEBUG, tag, format, args);
		}
	}

	@Override
	public void d(@NonNull Class<?> clazz, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.DEBUG, clazz.getSimpleName(), format, args);
		}
	}

	@Override
	public void d(String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.DEBUG, mConfig.getTag(), format, args);
		}
	}

	@Override
	public void d(@NonNull String tag, Object object) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.DEBUG, tag, object);
		}
	}

	@Override
	public void d(@NonNull Class<?> clazz, Object object) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.DEBUG, clazz.getSimpleName(), object);
		}
	}

	@Override
	public void d(Object object) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.DEBUG, mConfig.getTag(), object);
		}
	}

	@Override
	public void i(@NonNull String tag, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.INFO, tag, format, args);
		}
	}

	@Override
	public void i(@NonNull Class<?> clazz, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.INFO, clazz.getSimpleName(), format, args);
		}
	}

	@Override
	public void i(String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.INFO, mConfig.getTag(), format, args);
		}
	}

	@Override
	public void i(@NonNull String tag, Object object) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.INFO, tag, object);
		}
	}

	@Override
	public void i(@NonNull Class<?> clazz, Object object) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.INFO, clazz.getSimpleName(), object);
		}
	}

	@Override
	public void i(Object object) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.INFO, mConfig.getTag(), object);
		}
	}

	@Override
	public void w(@NonNull String tag, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.WARN, tag, format, args);
		}
	}

	@Override
	public void w(@NonNull Class<?> clazz, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.WARN, clazz.getSimpleName(), format, args);
		}
	}

	@Override
	public void w(String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.WARN, mConfig.getTag(), format, args);
		}
	}

	@Override
	public void w(@NonNull String tag, Object object) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.WARN, tag, object);
		}
	}

	@Override
	public void w(@NonNull Class<?> clazz, Object object) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.WARN, clazz.getSimpleName(), object);
		}
	}

	@Override
	public void w(Object object) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.WARN, mConfig.getTag(), object);
		}
	}

	@Override
	public void e(@NonNull String tag, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.ERROR, tag, format, args);
		}
	}

	@Override
	public void e(@NonNull Class<?> clazz, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.ERROR, clazz.getSimpleName(), format, args);
		}
	}

	@Override
	public void e(String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.ERROR, mConfig.getTag(), format, args);
		}
	}

	@Override
	public void e(@NonNull String tag, Object object) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.ERROR, tag, object);
		}
	}

	@Override
	public void e(@NonNull Class<?> clazz, Object object) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.ERROR, clazz.getSimpleName(), object);
		}
	}

	@Override
	public void e(Object object) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.ERROR, mConfig.getTag(), object);
		}
	}

	@Override
	public void wtf(@NonNull String tag, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.ASSERT, tag, format, args);
		}
	}

	@Override
	public void wtf(@NonNull Class<?> clazz, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.ASSERT, clazz.getSimpleName(), format, args);
		}
	}

	@Override
	public void wtf(String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.ASSERT, mConfig.getTag(), format, args);
		}
	}

	@Override
	public void wtf(@NonNull String tag, Object object) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.ASSERT, tag, object);
		}
	}

	@Override
	public void wtf(@NonNull Class<?> clazz, Object object) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.ASSERT, clazz.getSimpleName(), object);
		}
	}

	@Override
	public void wtf(Object object) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.ASSERT, mConfig.getTag(), object);
		}
	}

	@Override
	public void json(@NonNull String tag, String json) {
		if (mConfig.isLogEnable()) {
			int indent = 4;
			if (TextUtils.isEmpty(json)) {
				d(tag, "JSON{json is empty}");
				return;
			}
			try {
				if (json.startsWith("{")) {
					JSONObject jsonObject = new JSONObject(json);
					String msg = jsonObject.toString(indent);
					d(tag, msg);
				} else if (json.startsWith("[")) {
					JSONArray jsonArray = new JSONArray(json);
					String msg = jsonArray.toString(indent);
					d(tag, msg);
				}
			} catch (JSONException e) {
				e(tag, e.toString() + "\n\njson = " + json);
			}
		}
	}

	@Override
	public void json(@NonNull Class<?> clazz, String json) {
		if (mConfig.isLogEnable()) {
			json(clazz.getSimpleName(), json);
		}
	}

	@Override
	public void json(String json) {
		if (mConfig.isLogEnable()) {
			json(mConfig.getTag(), json);
		}
	}

	@Override
	public void xml(@NonNull String tag, String xml) {
		if (mConfig.isLogEnable()) {
			if (TextUtils.isEmpty(xml)) {
				d(tag, "XML{xml is empty}");
				return;
			}
			try {
				Source xmlInput = new StreamSource(new StringReader(xml));
				StreamResult xmlOutput = new StreamResult(new StringWriter());
				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
				transformer.transform(xmlInput, xmlOutput);
				d(tag, xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
			} catch (TransformerException e) {
				e(tag, e.toString() + "\n\nxml = " + xml);
			}
		}
	}

	@Override
	public void xml(@NonNull Class<?> clazz, String xml) {
		if (mConfig.isLogEnable()) {
			xml(clazz.getSimpleName(), xml);
		}
	}

	@Override
	public void xml(String xml) {
		if (mConfig.isLogEnable()) {
			xml(mConfig.getTag(), xml);
		}
	}
}
