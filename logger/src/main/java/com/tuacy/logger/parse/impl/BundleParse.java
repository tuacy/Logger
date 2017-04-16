package com.tuacy.logger.parse.impl;

import android.os.Bundle;


import com.tuacy.logger.LoggerConvert;
import com.tuacy.logger.parse.IParser;

import java.util.List;

/**
 * Bundle对象转换为log字符串
 */
public class BundleParse implements IParser<Bundle> {

	@Override
	public Class<Bundle> classType() {
		return Bundle.class;
	}

	@Override
	public String parse(Bundle bundle, List<IParser> parsers) {
		if (bundle != null) {
			StringBuilder builder = new StringBuilder(bundle.getClass().getName() + " [" + LINE_SEPARATOR);
			for (String key : bundle.keySet()) {
				builder.append(String.format("'%s' => %s " + LINE_SEPARATOR, key, LoggerConvert.objectToString(bundle.get(key), parsers)));
			}
			builder.append("]");
			return builder.toString();
		}
		return null;
	}
}
