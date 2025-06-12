package com.fabien.kata.fooBarQuix.service;

import org.springframework.stereotype.Service;

import static com.fabien.kata.fooBarQuix.common.FooBarQuixConstants.*;

@Service
public class FooBarQuixService {

    public String getFooBarQuix (Integer num ) {
        validateNum(num);
        StringBuilder sRet = new StringBuilder();

        if(num % 3 == 0) {
            sRet.append(FOO);
        }
        if(num % 5 == 0){
            sRet.append(BAR);
        }

        //Verification de gauche à droite si num contient un 3,5,7
        String strNum = String.valueOf(num);
        strNum.chars().forEach( c -> {
            if(c == '3' ) sRet.append(FOO);
            if(c == '5') sRet.append(BAR);
            if(c == '7') sRet.append(QUIX);
        });

        // Si sRet est vide alors aucune règle n'est vérifié on retourne le num
        if(sRet.toString().isEmpty()) {
            sRet.append(num);
        }

        return sRet.toString();
    }

    private void validateNum(Integer num){
        if(num == null) throw new IllegalArgumentException("Invalid input : cant be null");
        if(num < 0 || num>100) throw new IllegalArgumentException("Invalid input : Number must be between 0 and 100");
    }
}
