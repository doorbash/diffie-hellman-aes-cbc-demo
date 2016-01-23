package security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

public class DiffieHellmanB {
	
	KeyAgreement bobKeyAgree;
	PublicKey alicePubKey;

	public byte[] genPubKey(byte[] aPubKey) throws Exception
	{
		KeyFactory bobKeyFac = KeyFactory.getInstance("DH");
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(aPubKey);
		alicePubKey = bobKeyFac.generatePublic(x509KeySpec);

		DHParameterSpec dhParamSpec = ((DHPublicKey) alicePubKey).getParams();

		KeyPairGenerator bobKpairGen = KeyPairGenerator.getInstance("DH");
		bobKpairGen.initialize(dhParamSpec);
		KeyPair bobKpair = bobKpairGen.generateKeyPair();

		bobKeyAgree = KeyAgreement.getInstance("DH");
		bobKeyAgree.init(bobKpair.getPrivate());

		return bobKpair.getPublic().getEncoded();
	}
	
	public byte[] genSecret() throws Exception
	{
		bobKeyAgree.doPhase(alicePubKey, true);
		return bobKeyAgree.generateSecret();
	}

}
