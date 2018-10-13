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

        path("/api", () -> {
            before("/*", (q, a) -> getLogger().info("Received API-call!"));
            get("/quote", (request, response) -> {

                String text = request.queryParamOrDefault("text", "Just some text");
                String avatarURL = request.queryParamOrDefault("avatar",
                        "https://i.imgur.com/63aniDJ.png"
                );
                String name = request.queryParamOrDefault("name", "someone");
                String timestamp = request.queryParamOrDefault("time", String.valueOf(
                        System.currentTimeMillis()
                ));
                String format = request.queryParamOrDefault("format",
                        "dd. MMM yyyy HH:mm:ss zzz"
                );

                String color = request.queryParamOrDefault("color", "#ffffff");

                try {
                    response.raw().setContentType("image/png");
                    HttpServletResponse raw = response.raw();
                    raw.getOutputStream().write(
                            ImageUtil.getQuoteImage(
                                    text.replace("\\n", "\\n "),
                                    avatarURL,
                                    name,
                                    timestamp,
                                    format,
                                    color
                            )
                    );
                    raw.getOutputStream().flush();
                    raw.getOutputStream().close();

                    getLogger().info("Success!");
                    return raw;
                } catch (IOException ex) {
                    halt("Something went wrong! Check if the Parameters are valid!");
                }

                getLogger().error("Failure!");
                return response;
            });
        });

        internalServerError(((request, response) -> {

            String text = "Error while creating a image (HTTP 500).\\n " +
                          "Check the params for any illegal characters.";
            String avatarURL = "https://i.imgur.com/qQ8g1Ir.png";
            String name = "ERROR (500)";
            String timestamp = String.valueOf(System.currentTimeMillis());
            String format = "dd. MMM yyyy HH:mm:ss zzz";

            String color = "#ff0000";

            try {
                response.raw().setContentType("image/png");
                HttpServletResponse raw = response.raw();
                raw.getOutputStream().write(
                        ImageUtil.getQuoteImage(
                                text.replace("\\n", "\\n "),
                                avatarURL,
                                name,
                                timestamp,
                                format,
                                color
                        )
                );
                raw.getOutputStream().flush();
                raw.getOutputStream().close();

                getLogger().info("API-Error! (HTTP 500)");
                return raw;
            } catch (IOException ex) {
                halt();
            }

            getLogger().error("Failure!");
            return response;
        }));

        getLogger().info("API started!");

    }

    public static Logger getLogger(){
        return logger;
    }
}
