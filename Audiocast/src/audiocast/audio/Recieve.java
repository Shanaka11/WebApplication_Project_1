package audiocast.audio;

/*This will accomplish one to one communication
 Record audio
 Store in a queue
 Retrieve byte array by array and send
 */
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/*This will accomplish one to one communication
 Record audio
 Store in a queue
 Retrieve byte array by array and send
 */
import java.util.concurrent.BlockingQueue;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Recieve {

	static DatagramSocket aSocket = null;
	static String hostAddress = "127.0.0.1";
	final static int PORT = 1234;
	BlockingQueue<byte[]> queue;

	public Recieve() {

		byte[] buffer = new byte[1024];

		DatagramPacket rcvPckt = new DatagramPacket(buffer, buffer.length);

		try {
			while (true) {

				aSocket.receive(rcvPckt);

				queue.add(rcvPckt.getData());

				aSocket.setSoTimeout(10000);

			}
		} catch (SocketException e) {

		} catch (IOException e) {

			aSocket.close();
		}

	}

}
