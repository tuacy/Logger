package com.tuacy.logger.parse.impl;


import com.tuacy.logger.parse.IParser;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * Throwable对象转换为log字符串
 */
public class ThrowableParse implements IParser<Throwable> {

	@Override
	public Class<Throwable> classType() {
		return Throwable.class;
	}

	@Override
	public String parse(Throwable throwable, List<IParser> parsers) {
		return parse(throwable, 0, parsers);
	}

	@Override
	public String parse(Throwable throwable, int tab, List<IParser> parsers) {
		return getStackTraceString(throwable);
	}

	private String getStackTraceString(Throwable t) {
		StringWriter sw = new StringWriter(256);
		PrintWriter pw = new PrintWriter(sw, false);
		t.printStackTrace(pw);
		pw.flush();
		return sw.toString();
	}
}
