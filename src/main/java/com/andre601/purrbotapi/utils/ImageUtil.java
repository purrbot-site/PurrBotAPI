package com.andre601.purrbotapi.utils;

import com.andre601.purrbotapi.PurrBotAPI;

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

    public ImageUtil(){}

    private static final String[] UA = {"User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36"};

    private BufferedImage getUserAvatar(String url) throws Exception{

        BufferedImage avatar;

        URL icon = new URL(url);
        URLConnection connection = icon.openConnection();
        connection.setRequestProperty(UA[0], UA[1]);
        connection.connect();
        avatar = ImageIO.read(connection.getInputStream());

        return avatar;
    }

    public byte[] getQuoteImage(String text, String avatarURL,String name, String timeStamp,
                                              String format, String color) throws Exception {

        BufferedImage avatar = getUserAvatar(avatarURL);
        BufferedImage finalAvatar = getRoundAvatar(avatar, 217);

        String[] quote = text.split(" ");

        Font textFont = new Font("Arial", Font.PLAIN, 55);

        BufferedImage image = new BufferedImage(1920, 300, BufferedImage.TYPE_INT_ARGB);

        Graphics2D img = image.createGraphics();

        Color nameColor = ColourUtil.toColor(color);

        StringBuilder sb = new StringBuilder();
        String str = "";
        List<String> msg = new ArrayList<>();
        int lines = 1;
        for(String a : quote){

            if(a.contains("\n")){
                if(a.endsWith("\n")) {
                    a = a.replace("\n", "");

                    if(img.getFontMetrics(textFont).stringWidth(str + " " + a) >= 1500) {
                        msg.add(str);
                        str = "";
                        sb = new StringBuilder();
                        lines++;
                    }

                    sb.append(a);
                    str = sb.toString();
                    msg.add(str);
                    str = "";
                    sb = new StringBuilder();
                    lines++;
                    continue;
                }else{

                    String word0 = a.split("\n")[0];
                    String word1 = a.split("\n")[1];

                    if(img.getFontMetrics(textFont).stringWidth(str + " " + word0) >= 1500) {
                        msg.add(str);
                        str = "";
                        sb = new StringBuilder();
                        lines++;
                    }

                    sb.append(word0);
                    str = sb.toString();
                    msg.add(str);
                    str = "";
                    sb = new StringBuilder();
                    lines++;

                    if(word1.equals("") || word1.isEmpty()) {
                        continue;
                    }

                    sb.append(word1).append(" ");
                    str = sb.toString();
                    continue;
                }
            }

            if(img.getFontMetrics(textFont).stringWidth(str + " " + a) >= 1500) {
                msg.add(str);
                str = "";
                sb = new StringBuilder();
                lines++;
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

        finalImg.drawImage(finalAvatar, 10, 5, null);

        Font nameFont = new Font("Arial", Font.BOLD, 60);
        Font dateFont = new Font("Arial", Font.PLAIN, 30);

        if(nameColor == null) nameColor = Color.WHITE;

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

    public byte[] getStatusImage(String url, String status) throws Exception{
        BufferedImage avatar = getUserAvatar(url);
        BufferedImage statusImg;

        switch(status.toUpperCase()){
            case "ONLINE":
                statusImg = ImageIO.read(new File("img/status/online.png"));
                break;

            case "DO_NOT_DISTURB":
            case "DND":
                statusImg = ImageIO.read(new File("img/status/dnd.png"));
                break;

            case "IDLE":
                statusImg = ImageIO.read(new File("img/status/idle.png"));
                break;

            case "OFFLINE":
            default:
                statusImg = ImageIO.read(new File("img/status/offline.png"));
                break;
        }

        BufferedImage image = getRoundAvatar(avatar, 950);
        Graphics2D img = image.createGraphics();

        int statusImgX = image.getWidth() - statusImg.getWidth();
        int statusImgY = image.getHeight() - statusImg.getHeight();
        img.drawImage(statusImg, statusImgX, statusImgY,null);

        img.dispose();

        byte[] rawImage;
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            ImageIO.write(image, "png", baos);

            baos.flush();
            rawImage = baos.toByteArray();
        }

        return rawImage;
    }

    public byte[] getWelcomeImage(String image, String name, String avatar, String colorString, long size)
            throws Exception{
        BufferedImage ava = getUserAvatar(avatar);
        BufferedImage img;
        BufferedImage layer;
        String imageType;

        BufferedImage finalAvatar = getRoundAvatar(ava, 290);

        if(image.equalsIgnoreCase("random"))
            imageType = PurrBotAPI.getRandomImage();
        else
        if(PurrBotAPI.getImageList().contains(image))
            imageType = image;
        else
            imageType = "purr";

        layer = ImageIO.read(new File("img/welcome/" + imageType.toLowerCase() + ".png"));
        img = new BufferedImage(layer.getWidth(), layer.getHeight(), layer.getType());

        Graphics2D graphic = img.createGraphics();

        //  Adding the different images (background -> User-Avatar -> actual image)
        graphic.drawImage(layer, 0, 0, null);
        graphic.drawImage(finalAvatar, 5, 5, null);

        //  Creating the font for the custom text.
        Font text = new Font("Arial", Font.PLAIN, 85);

        Color color = ColourUtil.toColor(colorString);
        if(color == null) color = Color.WHITE;

        graphic.setColor(color);
        graphic.setFont(text);

        //  Setting the actual text. \n is (sadly) not supported, so we have to make each new line seperate.
        graphic.drawString("Welcome",320, 85);
        graphic.drawString(name,320, 165);
        graphic.drawString(String.format(
                "You are user #%s",
                size
        ),320, 245);

        graphic.dispose();

        byte[] raw;
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            ImageIO.write(img, "png", baos);

            baos.flush();
            raw = baos.toByteArray();
        }

        return raw;
    }

    private BufferedImage resize(BufferedImage image, int newHeight){

        BufferedImage output = new BufferedImage(image.getWidth(), newHeight, image.getType());

        Graphics2D tmpImg = output.createGraphics();
        tmpImg.drawImage(image, 0, 0, image.getWidth(), newHeight, null);
        tmpImg.dispose();

        return output;

    }

    private BufferedImage getRoundAvatar(BufferedImage image, int newSize){
        BufferedImage output = new BufferedImage(newSize, newSize, BufferedImage.TYPE_INT_ARGB);

        Graphics2D tmpImg = output.createGraphics();
        tmpImg.setClip(new Ellipse2D.Float(0, 0, newSize, newSize));
        tmpImg.drawImage(image, 0, 0, newSize, newSize, null);
        tmpImg.dispose();

        return output;
    }

}
