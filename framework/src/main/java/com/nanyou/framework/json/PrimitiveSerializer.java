package com.nanyou.framework.json;

import java.io.Writer;
import java.net.URLDecoder;

import com.nanyou.framework.util.JavaScriptUtil;

public class PrimitiveSerializer implements JSONSerializer {

	public void serialize(Object object, Writer os) {
		try {
			if (object.equals(Boolean.TRUE)) {
				os.write("true"); //$NON-NLS-1$ //$NON-NLS-2$
			} else if (object.equals(Boolean.FALSE)) {
				os.write("false"); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				os.write(JavaScriptUtil.escapeJavaScript(object.toString())); //$NON-NLS-1$
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Object deseialize(Class paramType, String value) {
		try {
			if (paramType == Boolean.TYPE || paramType == Boolean.class) {
				return Boolean.valueOf(value.trim());
			}

			if (paramType == Byte.TYPE || paramType == Byte.class) {
				if (value.length() == 0) {
					byte b = 0;
					return new Byte(b);
				}
				return new Byte(value.trim());
			}

			if (paramType == Short.TYPE || paramType == Short.class) {
				if (value.length() == 0) {
					short s = 0;
					return new Short(s);
				}
				return new Short(value.trim());
			}

			if (paramType == Character.TYPE || paramType == Character.class) {
				String decode = URLDecoder.decode(value, "UTF-8");
				if (decode.length() == 1) {
					return new Character(decode.charAt(0));
				} else {
					throw new IllegalArgumentException(
							"PrimitiveConverter String is too Long"); //$NON-NLS-1$
				}
			}

			if (paramType == Integer.TYPE || paramType == Integer.class) {
				if (value.length() == 0) {
					return new Integer(0);
				}
				return new Integer(value.trim());
			}

			if (paramType == Long.TYPE || paramType == Long.class) {
				if (value.length() == 0) {
					return new Long(0);
				}
				return new Long(value.trim());
			}

			if (paramType == Float.TYPE || paramType == Float.class) {
				if (value.length() == 0) {
					return new Float(0);
				}
				return new Float(URLDecoder.decode(value.trim(), "UTF-8"));
			}

			if (paramType == Double.TYPE || paramType == Double.class) {
				if (value.length() == 0) {
					return new Double(0);
				}
				return new Double(URLDecoder.decode(value.trim(), "UTF-8"));
			}

			throw new IllegalArgumentException(
					"Type is not primitive " + paramType.getName()); //$NON-NLS-1$
		} catch (Exception ex) {
			throw new IllegalArgumentException("Format error " + value); //$NON-NLS-1$
		}
	}

}
