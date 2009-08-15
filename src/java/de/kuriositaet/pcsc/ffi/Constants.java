package de.kuriositaet.pcsc.ffi;

import com.sun.jna.NativeLong;

import de.kuriositaet.pcsc.Disposition;
import de.kuriositaet.pcsc.Protocol;
import de.kuriositaet.pcsc.Scope;
import de.kuriositaet.pcsc.ShareMode;

public class Constants {
	// SCARD_SCOPE_USER Not used
	final public static NativeLong SCARD_SCOPE_USER = new NativeLong(0x0000);
	// SCARD_SCOPE_TERMINAL Not used
	final public static NativeLong SCARD_SCOPE_TERMINAL = new NativeLong(0x0001);
	// SCARD_SCOPE_GLOBAL Not used
	final public static NativeLong SCARD_SCOPE_GLOBAL = new NativeLong(0x0003);
	// SCARD_SCOPE_SYSTEM Services on the local machine
	final public static NativeLong SCARD_SCOPE_SYSTEM = new NativeLong(0x0002);

	public static NativeLong mapScope(Scope scope) {
		switch(scope) {
		case USER:
			return SCARD_SCOPE_USER;
		case TERMINAL:
			return SCARD_SCOPE_TERMINAL;
		case GLOBAL:
			return SCARD_SCOPE_GLOBAL;
		case SYSTEM:
			return SCARD_SCOPE_SYSTEM;
		}
		return null;
	}
	
	/** < Exclusive mode only */
	final public static NativeLong SCARD_SHARE_EXCLUSIVE = new NativeLong(
			0x0001);
	/** < Shared mode only */
	final public static NativeLong SCARD_SHARE_SHARED = new NativeLong(0x0002);
	/** < Raw mode only */
	final public static NativeLong SCARD_SHARE_DIRECT = new NativeLong(0x0003);
	
	public static NativeLong mapShareMode(ShareMode share) {
		switch(share){
		case EXCLUSIVE:
			return SCARD_SHARE_EXCLUSIVE;
		case SHARED:
			return SCARD_SHARE_SHARED;
		case DIRECT:
			return SCARD_SHARE_DIRECT;
		}
		return null;
	}

	/** < protocol not set */
	final public static NativeLong SCARD_PROTOCOL_UNSET = new NativeLong(0x0000);
	/** < T=0 active protocol. */
	final public static NativeLong SCARD_PROTOCOL_T0 = new NativeLong(0x0001);
	/** < T=1 active protocol. */
	final public static NativeLong SCARD_PROTOCOL_T1 = new NativeLong(0x0002);
	/** T=0 or T=1*/
	final public static NativeLong SCARD_PROTOCOL_T0_T1 = new NativeLong(0x0001 | 0x0002);
	/** < Raw active protocol. */
	final public static NativeLong SCARD_PROTOCOL_RAW = new NativeLong(0x0004);
	/** < T=15 protocol. */
	final public static NativeLong SCARD_PROTOCOL_T15 = new NativeLong(0x0008);
	
	public static NativeLong mapProtocol(Protocol protocol) {
		switch (protocol){
		case UNSET:
			return SCARD_PROTOCOL_UNSET;
		case T0:
			return SCARD_PROTOCOL_T0;
		case T1:
			return SCARD_PROTOCOL_T1;
		case T0_T1:
			return SCARD_PROTOCOL_T0_T1;
		case RAW:
			return SCARD_PROTOCOL_RAW;
		case T15:
			return SCARD_PROTOCOL_T15;
		}
		return null;
	}

	/** < Do nothing on close */
	final public static NativeLong SCARD_LEAVE_CARD = new NativeLong(0x0000);
	/** < Reset on close */
	final public static NativeLong SCARD_RESET_CARD = new NativeLong(0x0001);
	/** < Power down on close */
	final public static NativeLong SCARD_UNPOWER_CARD = new NativeLong(0x0002);
	/** < Eject on close */
	final public static NativeLong SCARD_EJECT_CARD = new NativeLong(0x0003);

