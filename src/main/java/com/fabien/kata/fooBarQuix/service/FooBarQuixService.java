package com.fabien.kata.fooBarQuix.service;

import org.springframework.stereotype.Service;

@Service
public class FooBarQuixService {

    protected final String FOO = "FOO";
    protected final String BAR = "BAR";
    protected final String QUIX = "QUIX";

    public String getFooBarQuix (Integer num ) {
        validateNumber(num);

        // Dans ce cas 0 est considéré comme divisible par 3 et 5
        StringBuilder sRet = new StringBuilder();
        if(num % 3 == 0) sRet.append(FOO);
        if(num % 5 == 0) sRet.append(BAR);

        String strNum = String.valueOf(num);
        strNum.chars().forEach( c -> {
            if(c == '3' ) sRet.append(FOO);
            if(c == '5') sRet.append(BAR);
            if(c == '7') sRet.append(QUIX);
                });

        if(sRet.toString().isEmpty()) sRet.append(num);

        return sRet.toString();
    }

    private void validateNumber(Integer num){
        if(num == null) throw new IllegalArgumentException("Invalid input : cant be null");
        if(num < 0 || num>100) throw new IllegalArgumentException("Invalid input : Number must be between 0 and 100");
    }

}
