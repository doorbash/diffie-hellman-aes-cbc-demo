import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import security.DiffieHellmanA;

public class Alice extends Person {

	public void run() {

		try {

			ServerSocket listener = new ServerSocket(9090);
			Socket socket = listener.accept();
			try {
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());

				DiffieHellmanA dh = new DiffieHellmanA();

				write(dh.genPubKey());

				byte[] bobPubKeyEnc = read();

				byte[] secret = dh.genSecret(bobPubKeyEnc);

				writeSecure(secret, "This is a clear text message.");

			} finally {
				socket.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
