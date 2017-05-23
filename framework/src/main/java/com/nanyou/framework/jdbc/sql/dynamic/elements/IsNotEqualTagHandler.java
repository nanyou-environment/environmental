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

import com.nanyou.framework.jdbc.sql.dynamic.elements.IsEqualTagHandler;
import com.nanyou.framework.jdbc.sql.dynamic.elements.SqlTag;
import com.nanyou.framework.jdbc.sql.dynamic.elements.SqlTagContext;

public class IsNotEqualTagHandler extends IsEqualTagHandler {

	public boolean isCondition(SqlTagContext ctx, SqlTag tag, Object parameterObject) {
		return !super.isCondition(ctx, tag, parameterObject);
	}

}