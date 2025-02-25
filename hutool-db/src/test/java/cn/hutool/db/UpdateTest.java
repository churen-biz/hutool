package cn.hutool.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdateTest {

	Db db;

	@BeforeEach
	public void init() {
		db = Db.use("test");
	}

	/**
	 * 对更新做单元测试
	 *
	 * @throws SQLException SQL异常
	 */
	@Test
	@Disabled
	public void updateTest() throws SQLException {

		// 改
		int update = db.update(Entity.create("user").set("age", 88), Entity.create().set("name", "unitTestUser"));
		assertTrue(update > 0);
		Entity result2 = db.get("user", "name", "unitTestUser");
		assertSame(88, result2.getInt("age"));
	}
}
