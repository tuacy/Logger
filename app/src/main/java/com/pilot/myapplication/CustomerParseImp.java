package com.pilot.myapplication;


import com.pilot.myapplication.bean.CustomerParseBean;
import com.tuacy.logger.parse.IParser;
import com.tuacy.logger.parse.TabUtils;

import java.util.List;

public class CustomerParseImp implements IParser<CustomerParseBean> {

	@Override
	public Class<CustomerParseBean> classType() {
		return null;
	}

	@Override
	public String parse(CustomerParseBean customerParseBean, List<IParser> parsers) {
		return parse(customerParseBean, 0, parsers);
	}

	@Override
	public String parse(CustomerParseBean customerParseBean, int tab, List<IParser> parsers) {
		if (customerParseBean != null) {
			StringBuilder builder = new StringBuilder(customerParseBean.getClass().getName() + " [" + LINE_SEPARATOR);
			builder.append(TabUtils.getTabString(tab + 1));
			builder.append(String.format("'%s' => %s " + LINE_SEPARATOR, "name", customerParseBean.getNanme()));
			builder.append(TabUtils.getTabString(tab + 1));
			builder.append(String.format("'%s' => %s " + LINE_SEPARATOR, "password", customerParseBean.getPasswrod()));
			builder.append(TabUtils.getTabString(tab));
			builder.append("]");
			return builder.toString();
		}
		return null;
	}
}
