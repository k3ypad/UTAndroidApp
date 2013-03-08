package superfriends.universitytimesapp;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.webkit.WebView;

/*Test class that displays html text from the bundle that it was 
 * created with
 * references
 * http://developer.android.com/reference/android/webkit/WebView.html*/
public class DisplayHTML extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String received_html = intent.getStringExtra(MainActivity.HTML_PAGE);
		WebView view = new WebView(this);
		view.loadData(received_html, "text/html", "UTF-8");
		setContentView(view);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_html, menu);
		return true;
	}

}
