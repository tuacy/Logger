package com.tuacy.logger.parse.impl;

import android.os.Bundle;


import com.tuacy.logger.LoggerTransform;
import com.tuacy.logger.parse.IParser;
import com.tuacy.logger.parse.TabUtils;

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
		return parse(bundle, 0, parsers);
	}

	@Override
	public String parse(Bundle bundle, int tab, List<IParser> parsers) {
		if (bundle != null) {
			StringBuilder builder = new StringBuilder(bundle.getClass().getName() + " [" + LINE_SEPARATOR);
			for (String key : bundle.keySet()) {
				builder.append(TabUtils.getTabString(tab + 1));
				builder.append(String.format("'%s' => %s " + LINE_SEPARATOR, key, LoggerTransform.transformToString(bundle.get(key), parsers)));
			}
			builder.append(TabUtils.getTabString(tab));
			builder.append("]");
			return builder.toString();
		}
		return null;
	}
}
