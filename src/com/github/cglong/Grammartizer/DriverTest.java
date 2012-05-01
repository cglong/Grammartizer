package com.github.cglong.Grammartizer;

import static org.junit.Assert.*;
import org.junit.Test;

public class DriverTest {
	@Test
	public void testNoGrammar() {
		String[] args = {};
		try {
			assertFalse(Driver.testMain(args) == 0);
		} catch (SecurityException e) {}
	}
	
	@Test
	public void testTiny() {
		String[] args = {"tiny.txt"};
		try {
			assertFalse(Driver.testMain(args) == 0);
		} catch (SecurityException e) {}
	}
	
	@Test
	public void testTinyNew() {
		String[] args = {"tiny_new.txt"};
		try {
			assertTrue(Driver.testMain(args) == 0);
		} catch (SecurityException e) {}
	}
}
