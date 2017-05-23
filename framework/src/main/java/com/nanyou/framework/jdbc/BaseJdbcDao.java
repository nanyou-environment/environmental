package com.nanyou.framework.jdbc;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.lang.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.jdbc.support.lob.LobHandler;

import com.nanyou.framework.util.SqlUtils;
import com.nanyou.framework.jdbc.DB2Support;
import com.nanyou.framework.jdbc.DB2Utils;
import com.nanyou.framework.jdbc.MySqlSupport;
import com.nanyou.framework.jdbc.MySqlUtils;
import com.nanyou.framework.jdbc.OracleSupport;
import com.nanyou.framework.jdbc.OracleUtils;
import com.nanyou.framework.jdbc.SQLServerSupport;
import com.nanyou.framework.jdbc.SQLServerUtils;

public class BaseJdbcDao extends NamedParameterJdbcDaoSupport implements MySqlSupport {
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	public void init(){
		this.setDataSource(dataSource);
	}
	
	private LobHandler defaultLobHandler;

	private DataFieldMaxValueIncrementer dataFieldMaxValueIncrementer;

	public DataFieldMaxValueIncrementer getDataFieldMaxValueIncrementer() {
		return dataFieldMaxValueIncrementer;
	}

	public void setDataFieldMaxValueIncrementer(DataFieldMaxValueIncrementer dataFieldmaxValueIncrementer) {
		this.dataFieldMaxValueIncrementer = dataFieldmaxValueIncrementer;
	}

	public LobHandler getDefaultLobHandler() {
		return defaultLobHandler;
	}

	public void setDefaultLobHandler(LobHandler defaultLobHandler) {
		this.defaultLobHandler = defaultLobHandler;
	}

	public Long getNextLongID() {
		return new Long(getDataFieldMaxValueIncrementer().nextLongValue());
	}

	public String getPaginationString(String sql, Integer start, Integer limit) {
		if (this instanceof DB2Support){
			return DB2Utils.getPaginationString(sql, start, limit);
		}else if (this instanceof OracleSupport){
			return OracleUtils.getPaginationString(sql,start, limit);
		}else if (this instanceof SQLServerSupport){
			return SQLServerUtils.getPaginationString(sql, start, limit);
		}else if (this instanceof MySqlSupport){
			return MySqlUtils.getPaginationString(sql, start, limit);
		}
		return null;
	}
	
	public String getRemotePaginationString(String sql, Integer start, Integer limit,String sort,String dir) {
		if(sort!=null&&dir!=null){
			//判断去除原排序字�?
			int sortIndex = sql.lastIndexOf("ORDER BY");
			if (sortIndex == -1)
				sortIndex = sql.lastIndexOf("order by");
			if (sortIndex != -1) {
				String sortStr = sql.substring(sortIndex);
				if (sortStr.indexOf("FROM ") == -1 && sortStr.indexOf("AND ") == -1) {
					sql = sql.substring(0, sortIndex) +" order by "+sort+" "+dir;
				} else {
					sql = "select * from ("+sql +") as name order by "+sort+" "+dir;
				}
			} else {
				sql = sql +" order by "+sort+" "+dir;
			}
			
		}
		if(start!=null&&limit!=null){
			sql=getPaginationString(sql, start, limit);
		}
		return sql;
	}
	
	public String getTotalCountString(String sql){
		return "SELECT COUNT(*) FROM ("+sql+") CDD;";
	}

	public String loadSQL(String sqlId) {
		
		String simpleClassName = ClassUtils.getShortClassName(this.getClass().getName());
		String className = 
				this.getClass().getPackage().getName().replace('.', '/') + "/sql/" + simpleClassName
						+ ".sql";
		String sql =  SqlUtils.loadSQL(className, sqlId);
		if (sql == null) {
			simpleClassName = ClassUtils.getShortClassName(this.getClass().getSuperclass().getName());
			className = 
					this.getClass().getSuperclass().getPackage().getName().replace('.', '/') + "/sql/" + simpleClassName
							+ ".sql";
			sql =  SqlUtils.loadSQL(className, sqlId);
		}
		return sql;
	}

	public String loadSQL(String sqlId, Map parameters) {
		String simpleClassName = ClassUtils.getShortClassName(this.getClass().getName());
		String className =
				this.getClass().getPackage().getName().replace('.', '/') + "/sql/" + simpleClassName
						+ ".sql";
		String sql = SqlUtils.loadSQL(className, sqlId, parameters);
		if (sql == null) {
			simpleClassName = ClassUtils.getShortClassName(this.getClass().getSuperclass().getName());
			className = 
					this.getClass().getSuperclass().getPackage().getName().replace('.', '/') + "/sql/" + simpleClassName
							+ ".sql";
			sql =  SqlUtils.loadSQL(className, sqlId, parameters);
		}
		return SqlUtils.loadSQL(className, sqlId, parameters);
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
}
