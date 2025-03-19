package com.jonghwan.typing.shared.constant;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomNumberProvider {
    private final int randomNumber;

    public RandomNumberProvider() {
        this.randomNumber = new Random().nextInt(1000); // 0 ~ 999 사이의 랜덤 값 생성
    }

    public int getRandomNumber() {
        return randomNumber;
    }
}