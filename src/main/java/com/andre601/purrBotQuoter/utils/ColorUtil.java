package com.andre601.purrBotQuoter.utils;

import java.awt.*;

public class ColorUtil {

    public static Color checkColor(String color){

        Color result = null;

        try{
            result = Color.decode((color.startsWith("#") ? color : "#" + color));
        }catch (Exception ex){
            return null;
        }

        return result;
    }

}
