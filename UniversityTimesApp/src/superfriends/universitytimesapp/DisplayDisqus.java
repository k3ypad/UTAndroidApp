package superfriends.universitytimesapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class DisplayDisqus extends Activity {

    private int articleId;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        articleId = intent.getIntExtra("articleId",0);
        setContentView(R.layout.disqus_layout);
        WebView webDisqus = (WebView) findViewById(R.id.disqus_webview);
        WebSettings webSettings2 = webDisqus.getSettings();
        webSettings2.setJavaScriptEnabled(true);
        webSettings2.setBuiltInZoomControls(true);
        webDisqus.requestFocusFromTouch();
        webDisqus.setWebViewClient(new WebViewClient());
        webDisqus.setWebChromeClient(new WebChromeClient());
        webDisqus.loadUrl("http://utdummy.tfa.ie/showcomments.php/showcomments.php?disqus_id=" + articleId);
    }
    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
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
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}