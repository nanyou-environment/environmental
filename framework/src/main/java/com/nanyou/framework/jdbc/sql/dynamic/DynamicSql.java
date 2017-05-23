/*
 *
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
package com.nanyou.framework.jdbc.sql.dynamic;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.nanyou.framework.jdbc.sql.Sql;
import com.nanyou.framework.jdbc.sql.SqlChild;
import com.nanyou.framework.jdbc.sql.SqlText;
import com.nanyou.framework.jdbc.sql.dynamic.elements.DynamicParent;
import com.nanyou.framework.jdbc.sql.dynamic.elements.SqlTag;
import com.nanyou.framework.jdbc.sql.dynamic.elements.SqlTagContext;
import com.nanyou.framework.jdbc.sql.dynamic.elements.SqlTagHandler;
import com.nanyou.framework.jdbc.sql.dynamic.elements.SqlTagHandlerFactory;
import com.nanyou.framework.jdbc.sql.simple.SimpleDynamicSql;

public class DynamicSql implements Sql, DynamicParent {

	private String dynamicSql;

	private String sqlStatement;

	private List children = new ArrayList();

	public DynamicSql(String dynamicSql) {
		this.dynamicSql = dynamicSql;
	}

	public String getSql(Object parameterObject) {
		if (dynamicSql != null) {
			findChildren(this,dynamicSql);
			process(parameterObject);
		}
		return sqlStatement;
	}

	private void findChildren(DynamicParent parent,String sql) {
		try {
			sql = StringUtils.remove(sql, ";");

			BufferedReader in = new BufferedReader(new StringReader(sql));
			while (true) {
				String line = in.readLine();
				if (line == null) {
					break;
				}
				if (StringUtils.trim(line).startsWith("--<")) {
					line = StringUtils.trim(line);
					line = new String(line.substring(3, line.lastIndexOf('>')));
					StringTokenizer st = new StringTokenizer(line, "= \t");
					String tagName = st.nextToken();
					SqlTag child = new SqlTag();
					child.setName(tagName);
					child.setHandler(SqlTagHandlerFactory.getSqlTagHandler(tagName));
					while (st.hasMoreTokens()) {
						String key = st.nextToken();
						String value = StringUtils.remove(st.nextToken(),"\"");
						if ("prepend".equalsIgnoreCase(key)) {
							child.setPrependAttr(value);
						} else if ("property".equalsIgnoreCase(key)) {
							child.setPropertyAttr(value);
						} else if ("open".equalsIgnoreCase(key)) {
							child.setOpenAttr(value);
						} else if ("close".equalsIgnoreCase(key)) {
							child.setCloseAttr(value);
						} else if ("conjunction".equalsIgnoreCase(key)) {
							child.setConjunctionAttr(value);
						} else if ("compareProperty".equalsIgnoreCase(key)) {
							child.setComparePropertyAttr(value);
						} else if ("compareValue".equalsIgnoreCase(key)) {
							child.setCompareValueAttr(value);
						}
					}
					String endTagName = "</" + tagName + ">";
					StringBuffer sb = new StringBuffer();
					while (true) {
						line = in.readLine();
						if (line == null) {
							break;
						}
						if (line.indexOf(endTagName) > 0) {
							break;
						}
						sb.append(line).append("\n");
					}
					findChildren(child,sb.toString());
					parent.addChild(child);
				} else {
					SqlText child = new SqlText();
					((SqlText) child).setText(line);
					parent.addChild(child);
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private void process(Object parameterObject) {

		SqlTagContext ctx = new SqlTagContext();
		List localChildren = children;
		processBodyChildren(ctx, parameterObject, localChildren.iterator());

		String dynSql = ctx.getBodyText();

		// Processes $substitutions$ after DynamicSql
		if (SimpleDynamicSql.isSimpleDynamicSql(dynSql)) {
			dynSql = new SimpleDynamicSql(dynSql).getSql(parameterObject);
		}
		this.sqlStatement = dynSql;
	}

	private void processBodyChildren(SqlTagContext ctx, Object parameterObject, Iterator localChildren) {
		PrintWriter out = ctx.getWriter();
		processBodyChildren(ctx, parameterObject, localChildren, out);
	}

	private void processBodyChildren(SqlTagContext ctx, Object parameterObject, Iterator localChildren, PrintWriter out) {
		while (localChildren.hasNext()) {
			SqlChild child = (SqlChild) localChildren.next();
			if (child instanceof SqlText) {
				SqlText sqlText = (SqlText) child;
				String sqlStatement = sqlText.getText();
				out.println(sqlStatement);
			} else if (child instanceof SqlTag) {
				SqlTag tag = (SqlTag) child;
				SqlTagHandler handler = tag.getHandler();
				int response = SqlTagHandler.INCLUDE_BODY;
				do {
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);

					response = handler.doStartFragment(ctx, tag, parameterObject);
					if (response != SqlTagHandler.SKIP_BODY) {

						processBodyChildren(ctx, parameterObject, tag.getChildren(), pw);
						pw.flush();
						pw.close();
						StringBuffer body = sw.getBuffer();
						response = handler.doEndFragment(ctx, tag, parameterObject, body);
						handler.doPrepend(ctx, tag, parameterObject, body);

						if (response != SqlTagHandler.SKIP_BODY) {
							if (body.length() > 0) {
								out.println(body.toString());
							}
						}

					}
				} while (response == SqlTagHandler.REPEAT_BODY);

				ctx.popRemoveFirstPrependMarker(tag);

				if (ctx.peekIterateContext() != null && ctx.peekIterateContext().getTag() == tag) {
					ctx.setAttribute(ctx.peekIterateContext().getTag(), null);
					ctx.popIterateContext();
				}

			}
		}
	}

	public void addChild(SqlChild child) {
		children.add(child);
	}

}
