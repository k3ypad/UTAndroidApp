package superfriends.universitytimesapp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class ArticleManager{

    private InputStream is = null;
    private JSONObject jObj = null;
    private String json = "";
    private static int currentId =0;
    public static final int MAX_ARTICLES = 200;

    public JSONObject getJSONFromUrl(String url) throws JSONException {

        // Making HTTP request
        try {
            // default HTTPClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            System.out.println("The status " + httpResponse.getStatusLine().getStatusCode());
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();

            jObj = new JSONObject(json);
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting Result " + e.toString());
        }
        // return JSON String
        System.out.println(" jOBJ " + jObj);
        return jObj;

    }

    public Article getNextArticle() throws JSONException{
        jObj = null;
        String sb = "";
        Article myArticle = null;
        while(jObj==null){
            currentId++;
            if(currentId == 200){
                currentId = 0;
            }
            sb= sb + "http://utdummy.tfa.ie/";
            sb = sb + currentId;
            sb = sb +".json";

            try {
                JSONObject jObj = getJSONFromUrl(sb.toString());
                if(jObj == null ){
                    //System.out.println("we fucked up" + currentId);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            sb = "";
            if(jObj!=null){
                myArticle = new Article(jObj.getInt("ID"), jObj.getString("Post_title"),
                        jObj.getString("Post_tag")
                        ,jObj.getString("Post_body"), jObj.getString("Thumb_url"));
                Log.i("article id: ", String.valueOf(myArticle.getId()));
            }
        }

        return myArticle;
    }

    public ArrayList<Article> getLatestArticles(int number){
        ArrayList<Article> articles = new ArrayList<Article>();
        for(int i=0 ; i< number; i++){
            try {
                Article myArticle = getNextArticle();
                articles.add(myArticle);
                currentId = myArticle.getId();
                System.out.println("HI " + myArticle.getId());
//				System.out.println("HI " + myArticle.getHeading());
//				System.out.println("HI " + myArticle.getBody());
//				System.out.println("HI " + myArticle.getUrl());

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
		/*
		 * implement code that will fetch JSON parse and convert it into
		 * Articles. Create additional classes/methods if you feel appropriate
		 * or extends/modify existing.
		 * Articles start from 1 and end 100 and are static json pages.
		 * the url format is http://utdummy.tfa.ie/<number>.json
		 *
		 * the format for the json is:
		 *
		 * {
		 * 	"ID" :
		 * 	"Heading":
		 * 	"Body":
		 * 	"Thumb_nail":
		 * }
		 *
		 * There won't be more than 100 articles at any given time.
		 * */


        return articles;
    }

    public ArrayList<Article> getRandomArticles(int number){
        RandomArticleGenerator gen = new RandomArticleGenerator();
        ArrayList<Article> articles = new ArrayList<Article>();
        for(int i = 0; i < number; i++){
            articles.add(gen.getRandomArticle());
        }
        return articles;
    }

	/*
  private class articleRequest extends AsyncTask<String, Void, String>{
    private Article article;

    public articleRequest(Article article){
        this.article = article;
    }

    protected String doInBackground(String... params) {

    	return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

    }
  }     
	 */
}
