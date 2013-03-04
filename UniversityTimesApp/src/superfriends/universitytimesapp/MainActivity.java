package superfriends.universitytimesapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    
}
