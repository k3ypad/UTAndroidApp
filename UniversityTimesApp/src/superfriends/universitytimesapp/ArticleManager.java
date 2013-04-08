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

    /*
     * Method which fetches JSONs from URL and parses it into its separate articles.
     * The format for the JSON is:
	 * {
	 * 	"ID" :
	 * 	"Heading":
	 * 	"Body":
	 * 	"Thumb_nail":
	 * }
     */
    public JSONObject getJSONFromUrl(String url) throws JSONException {

        // Making HTTP request
        try {
            // default HTTPClient
        	// fetches JSON from the given URL
            DefaultHttpClient httpClient = new DefaultHttpClient();	
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            System.out.println("The status " + httpResponse.getStatusLine().getStatusCode());
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null) {		// 
                sb.append(line + "\n");		// builds a string with made up of the JSON file
            }
            is.close();
            json = sb.toString();			

            jObj = new JSONObject(json); 	// make a JSON Object using the json string from the url
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
/*
 * Method which fetches the next article from server
 * it does this by storing the current ID of the last article
 * and fetching the next valid article
 * It calls the getJSONFromUrl to get the new JSON and stores
 * it as an article
 */
    public Article getNextArticle() throws JSONException{
        jObj = null;
        String sb = "";
        Article myArticle = null;
        while(jObj==null){
            currentId++;
            if(currentId == 200){		// if currentId is less than the max articles
                currentId = 0;			// currentId goes back to start
            }
            sb= sb + "http://utdummy.tfa.ie/";		// makes a URL using the CurrentId 
            sb = sb + currentId;
            sb = sb +".json";

            try {
                JSONObject jObj = getJSONFromUrl(sb.toString());	// calls getJsonFromUrl method
            } catch (JSONException e) {
                e.printStackTrace();
            }

            sb = "";
            if(jObj!=null){
                myArticle = new Article(jObj.getInt("ID"), jObj.getString("Post_title"),	// parses the JSON file and stores them in the article 
                        jObj.getString("Post_tag")											// under their appropriate headings
                        ,jObj.getString("Post_body"), jObj.getString("Thumb_url"));
                Log.i("article id: ", String.valueOf(myArticle.getId()));
            }
        }

        return myArticle;
    }
/*
 * This calls the getNextArticle() a specified amount of times, 
 * by the parametre passed in from the main 
 */
    public ArrayList<Article> getLatestArticles(int number){
        ArrayList<Article> articles = new ArrayList<Article>();
        for(int i=0 ; i< number; i++){							
            try {
                Article myArticle = getNextArticle();			// calls the getNextArticle method the specified amount of times
                articles.add(myArticle);						// adds the current article to the arrayList of articles
                currentId = myArticle.getId();					// updates currentId
                

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }


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


}
