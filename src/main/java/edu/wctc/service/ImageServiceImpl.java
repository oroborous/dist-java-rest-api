package edu.wctc.service;

import edu.wctc.dao.ImageDAO;
import edu.wctc.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageDAO imageDAO;

    // Needed to find path to "not found" image on disk
    @Autowired
    private ServletContext servletContext;

    private Image notFoundImage;

    @Transactional
    @Override
    public void delete(int id) {
        if (id != 0) {
            imageDAO.delete(id);
        }
    }

    @Transactional
    @Override
    public void deleteUnusedImages() {
        imageDAO.deleteUnusedImages();
    }

    @Transactional
    @Override
    public Image getImage(Integer imageId) {
        Image image = null;

        // Try to get image from database if id present
        if (imageId != null) {
            image = imageDAO.getImage(imageId);
        }

        // Still no image, so failover to "not found" image
        if (image == null || image.getFile() == null || image.getFile().length == 0) {
            initMissingDonutImage();
            image = notFoundImage;
        }

        return image;
    }

    private void initMissingDonutImage() {
        if (notFoundImage == null) {
            try {
                notFoundImage = new Image();
                notFoundImage.setName("none.jpg");
                notFoundImage.setMimeType(MediaType.IMAGE_JPEG_VALUE);

                String pathStr = servletContext.getRealPath("/WEB-INF/resources/img/none.jpg");
                byte[] data = Files.readAllBytes(Paths.get(pathStr));
                notFoundImage.setFile(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Transactional
    @Override
    public int saveImage(MultipartFile image) {
        if (image.getSize() == 0) {
            return -1;
        }

        Image imageData = new Image();

        try {
            imageData.setFile(image.getBytes());

            // Limit file name to 100 characters
            String fileNameLower = image.getOriginalFilename().toLowerCase();
            if (fileNameLower.length() > 100) {
                fileNameLower = fileNameLower.substring(fileNameLower.length() - 100);
            }

            imageData.setName(fileNameLower);

            // Set MIME type based on file extension
            if (fileNameLower.endsWith("png"))
                imageData.setMimeType(MediaType.IMAGE_PNG_VALUE);
            else if (fileNameLower.endsWith("jpg") || fileNameLower.endsWith("jpeg"))
                imageData.setMimeType(MediaType.IMAGE_JPEG_VALUE);
            else if (fileNameLower.endsWith("gif"))
                imageData.setMimeType(MediaType.IMAGE_GIF_VALUE);
            else
                imageData.setMimeType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageDAO.saveImage(imageData);

        return imageData.getId();
    }
}
