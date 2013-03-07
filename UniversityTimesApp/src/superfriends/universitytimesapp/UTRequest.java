package superfriends.universitytimesapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;

public class UTRequest extends AsyncTask<String,Void,String>{
	private String host;
	private int port;
	private QueryListener activity;
	public UTRequest(QueryListener activity,String host, int port){
		this.host = host;
		this.port = port;
		this.activity = activity;
	}
	@Override
	protected String doInBackground(String... params) {
		Socket socket = null;
		DataInputStream input = null;
		DataOutputStream output = null;
		String result = null;
		try {
			socket = new Socket(host,port);
//	    	System.out.println("debugging");
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			output.writeBytes(params[0]);
			byte [] buffer = new byte[10000];
			input.read(buffer);
			result = new String(buffer);
			System.out.println("The result is : " + result);
			
		} catch (UnknownHostException e) {
			System.err.println("Could not resolve host! " + e);
//			e.printStackTrace();
		} catch (IOException e) {
			 System.err.println("Couldn't execute I/O");
		}catch(Exception e){
			System.out.println("Error here:" + e.toString());
			
		}finally{
			try {
				socket.close();
				input.close();
				output.close();
			} catch (IOException e) {
				System.err.println("Problem closing connection.");
			}

		}
		return result;
	}
	@Override
	protected void onPostExecute(String result) {
		activity.receiveResult(result);
	}
	


}
