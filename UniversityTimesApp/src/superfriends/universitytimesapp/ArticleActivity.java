package superfriends.universitytimesapp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class ArticleActivity extends Activity {
    static LayoutParams lpsmallbutton = new LayoutParams(LayoutParams.MATCH_PARENT, 50);
    static String ArticleBody;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent= getIntent();
		ArticleBody = intent.getStringExtra("maintext"); // will return "FirstKeyValue"
		
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.activity_article);
		setupActionBar();
		ArticleLayout();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
public void ArticleLayout() {
		
		/**
		 * Write code here that will get the id
		 * of the button that is pressed and if
		 * for example the button is number 14
		 * then get article 14 and use that as the body
		 */
		
		/**
		 * ToDo, Set an onClickListener for the return
		 * button and when pressed it will switch
		 * to the previous layout
		 */

		LinearLayout lla = (LinearLayout)findViewById(R.id.articlelayout);
		String body = getString(R.string.dummyBody);
		
		// Gets the image file for the article
		ImageView ii = new ImageView(this);
		ii.setImageResource(R.drawable.cool);

		// Creates QnA button
		Button qna = new Button(this);
		qna.setTextSize(10.0f);
		qna.setText("Comment");
        qna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDisqus();


            }
        });
		
		// Creates a TextView which houses the article and makes it scrollable
		TextView article = new TextView(this);	
		article.setText(ArticleBody);
		
		// Adds everything to the layout with indexes
	    //lla.addView(ii, 0);
		lla.addView(article, 0);
		lla.addView(qna, 1, lpsmallbutton);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.article, menu);
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

    private void startDisqus(){
        Intent intent = new Intent(this,DisplayDisqus.class);
        intent.putExtra("id",1);
        startActivity(intent);
    }

}
