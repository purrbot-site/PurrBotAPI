package com.andre601.purrbotapi;

import ch.qos.logback.classic.Logger;
import com.andre601.purrbotapi.utils.HttpUtil;
import com.andre601.purrbotapi.utils.ImageUtil;
import org.slf4j.LoggerFactory;
import spark.Redirect;
import spark.Spark;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static spark.Spark.*;

public class PurrBotAPI {

    private static Logger logger = (Logger)LoggerFactory.getLogger(PurrBotAPI.class);
    private static ImageUtil imageUtil;
    private static HttpUtil httpUtil;
    private static List<String> imageList = new ArrayList<>();
    private static Random random = new Random();

    public static void main(String[] args){

        imageUtil = new ImageUtil();
        httpUtil = new HttpUtil();

        Collections.addAll(imageList, httpUtil.getImages(
                "https://raw.githubusercontent.com/Andre601/PurrBot-files/master/files/welcome-images"
        ).split("\n"));

        Spark.port(2000);
        staticFiles.location("");
        redirect.get("api", "api.html", Redirect.Status.PERMANENT_REDIRECT);
        init();

        path("/api", () -> {
            before("/*", (q, a) -> getLogger().info("Received API-call!"));
            get("/quote", (request, response) -> {

                getLogger().info("Generate image for quote...");

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

                String color = request.queryParamOrDefault("color", "16777215");

                try {
                    response.raw().setContentType("image/png");
                    HttpServletResponse raw = response.raw();
                    raw.getOutputStream().write(
                            imageUtil.getQuoteImage(
                                    text,
                                    avatarURL,
                                    name,
                                    timestamp,
                                    format,
                                    color
                            )
                    );
                    raw.getOutputStream().flush();
                    raw.getOutputStream().close();

                    getLogger().info("Successfully returned quote-image.");
                    return raw;
                } catch (IOException ex) {
                    halt("Something went wrong! Check if the Parameters are valid!");
                }

                getLogger().error("Coudn't create a Quote-image.");
                return response;
            });

            get("/status", (request, response) -> {

                getLogger().info("Generate status-image...");

                String avatar = request.queryParamOrDefault("avatar",
                        "https://i.imgur.com/63aniDJ.png"
                );
                String status = request.queryParamOrDefault("status", "offline");

                try{
                    response.raw().setContentType("image/png");
                    HttpServletResponse raw = response.raw();
                    raw.getOutputStream().write(
                            imageUtil.getStatusImage(
                                    avatar,
                                    status
                            )
                    );
                    raw.getOutputStream().flush();
                    raw.getOutputStream().close();

                    getLogger().info("Successfully returned status-image.");
                    return raw;
                }catch (IOException ex){
                    halt("Something went wrong! Check if the Parameters are valid!");
                }

                getLogger().error("Couldn't create a Status-image.");
                return response;
            });

            get("/welcome", (request, response) -> {
                getLogger().info("Generate welcome-image...");

                String name = request.queryParamOrDefault("name", "Someone");
                String avatar = request.queryParamOrDefault("avatar",
                        "https://i.imgur.com/63aniDJ.png"
                );
                String image = request.queryParamOrDefault("image", "purr");
                String color = request.queryParamOrDefault("color", "hex:#ffffff");
                long size = Long.valueOf(request.queryParamOrDefault("size", "1"));

                try{
                    response.raw().setContentType("image/png");
                    HttpServletResponse raw = response.raw();
                    raw.getOutputStream().write(
                            imageUtil.getWelcomeImage(
                                    image,
                                    name,
                                    avatar,
                                    color,
                                    size
                            )
                    );
                    raw.getOutputStream().flush();
                    raw.getOutputStream().close();

                    getLogger().info("Successfully returned welcome-image.");
                    return raw;
                }catch (Exception ex){
                    halt("Something went wrong! Check if the Parameters are valid!");
                }

                getLogger().error("Couldn't create a welcome-image.");
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

            String color = "16777215";

            try {
                response.raw().setContentType("image/png");
                HttpServletResponse raw = response.raw();
                raw.getOutputStream().write(
                        imageUtil.getQuoteImage(
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

    public static List<String> getImageList(){
        return imageList;
    }

    public static Random getRandom(){
        return random;
    }

    public static String getRandomImage(){
        return PurrBotAPI.getImageList().size() > 0 ? PurrBotAPI.getImageList().get(
                PurrBotAPI.getRandom().nextInt(PurrBotAPI.getImageList().size())
        ) : "purr";
    }
}
