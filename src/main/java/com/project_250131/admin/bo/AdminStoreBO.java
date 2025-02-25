package com.project_250131.admin.bo;

import com.project_250131.store.entity.StoreEntity;
import com.project_250131.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AdminStoreBO {

    private final StoreRepository storeRepository;

    public void deleteStoreEntityByStoreId(int id) {
        storeRepository.deleteById(id);
    }

    public boolean addStoreEntity(String storeName, String continent, String roadAddress, String streetAddress,
                               String latitude, String longitude, String openHourWeekDays,
                               String openHourWeekends, String phoneNumber) {
        StoreEntity store = storeRepository.save(
                StoreEntity.builder()
                        .storeName(storeName)
                        .continent(continent)
                        .roadAddress(roadAddress)
                        .streetAddress(streetAddress)
                        .latitude(latitude)
                        .longitude(longitude)
                        .openHourWeekdays(openHourWeekDays)
                        .openHourWeekends(openHourWeekends)
                        .phoneNumber(phoneNumber)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );

        return store == null ? false : true;
    }
}
