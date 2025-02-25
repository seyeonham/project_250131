package com.project_250131.admin.bo;

import com.project_250131.common.FileManagerService;
import com.project_250131.menu.domain.Menu;
import com.project_250131.menu.mapper.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class AdminMenuBO {

    private final MenuMapper menuMapper;
    private final FileManagerService fileManagerService;

    public int addMenu(int storeId, MultipartFile file, String name, int price) {
        String imagePath = fileManagerService.uploadMenuFile(file, storeId);

        if (imagePath == null) { // 파일 업로드 실패
            return 0;
        }

        int rowCount = menuMapper.insertMenu(storeId, imagePath, name, price);

        return rowCount;
    }
}
