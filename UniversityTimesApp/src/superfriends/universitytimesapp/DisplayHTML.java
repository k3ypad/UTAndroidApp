package superfriends.universitytimesapp;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.widget.TextView;

public class DisplayHTML extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String received_html = intent.getStringExtra(MainActivity.HTML_PAGE);
		TextView view = new TextView(this);
		view.setText(Html.fromHtml(received_html));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_html, menu);
		return true;
	}

}
