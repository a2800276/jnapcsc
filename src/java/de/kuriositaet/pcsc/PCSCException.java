package de.kuriositaet.pcsc;

import com.sun.jna.NativeLong;

/**
 * Base Exception for all problems occuring calling the PCSC Interface.
 * N.B.: This exception extends RuntimeException so it's use is
 * not declared anywhere and you're NOT required to catch it.
 * @author tbe
 *
 */
public class PCSCException extends java.lang.RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9057380633696900782L;
	private NativeLong err;
	public PCSCException(String mes, NativeLong err) {
		super(mes + ":" + stringify(err));
		this.err=err;
	}
	public PCSCException(String mes) {
		super(mes);
	}
	private static String stringify(NativeLong err) {
		return Util.stringifyError(err);
	}
	
	public NativeLong getErr() {
		return err;
	}
	
}
