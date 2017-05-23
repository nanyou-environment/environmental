package com.nanyou.framework.jdbc;

public class DB2Utils {
	private static String getRowNumber(String sql) {
		StringBuffer rownumber = new StringBuffer(50).append("rownumber() over(");

		int orderByIndex = sql.toLowerCase().indexOf("order by");

		if (orderByIndex > 0 && !hasDistinct(sql)) {
			rownumber.append(sql.substring(orderByIndex));
		}

		rownumber.append(") as rownumber_,");

		return rownumber.toString();
	}

	public static String getPaginationString(String sql, Integer start, Integer limit) {
		return getLimitString(sql, start.intValue() + 1, (int) (start.intValue() + limit.intValue()));
	}

	private static String getLimitString(String sql, int from, int to) {
		int startOfSelect = sql.toLowerCase().indexOf("select");

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100).append(sql.substring(0, startOfSelect)) // add
				// the
				// comment
				.append("select * from ( select ") // nest the main query in an
				// outer select
				.append(getRowNumber(sql)); // add the rownnumber bit into the
		// outer query select list

		if (hasDistinct(sql)) {
			pagingSelect.append(" row_.* from ( ") // add another (inner)
					// nested select
					.append(sql.substring(startOfSelect)) // add the main
					// query
					.append(" ) as row_"); // close off the inner nested select
		} else {
			pagingSelect.append(sql.substring(startOfSelect + 6)); // add the
			// main
			// query
		}

		pagingSelect.append(" ) as temp_ where rownumber_ ");

		// add the restriction to the outer select

		pagingSelect.append("between ").append(from).append(" and ").append(to);

		return pagingSelect.toString();
	}

	private static boolean hasDistinct(String sql) {
		return sql.toLowerCase().indexOf("select distinct") >= 0;
	}

	

}
