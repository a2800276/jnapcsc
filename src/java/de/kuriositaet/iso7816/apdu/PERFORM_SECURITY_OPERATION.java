// Copyright 2009 by Tim Becker (tim.becker@kuriostaet.de)
// MIT License, for details, see the LICENSE file accompaning
// this distribution

// The base implemenation of this class was generate automatically,
// please see src/ruby/make_apdu.rb

package de.kuriositaet.iso7816.apdu;

import de.kuriositaet.iso7816.APDU;
/**
 * Class implementing the PERFORM SECURITY OPERATION command defined in ISO 7816-4
 *
 */
public class PERFORM_SECURITY_OPERATION extends APDU {
	public PERFORM_SECURITY_OPERATION () {
		super("PERFORM SECURITY OPERATION", (byte)0x00, (byte)0x2A);
	}
}
