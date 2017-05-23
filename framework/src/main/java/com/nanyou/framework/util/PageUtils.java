package com.nanyou.framework.util;

public class PageUtils {
	
	public static final Integer pageSize = 10;//默认每页数据�?

	/**
	 * 根据页数和每页数据量，取得开始索�?
	 * 
	 * @param page 第几�?
	 * @param rows 每页数据�?
	 * @return �?��索引
	 */
	public static Integer getStartNum(Integer pageNo, Integer pageSize) {
		if (pageNo != null) {
			return (pageNo - 1) * pageSize;
		}
		return 0;
	}
	
	public static Integer getStartNum(Integer pageNo) {
		return getStartNum(pageNo,pageSize);
	}

	/**
	 * 根据数据总量和每页数据量，取得�?页数
	 * 
	 * @param count 数据总量
	 * @param pageSize 每页数据�?
	 * @return 总页�?
	 */
	public static Integer getTotalPage(Integer count, Integer pageSize) {
		if (count % pageSize == 0) {
			return count / pageSize;
		} else {
			return count / pageSize + 1;
		}
	}

	public static Integer getTotalPage(Integer count) {
		return getTotalPage(count, pageSize);
	}
	
}
