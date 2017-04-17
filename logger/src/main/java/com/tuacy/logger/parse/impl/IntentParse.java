package com.tuacy.logger.parse.impl;

import android.content.Intent;
import android.text.TextUtils;
import android.util.SparseArray;

import com.tuacy.logger.parse.IParser;
import com.tuacy.logger.parse.TabUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Intent对象转换为log字符串
 */
public class IntentParse implements IParser<Intent> {

	private static SparseArray<String> mFlagMap = new SparseArray<>();

	static {
		Class cla = Intent.class;
		Field[] fields = cla.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getName().startsWith("FLAG_")) {
				int value = 0;
				try {
					Object object = field.get(cla);
					if (object instanceof Integer || object.getClass().getSimpleName().equals("int")) {
						value = (int) object;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (mFlagMap.get(value) == null) {
					mFlagMap.put(value, field.getName());
				}
			}
		}
	}

	@Override
	public Class<Intent> classType() {
		return Intent.class;
	}

	@Override
	public String parse(Intent intent, List<IParser> parsers) {
		return parse(intent, 0, parsers);
	}

	@Override
	public String parse(Intent intent, int tab, List<IParser> parsers) {
		StringBuilder builder = new StringBuilder(classType().getSimpleName() + " [" + LINE_SEPARATOR);
		builder.append(TabUtils.getTabString(tab + 1));
		builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Scheme", intent.getScheme()));
		builder.append(TabUtils.getTabString(tab + 1));
		builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Action", intent.getAction()));
		builder.append(TabUtils.getTabString(tab + 1));
		builder.append(String.format("%s = %s" + LINE_SEPARATOR, "DataString", intent.getDataString()));
		builder.append(TabUtils.getTabString(tab + 1));
		builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Type", intent.getType()));
		builder.append(TabUtils.getTabString(tab + 1));
		builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Package", intent.getPackage()));
		builder.append(TabUtils.getTabString(tab + 1));
		builder.append(String.format("%s = %s" + LINE_SEPARATOR, "ComponentInfo", intent.getComponent()));
		builder.append(TabUtils.getTabString(tab + 1));
		builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Flags", getFlags(intent.getFlags())));
		builder.append(TabUtils.getTabString(tab + 1));
		builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Categories", intent.getCategories()));
		builder.append(TabUtils.getTabString(tab + 1));
		builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Extras", new BundleParse().parse(intent.getExtras(), tab + 1, parsers)));
		builder.append(TabUtils.getTabString(tab));
		builder.append("]");
		return builder.toString();
	}

	private String getFlags(int flags) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < mFlagMap.size(); i++) {
			int flagKey = mFlagMap.keyAt(i);
			if ((flagKey & flags) == flagKey) {
				builder.append(mFlagMap.get(flagKey));
				builder.append(" | ");
			}
		}
		if (TextUtils.isEmpty(builder.toString())) {
			builder.append(flags);
		} else if (builder.indexOf("|") != -1) {
			builder.delete(builder.length() - 2, builder.length());
		}
		return builder.toString();
	}
}
