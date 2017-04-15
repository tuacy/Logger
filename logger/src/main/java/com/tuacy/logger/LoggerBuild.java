package com.tuacy.logger;

import com.tuacy.logger.bean.LoggerInfo;
import com.tuacy.logger.parse.impl.BundleParse;
import com.tuacy.logger.parse.impl.CollectionParse;
import com.tuacy.logger.parse.IParser;
import com.tuacy.logger.parse.impl.IntentParse;
import com.tuacy.logger.parse.impl.MapParse;
import com.tuacy.logger.parse.impl.ReferenceParse;
import com.tuacy.logger.parse.impl.ThrowableParse;
import com.tuacy.logger.printer.Printer;
import com.tuacy.logger.printer.impl.PrettyPrinter;

import java.util.ArrayList;
import java.util.List;


public class LoggerBuild {

	// 默认支持解析库
	public static final Class<? extends IParser>[] DEFAULT_PARSE_CLASS = new Class[]{BundleParse.class,
																					 IntentParse.class,
																					 CollectionParse.class,
																					 MapParse.class,
																					 ThrowableParse.class,
																					 ReferenceParse.class};


	private boolean mLogEnable = true;
	private LoggerInfo.LogLevel mLogLevel = LoggerInfo.LogLevel.VERBOSE;
	private String mTag = "Log";
	private List<IParser> mParseList = null;
	private Printer mPrinter = PrettyPrinter.getInstance();
	private boolean mShowThreadName = true;
	private boolean mShowStackTrace = true;
	private int mStackCount = 1;

	public LoggerBuild() {
		addParserClass(DEFAULT_PARSE_CLASS);
	}

	public LoggerBuild addParserClass(Class<? extends IParser>... classes) {
		for (Class<? extends IParser> cla : classes) {
			if (mParseList == null) {
				mParseList = new ArrayList<>();
			}
			try {
				mParseList.add(0, cla.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return this;
	}

	public LoggerBuild addParse(IParser parser) {
		mParseList.add(0, parser);
		return this;
	}

	public List<IParser> getParseList() {
		return mParseList;
	}

	public LoggerBuild setTag(String tag) {
		mTag = tag;
		return this;
	}

	public String getTag() {
		return mTag;
	}

	public LoggerBuild setLogLevel(LoggerInfo.LogLevel level) {
		mLogLevel = level;
		return this;
	}

	public LoggerInfo.LogLevel getLogLevel() {
		return mLogLevel;
	}

	public LoggerBuild setLogEnable(boolean enable) {
		mLogEnable = enable;
		return this;
	}

	public boolean isLogEnable() {
		return mLogEnable;
	}

	public LoggerBuild setPrinter(Printer printer) {
		mPrinter = printer;
		return this;
	}

	public Printer getPrinter() {
		return mPrinter;
	}

	public LoggerBuild setShowThreadName(boolean show) {
		mShowThreadName = show;
		return this;
	}

	public boolean isShowThreadName() {
		return mShowThreadName;
	}

	public LoggerBuild setShowStackTrace(boolean show) {
		mShowStackTrace = show;
		return this;
	}

	public boolean isShowStackTrace() {
		return mShowStackTrace;
	}

	public LoggerBuild setStackCount(int count) {
		mStackCount = count;
		return this;
	}

	public int getStackCount() {
		return mStackCount;
	}

}
