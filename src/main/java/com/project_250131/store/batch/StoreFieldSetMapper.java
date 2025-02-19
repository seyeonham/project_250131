package com.project_250131.store.batch;

import com.project_250131.store.domain.StoreDTO;
import com.project_250131.store.entity.StoreEntity;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Component
public class StoreFieldSetMapper implements FieldSetMapper<StoreDTO> {

    @Override
    public StoreDTO mapFieldSet(FieldSet fieldSet) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setContinent(fieldSet.readString("CTGRY_TWO_NM"));
        storeDTO.setPhoneNumber(fieldSet.readString("TEL_NO"));
        storeDTO.setRoadAddress(fieldSet.readString("RDNMADR_NM"));
        storeDTO.setStreetAddress(fieldSet.readString("LNM_ADDR"));
        storeDTO.setLatitude(fieldSet.readString("LC_LA"));
        storeDTO.setLongitude(fieldSet.readString("LC_LO"));
        storeDTO.setOpenHourWeekdays(fieldSet.readString("WORKDAY_OPER_TIME_DC"));
        storeDTO.setOpenHourWeekends(fieldSet.readString("WKEND_OPER_TIME_DC"));
        return storeDTO;
    }
}
