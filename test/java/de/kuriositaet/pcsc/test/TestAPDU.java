package de.kuriositaet.pcsc.test;

import org.junit.Assert;
import org.junit.Test;

import de.kuriositaet.iso7816.APDU;
import de.kuriositaet.iso7816.apdu.SELECT;


public class TestAPDU {
	@Test
	public void basicTests() {
		APDU apdu = new APDU((byte)0x01, (byte)0x02, (byte)0x03, (byte)0x04);
		byte [] apdu_expected = {1,2,3,4};
		Assert.assertArrayEquals(apdu_expected, apdu.getBytes());
		
		apdu = new APDU((byte)0x01, (byte)0x02, (byte)0x03, (byte)0x04, (byte)0x05);
		byte [] apdu_expected2 = {1,2,3,4, 5};
		Assert.assertArrayEquals(apdu_expected2, apdu.getBytes());
		
		apdu = new APDU((byte)0x01, (byte)0x02, (byte)0x03, (byte)0x04, apdu_expected2);
		byte [] apdu_expected3 = {1, 2, 3, 4, 5, 1, 2, 3, 4, 5};
		Assert.assertArrayEquals(apdu_expected3, apdu.getBytes());
		
		apdu = new APDU((byte)0x01, (byte)0x02, (byte)0x03, (byte)0x04, apdu_expected2, (byte)1);
		byte [] apdu_expected4 = {1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1};
		Assert.assertArrayEquals(apdu_expected4, apdu.getBytes());	
	}
	@Test
	public void toStringTest() {
		APDU apdu = new APDU((byte)0x01, (byte)0x02, (byte)0x03, (byte)0x04);
		String exp = "UNKNOWN\n"+
		 "| cla| ins|p1|p2|\n"+
		 "|  01|  02|03|04|\n";
		Assert.assertEquals(exp, apdu.toString());
		
		apdu = new APDU((byte)0xFE, (byte)0xEF, (byte)0xAA, (byte)0xF0);
		exp = "UNKNOWN\n"+
		 "| cla| ins|p1|p2|\n"+
		 "|  FE|  EF|AA|F0|\n";
		Assert.assertEquals(exp, apdu.toString());
		
		apdu = new APDU((byte)0x01, (byte)0x02, (byte)0x03, (byte)0x04, (byte)0x05);
		exp = "UNKNOWN\n"+
		 "| cla| ins|p1|p2|le|\n"+
		 "|  01|  02|03|04|05|\n";
		Assert.assertEquals(exp, apdu.toString());
		
		byte [] data = {1,2,3,4, 5};
		apdu = new APDU((byte)0x01, (byte)0x02, (byte)0x03, (byte)0x04, data);
		exp = "UNKNOWN\n"+
		 "| cla| ins|p1|p2|lc|      data|\n"+
		 "|  01|  02|03|04|05|0102030405|\n";
		Assert.assertEquals(exp, apdu.toString());
		
		apdu = new APDU((byte)0x01, (byte)0x02, (byte)0x03, (byte)0x04, data, (byte)1);
		exp = "UNKNOWN\n"+
		 "| cla| ins|p1|p2|lc|      data|le|\n"+
		 "|  01|  02|03|04|05|0102030405|01|\n";
		Assert.assertEquals(exp, apdu.toString());
		
		
		apdu = new APDU("SELECT", (byte)0x00, (byte)0xA4);
		exp = "SELECT\n"+
		 "| cla| ins|p1|p2|\n"+
		 "|  00|  A4|00|00|\n";
		Assert.assertEquals(exp, apdu.toString());
		
	}
	
	@Test
	public void toStringAPDUGenerated() {
		SELECT sel = new SELECT();
		String exp = "SELECT\n"+
		 "| cla| ins|p1|p2|\n"+
		 "|  00|  A4|00|00|\n";
		Assert.assertEquals(exp, sel.toString());
		
	}
}
