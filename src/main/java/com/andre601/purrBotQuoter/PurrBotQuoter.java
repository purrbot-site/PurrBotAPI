package com.andre601.purrBotQuoter;

import com.andre601.purrBotQuoter.utils.ImageUtil;
import spark.Spark;

import static spark.Spark.*;

public class PurrBotQuoter {

    public static void main(String[] args){

        Spark.port(2000);

        get("/api/quote", (request, response) -> {

            String text = request.queryParamOrDefault("text", "Just some text");
            String avatarURL = request.queryParamOrDefault("avatar",
                    "https://i.imgur.com/ZBvZLz4.png"
            );
            String name = request.queryParamOrDefault("name", "someone");
            String timestamp = request.queryParamOrDefault("time", String.valueOf(
                    System.currentTimeMillis()
            ));
            String format = request.queryParamOrDefault("format", "dd. MMM yyyy HH:mm:ss");

            return ImageUtil.getQuoteImage(text, avatarURL, name, timestamp, format);
        });

    }
}
