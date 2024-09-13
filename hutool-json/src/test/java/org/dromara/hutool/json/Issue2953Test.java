/*
 * Copyright (c) 2024 Hutool Team and hutool.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.hutool.json;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Issue2953Test {
	@Test
	public void parseObjWithBigNumberTest() {
		final String a = "{\"a\": 114793903847679990000000000000000000000}";
		final OldJSONObject jsonObject = JSONUtil.parseObj(a, JSONConfig.of());
		Assertions.assertEquals("{\"a\":\"114793903847679990000000000000000000000\"}", jsonObject.toString());
	}
}
