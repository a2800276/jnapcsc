package de.kuriositaet.pcsc;

import java.util.HashMap;
import java.util.Map;

import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import com.sun.jna.ptr.NativeLongByReference;

import de.kuriositaet.pcsc.ffi.Constants;
import de.kuriositaet.pcsc.ffi.PCSC_FFI;

/**
 * This class represents a smart card in a reader. It contains functionality
 * to establish and free connections to a card and to send and receive data.
 * 
 * Basically communicating to a smartcard is as follows:
 * <pre>
 *  Card card;
 *  bytes [] answer;
 *  try {
 *  	answer = card.transmit(someBytes);
 *  	doSomething(answer);
 *  }
 *  finally {
 *  	if (card != null) {
 *  		card.disconnect();
 *  	}
 *  }
 * </pre>
 * 
 * Note that all actions taken with card <i>may</i> throw an unchecked 
 * PCSCException that you would handle in a catch clause...
 * @author tbe
 *
 */
public class Card extends PCSCBase {
	PCSC_FFI ffi = PCSC_FFI.INSTANCE;
	
	private String reader;
	private Context ctx;
	private NativeLongByReference card = new NativeLongByReference();
	private NativeLongByReference proto = new NativeLongByReference();
	private boolean connected;
	private ShareMode shareMode;
	private boolean txInProgress;

	/**
	 * Indicates whether the Card was instantiated with a passed in Context
	 * or not, i.e. whether we'll need to dispose of the Context when
	 * disconnect is called.
	 */
	private boolean hasOwnContext;

	private Protocol protocol;
	
	/**
	 * Return the JNA handle of this card.
	 * @return the JNA handle representing this card
	 */
	private NativeLong getNativeCard() {
		return this.card.getValue();
	}
	
	/**
	 * Connect to a card with parameters provided in the map argument.
	 * 
	 * @param map
	 * @see CardOptions
	 */
	public Card(Map<CardOptions, Object> map){
		if (map.containsKey(CardOptions.CONTEXT)){
			this.ctx = (Context) map.get(CardOptions.CONTEXT);
		} else {
			this.ctx = new Context();
			this.hasOwnContext = true;
		}
		
		this.reader    = map.containsKey(CardOptions.READER) 
					     ? (String)map.get(CardOptions.READER) 
					     : this.ctx.listReaders()[0];
		
		this.protocol  = map.containsKey(CardOptions.PROTOCOL)
		                 ? (Protocol) map.get(CardOptions.PROTOCOL)
						 : Protocol.T0_T1;
		                
		this.shareMode = map.containsKey(CardOptions.SHARE_MODE)
						 ? (ShareMode)map.get(CardOptions.SHARE_MODE)
						 : ShareMode.EXCLUSIVE;
		
		establishConnection();
	}
	
	/**
	 * Establish a connection to a card using an internally managed Context 
	 * and using the first Reader returned by Context.listReaders()
	 * 
	 * @throws PCSCException
	 */
	public Card () {
		this(new HashMap<CardOptions, Object>());
	}
	
	
	
	/**
	 * Establish a connection to this card using the specified Context.
	 * @param ctx
	 * @throws PCSCException
	 */
	public Card (Context ctx) {
		this(ctx,null);
	}
	
	/**
	 * Establish a connection to a card in the indicated reader 
	 * using the provided Context.
	 * @param ctx
	 * @param reader
	 * @throws PCSCException
	 */
	public Card (Context ctx, String reader) {
		this.reader = reader != null ? reader : ctx.listReaders()[0];
		this.ctx    = ctx;
		this.protocol = Protocol.T0_T1;
		this.shareMode = ShareMode.EXCLUSIVE;
		establishConnection();
	}

	/**
	 * Internal method to call SCardConnect
	 */
	private void establishConnection() {
		
		NativeLong result = ffi.SCardConnect(
				this.ctx.getNativeContext(), 
				//new NativeLong(0),
				this.reader,
				//"",
				Constants.mapShareMode(this.shareMode), 
				Constants.mapProtocol(this.protocol),
				card, 
				proto);
		m(Stringifier.stringify(result));
		this.checkResult("Card()", result);
		
		connected = true;
	}
	
	/**
	 * Disconnect the Card
	 * @param disposition one of the disposition constants from Constants.
	 */
	private void disconnect(NativeLong disposition) {
		if (!connected){
			return;
		}
		
		if (txInProgress) {
			this.endTransaction();
		}
		
		NativeLong result = ffi.SCardDisconnect(
				this.getNativeCard(), 
				disposition);
		
		this.checkResult("Card.disconnect()", result);
		
		if (this.hasOwnContext){
			this.ctx.release();
		}
		
		connected = false;
	}
	
