/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.nanyou.framework.jdbc.sql.simple;

import java.util.StringTokenizer;

import com.nanyou.framework.jdbc.sql.Sql;

public class SimpleDynamicSql implements Sql {

	private static final String ELEMENT_TOKEN = "$";

	private String sqlStatement;

	public SimpleDynamicSql(String sqlStatement) {
		this.sqlStatement = sqlStatement;
	}

	public String getSql(Object parameterObject) {
		return processDynamicElements(sqlStatement, parameterObject);
	}

	public static boolean isSimpleDynamicSql(String sql) {
		return sql != null && sql.indexOf(ELEMENT_TOKEN) > -1;
	}

	private String processDynamicElements(String sql, Object parameterObject) {
		StringTokenizer parser = new StringTokenizer(sql, ELEMENT_TOKEN, true);
		StringBuffer newSql = new StringBuffer();

		String token = null;
		String lastToken = null;
		while (parser.hasMoreTokens()) {
			token = parser.nextToken();

			if (ELEMENT_TOKEN.equals(lastToken)) {
				if (ELEMENT_TOKEN.equals(token)) {
					newSql.append(ELEMENT_TOKEN);
					token = null;
				} else {

					Object value = null;
					if (parameterObject != null) {
						value = parameterObject;
					}
					if (value != null) {
						newSql.append(String.valueOf(value));
					}

					token = parser.nextToken();
					if (!ELEMENT_TOKEN.equals(token)) {
						throw new RuntimeException("Unterminated dynamic element in sql (" + sql + ").");
					}
					token = null;
				}
			} else {
				if (!ELEMENT_TOKEN.equals(token)) {
					newSql.append(token);
				}
			}

			lastToken = token;
		}

		return newSql.toString();
	}

}
