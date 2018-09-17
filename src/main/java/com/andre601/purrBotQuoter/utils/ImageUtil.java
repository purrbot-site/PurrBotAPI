package com.andre601.purrBotQuoter.utils;

import sun.util.locale.provider.TimeZoneNameUtility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ImageUtil {

    private static final String[] UA = {"User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36"};

    private static DateTimeFormatter date;

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

        BufferedImage image = new BufferedImage(1000, 0, 0);

        Graphics2D img = image.createGraphics();

        StringBuilder sb = new StringBuilder();
        String str = "";
        List<String> msg = new ArrayList<>();
        int lines = 0;
        for(int i = 0; i < quote.length; i++){
            if(img.getFontMetrics().stringWidth(str + " " + quote[i]) > image.getWidth()){
                msg.add(str);
                str = "";
                lines++;
            }else{
                sb.append(str).append(" ").append(quote[i]);
                str = sb.toString();
            }
        }

        int height = lines * 20;

        BufferedImage finalImage = resize(image, height);
        Graphics2D finalImg = finalImage.createGraphics();
        finalImg.setColor(new Color(54, 57, 63));
        finalImg.fillRect(0, 0, finalImage.getWidth(), finalImage.getHeight());

        finalImg.drawImage(avatar, 5, 5, 290, 290, null);
        finalImg.drawImage(overlay, 5, 5, null);

        Font textFont = new Font("Arial", Font.PLAIN, 20);
        Font nameFont = new Font("Arial", Font.BOLD, 20);

        finalImg.setColor(Color.WHITE);
        finalImg.setFont(nameFont);

        finalImg.drawString(name, 300, 0);

        if(format == null){
            date = DateTimeFormatter.ofPattern("dd. MMM yyyy HH:mm:ss");
        }else{
            date = DateTimeFormatter.ofPattern(format);
        }

        finalImg.setFont(textFont);
        int posY = 25;
        for(int i = 0; i < msg.size(); i++){
            finalImg.drawString(msg.get(i), 300, posY);
            posY = posY + 20;
        }

        finalImg.dispose();

        byte[] rawImage = null;
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
