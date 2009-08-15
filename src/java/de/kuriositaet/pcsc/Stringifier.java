package de.kuriositaet.pcsc;

import com.sun.jna.NativeLong;

/**
 * Converts error codes, etc. returned by the FFI interface into
 * String constants which equal the names of the defines in the C Code.
 * @author tbe
 *
 */
public class Stringifier {
	public static String stringify(NativeLong err) {
		switch (err.intValue()) {
		case 0x00000000:
			return "SCARD_S_SUCCESS";
		case 0x80100002:
			return "SCARD_E_CANCELLED";
		case 0x8010000E:
			return "SCARD_E_CANT_DISPOSE";
		case 0x80100008:
			return "SCARD_E_INSUFFICIENT_BUFFER";
		case 0x80100015:
			return "SCARD_E_INVALID_ATR";
		case 0x80100003:
			return "SCARD_E_INVALID_HANDLE";
		case 0x80100004:
			return "SCARD_E_INVALID_PARAMETER";
		case 0x80100005:
			return "SCARD_E_INVALID_TARGET";
		case 0x80100011:
			return "SCARD_E_INVALID_VALUE";
		case 0x80100006:
			return "SCARD_E_NO_MEMORY";
		case 0x80100013:
			return "SCARD_F_COMM_ERROR";
		case 0x80100001:
			return "SCARD_F_INTERNAL_ERROR";
		case 0x80100014:
			return "SCARD_F_UNKNOWN_ERROR";
		case 0x80100007:
			return "SCARD_F_WAITED_TOO_LONG";
		case 0x80100009:
			return "SCARD_E_UNKNOWN_READER";
		case 0x8010000A:
			return "SCARD_E_TIMEOUT";
		case 0x8010000B:
			return "SCARD_E_SHARING_VIOLATION";
		case 0x8010000C:
			return "SCARD_E_NO_SMARTCARD";
		case 0x8010000D:
			return "SCARD_E_UNKNOWN_CARD";
		case 0x8010000F:
			return "SCARD_E_PROTO_MISMATCH";
		case 0x80100010:
			return "SCARD_E_NOT_READY";
		case 0x80100012:
			return "SCARD_E_SYSTEM_CANCELLED";
		case 0x80100016:
			return "SCARD_E_NOT_TRANSACTED";
		case 0x80100017:
			return "SCARD_E_READER_UNAVAILABLE";

		case 0x80100065:
			return "SCARD_W_UNSUPPORTED_CARD";
		case 0x80100066:
			return "SCARD_W_UNRESPONSIVE_CARD";
		case 0x80100067:
			return "SCARD_W_UNPOWERED_CARD";
		case 0x80100068:
			return "SCARD_W_RESET_CARD";
		case 0x80100069:
			return "SCARD_W_REMOVED_CARD";

		case 0x80100019:
			return "SCARD_E_PCI_TOO_SMALL";
		case 0x8010001A:
			return "SCARD_E_READER_UNSUPPORTED";
		case 0x8010001B:
			return "SCARD_E_DUPLICATE_READER";
		case 0x8010001C:
			return "SCARD_E_CARD_UNSUPPORTED";
		case 0x8010001D:
			return "SCARD_E_NO_SERVICE";
		case 0x8010001E:
			return "SCARD_E_SERVICE_STOPPED";
		default:
			return "Unknown Error: " + err.intValue();
		}

	}

}
