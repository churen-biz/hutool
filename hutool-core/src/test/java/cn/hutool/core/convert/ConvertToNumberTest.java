package cn.hutool.core.convert;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

public class ConvertToNumberTest {
	@Test
	public void dateToLongTest(){
		final DateTime date = DateUtil.parse("2020-05-17 12:32:00");
		final Long dateLong = Convert.toLong(date);
		assert date != null;
		assertEquals(date.getTime(), dateLong.longValue());
	}

	@Test
	public void dateToIntTest(){
		final DateTime date = DateUtil.parse("2020-05-17 12:32:00");
		final Integer dateInt = Convert.toInt(date);
		assert date != null;
		assertEquals((int)date.getTime(), dateInt.intValue());
	}

	@Test
	public void dateToAtomicLongTest(){
		final DateTime date = DateUtil.parse("2020-05-17 12:32:00");
		final AtomicLong dateLong = Convert.convert(AtomicLong.class, date);
		assert date != null;
		assertEquals(date.getTime(), dateLong.longValue());
	}

	@Test
	public void toBigDecimalTest(){
		BigDecimal bigDecimal = Convert.toBigDecimal("1.1f");
		assertEquals(1.1f, bigDecimal.floatValue(), 0);

		bigDecimal = Convert.toBigDecimal("1L");
		assertEquals(1L, bigDecimal.longValue());
	}
}
