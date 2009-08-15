package de.kuriositaet.pcsc.ffi;

import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.NativeLongByReference;

public interface PCSC_FFI extends Library {
	PCSC_FFI INSTANCE = (PCSC_FFI)Native.loadLibrary("PCSC", PCSC_FFI.class);
	
	//BOOL	short 
	//BYTE    unsigned char 
	//DWORD    unsigned long 
	//LONG    long 
	//LPBYTE    unsigned char * 
	//LPCBYTE    const unsigned char * 
	//LPCSTR    const char * 
	//LPCVOID    const void * 
	//LPCWSTR   char * 
	//LPDWORD    unsigned long * 
	//LPSCARDCONTEXT  unsigned long * 
	//LPSCARDHANDLE  unsigned long * 
	//LPSTR    char * 
	//LPVOID    void * 
	//PSCARDCONTEXT  unsigned long * 
	//PSCARDHANDLE  unsigned long * 
	//RESPONSECODE long 
	//SCARDCONTEXT  unsigned long 
	//SCARDHANDLE  unsigned long 
	//ULONG    unsigned long 
	//USHORT    unsigned short 
	//WORD    unsigned long

	// Error Codes
	//
	// SCARD_S_SUCCESS 
	// 
	// SCARD_E_CANCELLED 
	// SCARD_E_CANT_DISPOSE 
	// SCARD_E_CARD_UNSUPPORTED 
	// SCARD_E_DUPLICATE_READER 
	// SCARD_E_INSUFFICIENT_BUFFER 
	// SCARD_E_INVALID_ATR 
	// SCARD_E_INVALID_HANDLE 
	// SCARD_E_INVALID_PARAMETER 
	// SCARD_E_INVALID_TARGET 
	// SCARD_E_INVALID_VALUE 
	// SCARD_E_NO_MEMORY 
	// SCARD_E_NO_SERVICE 
	// SCARD_E_NO_SMARTCARD 
	// SCARD_E_NOT_READY 
	// SCARD_E_NOT_TRANSACTED 
	// SCARD_E_PCI_TOO_SMALL 
	// SCARD_E_PROTO_MISMATCH 
	// SCARD_E_READER_UNAVAILABLE 
	// SCARD_E_READER_UNSUPPORTED 
	// SCARD_E_SERVICE_STOPPED 
	// SCARD_E_SHARING_VIOLATION 
	// SCARD_E_SYSTEM_CANCELLED 
	// SCARD_E_TIMEOUT 
	// SCARD_E_UNKNOWN_CARD 
	// SCARD_E_UNKNOWN_READER 
	// SCARD_F_COMM_ERROR 
	// SCARD_F_INTERNAL_ERROR 
	// SCARD_F_UNKNOWN_ERROR 
	// SCARD_F_WAITED_TOO_LONG 
	// 
	// SCARD_W_UNSUPPORTED_CARD 
	// SCARD_W_UNRESPONSIVE_CARD 
	// SCARD_W_UNPOWERED_CARD 
	// SCARD_W_RESET_CARD 
	// SCARD_W_REMOVED_CARD 
	
	/*LONG SCardEstablishContext(DWORD dwScope, 
	LPCVOID pvReserved1, 
	LPCVOID pvReserved2, 
	LPSCARDCONTEXT phContext);*/

	// dwScope Constants
	//SCARD_SCOPE_USER Not used 
	//SCARD_SCOPE_TERMINAL Not used 
	//SCARD_SCOPE_GLOBAL Not used 
	//SCARD_SCOPE_SYSTEM Services on the local machine

	NativeLong SCardEstablishContext(
		NativeLong dwScope,
		Pointer pvReserved1,
		Pointer pvReserved2,
		NativeLongByReference phContext	// long
	);


	// LONG SCardReleaseContext(SCARDCONTEXT hContext);
	
	NativeLong SCardReleaseContext(NativeLong hContext);

	// LONG SCardIsValidContext(SCARDCONTEXT hContext);
	
	NativeLong SCardIsValidContext(NativeLong hContext);

	//LONG SCardListReaders(SCARDCONTEXT hContext, 
	//	LPCSTR mszGroups, 
	//	LPSTR mszReaders, 
	//	LPDWORD pcchReaders); 
	
	NativeLong SCardListReaders(
		NativeLong hContext,
		String mszGroups, 	// const unsigned char *
		Pointer mszReaders, 	// char *
		NativeLongByReference pcchReaders	// unsigned long *
	);

	//LONG SCardListReaderGroups(SCARDCONTEXT hContext, 
	//	LPSTR mszGroups, 
	//	LPDWORD pcchGroups); 
	
	NativeLong SCardListReaderGroups(
		NativeLong hContext,
		Pointer mszGroups,	//char *
		NativeLongByReference pcchReaders	//unsigned long *
	);

	// LONG SCardConnect(SCARDCONTEXT hContext, 
	//	LPCSTR szReader, 
	//	DWORD dwShareMode, 
	//	DWORD dwPreferredProtocols, 
	//	LPSCARDHANDLE phCard, 
	//	LPDWORD pdwActiveProtocol);
	
	NativeLong SCardConnect(
		NativeLong hContext,
		String szReader, //char *
		NativeLong dwShareMode,
		NativeLong dwPreferredProtocols,
		NativeLongByReference phCard, //unsigned long *
		NativeLongByReference pdwActiveProtocol // unsigned long *	
	);

	// LONG SCardReconnect(SCARDHANDLE hCard, 
	// 	DWORD dwShareMode, 
	// 	DWORD dwPreferredProtocols, 
	// 	DWORD dwInitialization, 
	// 	LPDWORD pdwActiveProtocol); 


