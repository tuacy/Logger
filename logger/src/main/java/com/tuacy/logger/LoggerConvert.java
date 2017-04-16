package com.tuacy.logger;


import com.tuacy.logger.parse.IParser;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class LoggerConvert {

	// 解析属性最大层级
	public static final int MAX_CHILD_LEVEL = 2;

	private static final String STRING_OBJECT_NULL = "Object[object is null]";

	/**
	 * object对象转换为String
	 *
	 * @param object  对象
	 * @param parsers 我们Log库提供转换
	 * @return string
	 */
	public static String objectToString(Object object, List<IParser> parsers) {
		return objectToString(object, parsers, 0);
	}

	/**
	 * object对象转换为String
	 *
	 * @param object     对象
	 * @param parsers    我们Log库提供转换
	 * @param childLevel 等级
	 * @return string
	 */
	public static String objectToString(Object object, List<IParser> parsers, int childLevel) {
		if (object == null) {
			return STRING_OBJECT_NULL;
		}

		/**
		 * 内部解析器去解析
		 */
		if (parsers != null && parsers.size() > 0) {
			for (IParser parser : parsers) {
				if (parser.classType().isAssignableFrom(object.getClass())) {
					return parser.parse(object, parsers);
				}
			}
		}
		/**
		 * 数组
		 */
		if (isArray(object)) {
			return parseArray(object, parsers);
		}
		if (object.toString().startsWith(object.getClass().getName() + "@")) {
			/**
			 * 要转换的是没有实现toString()函数的对象
			 */
			StringBuilder builder = new StringBuilder();
			getClassFields(object.getClass(), builder, object, false, childLevel, parsers);
			Class superClass = object.getClass().getSuperclass();
			while (!superClass.equals(Object.class)) {
				getClassFields(superClass, builder, object, true, childLevel, parsers);
				superClass = superClass.getSuperclass();
			}
			return builder.toString();
		} else {
			return object.toString();
		}

	}

	/**
	 * 判断对象是否是数组
	 *
	 * @param object 对象
	 * @return 是否是数组
	 */
	public static boolean isArray(Object object) {
		return object.getClass().isArray();
	}

	/**
	 * 将数组内容转化为字符串
	 *
	 * @param array   数组
	 * @param parsers 内部支持的解析
	 * @return 字符串
	 */
	public static String parseArray(Object array, List<IParser> parsers) {
		StringBuilder result = new StringBuilder();
		traverseArray(result, array, parsers);
		return result.toString();
	}

	/**
	 * 遍历数组
	 */
	private static void traverseArray(StringBuilder result, Object array, List<IParser> parsers) {
		if (isArray(array)) {
			if (getArrayDimension(array) == 1) {
				switch (getType(array)) {
					case 'I':
						result.append(Arrays.toString((int[]) array));
						break;
					case 'D':
						result.append(Arrays.toString((double[]) array));
						break;
					case 'Z':
						result.append(Arrays.toString((boolean[]) array));
						break;
					case 'B':
						result.append(Arrays.toString((byte[]) array));
						break;
					case 'S':
						result.append(Arrays.toString((short[]) array));
						break;
					case 'J':
						result.append(Arrays.toString((long[]) array));
						break;
					case 'F':
						result.append(Arrays.toString((float[]) array));
						break;
					case 'L':
						Object[] objects = (Object[]) array;
						result.append("[");
						for (int i = 0; i < objects.length; ++i) {
							result.append(objectToString(objects[i], parsers));
							if (i != objects.length - 1) {
								result.append(",");
							}
						}
						result.append("]");
						break;
					default:
						result.append(Arrays.toString((Object[]) array));
						break;
				}
			} else {
				result.append("[");
				for (int i = 0; i < ((Object[]) array).length; i++) {
					traverseArray(result, ((Object[]) array)[i], parsers);
					if (i != ((Object[]) array).length - 1) {
						result.append(",");
					}
				}
				result.append("]");
			}
		} else {
			result.append("not a array!!");
		}
	}

	/**
	 * 获取数组的纬度
	 *
	 * @param object 数组
	 * @return dimension
	 */
	public static int getArrayDimension(Object object) {
		int dim = 0;
		for (int i = 0; i < object.toString().length(); ++i) {
			if (object.toString().charAt(i) == '[') {
				++dim;
			} else {
				break;
			}
		}
		return dim;
	}

	/**
	 * 获取数组类型
	 *
	 * @param object 如L为int型
	 * @return type
	 */
	public static char getType(Object object) {
		if (isArray(object)) {
			String str = object.toString();
			return str.substring(str.lastIndexOf("[") + 1, str.lastIndexOf("[") + 2).charAt(0);
		}
		return 0;
	}

	/**
	 * 是否为静态内部类
	 *
	 * @param cla 要判断的类
	 * @return 是否
	 */
	public static boolean isStaticInnerClass(Class cla) {
		if (cla != null && cla.isMemberClass()) {
			int modifiers = cla.getModifiers();
			if ((modifiers & Modifier.STATIC) == Modifier.STATIC) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 拼接class的字段和值
	 *
	 * @param o           对象
	 * @param isSubClass  是否class
	 * @param childOffset 递归解析属性的层级
	 */
	private static void getClassFields(Class cla,
									   StringBuilder builder,
									   Object o,
									   boolean isSubClass,
									   int childOffset,
									   List<IParser> parsers) {
		if (cla.equals(Object.class)) {
			return;
		}
		if (isSubClass) {
			builder.append(IParser.LINE_SEPARATOR + IParser.LINE_SEPARATOR + "=> ");
		}
		String breakLine = "";
		builder.append(cla.getSimpleName() + " {");
		/**
		 * 获得类的所有申明的字段
		 */
		Field[] fields = cla.getDeclaredFields();
		for (int i = 0; i < fields.length; ++i) {
			Field field = fields[i];
			field.setAccessible(true);
			if (cla.isMemberClass() && !isStaticInnerClass(cla) && i == 0) {
				continue;
			}
			Object subObject = null;
			try {
				subObject = field.get(o);
			} catch (IllegalAccessException e) {
				subObject = e;
			} finally {
				if (subObject != null) {
					// 解决Instant Run情况下内部类死循环的问题
					if (!isStaticInnerClass(cla) && (field.getName().equals("$change") || field.getName().equalsIgnoreCase("this$0"))) {
						continue;
					}
					if (subObject instanceof String) {
						subObject = "\"" + subObject + "\"";
					} else if (subObject instanceof Character) {
						subObject = "\'" + subObject + "\'";
					}
					if (childOffset < MAX_CHILD_LEVEL) {
						subObject = objectToString(subObject, parsers, childOffset + 1);
					}
				}
				String formatString = breakLine + "%s = %s, ";
				builder.append(String.format(formatString, field.getName(), subObject == null ? "null" : subObject.toString()));
			}
		}
		if (builder.toString().endsWith("{")) {
			builder.append("}");
		} else {
			builder.replace(builder.length() - 2, builder.length() - 1, breakLine + "}");
		}
	}

}
