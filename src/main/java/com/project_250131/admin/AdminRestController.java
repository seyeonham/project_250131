package com.project_250131.admin;

import com.project_250131.admin.bo.AdminMenuBO;
import com.project_250131.admin.bo.AdminStoreBO;
import com.project_250131.menu.bo.MenuBO;
import com.project_250131.menu.domain.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminRestController {

    private final AdminStoreBO adminStoreBO;
    private final AdminMenuBO adminMenuBO;

    @PostMapping("/add-menu")
    public Map<String, Object> addStore(
            @RequestParam("storeId") int storeId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("price") int price

    ) {
        int rowCount = adminMenuBO.addMenu(storeId, file, name, price);
        Map<String, Object> result = new HashMap<>();
        if (rowCount > 0) {
            result.put("code", 200);
            result.put("result", "성공");
        } else {
            result.put("code", 500);
            result.put("error_message", "메뉴를 추가하는데 실패했습니다.");
        }
        return result;
    }

    @DeleteMapping("/delete-store")
    public Map<String, Object> deleteStore(
            @RequestParam("storeId") int storeId
    ) {
        adminStoreBO.deleteStoreEntityByStoreId(storeId);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("result", "성공");
        return result;
    }

    @PostMapping("/add-store")
    public Map<String, Object> addStore(
            @RequestParam("storeName") String storeName,
            @RequestParam("continent") String continent,
            @RequestParam("roadAddress") String roadAddress,
            @RequestParam("streetAddress") String streetAddress,
            @RequestParam("latitude") String latitude,
            @RequestParam("longitude") String longitude,
            @RequestParam(value = "openHourWeekDays", required = false) String openHourWeekDays,
            @RequestParam(value = "openHourWeekends", required = false) String openHourWeekends,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber
    ) {
        boolean isSuccess = adminStoreBO.addStoreEntity(storeName, continent, roadAddress, streetAddress, latitude,
                longitude, openHourWeekDays, openHourWeekends, phoneNumber);

        Map<String, Object> result = new HashMap<>();
        if (isSuccess) {
            result.put("code", 200);
            result.put("result", "성공");
        } else {
            result.put("code", 500);
            result.put("error_message", "추가하는데 실패했습니다.");
        }

        return result;
    }
}
