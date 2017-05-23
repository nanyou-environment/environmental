package com.nanyou.framework.jdbc;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.util.CollectionUtils;

public class BaseJdbcTemplate extends JdbcTemplate {
	public Object queryForObject(String sql, RowMapper rowMapper)
			throws DataAccessException {
		List results = query(sql, rowMapper);
		int size = (results != null ? results.size() : 0);
		if (size == 0) {
			return null;
		}
		if (!CollectionUtils.hasUniqueObject(results)) {
			throw new IncorrectResultSizeDataAccessException(1, size);
		}
		return results.iterator().next();
	}

	public Object queryForObject(String sql, Class requiredType)
			throws DataAccessException {
		return queryForObject(sql, getSingleColumnRowMapper(requiredType));
	}

	public Object queryForObject(String sql, Object[] args, int[] argTypes,
			RowMapper rowMapper) throws DataAccessException {

		List results = (List) query(sql, args, argTypes,
				new RowMapperResultSetExtractor(rowMapper, 1));
		int size = (results != null ? results.size() : 0);
		if (size == 0) {
			return null;
		}
		if (!CollectionUtils.hasUniqueObject(results)) {
			throw new IncorrectResultSizeDataAccessException(1, size);
		}
		return results.iterator().next();
	}

	public Object queryForObject(String sql, Object[] args, RowMapper rowMapper)
			throws DataAccessException {
		List results = (List) query(sql, args, new RowMapperResultSetExtractor(
				rowMapper, 1));
		int size = (results != null ? results.size() : 0);
		if (size == 0) {
			return null;
		}
		if (!CollectionUtils.hasUniqueObject(results)) {
			throw new IncorrectResultSizeDataAccessException(1, size);
		}
		return results.iterator().next();
	}

	public Object queryForObject(String sql, Object[] args, int[] argTypes,
			Class requiredType) throws DataAccessException {

		return queryForObject(sql, args, argTypes,
				getSingleColumnRowMapper(requiredType));
	}

	public Object queryForObject(String sql, Object[] args, Class requiredType)
			throws DataAccessException {
		return queryForObject(sql, args, getSingleColumnRowMapper(requiredType));
	}

	public long queryForLong(String sql, Object[] args, int[] argTypes)
			throws DataAccessException {
		Number number = (Number) queryForObject(sql, args, argTypes, Long.class);
		return (number != null ? number.longValue() : 0);
	}

	public long queryForLong(String sql, Object... args)
			throws DataAccessException {
		Number number = (Number) queryForObject(sql, args, Long.class);
		return (number != null ? number.longValue() : 0);
	}

	public int queryForInt(String sql, Object[] args, int[] argTypes)
			throws DataAccessException {
		Number number = (Number) queryForObject(sql, args, argTypes,
				Integer.class);
		return (number != null ? number.intValue() : 0);
	}

	public int queryForInt(String sql, Object... args)
			throws DataAccessException {
		Number number = (Number) queryForObject(sql, args, Integer.class);
		return (number != null ? number.intValue() : 0);
	}

}
