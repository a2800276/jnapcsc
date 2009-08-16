// Copyright 2009 by Tim Becker (tim.becker@kuriostaet.de)
// MIT License, for details, see the LICENSE file accompaning
// this distribution

// The base implemenation of this class was generate automatically,
// please see src/ruby/make_apdu.rb

package de.kuriositaet.iso7816.apdu;

import de.kuriositaet.iso7816.APDU;
/**
 * Class implementing the ENVELOPE command defined in ISO 7816-4
 *
 */
public class ENVELOPE extends APDU {
	public ENVELOPE () {
		super("ENVELOPE", (byte)0x00, (byte)0xC2);
	}
}
