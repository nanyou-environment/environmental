package com.nanyou.framework.jdbc;

public class OracleUtils {
	public static String getPaginationString(String sql, Integer start, Integer limit) {

		Integer startRow = new Integer(start.intValue());
		Integer endRow = new Integer(start.intValue() + limit.intValue());
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		pagingSelect.append(sql);

		pagingSelect.append(") row_   " + " where rownum <= " + endRow + ")" + " where rownum_ > "
				+ startRow);

		if (isForUpdate) {
			pagingSelect.append(" for update");
		}

		return pagingSelect.toString();
	}

}
