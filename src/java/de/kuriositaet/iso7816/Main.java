package de.kuriositaet.iso7816;

import de.kuriositaet.iso7816.apdu.SELECT;
import de.kuriositaet.pcsc.Card;

public class Main {
	public static void main (String [] args) {
		
		int i  = 0;
		byte b = (byte)0xff;
		i = b;
		char c = 0xff;
		System.out.println(b);
		System.out.println(c);
		b = (byte)c;
		System.out.println(b);
		System.out.println(b < 0 ? b+256 : b);
		int j = 200;
		System.out.println((byte)i);
	}
	
	public void demo () {
		
//		Session ses = new Session();
//		ses.add(SELECT);
//		ses.add(GET_PO, new POST<GET_PO>(GET_PO getpo) {
//			byte [] rec_ids = getpo.getRecordIds();
//			getpo.getSession().insertAfter(getpo, new READ_RECORD(rec_ids);
//		});
		
		Card c = new Card();
		SELECT sel = new SELECT();
		byte[] result = c.transmit(sel.getBytes());
		
		
	}
}
