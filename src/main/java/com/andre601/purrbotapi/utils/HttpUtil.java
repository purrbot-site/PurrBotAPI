package com.andre601.purrbotapi.utils;


import org.apache.commons.io.IOUtils;

import java.net.URL;
import java.nio.charset.Charset;

public class HttpUtil {

    public String getImages(String link){
        try{
            return IOUtils.toString(new URL(link), Charset.forName("UTF-8"));
        }catch (Exception ex){
            return "";
        }
    }

}
