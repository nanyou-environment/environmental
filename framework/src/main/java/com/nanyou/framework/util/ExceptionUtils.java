package com.nanyou.framework.util;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

import com.nanyou.framework.exception.ForeignKeyConstraintViolationException;
import com.nanyou.framework.exception.FrameworkExceptions;
import com.nanyou.framework.exception.ServiceException;

public class ExceptionUtils {

	public static ServiceException translateException(Throwable t) {
		if (t instanceof ServiceException) {
			return (ServiceException) t;
		}

		if (t instanceof ForeignKeyConstraintViolationException) {
			return new ServiceException(FrameworkExceptions.FOREIGNKEY_CONSTRAINT);
		}

		if (t instanceof DataAccessException) {
			if (t.getCause() != null) {
				if (t.getCause() instanceof SQLException) {
					SQLException sqlEx = (SQLException) t.getCause();
					if (sqlEx.getNextException() != null) {
						return new ServiceException(FrameworkExceptions.DATABASE_ERROR,sqlEx.getNextException());
					}
					return new ServiceException(FrameworkExceptions.DATABASE_ERROR,sqlEx);
				}
				return new ServiceException(FrameworkExceptions.DATABASE_ERROR,t.getCause());
			} else {
				return new ServiceException(FrameworkExceptions.DATABASE_ERROR);
			}
		}

		if (t == null) {
			return new ServiceException(FrameworkExceptions.SYSTEM_ERROR);
		}

		return new ServiceException(FrameworkExceptions.SYSTEM_ERROR,t);
	}

}
