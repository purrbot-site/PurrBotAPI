package site.purrbot.api.endpoints;

public class Quote{
    
    private String avatar = "https://purrbot.site/assets/img/api/unknown.png";
    private String nameColor = "hex:ffffff";
    private String dateFormat = "dd. MMM yyyy hh:mm:ss zzz";
    private String username = "Someone";
    private String message = "Some message.";
    private long timestamp = System.currentTimeMillis();
    
    public String getAvatar(){
        return avatar;
    }
    
    public String getNameColor(){
        return nameColor;
    }
    
    public String getDateFormat(){
        return dateFormat;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getMessage(){
        return message;
    }
    
    public long getTimestamp(){
        return timestamp;
    }
}
