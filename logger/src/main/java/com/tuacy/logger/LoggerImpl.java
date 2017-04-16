package com.tuacy.logger;

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

import static com.tuacy.logger.bean.LoggerInfo.LogLevel.ASSERT;
import static com.tuacy.logger.bean.LoggerInfo.LogLevel.DEBUG;
import static com.tuacy.logger.bean.LoggerInfo.LogLevel.ERROR;
import static com.tuacy.logger.bean.LoggerInfo.LogLevel.INFO;
import static com.tuacy.logger.bean.LoggerInfo.LogLevel.WARN;

/**
 * 日志的具体实现
 */
public class LoggerImpl implements ILogger {

	private final LoggerBuild mConfig;


	public LoggerImpl(LoggerBuild config) {
		mConfig = config;
	}

	private void log(LoggerInfo.LogLevel logLevel, String tag, String format, Object... args) {
		log(logLevel, tag, String.format(format, args));
	}

	private void log(LoggerInfo.LogLevel logLevel, String tag, Object object) {
		log(logLevel, tag, LoggerConvert.objectToString(object, mConfig.getParseList()));
	}

	private void log(LoggerInfo.LogLevel logLevel, String tag, String message) {
		/**
		 * 是否显示日志
		 */
		if (!mConfig.isLogEnable()) {
			return;
		}
		/**
		 * 日志等级判断
		 */
		if (!levelCheck(logLevel)) {
			return;
		}

		LoggerInfo.Builder builder = new LoggerInfo.Builder(logLevel, tag, message);
		if (mConfig.isShowThreadName()) {
			builder.threadName(Thread.currentThread().getName());
		}
		if (mConfig.isShowStackTrace()) {
			builder.stackTrace(getStackTrace(), mConfig.getStackCount());
		}

		mConfig.getPrinter().print(builder.build());
	}

	private boolean levelCheck(LoggerInfo.LogLevel logLevel) {
		switch (mConfig.getLogLevel()) {
			case VERBOSE:
				return true;
			case DEBUG:
				return logLevel == DEBUG || logLevel == INFO || logLevel == WARN || logLevel == ERROR || logLevel == ASSERT;
			case INFO:
				return logLevel == INFO || logLevel == WARN || logLevel == ERROR || logLevel == ASSERT;
			case WARN:
				return logLevel == WARN || logLevel == ERROR || logLevel == ASSERT;
			case ERROR:
				return logLevel == ERROR || logLevel == ASSERT;
			case ASSERT:
				return logLevel == ASSERT;
		}
		return false;
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
	private int getUnusedStackCount(StackTraceElement[] trace) {
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
	public void v(String tag, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.VERBOSE, tag, args);
		}
	}

	@Override
	public void v(Class<?> clazz, String format, Object... args) {
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
	public void v(String tag, Object object) {
		if (mConfig.isLogEnable()) {
			log(LoggerInfo.LogLevel.VERBOSE, tag, object);
		}
	}

	@Override
	public void v(Class<?> clazz, Object object) {
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
	public void d(String tag, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(DEBUG, tag, format, args);
		}
	}

	@Override
	public void d(Class<?> clazz, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(DEBUG, clazz.getSimpleName(), format, args);
		}
	}

	@Override
	public void d(String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(DEBUG, mConfig.getTag(), format, args);
		}
	}

	@Override
	public void d(String tag, Object object) {
		if (mConfig.isLogEnable()) {
			log(DEBUG, tag, object);
		}
	}

	@Override
	public void d(Class<?> clazz, Object object) {
		if (mConfig.isLogEnable()) {
			log(DEBUG, clazz.getSimpleName(), object);
		}
	}

	@Override
	public void d(Object object) {
		if (mConfig.isLogEnable()) {
			log(DEBUG, mConfig.getTag(), object);
		}
	}

	@Override
	public void i(String tag, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(INFO, tag, format, args);
		}
	}

	@Override
	public void i(Class<?> clazz, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(INFO, clazz.getSimpleName(), format, args);
		}
	}

	@Override
	public void i(String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(INFO, mConfig.getTag(), format, args);
		}
	}

	@Override
	public void i(String tag, Object object) {
		if (mConfig.isLogEnable()) {
			log(INFO, tag, object);
		}
	}

	@Override
	public void i(Class<?> clazz, Object object) {
		if (mConfig.isLogEnable()) {
			log(INFO, clazz.getSimpleName(), object);
		}
	}

	@Override
	public void i(Object object) {
		if (mConfig.isLogEnable()) {
			log(INFO, mConfig.getTag(), object);
		}
	}

	@Override
	public void w(String tag, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(WARN, tag, format, args);
		}
	}

	@Override
	public void w(Class<?> clazz, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(WARN, clazz.getSimpleName(), format, args);
		}
	}

	@Override
	public void w(String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(WARN, mConfig.getTag(), format, args);
		}
	}

	@Override
	public void w(String tag, Object object) {
		if (mConfig.isLogEnable()) {
			log(WARN, tag, object);
		}
	}

	@Override
	public void w(Class<?> clazz, Object object) {
		if (mConfig.isLogEnable()) {
			log(WARN, clazz.getSimpleName(), object);
		}
	}

	@Override
	public void w(Object object) {
		if (mConfig.isLogEnable()) {
			log(WARN, mConfig.getTag(), object);
		}
	}

	@Override
	public void e(String tag, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(ERROR, tag, format, args);
		}
	}

	@Override
	public void e(Class<?> clazz, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(ERROR, clazz.getSimpleName(), format, args);
		}
	}

	@Override
	public void e(String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(ERROR, mConfig.getTag(), format, args);
		}
	}

	@Override
	public void e(String tag, Object object) {
		if (mConfig.isLogEnable()) {
			log(ERROR, tag, object);
		}
	}

	@Override
	public void e(Class<?> clazz, Object object) {
		if (mConfig.isLogEnable()) {
			log(ERROR, clazz.getSimpleName(), object);
		}
	}

	@Override
	public void e(Object object) {
		if (mConfig.isLogEnable()) {
			log(ERROR, mConfig.getTag(), object);
		}
	}

	@Override
	public void wtf(String tag, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(ASSERT, tag, format, args);
		}
	}

	@Override
	public void wtf(Class<?> clazz, String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(ASSERT, clazz.getSimpleName(), format, args);
		}
	}

	@Override
	public void wtf(String format, Object... args) {
		if (mConfig.isLogEnable()) {
			log(ASSERT, mConfig.getTag(), format, args);
		}
	}

	@Override
	public void wtf(String tag, Object object) {
		if (mConfig.isLogEnable()) {
			log(ASSERT, tag, object);
		}
	}

	@Override
	public void wtf(Class<?> clazz, Object object) {
		if (mConfig.isLogEnable()) {
			log(ASSERT, clazz.getSimpleName(), object);
		}
	}

	@Override
	public void wtf(Object object) {
		if (mConfig.isLogEnable()) {
			log(ASSERT, mConfig.getTag(), object);
		}
	}

	@Override
	public void json(String tag, String json) {
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
	public void json(Class<?> clazz, String json) {
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
	public void xml(String tag, String xml) {
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
	public void xml(Class<?> clazz, String xml) {
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
