package com.tuacy.logger.parse.impl;


import com.tuacy.logger.parse.IParser;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class ThrowableParse implements IParser<Throwable> {

	@Override
	public Class<Throwable> parseClassType() {
		return Throwable.class;
	}

	@Override
	public String parseString(Throwable throwable, List<IParser> parsers) {
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
