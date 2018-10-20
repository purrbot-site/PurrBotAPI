package com.andre601.purrbotapi.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
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
                                              String format, String color) throws Exception {

        BufferedImage avatar = getUserAvatar(avatarURL);
        BufferedImage overlay = ImageIO.read(new File("img/overlay.png"));

        String[] quote = text.split(" ");

        Font textFont = new Font("Arial", Font.PLAIN, 55);

        BufferedImage image = new BufferedImage(1920, 300, BufferedImage.TYPE_INT_ARGB);

        Graphics2D img = image.createGraphics();

        Color nameColor = new Color(Integer.valueOf(color));

        StringBuilder sb = new StringBuilder();
        String str = "";
        List<String> msg = new ArrayList<>();
        int lines = 1;
        for(String a : quote){
            if(img.getFontMetrics(textFont).stringWidth(str + " " + a) >= 1500) {
                msg.add(str);
                str = "";
                sb = new StringBuilder();
                lines++;
            }

            if(a.endsWith("\\n")){
                a = a.replace("\\n", "");

                sb.append(a);
                str = sb.toString();
                msg.add(str);
                str = "";
                sb = new StringBuilder();
                lines++;
                continue;
            }

            sb.append(a).append(" ");
            str = sb.toString();
        }
        msg.add(str);

        int height = 110 + (lines * 60);

        BufferedImage finalImage = resize(image, (height > image.getHeight() ? height : image.getHeight()));
        Graphics2D finalImg = finalImage.createGraphics();
        finalImg.setColor(new Color(54, 57, 63));
        finalImg.fillRect(0, 0, finalImage.getWidth(), finalImage.getHeight());

        finalImg.drawImage(avatar, 0, 0, 217, 217, null);
        finalImg.drawImage(overlay, 0, 0, null);

        Font nameFont = new Font("Arial", Font.BOLD, 60);
        Font dateFont = new Font("Arial", Font.PLAIN, 30);

        finalImg.setFont(nameFont);
        finalImg.setColor(nameColor);

        finalImg.drawString(name, 300, 65);

        long time = Long.parseLong(timeStamp);
        Date dateTime = new Date(time);
        SimpleDateFormat date = new SimpleDateFormat(format);
        String finalDate = date.format(dateTime);

        finalImg.setFont(dateFont);
        finalImg.setColor(new Color(103, 113, 122));

        int posX = 310 + finalImg.getFontMetrics(nameFont).stringWidth(name);

        finalImg.drawString(finalDate, posX, 65);

        finalImg.setFont(textFont);
        finalImg.setColor(Color.WHITE);
        int posY = 150;
        for(String a : msg){
            finalImg.drawString(a, 310, posY);
            posY = posY + 60;
        }

        finalImg.dispose();

        byte[] rawImage;
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            ImageIO.write(finalImage, "png", baos);

            baos.flush();
            rawImage = baos.toByteArray();
        }

        return rawImage;
    }

    public static byte[] getStatusImage(String url, String status) throws Exception{
        BufferedImage avatar = getUserAvatar(url);
        BufferedImage statusImg;

        switch(status.toUpperCase()){
            case "ONLINE":
                statusImg = ImageIO.read(new File("img/online.png"));
                break;

            case "DO_NOT_DISTURB":
            case "DND":
                statusImg = ImageIO.read(new File("img/dnd.png"));
                break;

            case "IDLE":
                statusImg = ImageIO.read(new File("img/idle.png"));
                break;

            case "OFFLINE":
            default:
                statusImg = ImageIO.read(new File("img/offline.png"));
                break;
        }

        BufferedImage image = resize(avatar);
        int width = image.getWidth();

        BufferedImage circleBuffer = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB);
        Graphics2D img = circleBuffer.createGraphics();
        img.setClip(new Ellipse2D.Float(0, 0, width, width));
        img.drawImage(image, 0, 0, width, width, null);

        img.setClip(null);

        int statusImgX = image.getWidth() - statusImg.getWidth();
        int statusImgY = image.getHeight() - statusImg.getHeight();
        img.drawImage(statusImg, statusImgX, statusImgY,null);

        img.dispose();

        byte[] rawImage;
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            ImageIO.write(circleBuffer, "png", baos);

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

    private static BufferedImage resize(BufferedImage image){

        BufferedImage output = new BufferedImage(950, 950, image.getType());

        Graphics2D tmpImg = output.createGraphics();
        tmpImg.drawImage(image, 0, 0, 950, 950, null);
        tmpImg.dispose();

        return output;

    }

}
