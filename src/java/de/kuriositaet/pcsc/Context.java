package de.kuriositaet.pcsc;

import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.NativeLongByReference;

import de.kuriositaet.pcsc.ffi.Constants;
import de.kuriositaet.pcsc.ffi.PCSC_FFI;

public class Context extends PCSCBase {
	private PCSC_FFI ffi = PCSC_FFI.INSTANCE;
	NativeLongByReference phContext = new NativeLongByReference();
	private boolean released;
	
	
	
	protected NativeLong getNativeContext() {
		return phContext.getValue();
	}
	/**
	 * Create a new Context using SCARD_SCOPE_SYSTEM
	 * wraps SCardEstablishContext
	 */
	public Context () {
		this(Scope.SYSTEM);
	}
	
	public Context(Scope scope) {
		this(Constants.mapScope(scope));
	}
	
	/**
	 * Create a new Context with the indicated scope.
	 * wraps SCardEstablishContext
	 * @param scope Scope constant from Constants
	 */
	private Context (NativeLong scope) {
		in("Context()");
		NativeLong err = ffi.SCardEstablishContext(
				scope, 
				Pointer.NULL, 
				Pointer.NULL, 
				phContext);
		this.checkResult("Context()", err);
		
		released = false;
		out("Context()");
	}
	
	/**
	 * Check whether the Context is valid
	 * @return whether the Context is valid
	 */
	public boolean isValid () {
		NativeLong ret = ffi.SCardIsValidContext(phContext.getValue());
		return (Constants.SCARD_S_SUCCESS.equals(ret));
	}
	
	/**
	 * Free the resources used by the Context.
	 * Wraps SCardReleaseContext
	 */
	public void release () {
		if (released) {
			return;
		}
		NativeLong err = ffi.SCardReleaseContext(phContext.getValue());
		this.checkResult("Context.release()", err);
		
		released = true;
	}
	
	/**
	 * List all available ReaderGroups
	 * @return an array of all available ReaderGroup names
	 */
	public String [] listReaderGroups () {
		in("listReaderGroups()");
		
		NativeLongByReference numReaderGroups = new NativeLongByReference();
		NativeLong result = ffi.SCardListReaderGroups(
				phContext.getValue(), 
				null, 
				numReaderGroups);
		
		this.checkResult("Context.listReaderGroups()1", result);
		
		
		Memory mem = new Memory(numReaderGroups.getValue().longValue());
		
		result = ffi.SCardListReaderGroups(
				phContext.getValue(), 
				mem, 
				numReaderGroups);
		
		this.checkResult("Context.listReaderGroups()2", result);

		out("listReaderGroups()");
		return Util.memToStringArray(mem);
	}
	
	/**
	 * List Readers available in the system
	 * @return an array containing all available readers.
	 */
	public String [] listReaders() {
		return listReaders(null);
	}
	/**
	 * List all available readers in a specified ReaderGroup.
	 * You can list all ReaderGroups using listReaderGroups
	 * @param readerGroup 
	 * @return an array of all Readers in the indicated ReaderGroup
	 */
	public String [] listReaders (String readerGroup) {
		in("listReaders()");
		
		NativeLongByReference numReaders = new NativeLongByReference();
		NativeLong result = ffi.SCardListReaders(
				phContext.getValue(), 
				readerGroup, 
				null, 
				numReaders);
		
		this.checkResult("Context.listReaders()1", result);
		
		Memory m = new Memory(numReaders.getValue().longValue());

		result = ffi.SCardListReaders(
				phContext.getValue(), 
				readerGroup, 
				m, 
				numReaders);
		
		this.checkResult("Context.listReaders()1", result);
		
		out("listReaders()");

		return Util.memToStringArray(m);
	}
	public static void main (String [] args) {
		Context c = new Context();
		String [] readerGroups = c.listReaderGroups();
		for (String s : readerGroups){
			System.out.println(s);
		}
		readerGroups = c.listReaders(readerGroups[0]);
		for (String s : readerGroups){
			System.out.println(s);
		}
		
		c.release();
	}
}
