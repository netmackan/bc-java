package org.bouncycastle.pqc.crypto.test;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.ParseException;

import junit.framework.TestCase;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.pqc.crypto.xmss.XMSSMT;
import org.bouncycastle.pqc.crypto.xmss.XMSSMTParameters;
import org.bouncycastle.pqc.crypto.xmss.XMSSMTPublicKeyParameters;
import org.bouncycastle.pqc.crypto.xmss.XMSSUtil;

/**
 * Test cases for XMSSMTPublicKey class.
 * 
 */
public class XMSSMTPublicKeyTest extends TestCase {

	public void testPublicKeyParsingSHA256() throws IOException, ClassNotFoundException {
		XMSSMTParameters params = new XMSSMTParameters(20, 10, new SHA256Digest());
		XMSSMT mt = new XMSSMT(params, new SecureRandom());
		mt.generateKeys();
		byte[] privateKey = mt.exportPrivateKey();
		byte[] publicKey = mt.exportPublicKey();

		try {
			mt.importState(privateKey, publicKey);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		assertTrue(XMSSUtil.compareByteArray(publicKey, mt.exportPublicKey()));
	}

	public void testConstructor() {
		XMSSMTParameters params = new XMSSMTParameters(20, 10, new SHA256Digest());
		XMSSMTPublicKeyParameters pk = new XMSSMTPublicKeyParameters.Builder(params).build();

		byte[] pkByte = pk.toByteArray();
		/* check everything is 0 */
		for (int i = 0; i < pkByte.length; i++) {
			assertEquals(0x00, pkByte[i]);
		}
	}
}
