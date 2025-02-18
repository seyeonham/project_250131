package com.project_250131.store.batch;

import com.project_250131.store.entity.StoreEntity;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Component
public class StoreFieldSetMapper implements FieldSetMapper<StoreEntity> {

    @Override
    public StoreEntity mapFieldSet(FieldSet fieldSet) {
        return StoreEntity.builder()
                .continent(fieldSet.readString("CTGRY_TWO_NM"))
                .phoneNumber(fieldSet.readString("TEL_NO"))
                .roadAddress(fieldSet.readString("RDNMADR_NM"))
                .streetAddress(fieldSet.readString("LNM_ADDR"))
                .latitude(fieldSet.readString("LC_LA"))
                .longitude(fieldSet.readString("LC_LO"))
                .openHourWeekdays(fieldSet.readString("WORKDAY_OPER_TIME_DC"))
                .openHourWeekends(fieldSet.readString("WKEND_OPER_TIME_DC"))
                .build();
    }
}
