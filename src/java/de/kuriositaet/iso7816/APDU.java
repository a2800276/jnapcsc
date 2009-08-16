package de.kuriositaet.iso7816;

import java.util.Formatter;

import de.kuriositaet.pcsc.Util;

public class APDU {
	private byte cla;
	private byte ins;
	private byte p1;
	private byte p2;
	private byte le;
	private boolean le_;
	private boolean data_;
	private byte[] data;
	private String name;

	public APDU (byte cla, byte ins) {
		this(cla, ins, (byte)0, (byte)0);
	}
	public APDU (String name, byte cla, byte ins) {
		this(name, cla, ins, (byte)0, (byte)0);
	}
	
	public APDU (byte cla, byte ins, byte p1, byte p2) {
		this.cla = cla;
		this.ins = ins;
		this.p1  = p1;
		this.p2  = p2;
		this.name = "UNKNOWN";
	}
	public APDU (String name, byte cla, byte ins, byte p1, byte p2) {
		this(cla, ins, p1, p2);
		setName(name);
	}
	
	public APDU(byte cla, byte ins, byte p1, byte p2, byte le) {
		this (cla, ins, p1, p2);
		this.le  = le;
		this.le_ = true;
	}
	public APDU (String name, byte cla, byte ins, byte p1, byte p2, byte le) {
		this(cla, ins, p1, p2, le);
		setName(name);
	}
	public APDU(byte cla, byte ins, byte p1, byte p2, byte [] data) {
		this (cla, ins, p1, p2);
		this.data  = data;
		this.data_ = true;
	}
	public APDU (String name, byte cla, byte ins, byte p1, byte p2, byte [] data) {
		this(cla, ins, p1, p2, data);
		setName(name);
	}
	public APDU(byte cla, byte ins, byte p1, byte p2, byte [] data, byte le) {
		this (cla, ins, p1, p2, data);
		this.le  = le;
		this.le_ = true;
	}
	public APDU (String name, byte cla, byte ins, byte p1, byte p2, byte [] data, byte le) {
		this(cla, ins, p1, p2, data, le);
		setName(name);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte getCla() {
		return cla;
	}
	public void setCla(byte cla) {
		this.cla = cla;
	}
	public byte getIns() {
		return ins;
	}
	public void setIns(byte ins) {
		this.ins = ins;
	}
	public byte getP1() {
		return p1;
	}
	public void setP1(byte p1) {
		this.p1 = p1;
	}
	public byte getP2() {
		return p2;
	}
	public void setP2(byte p2) {
		this.p2 = p2;
	}
	public byte getLe() {
		return le;
	}
	public void setLe(byte le) {
		this.le  = le;
		this.le_ = true;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
		this.data_ = true;
	}
	public byte[] getBytes () {
		int length_apdu = 4;// cla + ins + p1 + p2
		length_apdu = le_   ? length_apdu+1 : length_apdu;
		length_apdu = data_ ? length_apdu+this.data.length+1 : length_apdu;
		byte [] bytes    = new byte[length_apdu];
		        bytes[0] = cla;
		        bytes[1] = ins;
		        bytes[2] = p1;
		        bytes[3] = p2;
		if (data_){
			bytes[4] = (byte)data.length;
			System.arraycopy(data, 0, bytes, 5, data.length);
		}
		if (le_) {
			bytes[bytes.length-1] = this.le;
		}		
		return bytes;
	}
	
	public String toString () {
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
		sb.append (getName());
		sb.append ("\n");
		sb.append ("| cla| ins|p1|p2|");
		if (data_) {
			sb.append("lc|");
			f.format("%"+data.length*2+"s|", "data");
		}
		if (le_) {
			sb.append("le|");
		}
		
		sb.append ("\n");

		
		f.format("|  %02X|  %02X|%02X|%02X|", this.cla, this.ins, this.p1, this.p2);
		if (data_){
			f.format("%02X|", data.length);
			f.format("%"+data.length*2+"s|",Util.binToHex(data));
		}
		if (le_){
		f.format("%02X|", this.le);
		}
		sb.append("\n");
		return sb.toString();
	}
}
