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

import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.convert.ConvertUtil;
import org.dromara.hutool.core.reflect.TypeReference;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * 测试转换为TreeSet是否成功。 TreeSet必须有默认的比较器
 */
public class Issue1101Test {

	@Test
	public void treeMapConvertTest(){
		final String json = "[{\"nodeName\":\"admin\",\"treeNodeId\":\"00010001_52c95b83-2083-4138-99fb-e6e21f0c1277\",\"sort\":0,\"type\":10,\"parentId\":\"00010001\",\"children\":[],\"id\":\"52c95b83-2083-4138-99fb-e6e21f0c1277\",\"status\":true},{\"nodeName\":\"test\",\"treeNodeId\":\"00010001_97054a82-f8ff-46a1-b76c-cbacf6d18045\",\"sort\":0,\"type\":10,\"parentId\":\"00010001\",\"children\":[],\"id\":\"97054a82-f8ff-46a1-b76c-cbacf6d18045\",\"status\":true}]";
		final JSONArray objects = JSONUtil.parseArray(json);
		final TreeSet<TreeNodeDto> convert = ConvertUtil.convert(new TypeReference<TreeSet<TreeNodeDto>>() {
		}, objects);
		Assertions.assertEquals(2, convert.size());
	}

	@Test
	public void test(){
		final String json = "{\n" +
				"\t\"children\": [{\n" +
				"\t\t\"children\": [],\n" +
				"\t\t\"id\": \"52c95b83-2083-4138-99fb-e6e21f0c1277\",\n" +
				"\t\t\"nodeName\": \"admin\",\n" +
				"\t\t\"parentId\": \"00010001\",\n" +
				"\t\t\"sort\": 0,\n" +
				"\t\t\"status\": true,\n" +
				"\t\t\"treeNodeId\": \"00010001_52c95b83-2083-4138-99fb-e6e21f0c1277\",\n" +
				"\t\t\"type\": 10\n" +
				"\t}, {\n" +
				"\t\t\"children\": [],\n" +
				"\t\t\"id\": \"97054a82-f8ff-46a1-b76c-cbacf6d18045\",\n" +
				"\t\t\"nodeName\": \"test\",\n" +
				"\t\t\"parentId\": \"00010001\",\n" +
				"\t\t\"sort\": 0,\n" +
				"\t\t\"status\": true,\n" +
				"\t\t\"treeNodeId\": \"00010001_97054a82-f8ff-46a1-b76c-cbacf6d18045\",\n" +
				"\t\t\"type\": 10\n" +
				"\t}],\n" +
				"\t\"id\": \"00010001\",\n" +
				"\t\"nodeName\": \"测试\",\n" +
				"\t\"parentId\": \"0001\",\n" +
				"\t\"sort\": 0,\n" +
				"\t\"status\": true,\n" +
				"\t\"treeNodeId\": \"00010001\",\n" +
				"\t\"type\": 0\n" +
				"}";

		final OldJSONObject jsonObject = JSONUtil.parseObj(json);

		final TreeNode treeNode = JSONUtil.toBean(jsonObject, TreeNode.class);
		Assertions.assertEquals(2, treeNode.getChildren().size());

		final TreeNodeDto dto = new TreeNodeDto();
		BeanUtil.copyProperties(treeNode, dto, true);
		Assertions.assertEquals(2, dto.getChildren().size());
	}

	@Data
	public static class TreeNodeDto {
		private String id;
		private String parentId;
		private int sort;
		private String nodeName;
		private int type;
		private Boolean status;
		private String treeNodeId;
		private TreeSet<TreeNodeDto> children = new TreeSet<>(Comparator.comparing(o -> o.id));
	}

	@Data
	public static class TreeNode implements Comparable<TreeNode> {
		private String id;
		private String parentId;
		private int sort;
		private String nodeName;
		private int type;
		private Boolean status;
		private String treeNodeId;
		private TreeSet<TreeNode> children = new TreeSet<>();

		@Override
		public int compareTo(final TreeNode o) {
			return id.compareTo(o.getId());
		}
	}
}
