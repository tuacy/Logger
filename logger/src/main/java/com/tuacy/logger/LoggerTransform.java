package com.tuacy.logger;

import com.tuacy.logger.parse.IParser;
import com.tuacy.logger.parse.TabUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class LoggerTransform {

	// 解析class extends最大层级
	private static final int MAX_CHILD_LEVEL = 3;

	private static final String STRING_OBJECT_NULL = "Object[object is null]";

	/**
	 * object对象转换为String
	 *
	 * @param object  对象
	 * @param parsers 我们Log库提供转换
	 * @return string
	 */
	public static String transformToString(Object object, List<IParser> parsers) {
		return transformToString(object, 0, parsers, 0);
	}

	/**
	 * object对象转换为String
	 *
	 * @param object  对象
	 * @param tab     tab
	 * @param parsers 我们Log库提供转换
	 * @return string
	 */
	public static String transformToString(Object object, int tab, List<IParser> parsers) {
		return transformToString(object, tab, parsers, 0);
	}

	/**
	 * object对象转换为String
	 *
	 * @param object     对象
	 * @param parsers    我们Log库提供转换
	 * @param childLevel 等级
	 */
	public static String transformToString(Object object, List<IParser> parsers, int childLevel) {
		return transformToString(object, 0, parsers, childLevel);
	}

	/**
	 * object对象转换为String
	 *
	 * @param object     对象
	 * @param tab        tab
	 * @param parsers    我们Log库提供转换
	 * @param childLevel 等级
	 * @return string
	 */
	public static String transformToString(Object object, int tab, List<IParser> parsers, int childLevel) {
		if (object == null) {
			return STRING_OBJECT_NULL;
		}

		/**
		 * 内部解析器去解析
		 */
		if (parsers != null && parsers.size() > 0) {
			for (IParser parser : parsers) {
				if (parser.classType().isAssignableFrom(object.getClass())) {
					return parser.parse(object, tab, parsers);
				}
			}
		}
		/**
		 * 数组
		 */
		if (isArray(object)) {
			return parseArray(object, tab, parsers);
		}
		if (object.toString().startsWith(object.getClass().getName() + "@")) {
			/**
			 * 要转换的是没有重写toString()函数的对象
			 */
			StringBuilder builder = new StringBuilder();
			getClassFields(object.getClass(), tab, builder, object, false, childLevel, parsers);
			Class superClass = object.getClass().getSuperclass();
			while (!superClass.equals(Object.class)) {
				getClassFields(superClass, tab + 1, builder, object, true, childLevel, parsers);
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
	 * @param tab     tab缩进等级
	 * @param parsers 内部支持的解析
	 * @return 字符串
	 */
	public static String parseArray(Object array, int tab, List<IParser> parsers) {
		StringBuilder result = new StringBuilder();
		traverseArray(result, array, tab, parsers);
		return result.toString();
	}

	/**
	 * 遍历数组
	 */
	private static void traverseArray(StringBuilder result, Object array, int tab, List<IParser> parsers) {
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
							result.append(transformToString(objects[i], parsers));
							if (i != objects.length - 1) {
								result.append(",");
							}
						}
						result.append(TabUtils.getTabString(tab));
						result.append("]");
						break;
					default:
						result.append(Arrays.toString((Object[]) array));
						break;
				}
			} else {
				result.append("[");
				for (int i = 0; i < ((Object[]) array).length; i++) {
					traverseArray(result, ((Object[]) array)[i], tab + 1, parsers);
					if (i != ((Object[]) array).length - 1) {
						result.append(",");
					}
				}
				result.append(TabUtils.getTabString(tab));
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
	 * @param cla         class
	 * @param tab         tab缩进等级
	 * @param builder     StringBuilder
	 * @param o           Object
	 * @param isSubClass  是否是super class
	 * @param childOffset super等级
	 * @param parsers     内部支持的解析
	 */
	private static void getClassFields(Class cla,
									   int tab,
									   StringBuilder builder,
									   Object o,
									   boolean isSubClass,
									   int childOffset,
									   List<IParser> parsers) {
		if (cla.equals(Object.class)) {
			return;
		}
		if (isSubClass) {
			builder.append(IParser.LINE_SEPARATOR + TabUtils.getTabString(tab) + "=> ");
		}
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
			Object subObject;
			try {
				subObject = field.get(o);
			} catch (IllegalAccessException e) {
				subObject = e;
			}
			if (field.getName().equals("$change") || field.getName().equalsIgnoreCase("this$0") ||
				field.getName().equals("serialVersionUID")) {
				continue;
			}
			if (subObject != null) {
				if (subObject instanceof String) {
					subObject = "\"" + subObject + "\"";
				} else if (subObject instanceof Character) {
					subObject = "\'" + subObject + "\'";
				}
				if (childOffset < MAX_CHILD_LEVEL) {
					subObject = transformToString(subObject, parsers, childOffset + 1);
				}
			}
			builder.append(IParser.LINE_SEPARATOR);
			builder.append(TabUtils.getTabString(tab + 1));
			String formatString = "%s = %s";
			builder.append(String.format(formatString, field.getName(), subObject == null ? "null" : subObject.toString()));

		}
		builder.append(IParser.LINE_SEPARATOR);
		builder.append(TabUtils.getTabString(tab));
		builder.append("}");
	}

}