	/**
	 * Disconnect the card with the disposition SCARD_UNPOWER_CARD
	 */
	public void disconnect () {
		disconnect(Disposition.UNPOWER_CARD);
	}
	
	/**
	 * Disconnect the Card
	 * @param disposition one of the Disposition enums.
	 * @see Disposition
	 */
	public void disconnect(Disposition disposition){
		disconnect(Constants.mapDisposition(disposition));
	}
	
	/**
	 * Reconnect to the card
	 */
	public void reconnect () {
		NativeLong result = ffi.SCardReconnect(
				this.getNativeCard(), 
				Constants.mapShareMode(this.shareMode), 
				Constants.mapProtocol(this.protocol), 
				Constants.SCARD_RESET_CARD,
				this.proto);
		this.checkResult("Card.reconnect()", result);	
	}
	
	/**
	 * Begin a Transaction. 
	 * from PCSClite: "This function establishes a temporary exclusive access mode for 
	 * doing a serie of commands in a transaction. "
	 * 
	 * A runtime PCSCException is thrown in case a transaction is already
	 * in progress.
	 */
	public void beginTransaction() {
		if (txInProgress){
			throw new PCSCException("Tx already in progress");
		}
		NativeLong result = ffi.SCardBeginTransaction(this.getNativeCard());
		this.checkResult("Card.beginTx()", result);
		txInProgress = true;
	}
	
	/**
	 * End a transaction. In case no transasction is in progress, no action is taken.
	 */
	public void endTransaction() {
		if (!txInProgress) {
			return;
		}
		NativeLong result = ffi.SCardEndTransaction(
				this.getNativeCard(), 
				Constants.SCARD_LEAVE_CARD);
		
		this.checkResult("Card.endTx()", result);
		txInProgress = false;
	}
	
	/**
	 * In PCSC this function (SCardStatus) can be used to query
	 * the card, e.g. for it's ATR. A full implementation of
	 * this is currently pending. This call SCardStatus, but does
	 * nothing with the results.
	 */
	public void status () {
		NativeLongByReference readerSize = new NativeLongByReference();
		NativeLongByReference pdwState   = new NativeLongByReference();
		NativeLongByReference atrLen     = new NativeLongByReference();
		NativeLong result = ffi.SCardStatus(
				getNativeCard(), 
				null, 
				readerSize, 
				pdwState, 
				this.proto, 
				null, 
				atrLen);
		
		this.checkResult("Card.getStatus()1", result);
		
		Memory readerName = new Memory(readerSize.getValue().longValue());
		Memory atr        = new Memory(atrLen.getValue().longValue());
		
		result = ffi.SCardStatus(
				getNativeCard(), 
				readerName, 
				readerSize, 
				pdwState, 
				this.proto, 
				atr, 
				atrLen);
		this.checkResult("Card.getStatus()2", result);
	}
	
	
	/**
	 * Send the bytes passed as arguments to the card.
	 * @param bytes the bytes to send to the card
	 * @return the bytes returned by the card.
	 */
	public byte[] transmit(byte [] bytes) {
		PCSC_FFI.SCardIORequest req = new PCSC_FFI.SCardIORequest();
		req.dwProtocol  = Constants.SCARD_PROTOCOL_T0;
		req.cbPciLength = new NativeLong(0);
		
		PCSC_FFI.SCardIORequest resp = new PCSC_FFI.SCardIORequest();
			
		Memory sendBuffer = new Memory(bytes.length);
		sendBuffer.write(0, bytes, 0, bytes.length);
		NativeLong sendBufferLength = new NativeLong(bytes.length);
		
		//TODO figure out max bytes received...
		// shouldn't be more than 0xff
		
		Memory recvBuffer = new Memory(1024);
		NativeLongByReference bytesReceived = new NativeLongByReference(new NativeLong(1024));
		
		NativeLong result = ffi.SCardTransmit(
				this.getNativeCard(), 
				req, 
				sendBuffer, 
				sendBufferLength, 
				resp, 
				recvBuffer, 
				bytesReceived);
		
		this.checkResult("Card.transmit()", result);
		
		return recvBuffer.getByteArray(0, bytesReceived.getValue().intValue());
	}
	
	public static void main (String [] args) {
		Context ctx  = null;
		Card    card = null;
		byte [] bytes = {0x00, 0x00, 0x00, 0x00,0x00};
		try {
			ctx  = new Context();
			card = new Card(ctx);
			card.reconnect();
			card.status();
			byte [] ret = card.transmit(bytes);
			System.out.println(Util.binToHex(ret));
			card.beginTransaction();
			card.endTransaction();
			
			
		} catch (PCSCException pe) {
			pe.printStackTrace();
		}
		finally {
			if (null != card) {
				card.disconnect();
			}

			if (null != ctx) {
				ctx.release();
			}
		}//finally
		System.out.println("done");
	}
	
}

