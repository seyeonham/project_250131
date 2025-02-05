package com.project_250131.test;

import com.project_250131.user.bo.UserBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class TestController {

    private final UserBO userBO;

    @GetMapping("/test1")
    @ResponseBody
    public String test1() {
        return "<h2>Hello World!</h2>";
    }

    @GetMapping("/test2")
    @ResponseBody
    public Map<String, String> test2() {
        Map<String, String> map = new HashMap<>();
        map.put("과일", "사과");
        map.put("음식", "치킨");
        map.put("커피", "라떼");
        map.put("빵", "식빵");
        return map;
    }

    @GetMapping("/test3")
    public String test3() {
        return "test/test3";
    }

    @GetMapping("/test4")
    @ResponseBody
    public List<Map<String, Object>> test4() {
        return userBO.getUserListTest();
    }
}
