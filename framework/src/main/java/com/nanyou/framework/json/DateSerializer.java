package com.nanyou.framework.json;

import java.io.Writer;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateSerializer implements JSONSerializer {
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final SimpleDateFormat _defDateTimeFmt = new SimpleDateFormat(
			DEFAULT_DATETIME_FORMAT);

	public static final SimpleDateFormat _defDateFmt = new SimpleDateFormat(
			DEFAULT_DATE_FORMAT);

	public void serialize(Object data, Writer os) {
		try {
			Date date = (Date) data;
			if (date instanceof Timestamp) {
				String value = "\"" + _defDateTimeFmt.format(date) + "\"";
				os.write(value);
			}else{
				String value = "\"" + _defDateFmt.format(date) + "\"";
				os.write(value);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public Object deseialize(Class paramType, String json) {
		if (json.indexOf(':') > 0) {
			return _defDateTimeFmt.parse(json, new ParsePosition(0));
		} else {
			return _defDateFmt.parse(json, new ParsePosition(0));
		}
	}

}
