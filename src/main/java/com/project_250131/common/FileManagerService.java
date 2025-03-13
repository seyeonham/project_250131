package com.project_250131.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class FileManagerService {
    public static final String BASE_UPLOAD_PATH = "/home/ec2-user/project_images/";

    // input: multipart file, storeId(폴더명으로 사용)
    // output: imagePath(String)
    public String uploadMenuFile(MultipartFile file, int storeId) {

        // 메뉴 폴더 찾기
        String uploadPath = BASE_UPLOAD_PATH + "menu";

        // 폴더 생성
        String directoryName = storeId + "_" + System.currentTimeMillis();
        String filePath = uploadPath + File.separator + directoryName;

        File directory = new File(filePath);
        if (!directory.mkdirs()) {
            return null;
        }

        // 파일 업로드
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath + File.separator + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return "/images/" + "menu/" + directoryName + "/" + file.getOriginalFilename();
    }

    // input: multipart file, storeId(폴더명으로 사용), userId(폴더명으로 사용)
    // output: imagePath(String)
    public String uploadReviewFile(MultipartFile file, int storeId, int userId) {

        // 메뉴 폴더 찾기
        String uploadPath = BASE_UPLOAD_PATH + "review";

        // 폴더 생성
        String directoryName = storeId + "_" + userId + "_" + System.currentTimeMillis();
        String filePath = uploadPath + File.separator + directoryName;

        File directory = new File(filePath);
        if (!directory.mkdirs()) {
            return null;
        }

        // 파일 업로드
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath + File.separator + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return "/images/" + "review/" + directoryName + "/" + file.getOriginalFilename();
    }

    public void deleteFile(String imagePath) {
        Path path = Paths.get(BASE_UPLOAD_PATH + imagePath.replace("/images/", ""));

        if (Files.exists(path)) {
            // 이미지 삭제
            try {
                Files.delete(path);
            } catch (IOException e) {
                log.info("[### 파일매니저 이미지 삭제] imagePath:{}", imagePath);
                return;
            }

            // 디렉토리 삭제
            path = path.getParent();
            if (Files.exists(path)) {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    log.info("[### 파일매니저 디렉토리 삭제] imagePath:{}", imagePath);
                    return;
                }
            }
        }
    }
}
