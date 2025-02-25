package com.project_250131.common;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileManagerService {
    public static String FILE_UPLOAD_PATH = "C:\\함세연\\project_250131\\project_images\\";

    // input: multipart file, menuId(폴더명으로 사용)
    // output: imagePath(String)
    public String uploadMenuFile(MultipartFile file, int storeId) {

        // 메뉴 폴더 찾기
        FILE_UPLOAD_PATH = FILE_UPLOAD_PATH + "menu/";

        // 폴더 생성
        String directoryName = storeId + "_" + System.currentTimeMillis();
        String filePath = FILE_UPLOAD_PATH + directoryName + "/";

        File directory = new File(filePath);
        if (directory.mkdir() == false) {
            return null;
        }

        // 파일 업로드
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return "/images/" + directoryName + "/" + file.getOriginalFilename();
    }
}
