package superfriends.universitytimesapp;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;

/*a class that represents a single request to a given server
 * the request is in the form of a string. It is up to the client 
 * and server to agree on an exchange protocol. This class only abstracts 
 * the process.
 * references:
 * 	 http://developer.android.com/reference/android/os/AsyncTask.html
 *   http://docs.oracle.com/javase/1.4.2/docs/api/java/io/BufferedInputStream.html
 *   http://stackoverflow.com/questions/4110566/java-using-datainputstream-with-sockets-buffered-or-not
 * */
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
	/*method called when AsyncTask execute(params) is invoked*/
	protected String doInBackground(String... params) {
		Socket socket = null;
		BufferedInputStream input = null;
		DataOutputStream output = null;
		String result = null;
		try {
			socket = new Socket(host,port);

			
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
			
			
			
		} catch (UnknownHostException e) {
			System.err.println("Could not resolve host! " + e);
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
	/*called once doInBackground is finished*/
	protected void onPostExecute(String result) {
		activity.receiveResult(result);
	}
	


}
