package com.tuacy.logger.parse.impl;


import com.tuacy.logger.LoggerTransform;
import com.tuacy.logger.parse.IParser;
import com.tuacy.logger.parse.TabUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * map对象转换为log字符串
 */
public class MapParse implements IParser<Map> {

	@Override
	public Class<Map> classType() {
		return Map.class;
	}

	@Override
	public String parse(Map map, List<IParser> parsers) {
		return parse(map, 0, parsers);
	}

	@Override
	public String parse(Map map, int tab, List<IParser> parsers) {
		String msg = map.getClass().getName() + " [" + LINE_SEPARATOR;
		Set keys = map.keySet();
		for (Object key : keys) {
			String itemString = "%s -> %s" + LINE_SEPARATOR;
			Object value = map.get(key);
			if (value != null) {
				if (value instanceof String) {
					value = "\"" + value + "\"";
				} else if (value instanceof Character) {
					value = "\'" + value + "\'";
				}
			}
			msg += TabUtils.getTabString(tab + 1) + String.format(itemString, LoggerTransform.transformToString(key, parsers),
																  LoggerTransform.transformToString(value, tab + 1, parsers));
		}
		msg = msg + TabUtils.getTabString(tab);
		return msg + "]";
	}
}
