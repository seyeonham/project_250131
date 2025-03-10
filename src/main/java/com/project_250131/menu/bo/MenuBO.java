package com.project_250131.menu.bo;

import com.project_250131.menu.domain.Menu;
import com.project_250131.menu.mapper.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuBO {

    private final MenuMapper menuMapper;

    public Menu getMenuByStoreId(int storeId) {
        return menuMapper.selectMenuByStoreId(storeId);
    }

    public List<Menu> getMenuListByStoreId(int storeId) {
        return menuMapper.selectMenuListByStoreId(storeId);
    }
}
