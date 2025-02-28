package com.project_250131;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class 람다테스트 {

    //@Test
    void 람다테스트1() {
        List<String> list = List.of("apple", "banana", "cherry");
        list.stream()   // 줄줄이 이어짐
                .filter(fruit -> fruit.startsWith("b")) // 필터링
                .forEach(fruit -> log.info(fruit));
    }

    @Test
    void 람다테스트2() {
        List<String> list = List.of("apple", "banana", "cherry");
        list = list.stream()
                .map(fruit -> fruit.toUpperCase()) // stream
                .collect(Collectors.toList()); // stream to List
        log.info(list.toString());
    }
}
