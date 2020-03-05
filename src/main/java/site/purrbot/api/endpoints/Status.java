package site.purrbot.api.endpoints;

public class Status{
    
    private String avatar = "https://i.imgur.com/63aniDJ.png";
    private String status = "offline";
    
    public String getAvatar(){
        return avatar;
    }
    
    public String getStatus(){
        return status;
    }
}
