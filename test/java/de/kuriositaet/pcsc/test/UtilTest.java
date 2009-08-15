package de.kuriositaet.pcsc.test;

import org.junit.Assert;
import org.junit.Test;

import com.sun.jna.Memory;

import de.kuriositaet.pcsc.Util;


public class UtilTest {
	final static byte [] ONE = {0x31, 0x32, 0x33, 0x00, 0x34, 0x35, 0x36, 0x00, 0x00};
	final static String [] ONE_R = {
		"123",
		"456"
	};
	final static String ONE_R2 = "313233003435360000";
	
	final static byte [] TWO = {0x31, 0x32, 0x33, 0x00, 0x00, 0x00};
	final static String [] TWO_R = {
		"123",
	};
	final static String TWO_R2 = "313233000000";
	
	final static byte   [] THREE = {0x31, 0x32, 0x33};
	final static String [] THREE_R = {};
	final static String    THREE_R2 = "313233";
	
	final static byte   [] FOUR = {0x00};
	final static String [] FOUR_R = {""};
	final static String    FOUR_R2 = "00";
	
	final static byte   [] FIVE = {0x00, 0x00};
	final static String [] FIVE_R = {""};
	final static String    FIVE_R2 = "0000";
	
	final static Object [][] TESTS = {
		{"ONE", ONE, ONE_R},{"TWO", TWO, TWO_R},
		{"THREE", THREE, THREE_R}, {"FOUR", FOUR, FOUR_R},
		{"FIVE", FIVE, FIVE_R},
	};
	
	final static Object [][] TESTS_BIN_TO_HEX = {
		{"ONE", ONE, ONE_R2},{"TWO", TWO, TWO_R2},
		{"THREE", THREE, THREE_R2}, {"FOUR", FOUR, FOUR_R2},
		{"FIVE", FIVE, FIVE_R2},
	};
	
	@Test
	public void testMemToStringArray() {
		for (Object [] arr : TESTS) {
			_testMemToStringArray(arr);
		}
		
		
	}
	
	private void _testMemToStringArray(Object[] arr) {
		String name   = (String) arr[0];
		byte [] b_arr = (byte[]) arr[1];
		String [] res = (String[]) arr[2];
		
		Memory mem = new Memory(b_arr.length);
		mem.write(0, b_arr, 0, b_arr.length);
		String [] result = Util.memToStringArray(mem);
		
		Assert.assertArrayEquals(name, res, result);
		
	}
	@Test 
	public void binToHexTest () {
		for (Object [] arr : TESTS_BIN_TO_HEX) {
			_testBinToHex(arr);
		}
	}

	private void _testBinToHex(Object[] arr) {
		String  name  = (String) arr[0];
		byte [] b_arr = (byte[]) arr[1];
		String  res   = (String) arr[2];
		
		String result = Util.binToHex(b_arr);
		Assert.assertEquals(name, res, result);
	}
	
	@Test
	public void memToByteTest () {
		for (Object [] arr : TESTS) {
			_testMemToByte(arr);
		}
		
	}

	private void _testMemToByte(Object[] arr) {
		String name   = (String) arr[0];
		byte [] b_arr = (byte[]) arr[1];
		
		Memory m = new Memory(b_arr.length);
		m.write(0, b_arr, 0, b_arr.length);
		Assert.assertArrayEquals(name, b_arr, Util.memToByte(m));
	}
	
}
