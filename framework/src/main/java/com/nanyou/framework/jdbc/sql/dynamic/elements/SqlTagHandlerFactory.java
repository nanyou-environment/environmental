/*
 *  Copyright 2004 Clinton Begin
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
package com.nanyou.framework.jdbc.sql.dynamic.elements;

import java.util.HashMap;
import java.util.Map;

import com.nanyou.framework.jdbc.sql.dynamic.elements.DynamicTagHandler;
import com.nanyou.framework.jdbc.sql.dynamic.elements.IsEmptyTagHandler;
import com.nanyou.framework.jdbc.sql.dynamic.elements.IsEqualTagHandler;
import com.nanyou.framework.jdbc.sql.dynamic.elements.IsGreaterEqualTagHandler;
import com.nanyou.framework.jdbc.sql.dynamic.elements.IsGreaterThanTagHandler;
import com.nanyou.framework.jdbc.sql.dynamic.elements.IsLessEqualTagHandler;
import com.nanyou.framework.jdbc.sql.dynamic.elements.IsLessThanTagHandler;
import com.nanyou.framework.jdbc.sql.dynamic.elements.IsNotEmptyTagHandler;
import com.nanyou.framework.jdbc.sql.dynamic.elements.IsNotEqualTagHandler;
import com.nanyou.framework.jdbc.sql.dynamic.elements.IsNotNullTagHandler;
import com.nanyou.framework.jdbc.sql.dynamic.elements.IsNotParameterPresentTagHandler;
import com.nanyou.framework.jdbc.sql.dynamic.elements.IsNotPropertyAvailableTagHandler;
import com.nanyou.framework.jdbc.sql.dynamic.elements.IsNullTagHandler;
import com.nanyou.framework.jdbc.sql.dynamic.elements.IsParameterPresentTagHandler;
import com.nanyou.framework.jdbc.sql.dynamic.elements.IsPropertyAvailableTagHandler;
import com.nanyou.framework.jdbc.sql.dynamic.elements.IterateTagHandler;
import com.nanyou.framework.jdbc.sql.dynamic.elements.SqlTagHandler;

public class SqlTagHandlerFactory {

	private static final Map HANDLER_MAP = new HashMap();

	static {
		HANDLER_MAP.put("isEmpty", new IsEmptyTagHandler());
		HANDLER_MAP.put("isEqual", new IsEqualTagHandler());
		HANDLER_MAP.put("isGreaterEqual", new IsGreaterEqualTagHandler());
		HANDLER_MAP.put("isGreaterThan", new IsGreaterThanTagHandler());
		HANDLER_MAP.put("isLessEqual", new IsLessEqualTagHandler());
		HANDLER_MAP.put("isLessThan", new IsLessThanTagHandler());
		HANDLER_MAP.put("isNotEmpty", new IsNotEmptyTagHandler());
		HANDLER_MAP.put("isNotEqual", new IsNotEqualTagHandler());
		HANDLER_MAP.put("isNotNull", new IsNotNullTagHandler());
		HANDLER_MAP.put("isNotParameterPresent", new IsNotParameterPresentTagHandler());
		HANDLER_MAP.put("isNotPropertyAvailable", new IsNotPropertyAvailableTagHandler());
		HANDLER_MAP.put("isNull", new IsNullTagHandler());
		HANDLER_MAP.put("isParameterPresent", new IsParameterPresentTagHandler());
		HANDLER_MAP.put("isPropertyAvailable", new IsPropertyAvailableTagHandler());
		HANDLER_MAP.put("iterate", new IterateTagHandler());
		HANDLER_MAP.put("dynamic", new DynamicTagHandler());
	}

	private SqlTagHandlerFactory() {
	}

	public static SqlTagHandler getSqlTagHandler(String name) {
		return (SqlTagHandler) HANDLER_MAP.get(name);
	}

}
