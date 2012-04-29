package com.github.cglong.Grammartizer;

import static org.junit.Assert.*;
import org.junit.Test;

public class DriverTest {
	@Test
	public void testNoLanguage() {
		String[] args = {};
		try {
			assertFalse(Driver.testMain(args) == 0);
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testTiny() {
		String[] args = {"tiny.txt"};
		try {
			assertEquals(0, Driver.testMain(args));
		} catch (Exception e) {
		}
	}
}
