package audiocast.audio;

/*This will accomplish one to one communication
 Record audio
 Store in a queue
 Retrieve byte array by array and send
 */
import java.util.concurrent.BlockingQueue;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Send extends Thread{

	static DatagramSocket aSocket = null;
	static String hostAddress = "127.0.0.1";
	final static int PORT = 1234;
	final BlockingQueue<byte[]> queue;

	//Stream ekak awoth hda nadda
	public Send(BlockingQueue<byte[]> queue) {

		this.queue = queue;

	}

	public void getDestination(String ip) {

		Send.hostAddress = ip;

	}

	public void run() {

		try {
			aSocket = new DatagramSocket(PORT);
			InetAddress aClient = InetAddress.getByName(hostAddress);
			while (queue.size() != 0) {

				byte[] clip = queue.remove();
				//PORT kyana eka deparak enne na neda ?
				DatagramPacket pckt = new DatagramPacket(clip, PORT, aClient,
						PORT);
			}
		} catch (SocketException | UnknownHostException ex) {

			Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);

		} finally {

			if (aSocket != null)
				aSocket.close();

		}

	}
}