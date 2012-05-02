package com.github.cglong.Grammartizer;

import static org.junit.Assert.*;
import org.junit.Test;

public class DriverTest {
	@Test
	public void testNoGrammar() {
		String[] args = {};
		try {
			assertFalse(Driver.realMain(args) == 0);
		} catch (SecurityException e) {}
	}
	
	@Test
	public void testNoInput() {
		String[] args = {"Grammars/tiny_new.txt"};
		try {
			assertFalse(Driver.realMain(args) == 0);
		} catch (SecurityException e) {}
	}
	
	@Test
	public void testTiny() {
		String[] args = {"Grammars/tiny.txt", "Inputs/tiny_easy.txt"};
		try {
			assertFalse(Driver.realMain(args) == 0);
		} catch (SecurityException e) {}
	}
	
	@Test
	public void testTinyNew() {
		String[] args = {"Grammars/tiny_new.txt", "Inputs/tiny_easy.txt"};
		try {
			assertTrue(Driver.realMain(args) == 0);
		} catch (SecurityException e) {}
	}
	
	@Test
	public void testSample1() {
		String[] args = {"Grammars/sample1.txt", "Inputs/sample1_easy.txt"};
		try {
			assertTrue(Driver.realMain(args) == 0);
		} catch (SecurityException e) {}
	}
	
	@Test
	public void testSample2() {
		String[] args = {"Grammars/sample2.txt", "Inputs/sample2_easy.txt"};
		try {
			assertTrue(Driver.realMain(args) == 0);
		} catch (SecurityException e) {}
	}
}
