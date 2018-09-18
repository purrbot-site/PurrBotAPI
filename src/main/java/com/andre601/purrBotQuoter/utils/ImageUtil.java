package com.andre601.purrBotQuoter.utils;

import com.andre601.purrBotQuoter.PurrBotQuoter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImageUtil {

    private static final String[] UA = {"User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36"};

    private static BufferedImage getUserAvatar(String url) throws Exception{

        BufferedImage avatar;

        URL icon = new URL(url);
        URLConnection connection = icon.openConnection();
        connection.setRequestProperty(UA[0], UA[1]);
        connection.connect();
        avatar = ImageIO.read(connection.getInputStream());

        return avatar;
    }

    public static byte[] getQuoteImage(String text, String avatarURL,String name, String timeStamp,
                                              String format) throws Exception {

        BufferedImage avatar = getUserAvatar(avatarURL);
        BufferedImage overlay = ImageIO.read(new File("img/overlay.png"));

        String[] quote = text.split(" ");

        PurrBotQuoter.getLogger().info("Got text: " + text);

        Font textFont = new Font("Arial", Font.PLAIN, 60);

        BufferedImage image = new BufferedImage(1000, 300, BufferedImage.TYPE_INT_ARGB);

        Graphics2D img = image.createGraphics();

        StringBuilder sb = new StringBuilder();
        String str = "";
        List<String> msg = new ArrayList<>();
        int lines = 1;
        for(int i = 0; i < quote.length; i++){
            if(img.getFontMetrics(textFont).stringWidth(str + " " + quote[i]) > 700){
                PurrBotQuoter.getLogger().info("Made new line.");
                msg.add(str);
                str = "";
                lines++;
            }else
            if(quote[i].endsWith("\n")) {
                sb.append(str).append(" ").append(quote[i]);
                msg.add(sb.toString());
                str = "";
                lines++;
            }else{
                PurrBotQuoter.getLogger().info("Added word " + quote[i]);
                sb.append(str).append(" ").append(quote[i]);
                str = sb.toString();
            }
        }
        PurrBotQuoter.getLogger().info("Added final word(s): " + str);
        msg.add(str);

        int height = lines * 60;

        BufferedImage finalImage = resize(image, (height > image.getHeight() ? height : image.getHeight()));
        Graphics2D finalImg = finalImage.createGraphics();
        finalImg.setColor(new Color(54, 57, 63));
        finalImg.fillRect(0, 0, finalImage.getWidth(), finalImage.getHeight());

        finalImg.drawImage(avatar, 5, 5, 290, 290, null);
        finalImg.drawImage(overlay, 0, 0, null);

        Font nameFont = new Font("Arial", Font.BOLD, 60);
        Font dateFont = new Font("Arial", Font.PLAIN, 20);

        finalImg.setFont(nameFont);
        finalImg.setColor(Color.WHITE);

        PurrBotQuoter.getLogger().info("Write name " + name);
        finalImg.drawString(name, 300, 65);
        PurrBotQuoter.getLogger().info("Name written!");

        long time = Long.parseLong(timeStamp);
        Date dateTime = new Date(time);
        SimpleDateFormat date = new SimpleDateFormat(format);
        String finalDate = date.format(dateTime);

        finalImg.setFont(dateFont);
        finalImg.setColor(new Color(85, 87, 93));

        int posX = 310 + finalImg.getFontMetrics(nameFont).stringWidth(name);

        PurrBotQuoter.getLogger().info("Write date: " + finalDate);
        finalImg.drawString(finalDate, posX, 65);
        PurrBotQuoter.getLogger().info("Date written!");

        finalImg.setFont(textFont);
        finalImg.setColor(Color.WHITE);
        int posY = 130;
        for(int i = 0; i < msg.size(); i++){
            PurrBotQuoter.getLogger().info("Writing word(s) " + msg.get(i) + " at X:300, Y:" + posY);
            finalImg.drawString(msg.get(i), 300, posY);
            posY = posY + 60;
        }
        PurrBotQuoter.getLogger().info("Word(s) written!");

        finalImg.dispose();

        byte[] rawImage;
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            ImageIO.write(finalImage, "png", baos);

            baos.flush();
            rawImage = baos.toByteArray();
        }

        return rawImage;
    }

    private static BufferedImage resize(BufferedImage image, int newHeight){

        BufferedImage output = new BufferedImage(image.getWidth(), newHeight, image.getType());

        Graphics2D tmpImg = output.createGraphics();
        tmpImg.drawImage(image, 0, 0, image.getWidth(), newHeight, null);
        tmpImg.dispose();

        return output;

    }

}
