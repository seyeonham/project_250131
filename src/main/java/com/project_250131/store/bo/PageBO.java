package com.project_250131.store.bo;

import com.project_250131.store.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PageBO {

    public Page storeListPage(Pageable pageable, int page, int storeCount) {
        Page storeListPage = new Page();

        Pageable updatedPageable = PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort());
        storeListPage.setPageable(updatedPageable);

        int currentGroup = (int) Math.ceil((double) page / 10);
        storeListPage.setCurrentGroup(currentGroup);

        int startPage = (currentGroup - 1) * 10 + 1;
        storeListPage.setStartPage(startPage);

        int totalPages = (int) Math.ceil((double) storeCount / pageable.getPageSize());
        storeListPage.setTotalPages(totalPages);

        int endPage = Math.min(startPage + 9, totalPages);
        storeListPage.setEndPage(endPage);

        return storeListPage;
    }
}
