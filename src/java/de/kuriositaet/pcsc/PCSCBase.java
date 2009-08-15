package de.kuriositaet.pcsc;

import com.sun.jna.NativeLong;

import de.kuriositaet.pcsc.ffi.Constants;

/**
 * This class provides some base functionality common to the classes
 * calling the PCSC interface.
 * @author tbe
 *
 */
public abstract class PCSCBase {
	protected void m (String f) {
		System.out.println(this.getClass().getSimpleName()+f);
	}
	protected void in (String f){
		m(">>> "+f);
	}
	protected void out (String f) {
		m("<<< "+f);
	}
	public void checkResult(String mes, NativeLong err) {
		if (!Constants.SCARD_S_SUCCESS.equals(err)){
			throw new PCSCException(mes, err);
		}
	}
}
