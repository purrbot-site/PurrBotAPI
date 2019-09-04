package site.purrbot.api.defaults;

public enum Strings {
    TEXT("Just some string."),
    AVATAR("https://i.imgur.com/63aniDJ.png"),
    NAME("Someone"),
    TIMEFORMAT("dd. MMM yyyy hh:mm:ss zzz"),

    STATUS("offline"),
    IMAGE("purr"),

    ERR_AVATAR("https://i.imgur.com/qQ8g1Ir.png"),
    ERR_NAME("Error appeared!"),
    ERR_MSG("Error happened while creating an image (Error code 500 \"internal server error\")\\nCheck values for any illegal characters.");

    private String string;

    Strings(String string){
        this.string = string;
    }

    public String getString(){
        return string;
    }
}
