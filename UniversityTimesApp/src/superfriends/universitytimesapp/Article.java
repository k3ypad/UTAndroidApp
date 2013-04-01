package superfriends.universitytimesapp;


public class Article{
    //member fields
    private int id;
    private String heading;
    private String body;
    private String url;
    private String tag;



    public Article(){

    }

    public Article(int id, String heading,String tag, String body,String url){
        this.id = id;
        this.heading = heading;
        this.body = body;
        this.url = url;
        this.tag = tag;
    }

    public String getHeading() {
        return heading;
    }
    public void setHeading(String heading) {
        this.heading = heading;
    }
    public String getBody(int code) {
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


}