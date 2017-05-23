package com.nanyou.framework.jdbc.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.jdbc.core.ColumnMapRowMapper;

public class RowNumberColumnMapRowMapper extends ColumnMapRowMapper {

	public Map mapRow(ResultSet rs, int row) throws SQLException {
		
		Map map = (Map) super.mapRow(rs, row);
		map.put("ROW_NO",new Integer(row));
		return map;
	}

}
