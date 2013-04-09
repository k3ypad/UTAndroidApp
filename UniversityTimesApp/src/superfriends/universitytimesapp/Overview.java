package superfriends.universitytimesapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Overview extends Activity {

	Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new Handler();
		setContentView(R.layout.activity_overview);
		
		// Show the Up button in the action bar.
		setupActionBar();
		 
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.overview, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Below represents all the buttons on the overview activity
	 */
	
	public void all_section(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, MainActivity.class);
		MainActivity.tag = null;
        setResult(RESULT_OK, intent);
        startActivity(intent);
        finish();
	}
	
	public void news_section(View view) {
	    // Do something in response to button
		Intent intent = new Intent();
		MainActivity.tag = "news";
        setResult(RESULT_OK, intent);
        finish();
	}
	
	public void opinion_section(View view) {
	    // Do something in response to button
		Intent intent = new Intent();
		MainActivity.tag = "opinion";
        setResult(RESULT_OK, intent);
        finish();
	}
	
	public void features_section(View view) {
	    // Do something in response to button
		Intent intent = new Intent();
		MainActivity.tag = "features";
        setResult(RESULT_OK, intent);
        finish();
	}
	
	public void sport_section(View view) {
	    // Do something in response to button
		Intent intent = new Intent();
		MainActivity.tag = "sport";
		System.out.println(MainActivity.tag);
        setResult(RESULT_OK, intent);
        finish();
	}

}
