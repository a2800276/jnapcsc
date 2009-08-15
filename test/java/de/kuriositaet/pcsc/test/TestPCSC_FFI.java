package de.kuriositaet.pcsc.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.NativeLongByReference;

import de.kuriositaet.pcsc.Constants;
import de.kuriositaet.pcsc.ffi.PCSC_FFI;

public class TestPCSC_FFI {
	
	static final String [] METHODS = {
		"SCardBeginTransaction",
		"SCardCancel",
		"SCardConnect",
		"SCardControl",
		"SCardDisconnect",
		"SCardEndTransaction",
		"SCardEstablishContext",
		"SCardGetAttrib",
		"SCardGetStatusChange",
		"SCardIsValidContext",
		"SCardListReaderGroups",
		"SCardListReaders",
		"SCardReconnect",
		"SCardReleaseContext",
		"SCardSetAttrib",
		"SCardSetTimeout",
		"SCardStatus",
		"SCardTransmit",
	};
	
	@Test
	public void hasAllMethods() throws Exception {
		PCSC_FFI ffi = PCSC_FFI.INSTANCE;		
		Class<? extends PCSC_FFI> c = ffi.getClass();
		ArrayList<String> meths = new ArrayList<String>();
		for (Method m : c.getMethods()) {
			if (m.getName().startsWith("SCard")) {
				meths.add(m.getName());
			} 
		}
		Collections.sort(meths);
		for (String s : meths) {
			System.out.println(s);
		}
		Assert.assertArrayEquals(METHODS, meths.toArray());
	
	} 
	@Test 
	public void testEstablishContext() {
		PCSC_FFI ffi = PCSC_FFI.INSTANCE;
		Pointer reserved = null;
		NativeLongByReference phContext = new NativeLongByReference();
		ffi.SCardEstablishContext(Constants.SCARD_SCOPE_SYSTEM, reserved, reserved, phContext);
		System.out.println(phContext.getValue());
		NativeLong result = ffi.SCardReleaseContext(phContext.getValue());
		System.out.println(result);
	}
	
}
