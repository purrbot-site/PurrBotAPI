package site.purrbot.api.endpoints;

public class Quote{
    
    private final String avatar = "https://purrbot.site/assets/img/api/unknown.png";
    private final String nameColor = "hex:ffffff";
    private final String dateFormat = "dd. MMM yyyy hh:mm:ss zzz";
    private final String username = "Someone";
    private final String message = "Some message.";
    private final long timestamp = System.currentTimeMillis();
    
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
