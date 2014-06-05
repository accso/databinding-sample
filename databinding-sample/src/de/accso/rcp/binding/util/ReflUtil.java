package de.accso.rcp.binding.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 
 * @author Jan Mischlich, Accso GmbH
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ReflUtil {

	private static Field findField(Class clazz, String fieldName) {
		if (clazz == null || fieldName == null)
			return null;
		Class<?> searchClass = clazz;
		while (!Object.class.equals(searchClass) && searchClass != null) {
			Field[] allFieldList = searchClass.getDeclaredFields();
			for (Field field : allFieldList) {
				if (fieldName.equals(field.getName())) {
					return field;
				}
			}
			searchClass = searchClass.getSuperclass();
		}

		return null;
	}

	public static Field getField(Class clazz, String fieldName) {
		if (clazz == null || fieldName == null)
			return null;
		Field field;
		try {
			int indexFirstPoint = fieldName.indexOf(".");
			if (indexFirstPoint < 0) {

				field = findField(clazz, fieldName);

			} else {
				String curField = fieldName.substring(0, indexFirstPoint);
				Field declaredField = findField(clazz, curField);
				if (declaredField == null)
					return null;
				else
					field = getField(declaredField.getType(),
							fieldName.substring(indexFirstPoint + 1));
			}
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		return field;
	}

	public static Method getMethod(Class clazz, String methodName) {
		if (clazz == null || methodName == null)
			return null;

		Method method;
		try {
			int indexFirstPoint = methodName.indexOf(".");
			if (indexFirstPoint < 0) {
				method = clazz.getDeclaredMethod(methodName);

			} else {
				String curField = methodName.substring(0, indexFirstPoint);
				Field declaredField = findField(clazz, curField);
				if (declaredField == null)
					return null;
				else
					method = getMethod(declaredField.getType(),
							methodName.substring(indexFirstPoint + 1));
			}
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		return method;
	}

	private static String createGetMethod(String fieldName) {
		return "get" + Character.toUpperCase(fieldName.charAt(0))
				+ fieldName.substring(1);
	}

	private static String createSetMethod(String fieldName) {
		return "set" + Character.toUpperCase(fieldName.charAt(0))
				+ fieldName.substring(1);
	}

	public static void setProperty(Object source, String propertyKey,
			Object value) {
		try {
			Class clazz = source.getClass();
			int indexFirstPoint = propertyKey.indexOf(".");
			if (indexFirstPoint < 0) {
				Method findMethod = clazz.getMethod(
						createSetMethod(propertyKey),
						getField(clazz, propertyKey).getType());
				findMethod.invoke(source, value);
				return;
			}
			String curField = propertyKey.substring(0, indexFirstPoint);
			Method method = clazz.getMethod(createGetMethod(curField));
			Object nastedObj = method.invoke(source);

			if (nastedObj != null)
				setProperty(nastedObj,
						propertyKey.substring(indexFirstPoint + 1), value);

		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public static Object getProperty(Object source, String propertyKey) {
		try {
			Class clazz = source.getClass();
			int indexFirstPoint = propertyKey.indexOf(".");

			if (indexFirstPoint < 0) {
				Method findMethod = clazz.getMethod(createGetMethod(propertyKey));
				return findMethod.invoke(source);
			}

			String curField = propertyKey.substring(0, indexFirstPoint);
			Method method = clazz.getMethod(createGetMethod(curField));
			Object nastedObj = method.invoke(source);

			if (nastedObj != null)
				return getProperty(nastedObj,
						propertyKey.substring(indexFirstPoint + 1));

		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		return null;
	}
}
