package com.project_250131.store.domain;

import com.project_250131.store.entity.StoreEntity;
import lombok.Data;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class StoreDTO {

    private int id;
    private String storeName;
    private String continent;
    private String roadAddress;
    private String streetAddress;
    private String latitude;
    private String longitude;
    private String phoneNumber;
    private String openHourWeekdays;
    private String openHourWeekends;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static List<String> getFieldNames() {
        Field[] declaredFields = StoreDTO.class.getDeclaredFields();
        List<String> result = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            result.add(declaredField.getName());
        }
        return result;
    }

    public StoreEntity toEntity() {
        return StoreEntity.builder()
                .id(this.id)
                .storeName(this.storeName)
                .continent(this.continent)
                .roadAddress(this.roadAddress)
                .streetAddress(this.streetAddress)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .phoneNumber(this.phoneNumber)
                .openHourWeekdays(this.openHourWeekdays)
                .openHourWeekends(this.openHourWeekends)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
