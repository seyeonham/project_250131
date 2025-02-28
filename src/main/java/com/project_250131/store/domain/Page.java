package com.project_250131.store.domain;

import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class Page {

    private Pageable pageable;
    private int currentGroup;
    private int startPage;
    private int totalPages;
    private int endPage;

}
