package security;

import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyAgreement;
import javax.crypto.spec.DHParameterSpec;

public class DiffieHellmanA {

	private KeyAgreement aliceKeyAgree;
	private byte[] alicePubKeyEnc;

	public byte[] genPubKey() throws Exception {
		System.out.println("Creating Diffie-Hellman parameters (takes VERY long) ...");
		AlgorithmParameterGenerator paramGen = AlgorithmParameterGenerator.getInstance("DH");
		paramGen.init(512);
		AlgorithmParameters params = paramGen.generateParameters();
		DHParameterSpec dhSkipParamSpec = (DHParameterSpec) params.getParameterSpec(DHParameterSpec.class);
		KeyPairGenerator aliceKpairGen = KeyPairGenerator.getInstance("DH");
		aliceKpairGen.initialize(dhSkipParamSpec);
		KeyPair aliceKpair = aliceKpairGen.generateKeyPair();
		aliceKeyAgree = KeyAgreement.getInstance("DH");
		aliceKeyAgree.init(aliceKpair.getPrivate());
		alicePubKeyEnc = aliceKpair.getPublic().getEncoded();
		return alicePubKeyEnc;
	}

	public byte[] genSecret(byte[] endPartyPubKey) throws Exception {
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(alicePubKeyEnc);
		KeyFactory aliceKeyFac = KeyFactory.getInstance("DH");
		x509KeySpec = new X509EncodedKeySpec(endPartyPubKey);
		PublicKey bobPubKey = aliceKeyFac.generatePublic(x509KeySpec);
		aliceKeyAgree.doPhase(bobPubKey, true);
		return aliceKeyAgree.generateSecret();
	}

}
