package de.kuriositaet.pcsc;

import java.util.ArrayList;
import java.util.Formatter;

import com.sun.jna.Memory;
import com.sun.jna.NativeLong;

import de.kuriositaet.pcsc.ffi.PCSC_FFI;

public class Util {
	/**
	 * Provides a more readable version of an error returned by PCSC.
	 * @param err
	 * @return
	 */
	public static String stringifyError(NativeLong err) {
		PCSC_FFI ffi = PCSC_FFI.INSTANCE;
		return ffi.pcsc_stringify_error(err);
	}
	/**
	 * Provides a more readable version of an error returned by PCSC.
	 */
	public static String stringifyError(long err) {
		NativeLong l = new NativeLong(err);
		return stringifyError(l);
	}
	public static final String [] CAST = {};
	
	/**
	 * Utility to convert JNA Memory into an array of Strings.
	 * Expects each String in memory to be seperated by binary nulls
	 * and a final binary null to indicate the end of the array.
	 * 
	 * Example:
	 * 
	 * 	"0x00one0x00two\x00three0x000x00"
	 * 
	 * is converted to:
	 * 
	 * 	{"one", "two", "three"}
	 * @param mem
	 * @return
	 */
	public static String[] memToStringArray(Memory mem){
		ArrayList<String> results = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		boolean prevNull = false;
		for (long i =0; i!=mem.getSize(); ++i) {
			if (mem.getByte(i) == 0x00) {
				
				if (prevNull) {
					//we are done, two nulls...
					break;
				}
				// word is done
				prevNull = true;
				results.add(builder.toString());
				builder = new StringBuilder();
			} else {
				prevNull = false;
				builder.append((char)mem.getByte(i));
			}
		}
		return results.toArray(CAST);
	}
	
	/**
	 * Returns a String (hex) representation of the passed in byte array.
	 * @param arr
	 * @return
	 */
	public static String binToHex(byte [] arr) {
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
		for (byte b : arr) { 
			f.format("%02x", b);
		}
		return sb.toString();
	}
	/**
	 * Returns a String (hex) representation of the passed memory.
	 * @param arr
	 * @return
	 */
	public static String binToHex(Memory m) {
		return binToHex(memToByte(m));
	}
	
	/**
	 * Converts the JNA Memory passed in into a byte array.
	 * @param m
	 * @return
	 */
	public static byte[] memToByte(Memory m) {
		byte [] arr = new byte[(int) m.getSize()];
		m.read(0, arr, 0, (int) m.getSize());
		return arr;
	}
}
