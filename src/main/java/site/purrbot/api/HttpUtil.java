package site.purrbot.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class HttpUtil {

    private List<String> images;
    private final OkHttpClient CLIENT = new OkHttpClient();

    HttpUtil(){
        try {
            images = loadImages();
        }catch(IOException ex){
            images = null;
        }
    }

    private List<String> loadImages() throws IOException{
        Request request = new Request.Builder()
                .url("https://raw.githubusercontent.com/Andre601/PurrBot/master/src/main/resources/random.json")
                .build();

        try(Response response = CLIENT.newCall(request).execute()){
            if(response.body() == null)
                throw new NullPointerException("Received response body was null.");

            if(!response.isSuccessful())
                throw new IOException(String.format(
                        "Couldn't perform request. Site responded with %d (%s)",
                        response.code(),
                        response.message()
                ));

            JsonObject json = new Gson().fromJson(response.body().string(), JsonObject.class);
            List<String> temp = new ArrayList<>();

            JsonArray array = json.getAsJsonArray("welcome_img");
            for(int i = 0; i < array.size(); i++)
                temp.add(array.get(i).getAsString());

            return temp;
        }
    }

    List<String> getImages(){
        if(images.isEmpty())
            throw new NullPointerException("List of images was empty!");

        return images;
    }

}
