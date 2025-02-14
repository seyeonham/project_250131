package com.project_250131.store;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/store")
@Controller
public class StoreController {

    @GetMapping("/user-store-list")
    public String userStoreList() {
        return "store/userStoreList";
    }
}
