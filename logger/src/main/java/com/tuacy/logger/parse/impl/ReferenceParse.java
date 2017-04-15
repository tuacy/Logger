package com.tuacy.logger.parse.impl;


import com.tuacy.logger.LoggerConvert;
import com.tuacy.logger.parse.IParser;

import java.lang.ref.Reference;
import java.util.List;

public class ReferenceParse implements IParser<Reference> {

	@Override
	public Class<Reference> parseClassType() {
		return Reference.class;
	}

	@Override
	public String parseString(Reference reference, List<IParser> parsers) {
		Object actual = reference.get();
		StringBuilder builder = new StringBuilder(reference.getClass().getSimpleName() + "<"
												  + actual.getClass().getSimpleName() + "> {");
		builder.append("→" + LoggerConvert.objectToString(actual, parsers));
		return builder.toString() + "}";
	}
}
