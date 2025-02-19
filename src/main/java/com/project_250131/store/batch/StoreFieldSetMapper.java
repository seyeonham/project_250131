package com.project_250131.store.batch;

import com.project_250131.store.domain.StoreDTO;
import com.project_250131.store.entity.StoreEntity;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StoreFieldSetMapper implements FieldSetMapper<StoreDTO> {

    @Override
    public StoreDTO mapFieldSet(FieldSet fieldSet) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreName(fieldSet.readString("FCLTY_NM"));
        storeDTO.setContinent(fieldSet.readString("CTGRY_TWO_NM"));
        storeDTO.setRoadAddress(fieldSet.readString("RDNMADR_NM"));
        storeDTO.setStreetAddress(fieldSet.readString("LNM_ADDR"));
        storeDTO.setLatitude(fieldSet.readString("LC_LA"));
        storeDTO.setLongitude(fieldSet.readString("LC_LO"));
        storeDTO.setPhoneNumber(fieldSet.readString("TEL_NO"));
        storeDTO.setOpenHourWeekdays(fieldSet.readString("WORKDAY_OPER_TIME_DC"));
        storeDTO.setOpenHourWeekends(fieldSet.readString("WKEND_OPER_TIME_DC"));

        storeDTO.setCreatedAt(LocalDateTime.now());
        storeDTO.setUpdatedAt(LocalDateTime.now());

        return storeDTO;
    }
}
