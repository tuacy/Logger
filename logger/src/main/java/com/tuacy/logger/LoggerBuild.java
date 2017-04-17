package com.tuacy.logger;

import com.tuacy.logger.bean.LoggerInfo;
import com.tuacy.logger.parse.impl.BundleParse;
import com.tuacy.logger.parse.impl.CollectionParse;
import com.tuacy.logger.parse.IParser;
import com.tuacy.logger.parse.impl.IntentParse;
import com.tuacy.logger.parse.impl.MapParse;
import com.tuacy.logger.parse.impl.ReferenceParse;
import com.tuacy.logger.parse.impl.ThrowableParse;
import com.tuacy.logger.printer.IPrinter;
import com.tuacy.logger.printer.impl.PrettyPrinter;

import java.util.ArrayList;
import java.util.List;


public class LoggerBuild {

	/**
	 * 默认支持的log解析对象
	 */
	public static final Class<? extends IParser>[] DEFAULT_PARSE_CLASS = new Class[]{BundleParse.class,
																					 IntentParse.class,
																					 CollectionParse.class,
																					 MapParse.class,
																					 ThrowableParse.class,
																					 ReferenceParse.class};


	/**
	 * 是否显示打印信息
	 */
	private boolean             mLogEnable      = true;
	/**
	 * 打印的等级
	 */
	private LoggerInfo.LogLevel mLogLevel       = LoggerInfo.LogLevel.VERBOSE;
	/**
	 * 默认log的tag
	 */
	private String              mTag            = "Log";
	/**
	 * 支持的解析库，Bundle,Intent,Map,Throwable etc
	 */
	private List<IParser>       mParseList      = null;
	/**
	 * 打印方式完全自己控制
	 */
	private IPrinter            mPrinter        = PrettyPrinter.getInstance();
	/**
	 * log是否显示线程名字
	 */
	private boolean             mShowThreadName = true;
	/**
	 * log是否显示堆栈信息
	 */
	private boolean             mShowStackTrace = true;
	/**
	 * log最后基层堆栈信息（堆栈信息是打印后面堆栈，要是全部打印层次太深了）
	 */
	private int                 mStackCount     = 1;

	public LoggerBuild() {
		/**
		 * 添加默认的解析对象
		 */
		addParserClass(DEFAULT_PARSE_CLASS);
	}

	private LoggerBuild addParserClass(Class<? extends IParser>... classes) {
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

	/**
	 * 添加自定义的log对象解析
	 *
	 * @param parser 自定义log对象解析的实现
	 * @return LoggerBuild
	 */
	public LoggerBuild addParse(IParser parser) {
		mParseList.add(0, parser);
		return this;
	}

	/**
	 * 获取支持的log解析对象列表
	 *
	 * @return IParser list
	 */
	public List<IParser> getParseList() {
		return mParseList;
	}

	/**
	 * 设置默认的log tag
	 *
	 * @param tag tag
	 * @return LoggerBuild
	 */
	public LoggerBuild setTag(String tag) {
		mTag = tag;
		return this;
	}

	/**
	 * 获取log默认tag
	 *
	 * @return tag
	 */
	public String getTag() {
		return mTag;
	}

	/**
	 * 设置log等级
	 *
	 * @param level log等级
	 * @return LoggerBuild
	 */
	public LoggerBuild setLogLevel(LoggerInfo.LogLevel level) {
		mLogLevel = level;
		return this;
	}

	/**
	 * 获取log等级
	 *
	 * @return level
	 */
	public LoggerInfo.LogLevel getLogLevel() {
		return mLogLevel;
	}

	/**
	 * 设置是否允许log
	 *
	 * @param enable 是否允许
	 * @return LoggerBuild
	 */
	public LoggerBuild setLogEnable(boolean enable) {
		mLogEnable = enable;
		return this;
	}

	/**
	 * 是否允许log
	 *
	 * @return boolean
	 */
	public boolean isLogEnable() {
		return mLogEnable;
	}

	/**
	 * 设置log的输出格式
	 *
	 * @param printer log输出格式
	 * @return LoggerBuild
	 */
	public LoggerBuild setPrinter(IPrinter printer) {
		mPrinter = printer;
		return this;
	}

	/**
	 * 获取log输出格式
	 *
	 * @return IPrinter
	 */
	public IPrinter getPrinter() {
		return mPrinter;
	}

	/**
	 * 设置log是否显示线程名字
	 *
	 * @param show 是否显示线程名字
	 * @return LoggerBuild
	 */
	public LoggerBuild setShowThreadName(boolean show) {
		mShowThreadName = show;
		return this;
	}

	/**
	 * 是否log显示线程名字
	 *
	 * @return boolean
	 */
	public boolean isShowThreadName() {
		return mShowThreadName;
	}

	/**
	 * 是否打印log堆栈信息
	 *
	 * @param show 是否打印log堆栈信息
	 * @return LoggerBuild
	 */
	public LoggerBuild setShowStackTrace(boolean show) {
		mShowStackTrace = show;
		return this;
	}

	/**
	 * 是否打印log堆栈信息
	 *
	 * @return boolean
	 */
	public boolean isShowStackTrace() {
		return mShowStackTrace;
	}

	/**
	 * 设置log后面几级堆栈信息
	 *
	 * @param count int
	 * @return LoggerBuild
	 */
	public LoggerBuild setStackCount(int count) {
		mStackCount = count;
		return this;
	}

	/**
	 * 获取log后面几级堆栈信息
	 * @return int
	 */
	public int getStackCount() {
		return mStackCount;
	}

}
