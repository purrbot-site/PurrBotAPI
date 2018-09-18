package com.andre601.purrBotQuoter;

import ch.qos.logback.classic.Logger;
import com.andre601.purrBotQuoter.utils.ImageUtil;
import org.slf4j.LoggerFactory;
import spark.Spark;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static spark.Spark.*;

public class PurrBotQuoter {

    public static Logger logger = (Logger)LoggerFactory.getLogger(PurrBotQuoter.class);

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

            getLogger().info("Received request!");

            try{
                HttpServletResponse raw = response.raw();
                response.header("Content-Disposition", "attachment; filename=image.png");
                raw.getOutputStream().write(ImageUtil.getQuoteImage(
                        text, avatarURL, name, timestamp, format
                ));
                raw.getOutputStream().flush();
                raw.getOutputStream().close();
                response.type("image/png");

                getLogger().info("Success!");
                return raw;
            }catch (IOException ex){
                halt();
            }

            getLogger().error("Failure!");
            return response;
        });

    }

    public static Logger getLogger(){
        return logger;
    }
}
