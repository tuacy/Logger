package com.tuacy.logger.parse;

import java.util.List;

public interface IParser<T> {

	String LINE_SEPARATOR = System.getProperty("line.separator");

	Class<T> parseClassType();

	String parseString(T t, List<IParser> parsers);
}
