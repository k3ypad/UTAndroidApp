package superfriends.universitytimesapp;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;


//this class is just for testing at the moment
public class MainActivity extends Activity implements QueryListener {

    public static final String HTML_PAGE = "superfriends.universitytimesapp.HTML_PAGE";

    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    
    public void otherActivity(View view) {
		Intent intent = new Intent(this, DisplayMessage.class);
	    startActivity(intent);
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present 123.
        getMenuInflater().inflate(R.menu.main, menu);     
        return true;
    }
    /*at the moment method invoked from xml button onclick attribute*/
    public void sendQuery(View view){
    	ArticleManager articleManager = new ArticleManager();
    	ArrayList<Article> articles = articleManager.getLatestArticles(5);
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
