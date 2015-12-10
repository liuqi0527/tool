package game.tool.clazz;

import game.tool.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectUtils {

	private static final Logger logger = LoggerFactory.getLogger(ReflectUtils.class);

	private static final Map<Class<?>, Map<String, Setter>> settersMap = new HashMap();
	private static final Map<Class<?>, Map<String, Getter>> gettersMap = new HashMap();

	@SuppressWarnings("unchecked")
	@Deprecated
	public static void setFieldValue(Object target, String fname, Class ftype,
			Object fvalue) {
		setFieldValue(target, fname, fvalue);
	}

	@SuppressWarnings("unchecked")
	public static void setFieldValue(Object target, String fname, Object fvalue) {
		Class<?> clazz = target.getClass();
		Map<String, Setter> setters = settersMap.get(clazz);
		if (setters == null) {
			synchronized (settersMap) {
				setters = settersMap.get(clazz);
				if (setters == null) {
					setters = new HashMap();
					settersMap.put(clazz, setters);
				}
			}
		}

		Setter setter = setters.get(fname);
		if (setter == null) {
			synchronized (setters) {
				setter = setters.get(fname);
				if (setter == null) {
					setter = Setter.getSetter(clazz, fname);
					setters.put(fname, setter);
				}
			}
		}

		setter.set(target, fvalue);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Object target, String fname) {
		Class clazz = !(target instanceof Class) ? target.getClass()
				: (Class) target;

		Map<String, Getter> getters = gettersMap.get(clazz);
		if (getters == null) {
			synchronized (gettersMap) {
				getters = gettersMap.get(clazz);
				if (getters == null) {
					getters = new HashMap();
					gettersMap.put(clazz, getters);
				}
			}
		}

		Getter<T> getter = getters.get(fname);
		if (getter == null) {
			synchronized (getters) {
				getter = getters.get(fname);
				if (getter == null) {
					getter = Getter.getGetter(clazz, fname);
					getters.put(fname, getter);
				}
			}
		}

		return getter.get(target);
	}

	private static abstract class Setter {
		private static Setter getSetter(Class clazz, String propertyName) {
			try {
				String methodName = "set"
						+ StringUtils.capitalize(propertyName);
				for (Method method : clazz.getMethods()) {
					if (method.getName().equals(methodName)) {
						if (method.getParameterTypes().length == 1) {
							return new MethodSetter(method);
						}
					}
				}
			} catch (Exception e) {
			}

			do {
				try {
					Field declaredField = clazz.getDeclaredField(propertyName);
					if (!declaredField.isAccessible()) {
						declaredField.setAccessible(true);
					}
					return new FieldSetter(declaredField);
				} catch (Exception e2) {
				}
			} while ((clazz = clazz.getSuperclass()) != null);

			throw new RuntimeException(String.format(
					"no setters of %s in type %s", propertyName, clazz));
		}

		protected abstract void set(Object o, Object value);
	}

	private static class MethodSetter extends Setter {
		private Method method;

		private MethodSetter(Method method) {
			this.method = method;
		}

		protected void set(Object o, Object value) {
			try {
				method.invoke(o, value);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static class FieldSetter extends Setter {
		private Field field;

		private FieldSetter(Field method) {
			this.field = method;
		}

		protected void set(Object o, Object value) {
			try {
				field.set(o, value);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static abstract class Getter<T> {
		private static Getter getGetter(Class clazz, String propertyName) {
			String mname = StringUtils.capitalize(propertyName);
			try {
				Method method = clazz.getMethod("get" + mname);
				return new MethodGetter(method);
			} catch (NoSuchMethodException me) {
				if (logger.isDebugEnabled()) {
					// logger.debug("Cannot find the getter method, try access field directly:"+target+"#"+fname);
				}
				try {
					Method method = clazz.getMethod("is" + mname);
					return new MethodGetter(method);
				} catch (NoSuchMethodException me2) {
					try {
						Field field = clazz.getDeclaredField(propertyName);
						if (!field.isAccessible()) {
							field.setAccessible(true);
						}
						return new FieldGetter(field);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		}

		protected abstract T get(Object o);
	}

	private static class MethodGetter<T> extends Getter<T> {
		private Method method;

		private MethodGetter(Method method) {
			this.method = method;
		}

		protected T get(Object o) {
			try {
				return (T) method.invoke(o);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static class FieldGetter<T> extends Getter<T> {
		private Field field;

		private FieldGetter(Field method) {
			this.field = method;
		}

		protected T get(Object o) {
			try {
				return (T) field.get(o);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}
