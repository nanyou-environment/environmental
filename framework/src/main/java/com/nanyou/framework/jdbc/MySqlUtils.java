package com.nanyou.framework.jdbc;

public class MySqlUtils {
	public static String getPaginationString(String sql, Integer start, Integer limit) {
		return sql + " limit " + start + "," + limit;
	}
	
	/*
	
	public String getRemotePaginationString(String sql, Integer start, Integer limit,String sort,String dir) {
		if(sort!=null&&dir!=null){
			sql = "select * from ("+sql +") as name order by "+sort+" "+dir;
		}
		if(start!=null&&limit!=null){
			sql=getPaginationString(sql, start, limit);
		}
		return sql;
	}
	
	public String getMaxCode(String table, String columnPrefix, int level, Long parentId) {
		String sql = null;
		if (parentId == null || parentId.longValue() == 0) {
			sql =
					"select max(" + columnPrefix + "_CODE) from " + table + " where " + columnPrefix + "_LEVEL=" + level
							+ " and " + columnPrefix + "_PARE_ID is null";
		} else {
			sql =
					"select max(" + columnPrefix + "_CODE) from " + table + " where " + columnPrefix + "_LEVEL=" + level
							+ " and " + columnPrefix + "_PARE_ID=" + parentId;
		}

		return (String) getJdbcTemplate().queryForObject(sql, String.class);
	}
	*/
	
}
