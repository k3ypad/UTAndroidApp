package superfriends.universitytimesapp;

import java.io.IOException;
import java.net.UnknownHostException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;


//this class is just for testing at the moment
public class MainActivity extends Activity implements QueryListener {

    public static final String HTML_PAGE = "superfriends.universitytimesapp.HTML_PAGE";

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    	try {
			ConnectionManager.INSTANCE.connectToServer("utdummy.tfa.ie", 8080);
		} catch (UnknownHostException e) {
			System.out.println("Could not connect to server " + e.toString());
		} catch (IOException e) {
			System.out.println("IO problem : " + e.toString());
			e.printStackTrace();
		}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present 123.
        getMenuInflater().inflate(R.menu.main, menu);     
        return true;
    }
    /*at the moment method invoked from xml button onclick attribute*/
    public void sendQuery(View view){
    	ConnectionManager.INSTANCE.fetchDummyArticle(this);
    }

    /*when UTRequest is ready it will execute its own onPostExecute which in turn
     * executes receiveResult of the QueryListener it was passed*/
	public void receiveResult(String result) {
    	Intent intent = new Intent(this,DisplayHTML.class);
    	if(result == null){ 
    		System.out.println("the result is null");
    		result = "No data received from server";
    	}
    	intent.putExtra(HTML_PAGE, result);
    	startActivity(intent);
		
	}
    
    
    
}
