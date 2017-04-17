package com.tuacy.logger.parse;

import java.util.List;

/**
 * 对象转换成字符串的接口
 *
 * @param <T> 泛型，任何我们想要log的对象
 */
public interface IParser<T> {

	/**
	 * 换行
	 */
	String LINE_SEPARATOR = System.getProperty("line.separator");

	/**
	 * class 的类型
	 *
	 * @return class type
	 */
	Class<T> classType();

	/**
	 * 把我们想要输出的对象转换成String
	 *
	 * @param t       对象
	 * @param parsers 支持的转换库(对象里面可能还有对象，可能内部还得在转换)
	 * @return string
	 */
	String parse(T t, List<IParser> parsers);

	/**
	 * 把我们想要输出的对象转换成String
	 *
	 * @param t       对象
	 * @param tab     缩进等级
	 * @param parsers 支持的转换库(对象里面可能还有对象，可能内部还得在转换)
	 * @return string
	 */
	String parse(T t, int tab, List<IParser> parsers);
}
