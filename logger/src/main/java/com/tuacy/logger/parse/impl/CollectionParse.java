package com.tuacy.logger.parse.impl;


import com.tuacy.logger.LoggerConvert;
import com.tuacy.logger.parse.IParser;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CollectionParse implements IParser<Collection> {

	@Override
	public Class<Collection> parseClassType() {
		return Collection.class;
	}

	@Override
	public String parseString(Collection collection, List<IParser> parsers) {
		String simpleName = collection.getClass().getName();
		String msg = "%s size = %d [" + LINE_SEPARATOR;
		msg = String.format(msg, simpleName, collection.size());
		if (!collection.isEmpty()) {
			Iterator<Object> iterator = collection.iterator();
			int flag = 0;
			while (iterator.hasNext()) {
				String itemString = "[%d]:%s%s";
				Object item = iterator.next();
				msg += String.format(itemString, flag, LoggerConvert.objectToString(item, parsers),
									 flag++ < collection.size() - 1 ? "," + LINE_SEPARATOR : LINE_SEPARATOR);
			}
		}
		return msg + "]";
	}
}
