package superfriends.universitytimesapp;


//this class is not finished.
public class RandomArticleGenerator {

	public static final int NUM_CHOICES = 3;
	public static final String [] headings = 
		{"This is the end of overpaid workers!",
		"Trinity College fall from the top 100 rankings.",
		"Pedestrians outside Trinity are having trouble."};
	

	public RandomArticleGenerator(){
		
	}
	
	public Article getRandomArticle() {
		Article randomArticle = new Article();
		int cursor = (int)(Math.random()*10) % NUM_CHOICES;
		randomArticle.setHeading(headings[cursor]);
		randomArticle.setBody("");
		return randomArticle;
	}

}
