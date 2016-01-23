import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Arrays;

import security.AES;

public class Person extends Thread {
	
	DataOutputStream out;
	DataInputStream in;
	
	public byte[] read() throws Exception
	{
		int size = in.readInt();
		byte[] ret = new byte[size];
		for (int i = 0; i < size; i++)
			ret[i] = in.readByte();
		return ret;
	}
	public void write(byte[] data) throws Exception
	{
		out.writeInt(data.length);
		out.flush();
		out.write(data);
		out.flush();
	}
	
	
	public String readSecure(byte[] key) throws Exception
	{
		return AES.decrypt(Arrays.copyOfRange(key,0,16), Arrays.copyOfRange(key,16,32), read());
	}
	
	public void writeSecure(byte[] key,String text) throws Exception
	{
		write(AES.encrypt(Arrays.copyOfRange(key,0,16), Arrays.copyOfRange(key,16,32), text));
	}

}
