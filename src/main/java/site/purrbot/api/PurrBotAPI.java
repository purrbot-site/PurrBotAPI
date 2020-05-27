package site.purrbot.api;

import ch.qos.logback.classic.Logger;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import site.purrbot.api.endpoints.Quote;
import site.purrbot.api.endpoints.Status;
import spark.Response;
import spark.Spark;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static spark.Spark.*;

public class PurrBotAPI {

    private final Logger logger = (Logger)LoggerFactory.getLogger(PurrBotAPI.class);

    private ImageUtil imageUtil;

    public static void main(String[] args){
        new PurrBotAPI().start();
    }

    private void start(){
        logger.info("Starting PurrBotAPI API_VERSION");
        
        imageUtil = new ImageUtil();

        Spark.port(2000);

        path("/api", () -> {
            before("/*", (q, a) -> logger.info("Received API-call."));
            Gson gson = new Gson();

            post("/quote", (request, response) -> {
                logger.info("Request received! Type: POST; Endpoint: /quote");

                Quote quote = gson.fromJson(request.body(), Quote.class);
                
                if(quote == null)
                    return getErrorJSON(response, 403, "Invalid or empty JSON-body received!");
                

                try{
                    HttpServletResponse raw = response.raw();
                    raw.setContentType("image/png");
                    raw.getOutputStream().write(imageUtil.getQuote(quote));
                    raw.getOutputStream().flush();
                    raw.getOutputStream().close();

                    logger.info("Generated Quote image successfully!");
                    return raw;
                }catch(IOException ex){
                    logger.error("Couldn't create Quote image.");
    
                    return getErrorJSON(response, 500, "Couldn't generate image. Make sure the values are valid!");
                }
            });

            post("/status", (request, response) -> {
                logger.info("Request received! Type: POST; Endpoint: /status");
                Status status = gson.fromJson(request.body(), Status.class);
    
                if(status == null)
                    return getErrorJSON(response, 403, "Invalid or empty JSON-body received!");
                
                try{
                    HttpServletResponse raw = response.raw();
                    raw.setContentType("image/png");
                    raw.getOutputStream().write(imageUtil.getStatus(status));
                    raw.getOutputStream().flush();
                    raw.getOutputStream().close();

                    logger.info("Generated Status image successfully!");
                    return raw;
                }catch(IOException ex){
                    logger.error("Status image couldn't be created!");
    
                    return getErrorJSON(response, 500, "Couldn't generate image. Make sure the values are valid!");
                }

            });
        });
    }
    
    private String getErrorJSON(Response response, int code, String message){
        JSONObject json = new JSONObject()
                .put("code", code)
                .put("message", message);
        
        response.status(code);
        response.type("application/json");
        response.body(json.toString());
        
        return response.body();
    }
}
