package com.nanyou.framework.util;

import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

import com.nanyou.framework.spring.SpringContextUtil;

public class DBUtils {
	public static Long getNextLongID() {
		DataFieldMaxValueIncrementer incrementer = (DataFieldMaxValueIncrementer) SpringContextUtil
				.getBean("dataFieldMaxValueIncrementer");
		return new Long(incrementer.nextLongValue());
	}

}
