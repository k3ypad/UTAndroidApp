package superfriends.universitytimesapp;
import java.util.ArrayList;
import android.os.AsyncTask;


public class ArticleManager{
    
    public ArrayList<Article> getLatestArticles(int number){
    	ArrayList<Article> articles = new ArrayList<Article>();
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