	public static NativeLong mapDisposition(Disposition dis) {
		switch (dis) {
		case LEAVE_CARD:
			return SCARD_LEAVE_CARD;
		case RESET_CARD:
			return SCARD_RESET_CARD;
		case UNPOWER_CARD:
			return SCARD_UNPOWER_CARD;
		case EJECT_CARD:
			return SCARD_EJECT_CARD;
		}
		return null;
	}
	
	
	public static final NativeLong SCARD_S_SUCCESS = new NativeLong(0x00000000);
	public static final NativeLong SCARD_E_CANCELLED = new NativeLong(0x80100002);
	public static final NativeLong SCARD_E_CANT_DISPOSE = new NativeLong(0x8010000E);
	public static final NativeLong SCARD_E_INSUFFICIENT_BUFFER = new NativeLong(0x80100008);
	public static final NativeLong SCARD_E_INVALID_ATR = new NativeLong(0x80100015);
	public static final NativeLong SCARD_E_INVALID_HANDLE = new NativeLong(0x80100003);
	public static final NativeLong SCARD_E_INVALID_PARAMETER = new NativeLong(0x80100004);
	public static final NativeLong SCARD_E_INVALID_TARGET = new NativeLong(0x80100005);
	public static final NativeLong SCARD_E_INVALID_VALUE = new NativeLong(0x80100011);
	public static final NativeLong SCARD_E_NO_MEMORY = new NativeLong(0x80100006);
	public static final NativeLong SCARD_F_COMM_ERROR = new NativeLong(0x80100013);
	public static final NativeLong SCARD_F_INTERNAL_ERROR = new NativeLong(0x80100001);
	public static final NativeLong SCARD_F_UNKNOWN_ERROR = new NativeLong(0x80100014);
	public static final NativeLong SCARD_F_WAITED_TOO_LONG = new NativeLong(0x80100007);
	public static final NativeLong SCARD_E_UNKNOWN_READER = new NativeLong(0x80100009);
	public static final NativeLong SCARD_E_TIMEOUT = new NativeLong(0x8010000A);
	public static final NativeLong SCARD_E_SHARING_VIOLATION = new NativeLong(0x8010000B);
	public static final NativeLong SCARD_E_NO_SMARTCARD = new NativeLong(0x8010000C);
	public static final NativeLong SCARD_E_UNKNOWN_CARD = new NativeLong(0x8010000D);
	public static final NativeLong SCARD_E_PROTO_MISMATCH = new NativeLong(0x8010000F);
	public static final NativeLong SCARD_E_NOT_READY = new NativeLong(0x80100010);
	public static final NativeLong SCARD_E_SYSTEM_CANCELLED = new NativeLong(0x80100012);
	public static final NativeLong SCARD_E_NOT_TRANSACTED = new NativeLong(0x80100016);
	public static final NativeLong SCARD_E_READER_UNAVAILABLE = new NativeLong(0x80100017);

	public static final NativeLong SCARD_W_UNSUPPORTED_CARD = new NativeLong(0x80100065);
	public static final NativeLong SCARD_W_UNRESPONSIVE_CARD = new NativeLong(0x80100066);
	public static final NativeLong SCARD_W_UNPOWERED_CARD = new NativeLong(0x80100067);
	public static final NativeLong SCARD_W_RESET_CARD = new NativeLong(0x80100068);
	public static final NativeLong SCARD_W_REMOVED_CARD = new NativeLong(0x80100069);

	public static final NativeLong SCARD_E_PCI_TOO_SMALL = new NativeLong(0x80100019);
	public static final NativeLong SCARD_E_READER_UNSUPPORTED = new NativeLong(0x8010001A);
	public static final NativeLong SCARD_E_DUPLICATE_READER = new NativeLong(0x8010001B);
	public static final NativeLong SCARD_E_CARD_UNSUPPORTED = new NativeLong(0x8010001C);
	public static final NativeLong SCARD_E_NO_SERVICE = new NativeLong(0x8010001D);
	public static final NativeLong SCARD_E_SERVICE_STOPPED = new NativeLong(0x8010001E);

}
