package com.fnbadmin.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class ImageUtil {
    public static String getImageUrl(String imagePath) {
        String baseUrl = "http://localhost:8080/images/";
        return baseUrl + imagePath;
    }

    public static void uploadImage(String imagePath, byte[] imageData) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
        ImageIO.write(bufferedImage, "png", new File(imagePath));
    }

    //TODO S3
    //TODO Cloudinary
}
