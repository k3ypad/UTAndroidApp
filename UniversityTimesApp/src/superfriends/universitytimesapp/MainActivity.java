package superfriends.universitytimesapp;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;


//this class is just for testing at the moment
public class MainActivity extends Activity implements QueryListener {

    public static final String HTML_PAGE = "superfriends.universitytimesapp.HTML_PAGE";
    static LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 200);
    static ArrayList<Article> articles;
    static Article a;
    static Button myButton;
    static final int count = 4;
    public static String tag = null;
    static ArrayList<Button> buttonlist;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    buttonlist = new ArrayList<Button>();
	    
		getActionBar().setDisplayShowTitleEnabled(false);

		if(tag == null){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ArticleManager articleManager = new ArticleManager();
        
        String heading;
		final Intent intent = new Intent(this, ArticleActivity.class);
		articles = articleManager.getLatestArticles(count);

		View.OnClickListener listener = new View.OnClickListener() {

            public void onClick(View myButton) {
                int code = myButton.getId();
                System.out.println("button" + code);
                a = articles.get(code);
                String boddy = a.getBody(code);
        		intent.putExtra("maintext", boddy);
                intent.putExtra("article_id",a.getId());
        		startActivity(intent);
                }
            };
        
                for(int l = 0; l < count; l++){
                	myButton = new Button(this);
        			
        			// Set the click listener to all your buttons
        	        myButton.setOnClickListener(listener);
        			
        			a = articles.get(l);
        			heading = a.getHeading();
        			
        			//Sets text and id to button
        			myButton.setText(heading);
        			myButton.getBackground().setAlpha(45);
        			myButton.setId(l);
        			buttonlist.add(myButton);
         }
	     ButtonLayout();
		}
    }
	
	public void ButtonLayout() { 
		//Creates new layout and params to go with
		final LinearLayout llb = (LinearLayout)findViewById(R.id.buttonlayout);
		
		
		//Creates new buttons and indexes
		for(int i = 0; i < count; i++) {
			Button displayButton = buttonlist.get(i);
			//Adds button to view with index and parameters
			if(displayButton.getTag() == tag || tag == null){
				llb.addView(displayButton, i, lp);
			}
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
    	ArticleManager articleManager = new ArticleManager();
    	ArrayList<Article> articles = articleManager.getLatestArticles(5);
    }

    public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
		case R.id.overview:
			Intent myIntent = new Intent(MainActivity.this, Overview.class);
			MainActivity.this.startActivity(myIntent);
	    	break;
		case R.id.about:
			item2();
			break;
		case 3:
			break;
		}
		return true;
	}

	public void item2(){
		new AlertDialog.Builder(this)
		.setTitle("About")
		.setMessage("GPL Version 3.0")
		.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}).show();
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
