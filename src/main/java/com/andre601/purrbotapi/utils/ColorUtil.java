package com.andre601.purrbotapi.utils;

import java.awt.Color;

public class ColorUtil {

    public static Color toColor(String input){
        Color result;
        if(!input.startsWith("hex") && !input.startsWith("rgb")){
            try{
                result = new Color(Integer.valueOf(input));
            }catch (Exception ex){
                result = null;
            }

            return result;
        }

        String type = input.split(":")[0].toLowerCase();
        String value = input.split(":")[1].toLowerCase();

        switch (type){
            case "rgb":
                String[] rgb = (value.replace(" ", "")).split(",");

                String r = rgb[0];
                String g = rgb[1];
                String b = rgb[2];
                try{
                    result = new Color(Integer.valueOf(r), Integer.valueOf(g), Integer.valueOf(b));
                }catch (Exception ignored){
                    result = null;
                }
                break;

            case "hex":
                try {
                    result = Color.decode((value.startsWith("#") ? value : "#" + value));
                }catch (Exception ignored){
                    result = null;
                }
                break;

            default:
                result = null;
        }
        return result;
    }
}