	NativeLong SCardReconnect(
		NativeLong hCard,
		NativeLong dwShareMode,
		NativeLong dwPreferredProtocols,
		NativeLong dwInitialization,
		NativeLongByReference pdwActiveProtocol // unsigned long *	
	);

	// LONG SCardDisconnect(SCARDHANDLE hCard, DWORD dwDisposition);
	
	NativeLong SCardDisconnect( NativeLong hCard, NativeLong dwDisposition);

	//LONG SCardBeginTransaction(SCARDHANDLE hCard);
	
	NativeLong SCardBeginTransaction(NativeLong hCard);

	// LONG SCardEndTransaction(SCARDHANDLE hCard, 
	//	DWORD dwDisposition);
	
	NativeLong SCardEndTransaction(NativeLong hCard, NativeLong dwDisposition);
	

	// ------------------------------------------------------------------------	

	class SCardIORequest extends Structure {
		// typedef struct { 
		//	DWORD dwProtocol; /* SCARD_PROTOCOL_T0 or SCARD_PROTOCOL_T1 */ 
		//	DWORD cbPciLength; /* Length of this structure - not used */ 
		//} SCARD_IO_REQUEST; 

		public NativeLong dwProtocol;
		public NativeLong cbPciLength;

	}

	// LONG SCardTransmit(SCARDHANDLE hCard, 
	// 	LPCSCARD_IO_REQUEST pioSendPci, 
	// 	LPCBYTE pbSendBuffer, 
	// 	DWORD cbSendLength, 
	// 	LPSCARD_IO_REQUEST pioRecvPci, 
	// 	LPBYTE pbRecvBuffer, 
	// 	LPDWORD pcbRecvLength);
	
	NativeLong SCardTransmit(
		NativeLong hCard,
		SCardIORequest pioSendPci, // TODO struct {long, long}
		Memory pdSendBuffer, // const unsigned char *
		NativeLong cbSendLength,
		SCardIORequest pioRecvPci, // TODO struct
		Memory pbRecvBuffer, // unsigned char *
		NativeLongByReference pcbRecvLength // unsigned long *
	);
	
	// LONG SCardControl(SCARDHANDLE hCard, 
	// 	DWORD dwControlCode, 
	// 	LPCVOID pbSendBuffer, 
	// 	DWORD cbSendLength, 
	// 	LPVOID pbRecvBuffer, 
	// 	DWORD pcbRecvLength, 
	// 	LPDWORD lpBytesReturned); 
	
	long SCardControl(
			long hCard,
			long dwControlCode,
			Pointer pbSendBuffer, // const void *
			long cdSendLength,
			Pointer pbRecvBuffer, 
			long pbRecvLength,
			LongByReference lpBytesReturned);

	// LONG SCardStatus(SCARDHANDLE hCard, 
	// 	LPSTR szReaderName, 
	// 	LPDWORD pcchReaderLen, 
	// 	LPDWORD pdwState, 
	// 	LPDWORD pdwProtocol, 
	// 	LPBYTE pbAtr, 
	// 	LPDWORD pcbAtrLen); 
	
	NativeLong SCardStatus (
			NativeLong hCard,
			Memory readerName, // unsigned long *	
			NativeLongByReference readerSize,
			NativeLongByReference pdwState,
			NativeLongByReference pdwProtocol,
			Memory pbAtr, // unsigned char *
			NativeLongByReference pcbAtrLen);

	// ------------------------------------------------------------------------
	
	// LONG SCardGetStatusChange(SCARDCONTEXT hContext, 
	//	DWORD dwTimeout, 
	// 	LPSCARD_READERSTATE rgReaderStates, 
	// 	DWORD cReaders);

	class SCardReaderState extends Structure {
		//typedef struct { 
		//	LPCSTR szReader; /* Reader name */ 
		//	LPVOID pvUserData; /* User defined data */ 
		//	DWORD dwCurrentState; /* Current state of reader */ 
		//	DWORD dwEventState; /* Reader state after a state change */ 
		//	DWORD cbAtr; /* ATR Length, usually MAX_ATR_SIZE */ 
		//	BYTE rgbAtr[MAX_ATR_SIZE]; /* ATR Value */ 
		//} SCARD_READERSTATE; 
		
		String szReader;
		Pointer pvUserData;
		long dwCurrentState;
		long dwEventState;
		long cbAtr;
		Pointer rgbAtr;
	};
	
	long SCardGetStatusChange (
			long hContext,
			long dwTimeout,
			SCardReaderState rgReaderStates, // struct {...} 
			long cReaders);


	// LONG SCardCancel(SCARDCONTEXT hContext); 
	long SCardCancel(long hContext);

	// LONG SCardSetTimeout(SCARDCONTEXT hContext, 
	//	DWORD dwTimeout); 
	long SCardSetTimeout(long hContext, long dwTimeout);

	// LONG SCardGetAttrib(SCARDHANDLE hCard, 
	// 	DWORD dwAttrId, 
	// 	LPBYTE pbAttr, 
	// 	LPDWORD pcbAttrLen);

	long SCardGetAttrib (
			long hCard,
			long dwAttrId,
			Pointer pbAttr, // unsigned char*
			Pointer pcbAttrLen); // usigned long *

	// LONG SCardSetAttrib(SCARDHANDLE hCard, 
	// 	DWORD dwAttrId, 
	// 	LPCBYTE pbAttr, 
	// 	DWORD cbAttrLen); 
	
	long SCardSetAttrib(
			long hCard,
			long dwAttrId,
			Pointer pbAttr, //const unsigned char *
			long cbAttrLen);

	//char *pcsc_stringify_error(long error); 
	
	String pcsc_stringify_error(NativeLong error);

	
}
