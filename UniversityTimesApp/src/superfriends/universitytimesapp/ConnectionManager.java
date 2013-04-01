package superfriends.universitytimesapp;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.os.StrictMode;
/*A class that represent a single point for sending requests
 * Avoids creating multiple sockets and thus creating problems
 * references:
 * http://javarevisited.blogspot.ie/2012/07/why-enum-singleton-are-better-in-java.html
 * 
 * */
public enum ConnectionManager {
	INSTANCE; // use ConnectionManager.INSTANCE to access the global object
	private Socket socket;
	private boolean connected = false;
	
	
	public void connectToServer(String host, int port) throws UnknownHostException, IOException{
		//this ugly piece of code is needed for now otherwise would not allow to connect socket

		if(connected){ // in case of reconnect
			socket.close();
		}
		socket = new Socket(host,port);
		connected = true;
	}
	
	public void fetchDummyArticle(QueryListener activity){
		/*UTRequest(<activity to send back info>,<host>,<port to connect to on host>)*/
    	ServerRequest req = new ServerRequest(activity);
    	/*make a non-blocking request to server. Data returned will be through
    	 * the receiveResult(result) method below. Any activity that wants to 
    	 * use UTRequest then it must subscribe to the QueryListener interface.*/
    	req.execute("h3ll0");  
	}
	
	/*
	 * THE PUBLIC INTERFACE FOR FOR VARIOUS REQUEST GOES HERE
	 * 
	 * */
	
	
	
	/*a class that represents a single request to a given server
	 * the request is in the form of a string. It is up to the client 
	 * and server to agree on an exchange protocol. This class only abstracts 
	 * the process.
	 * references:
	 * 	 http://developer.android.com/reference/android/os/AsyncTask.html
	 *   http://docs.oracle.com/javase/1.4.2/docs/api/java/io/BufferedInputStream.html
	 *   http://stackoverflow.com/questions/4110566/java-using-datainputstream-with-sockets-buffered-or-not
	 * */
	private class ServerRequest extends AsyncTask<String,Void,String>{
		private QueryListener activity;
		
		ServerRequest(QueryListener activity){
			this.activity = activity;
		}
		@Override
		/*method called when AsyncTask execute(params) is invoked*/
		protected String doInBackground(String... params) {
			BufferedInputStream input = null;
			DataOutputStream output = null;
			String result = null;
			try {
	//This code is untested, has magic number and I am not sure how data is read and written exactly
	//
	//------------------------------------------------------------------------------			
				input = new BufferedInputStream(socket.getInputStream());
				output = new DataOutputStream(socket.getOutputStream());
				output.writeBytes(params[0]);
				byte [] buffer = new byte[10000];
				input.read(buffer);
				result = new String(buffer);
				System.out.println("The result is : " + result);//debug
	//-------------------------------------------------------------------------------
				
				
				
			} catch (IOException e) {
				 System.err.println("Couldn't execute I/O");
			}finally{
				try {
					input.close();
					output.close();
				} catch (IOException e) {
					System.err.println("Problem closing connection.");
				}
			}
			return result;
		}
		
		
		@Override
		/*called once doInBackground is finished*/
		protected void onPostExecute(String result) {
			activity.receiveResult(result);
		}
		

	
	}
}
