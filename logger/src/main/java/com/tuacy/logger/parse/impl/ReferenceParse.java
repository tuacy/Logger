package com.tuacy.logger.parse.impl;


import com.tuacy.logger.LoggerTransform;
import com.tuacy.logger.parse.IParser;
import com.tuacy.logger.parse.TabUtils;

import java.lang.ref.Reference;
import java.util.List;

/**
 * Reference对象转换为log字符串
 */
public class ReferenceParse implements IParser<Reference> {

	@Override
	public Class<Reference> classType() {
		return Reference.class;
	}

	@Override
	public String parse(Reference reference, List<IParser> parsers) {
		return parse(reference, 0, parsers);
	}

	@Override
	public String parse(Reference reference, int tab, List<IParser> parsers) {
		Object actual = reference.get();
		StringBuilder builder = new StringBuilder(
			reference.getClass().getSimpleName() + "<" + actual.getClass().getSimpleName() + "> {" + LINE_SEPARATOR);
		builder.append(TabUtils.getTabString(tab + 1));
		builder.append(LoggerTransform.transformToString(actual, tab + 1, parsers));
		builder.append(LINE_SEPARATOR);
		builder.append(TabUtils.getTabString(tab));
		builder.append("}");
		return builder.toString();
	}
}
