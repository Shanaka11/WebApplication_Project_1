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

public class Recieve{

	DatagramSocket aSocket = null;
	static String hostAddress = "127.0.0.1";
	final static int PORT = 1234;
	BlockingQueue<byte[]> queue;

	public Recieve(DatagramSocket socket) {

		
		byte[] buffer = new byte[1024];
		this.aSocket = socket;
		
		DatagramPacket rcvPckt = new DatagramPacket(buffer, buffer.length);

		try {
			while (true) {

				aSocket.receive(rcvPckt);

				queue.add(rcvPckt.getData());

				if(queue.remainingCapacity() == 0) break;
				//aSocket.setSoTimeout(10000);

			}
		} catch (SocketException e) {

		} catch (IOException e) {

			//aSocket.close();
		}

	}
	
	public BlockingQueue<byte[]> getQueue(){
		
		return queue;
		
	}
	

}
