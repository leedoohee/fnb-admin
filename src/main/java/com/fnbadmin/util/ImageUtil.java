package com.fnbadmin.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class ImageUtil {
    public static String uploadImage(String imagePath, byte[] imageData) throws IOException {
        String newFilename = UUID.randomUUID() + "." + "png";

        Path destinationPath = Paths.get(imagePath, newFilename);
        File destinationFile = destinationPath.toFile();

        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
        ImageIO.write(bufferedImage, "png", destinationFile);

        return "/images" + newFilename;
    }

    //TODO S3
    //TODO Cloudinary
}
