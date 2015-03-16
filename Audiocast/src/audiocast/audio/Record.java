package audiocast.audio;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

public final class Record extends Thread {

	private static final int MAXLEN = 1024;
	final AudioRecord stream;
	final BlockingQueue<byte[]> queue;	
	DatagramSocket socket;
	int PORT = 1234;
	String ip = "127.0.0.1";

	public Record(int sampleHz, BlockingQueue<byte[]> queue) {
		this.queue = queue;

		int bufsize = AudioRecord.getMinBufferSize(
				sampleHz, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
		Log.i("Audiocast","initialised recorder with buffer length "+ bufsize);
		
		try {
			
			socket = new DatagramSocket(PORT);
			
		} catch (SocketException e) {
			
			Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, e);
			
		}
		
		stream = new AudioRecord(
					MediaRecorder.AudioSource.MIC,
					sampleHz,
					AudioFormat.CHANNEL_IN_MONO, 
					AudioFormat.ENCODING_PCM_16BIT , 
					bufsize);
	}

	@Override
	public void run() {
		try {
			byte[] pkt = new byte[MAXLEN];
			
			while (!Thread.interrupted()) {
				int len = stream.read(pkt, 0, pkt.length);							
				queue.put(pkt);
				Log.d("Audiocast", "recorded "+len+" bytes");
				// I added this part
				if(queue.remainingCapacity() == 0){
					Send sender = new Send(queue,socket,ip);
					sender.start();
				}
			}
		} catch (InterruptedException e) {
		} finally {
			stream.stop();
			stream.release();
			socket.close();
		}

	}
	
	public void pause(boolean pause) {
		if (pause) stream.stop(); 
		else stream.startRecording();
		
		Log.i("Audiocast", "record stream state=" + stream.getState());	
	}
}
