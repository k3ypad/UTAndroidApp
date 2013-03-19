package superfriends.universitytimesapp;

import android.graphics.Bitmap;

public class Article{
    //member fields
	private int id;
	private String heading;
	private String body;
	private String url;
	
	
	
	public Article(){
		
	}
	
	public Article(String name,int id, String heading, String body,String url){
		this.id = id;
		this.heading = heading;
		this.body = body;
		this.url = url;
	}

	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	

}