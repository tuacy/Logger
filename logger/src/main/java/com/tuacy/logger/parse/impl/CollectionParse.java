package com.tuacy.logger.parse.impl;


import com.tuacy.logger.LoggerTransform;
import com.tuacy.logger.parse.IParser;
import com.tuacy.logger.parse.TabUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Collection对象转换为log字符串
 */
public class CollectionParse implements IParser<Collection> {

	@Override
	public Class<Collection> classType() {
		return Collection.class;
	}

	@Override
	public String parse(Collection collection, List<IParser> parsers) {
		return parse(collection, 0, parsers);
	}

	@Override
	public String parse(Collection collection, int tab, List<IParser> parsers) {
		String simpleName = collection.getClass().getName();
		String msg = "%s size = %d [" + LINE_SEPARATOR;
		msg = String.format(msg, simpleName, collection.size());
		if (!collection.isEmpty()) {
			Iterator iterator = collection.iterator();
			int flag = 0;
			while (iterator.hasNext()) {
				msg = msg + TabUtils.getTabString(tab + 1);
				String itemString = "[%d]:%s%s";
				Object item = iterator.next();
				msg += String.format(Locale.getDefault(), itemString, flag, LoggerTransform.transformToString(item, tab + 1, parsers),
									 flag++ < collection.size() - 1 ? "," + LINE_SEPARATOR : LINE_SEPARATOR);
			}
		}
		msg = msg + TabUtils.getTabString(tab);
		return msg + "]";
	}
}
