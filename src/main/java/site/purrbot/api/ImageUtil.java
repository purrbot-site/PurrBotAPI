package site.purrbot.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class ImageUtil {

    ImageUtil(){}

    private final OkHttpClient CLIENT = new OkHttpClient();

    private byte[] getAvatar(String url) throws IOException{
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "PurrBot-API")
                .build();

        try(Response response = CLIENT.newCall(request).execute()){
            if(!response.isSuccessful())
                throw new IOException(String.format(
                        "Couldn't get image! The server responded with %d (%s)",
                        response.code(),
                        response.message()
                ));

            if(response.body() == null)
                throw new NullPointerException("Received empty body.");

            return response.body().bytes();
        }
    }

    private BufferedImage getAvatar(String url, int size) throws IOException{
        InputStream is = new ByteArrayInputStream(getAvatar(url));

        BufferedImage source = ImageIO.read(is);
        BufferedImage avatar = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

        Graphics2D img = avatar.createGraphics();
        img.setClip(new Ellipse2D.Float(0, 0, size, size));
        img.drawImage(source, 0, 0, size, size, null);
        img.dispose();

        return avatar;
    }

    byte[] getQuote(String text, String avatarUrl, String name, String time, String format, String color) throws IOException{
        BufferedImage avatar = getAvatar(avatarUrl, 217);

        String[] quote = text.split(" ");

        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 55);
        BufferedImage image = new BufferedImage(1920, 300, BufferedImage.TYPE_INT_ARGB);

        Graphics2D img = image.createGraphics();
        Color col = getColor(color);

        String message = "";
        List<String> messages = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        Integer lines = 1;

        for(String s : quote){
            if(s.contains("\n")){
                if(s.endsWith("\n")){
                    s = s.replace("\n", "");
                    if(img.getFontMetrics(font).stringWidth(message + " " + s) >= 1500){
                        messages.add(message);

                        builder = new StringBuilder();

                        lines++;
                    }

                    builder.append(s);
                    message = builder.toString();
                    messages.add(message);

                    builder = new StringBuilder();

                    lines++;
                    continue;
                }else{
                    String before = s.split("\n")[0];
                    String after = s.split("\n")[1];

                    if(img.getFontMetrics(font).stringWidth(message + " " + before) >= 1500){
                        messages.add(message);

                        builder = new StringBuilder();

                        lines++;
                    }

                    builder.append(before);
                    message = builder.toString();
                    messages.add(message);

                    message = "";
                    builder = new StringBuilder();

                    lines++;

                    if(after.isEmpty())
                        continue;

                    builder.append(after).append(" ");
                    message = builder.toString();
                    continue;
                }
            }

            if(img.getFontMetrics(font).stringWidth(message + " " + s) >= 1500){
                messages.add(message);

                builder = new StringBuilder();

                lines++;
            }

            builder.append(s).append(" ");
            message = builder.toString();
        }

        messages.add(message);

        int height = 110 + (lines * 60);

        image = resize(image, height > image.getHeight() ? height : image.getHeight());
        img = image.createGraphics();

        img.setColor(new Color(0x36393F));
        img.fillRect(0, 0, image.getWidth(), image.getHeight());

        img.drawImage(avatar, 10, 10, null);

        Font fName = new Font(Font.SANS_SERIF, Font.BOLD, 60);
        Font fDate = new Font(Font.SANS_SERIF, Font.PLAIN, 30);

        if(col == null)
            col = new Color(0xFFFFFF);

        img.setColor(col);
        img.setFont(fName);

        img.drawString(name, 290, 65);

        Date date = new Date(Long.parseLong(time));
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        time = dateFormat.format(date);

        img.setColor(new Color(0x67717A));
        img.setFont(fDate);

        int positionX = 300 + img.getFontMetrics(fName).stringWidth(name);

        img.drawString(time, positionX, 65);

        img.setColor(new Color(0xFFFFFF));
        img.setFont(font);

        int positionY = 150;
        for(String s : messages){
            img.drawString(s, 290, positionY);
            positionY = positionY + 60;
        }

        img.dispose();

        byte[] raw;
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            ImageIO.write(image, "png", baos);

            baos.flush();
            raw = baos.toByteArray();
        }

        return raw;
    }

    byte[] getStatus(String url, String status) throws IOException{
        BufferedImage avatar = getAvatar(url, 950);
        BufferedImage statusImg;

        switch(status.toLowerCase()){
            case "online":
                statusImg = ImageIO.read(new File("img/status/online.png"));
                break;

            case "idle":
                statusImg = ImageIO.read(new File("img/status/idle.png"));
                break;

            case "dnd":
            case "do_not_disturb":
                statusImg = ImageIO.read(new File("img/status/dnd.png"));
                break;

            case "offline":
            default:
                statusImg = ImageIO.read(new File("img/status/online.png"));
        }

        Graphics2D img = avatar.createGraphics();

        int x = avatar.getWidth() - statusImg.getWidth();
        int y = avatar.getHeight() - statusImg.getHeight();

        img.drawImage(statusImg, x, y, null);
        img.dispose();

        byte[] raw;
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            ImageIO.write(avatar, "png", baos);

            baos.flush();
            raw = baos.toByteArray();
        }

        return raw;
    }

    private BufferedImage resize(BufferedImage image, int size){
        BufferedImage output = new BufferedImage(image.getWidth(), size, image.getType());

        Graphics2D img = output.createGraphics();
        img.drawImage(image, 0, 0, image.getWidth(), size, null);
        img.dispose();

        return output;
    }

    private Color getColor(String input){
        Color color;
        if(!input.toLowerCase().startsWith("hex:") && !input.toLowerCase().startsWith("rgb:")){
            try{
                color = new Color(Integer.valueOf(input));
            }catch(Exception ex){
                color = null;
            }

            return color;
        }

        String type = input.toLowerCase().split(":")[0];
        String value = input.toLowerCase().split(":")[1];

        switch(type){
            case "hex":
                try{
                    color = Color.decode(value.startsWith("#") ? value : "#" + value);
                }catch(Exception ex){
                    color = null;
                }
                break;

            case "rgb":
                String[] rgb = value.replace(" ", "").split(",");

                if(rgb.length < 3)
                    return null;

                try{
                    color = new Color(Integer.valueOf(rgb[0], Integer.valueOf(rgb[1], Integer.valueOf(rgb[2]))));
                }catch(Exception ex){
                    color = null;
                }
                break;

            default:
                color = null;
        }

        return color;
    }
}
