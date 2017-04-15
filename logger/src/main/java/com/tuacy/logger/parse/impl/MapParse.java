package com.tuacy.logger.parse.impl;


import com.tuacy.logger.LoggerConvert;
import com.tuacy.logger.parse.IParser;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapParse implements IParser<Map> {

	@Override
	public Class<Map> parseClassType() {
		return Map.class;
	}

	@Override
	public String parseString(Map map, List<IParser> parsers) {
		String msg = map.getClass().getName() + " [" + LINE_SEPARATOR;
		Set<Object> keys = map.keySet();
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
			msg += String.format(itemString, LoggerConvert.objectToString(key, parsers),
								 LoggerConvert.objectToString(value, parsers));
		}
		return msg + "]";
	}
}
