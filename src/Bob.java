import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import security.DiffieHellmanB;

public class Bob extends Person {

	public void run() {
		try {
			System.out.println("BOB: Connecting to Alice");
			Socket s = new Socket("127.0.0.1", 9090);
			out = new DataOutputStream(s.getOutputStream());
			in = new DataInputStream(s.getInputStream());

			DiffieHellmanB dh = new DiffieHellmanB();

			write(dh.genPubKey(read()));

			byte[] secret = dh.genSecret();

			System.out.println("BOB: Alice said : " + readSecure(secret));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
