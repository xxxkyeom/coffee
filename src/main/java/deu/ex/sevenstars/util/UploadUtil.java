package deu.ex.sevenstars.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    // 파일 업로드 및 썸네일 생성
    public List<String> upload(MultipartFile[] files) {
        List<String> filenames = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.getContentType().startsWith("image")) {
                log.error("Unsupported file type: " + file.getContentType());
                continue;
            }
            String uuid = UUID.randomUUID().toString();
            String saveFilename = uuid + "_" + file.getOriginalFilename();
            String savePath = uploadPath + File.separator;

            try {
                file.transferTo(new File(savePath + saveFilename));

                // 썸네일 파일 생성
                Thumbnails.of(new File(savePath + saveFilename))
                        .size(150, 150)
                        .toFile(savePath + "s_" + saveFilename);

                filenames.add(saveFilename);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return filenames;
    }
    // 파일 삭제
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
