package de.kuriositaet.pcsc.test;

import org.junit.Assert;
import org.junit.Test;

import de.kuriositaet.pcsc.Context;


public class TestContext {
	@Test
	public void testBasics() {
		Context c = new Context();
		Assert.assertTrue(c.isValid());
		c.release();
		Assert.assertFalse(c.isValid());
		c.release();
	}
	
	@Test
	public void testListReaderGroups() {
		Context c = new Context();
		//System.out.println("Native Long.size"+NativeLong.SIZE);
		//c.listReaderGroups();
		c.listReaders();
		c.release();
		
	}
}
