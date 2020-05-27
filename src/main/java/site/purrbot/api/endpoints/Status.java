package site.purrbot.api.endpoints;

public class Status{
    
    private final String avatar = "https://purrbot.site/assets/img/api/unknown.png";
    private final String status = "offline";
    private final boolean mobile = false;
    
    public String getAvatar(){
        return avatar;
    }
    
    public String getStatus(){
        return status;
    }
    
    public boolean isMobile(){
        return mobile;
    }
}
