package com.github.cglong.Grammartizer;

import static org.junit.Assert.*;
import org.junit.Test;

public class DriverTest {
	@Test
	public void testTiny() {
		String[] args = {"tiny.txt"};
		try {
			assertEquals(0, Driver.testMain(args));
		} catch (Exception e) {
		}
	}
}
