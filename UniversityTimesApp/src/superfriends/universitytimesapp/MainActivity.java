package superfriends.universitytimesapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity implements QueryListener {

    public static final String HTML_PAGE = "superfriends.universitytimesapp.HTML_PAGE";

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present 123.
        getMenuInflater().inflate(R.menu.main, menu);
        
        
        return true;
    }
    
    public void sendQuery(View view){
    	UTRequest req = new UTRequest(this,"utdummy.tfa.ie",8080);
    	req.execute("h3ll0");  


	
    }

	@Override
	public void receiveResult(String result) {
    	Intent intent = new Intent(this,DisplayHTML.class);
    	if(result == null) System.out.println("the result is null");
    	intent.putExtra(HTML_PAGE, result);
    	startActivity(intent);
		
	}
    
    
    
}
