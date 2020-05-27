package site.purrbot.api.endpoints;

public class Status{
    
    private String avatar = "https://purrbot.site/assets/img/api/unknown.png";
    private String status = "offline";
    private boolean mobile = false;
    
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
