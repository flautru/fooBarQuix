package com.fabien.kata.fooBarQuix.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class fooBarQuixServiceTest extends FooBarQuixService {

    @Test
    public void getFooBarQuix_returnError_NumNull(){
        assertThrows(IllegalArgumentException.class,()->getFooBarQuix(null),"Invalid input : cant be null");
    }

    @Test
    public void getFooBarQuix_returnError_NumNotBetween0And100(){
        assertThrows(IllegalArgumentException.class,()->getFooBarQuix(-1),"Invalid input : Number must be between 0 and 100");
        assertThrows(IllegalArgumentException.class,()->getFooBarQuix(1001),"Invalid input : Number must be between 0 and 100");
        assertThrows(IllegalArgumentException.class,()->getFooBarQuix(101),"Invalid input : Number must be between 0 and 100");
    }

    @Test
    public void getFooBarQuix_return0_whenIsDivisibleBy3AndContain3(){
        assertEquals (FOO+FOO, getFooBarQuix(3));
    }
    @Test
    public void getFooBarQuix_returnFOOFOO_whenIsDivisibleBy3AndContain3(){
        assertEquals (FOO+FOO, getFooBarQuix(3));
    }

    @Test
    public void getFooBarQuix_returnBARBAR_whenIsDivisibleBy5AndContain5(){
        assertEquals (BAR+BAR, getFooBarQuix(5));
    }

    @Test
    public void getFooBarQuix_returnFOO_whenIsDivisibleBy3(){
        assertEquals (FOO, getFooBarQuix(6));
        assertEquals (FOO, getFooBarQuix(9));
    }

    @Test
    public void getFooBarQuix_returnBAR_whenIsDivisibleBy5(){
        assertEquals (BAR, getFooBarQuix(10));
        assertEquals (BAR, getFooBarQuix(100));
    }

    @Test
    public void getFooBarQuix_returnQUIX_whenAndContain7(){
        assertEquals (QUIX, getFooBarQuix(7));
    }

    @Test
    public void getFooBarQuix_returnNum_whenNoRules(){
        assertEquals ("1", getFooBarQuix(1));
        assertEquals ("61", getFooBarQuix(61));
    }

    @Test
    public void getFooBarQuix_returnFOOBAR_whenIsDivisibleBy3AndContain5(){
        assertEquals (FOO+BAR, getFooBarQuix(51));
        // Dans le contexte de cette application 0 est considéré comme divisible par 3 et 5
        assertEquals (FOO+BAR, getFooBarQuix(0));
    }

    @Test
    public void getFooBarQuix_returnBARFOO_whenIsDivisibleBy5AndContain3(){
        assertEquals (BAR+FOO, getFooBarQuix(53));
    }

    @Test
    public void getFooBarQuix_returnFOOFOOFOO_whenIsDivisibleBy3AndContainTwice3(){
        assertEquals (FOO+FOO+FOO, getFooBarQuix(33));
    }

    @Test
    public void getFooBarQuix_returnFOOBARBAR_whenIsDivisibleBy3AndContain5(){
        assertEquals (FOO+FOO+FOO, getFooBarQuix(33));
    }
}