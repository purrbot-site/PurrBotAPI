package site.purrbot.api.endpoints;

import com.google.gson.annotations.SerializedName;

public class Quote{
    
    private String avatar = "https://i.imgur.com/63aniDJ.png";
    @SerializedName(value = "nameColor", alternate = {"color"})
    private String nameColor = "hex:ffffff";
    @SerializedName(value = "dateFormat", alternate = {"format"})
    private String dateFormat = "dd. MMM yyyy hh:mm:ss zzz";
    @SerializedName(value = "username", alternate = {"name"})
    private String username = "Someone";
    @SerializedName(value = "message", alternate = {"text"})
    private String message = "Some message.";
    @SerializedName(value = "timestamp", alternate = {"time"})
    private String timestamp = String.valueOf(System.currentTimeMillis());
    
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
    
    public String getTimestamp(){
        return timestamp;
    }
}
