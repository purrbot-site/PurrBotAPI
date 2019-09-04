package site.purrbot.api;

import ch.qos.logback.classic.Logger;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.LoggerFactory;
import site.purrbot.api.defaults.Strings;
import spark.Spark;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static spark.Spark.*;

public class PurrBotAPI {

    private Logger logger = (Logger)LoggerFactory.getLogger(PurrBotAPI.class);

    private ImageUtil imageUtil;

    public static void main(String[] args){
        new PurrBotAPI().start();
    }

    private void start(){
        imageUtil = new ImageUtil();

        Spark.port(2000);

        path("/api", () -> {
            before("/*", (q, a) -> logger.info("Received API-call."));
            Gson gson = new Gson();

            post("/quote", (request, response) -> {
                logger.info("Request received! Type: POST; Endpoint: /quote");

                JsonObject json = gson.fromJson(request.body(), JsonObject.class);

                String avatar = getStringOrDefault(json.get("avatar").getAsString(), Strings.AVATAR);
                String color = getStringOrDefault(json.get("color").getAsString(), String.valueOf(0xFFFFFF));
                String format = getStringOrDefault(json.get("format").getAsString(), Strings.TIMEFORMAT);
                String name = getStringOrDefault(json.get("name").getAsString(), Strings.NAME);
                String text = getStringOrDefault(json.get("text").getAsString(), Strings.TEXT);
                String time = getStringOrDefault(json.get("time").getAsString(), String.valueOf(System.currentTimeMillis()));

                try{
                    HttpServletResponse raw = response.raw();
                    raw.setContentType("image/png");
                    raw.getOutputStream().write(imageUtil.getQuote(
                            text,
                            avatar,
                            name,
                            time,
                            format,
                            color
                    ));
                    raw.getOutputStream().flush();
                    raw.getOutputStream().close();

                    logger.info("Generated Quote image successfully!");
                    return raw;
                }catch(IOException ex){
                    logger.error("Couldn't return image! Endpoint: /quote", ex);
                    halt("Something went wrong! Check if the values are valid!");
                }

                logger.error("Couldn't create Quote image.");

                response.type("application/json");
                response.status(500);
                response.body("{\"code\": 500,\"message\":\"Couldn't generate image. Make sure the values are valid!\"}");

                return response.body();
            });

            post("/status", (request, response) -> {
                logger.info("Request received! Type: POST; Endpoint: /status");

                JsonObject json = gson.fromJson(request.body(), JsonObject.class);

                String avatar = getStringOrDefault(json.get("avatar").getAsString(), Strings.AVATAR);
                String status = getStringOrDefault(json.get("status").getAsString(), Strings.STATUS);

                try{
                    HttpServletResponse raw = response.raw();
                    raw.setContentType("image/png");
                    raw.getOutputStream().write(imageUtil.getStatus(
                            avatar,
                            status
                    ));
                    raw.getOutputStream().flush();
                    raw.getOutputStream().close();

                    logger.info("Generated Status image successfully!");
                    return raw;
                }catch(IOException ex){
                    logger.error("Couldn't return image! Endpoint: /status", ex);
                    halt("Something went wrong! Check if the values are valid!");
                }

                logger.error("Status image couldn't be created!");

                response.type("application/json");
                response.status(500);
                response.body("{\"code\": 500,\"message\":\"Couldn't generate image. Make sure the values are valid!\"}");

                return response.body();
            });

            get("/quote", (request, response) -> {
                logger.info("Request received! Type: GET; Endpoint: /quote");

                response.type("application/json");
                response.status(403);
                response.body("{\"code\": 403,\"message\":\"GET request is no longer supported! Use a POST request instead.\"}");

                return response.body();
            });

            get("/status", (request, response) -> {
                logger.info("Request received! Type: GET; Endpoint: /status");

                response.type("application/json");
                response.status(403);
                response.body("{\"code\": 403,\"message\":\"GET request is no longer supported! Use a POST request instead.\"}");

                return response.body();
            });

            get("/welcome", (request, response) -> {
                logger.info("Request received! Type: GET; Endpoint: /welcome");

                response.type("application/json");
                response.status(403);
                response.body("{\"code\": 403,\"message\":\"This endpoint was removed and is no longer accessible.\"}");

                return response.body();
            });
        });

        internalServerError((request, response) -> {

            try {
                response.raw().setContentType("image/png");
                HttpServletResponse raw = response.raw();
                raw.getOutputStream().write(
                        imageUtil.getQuote(
                                Strings.ERR_MSG.getString(),
                                Strings.ERR_AVATAR.getString(),
                                Strings.ERR_NAME.getString(),
                                String.valueOf(System.currentTimeMillis()),
                                Strings.TIMEFORMAT.getString(),
                                String.valueOf(0xFFFFFF)
                        )
                );
                raw.getOutputStream().flush();
                raw.getOutputStream().close();

                logger.info("API-Error! (HTTP 500)");
                return raw;
            } catch (IOException ex) {
                halt();
            }

            logger.error("API failed!");
            return response;
        });
    }

    private String getStringOrDefault(String target, Strings defaultValue){
        return getStringOrDefault(target, defaultValue.getString());
    }

    private String getStringOrDefault(String target, String defaultValue){
        if(target == null)
            return defaultValue;

        if(target.isEmpty())
            return defaultValue;

        return target;
    }
}
