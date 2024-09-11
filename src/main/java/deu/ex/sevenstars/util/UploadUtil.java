package deu.ex.sevenstars.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
@Log4j2
public class UploadUtil {
    @Value("${deu.ex.sevenstars.upload.path}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        File tempDir = new File(uploadPath);
        if (!tempDir.exists()) {
            log.info("Creating upload directory: " + tempDir);
            tempDir.mkdir();
        }
        uploadPath = tempDir.getAbsolutePath();
    }

    public String upload(MultipartFile file) {
        if (!file.getContentType().startsWith("image")) {
            log.error("Unsupported file type: " + file.getContentType());
            throw new RuntimeException("Unsupported file type");
        }
        String uuid = UUID.randomUUID().toString();
        String saveFilename = uuid + "_" + file.getOriginalFilename();
        String savePath = uploadPath + File.separator;

        try {
            file.transferTo(new File(savePath + saveFilename));

            Thumbnails.of(new File(savePath + saveFilename))
                    .size(150, 150)
                    .toFile(savePath + "s_" + saveFilename);

            return saveFilename;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(String filename) {
        File file = new File(uploadPath + File.separator + filename);
        File thumbFile = new File(uploadPath + File.separator + "s_" + filename);
        try {
            if (file.exists()) file.delete();
            if (thumbFile.exists()) thumbFile.delete();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
