package cn.hutool.jwt;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JWTUtilTest {

	@Test
	public void createTest(){
		byte[] key = "1234".getBytes();
		Map<String, Object> map = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("uid", Integer.parseInt("123"));
				put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);

			}
		};

		JWTUtil.createToken(map, key);
	}

	@Test
	public void parseTest(){
		String rightToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
				"eyJzdWIiOiIxMjM0NTY3ODkwIiwiYWRtaW4iOnRydWUsIm5hbWUiOiJsb29seSJ9." +
				"U2aQkC2THYV9L0fTN-yBBI7gmo5xhmvMhATtu8v0zEA";
		final JWT jwt = JWTUtil.parseToken(rightToken);

		assertTrue(jwt.setKey("1234567890".getBytes()).verify());

		//header
		assertEquals("JWT", jwt.getHeader(JWTHeader.TYPE));
		assertEquals("HS256", jwt.getHeader(JWTHeader.ALGORITHM));
		assertNull(jwt.getHeader(JWTHeader.CONTENT_TYPE));

		//payload
		assertEquals("1234567890", jwt.getPayload("sub"));
		assertEquals("looly", jwt.getPayload("name"));
		assertEquals(true, jwt.getPayload("admin"));
	}

	@Test
	public void parseNullTest(){
		assertThrows(IllegalArgumentException.class, () -> {
			// https://gitee.com/dromara/hutool/issues/I5OCQB
			JWTUtil.parseToken(null);
		});
	}

	@Test
	public void verifyTest(){
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
				"eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFsbCJdLCJleHAiOjE2MjQwMDQ4MjIsInVzZXJJZCI6MSwiYXV0aG9yaXRpZXMiOlsiUk9MRV_op5LoibLkuozlj7ciLCJzeXNfbWVudV8xIiwiUk9MRV_op5LoibLkuIDlj7ciLCJzeXNfbWVudV8yIl0sImp0aSI6ImQ0YzVlYjgwLTA5ZTctNGU0ZC1hZTg3LTVkNGI5M2FhNmFiNiIsImNsaWVudF9pZCI6ImhhbmR5LXNob3AifQ." +
				"aixF1eKlAKS_k3ynFnStE7-IRGiD5YaqznvK2xEjBew";

		final boolean verify = JWTUtil.verify(token, "123456".getBytes());
		assertTrue(verify);
	}
}
